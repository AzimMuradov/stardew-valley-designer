/*
 * Copyright 2021-2023 Azim Muradov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.stardewvalleydesigner.editor

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.stardewvalleydesigner.editor.modules.history.*
import io.stardewvalleydesigner.editor.modules.map.EditorEngineImpl
import io.stardewvalleydesigner.editor.modules.map.MapState
import io.stardewvalleydesigner.editor.modules.options.OptionsState
import io.stardewvalleydesigner.editor.modules.options.reduce
import io.stardewvalleydesigner.editor.modules.palette.PaletteState
import io.stardewvalleydesigner.editor.modules.palette.reduce
import io.stardewvalleydesigner.editor.modules.toolkit.*
import io.stardewvalleydesigner.editor.modules.vislayers.VisLayersState
import io.stardewvalleydesigner.editor.modules.vislayers.reduce
import io.stardewvalleydesigner.editor.utils.UNREACHABLE
import io.stardewvalleydesigner.engine.EditorEngine
import io.stardewvalleydesigner.editor.EditorIntent as Intent
import io.stardewvalleydesigner.editor.EditorLabel as Label
import io.stardewvalleydesigner.editor.EditorState as State


internal typealias EditorStore = Store<Intent, State, Label>


// TODO : Editor behaviour

class EditorStoreFactory(private val storeFactory: StoreFactory) {

    fun create(engine: EditorEngine): EditorStore = object : EditorStore by storeFactory.create(
        name = "EditorStore",
        initialState = State.from(engine),
        bootstrapper = BootstrapperImpl(),
        executorFactory = { ExecutorImpl(engine) },
        reducer = reducer
    ) {}


    private sealed interface Action

    private sealed interface Msg {
        data class UpdateHistory(val state: HistoryState) : Msg
        data class UpdateMap(val state: MapState) : Msg
        data class UpdateToolkit(val state: ToolkitState) : Msg
        data class UpdatePalette(val state: PaletteState) : Msg
        data class UpdateVisLayers(val state: VisLayersState) : Msg
        data class UpdateOptions(val state: OptionsState) : Msg
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {

        override fun invoke() = Unit
    }

    private class ExecutorImpl(engine: EditorEngine) : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        private val history = HistoryManagerImpl(MapState.from(engine))

        private val engine = EditorEngineImpl(engine)

        private val toolkit = ToolkitImpl(engine, engine.layers.layout)

        @Suppress("LongMethod", "CyclomaticComplexMethod", "NestedBlockDepth")
        override fun executeIntent(intent: Intent, getState: () -> State) {
            if (intent is Intent.History) {
                when (intent) {
                    Intent.History.GoBack -> history.goBackIfCan()
                    Intent.History.GoForward -> history.goForwardIfCan()
                }
                val snapshot = history.currentMapState
                engine.pushState(snapshot)
                dispatch(Msg.UpdateMap(snapshot))
                dispatch(Msg.UpdateHistory(history.state))
            } else {
                when (intent) {
                    is Intent.Engine -> {
                        when (intent) {
                            is Intent.Engine.Start -> {
                                val actionReturn = getState().let { (_, snapshot, _, palette, visLayers) ->
                                    toolkit.start(
                                        coordinate = intent.coordinate,
                                        currentEntity = palette.inUse,
                                        selectedEntities = snapshot.selectedEntities,
                                        visLayers = visLayers.visibleLayers,
                                    )
                                }
                                actionReturn?.toolkit?.let { dispatch(Msg.UpdateToolkit(it)) }
                                dispatch(Msg.UpdateMap(engine.pullState(actionReturn?.selectedEntities)))
                            }

                            is Intent.Engine.Continue -> {
                                val actionReturn = getState().let { (_, snapshot, _, palette, visLayers) ->
                                    toolkit.keep(
                                        coordinate = intent.coordinate,
                                        currentEntity = palette.inUse,
                                        selectedEntities = snapshot.selectedEntities,
                                        visLayers = visLayers.visibleLayers,
                                    )
                                }
                                actionReturn?.toolkit?.let { dispatch(Msg.UpdateToolkit(it)) }
                                dispatch(Msg.UpdateMap(engine.pullState(actionReturn?.selectedEntities)))
                            }

                            Intent.Engine.End -> {
                                val actionReturn = getState().let { (_, snapshot, _, palette, visLayers) ->
                                    toolkit.end(
                                        currentEntity = palette.inUse,
                                        selectedEntities = snapshot.selectedEntities,
                                        visLayers = visLayers.visibleLayers,
                                    )
                                }
                                actionReturn?.toolkit?.let { dispatch(Msg.UpdateToolkit(it)) }
                                dispatch(Msg.UpdateMap(engine.pullState(actionReturn?.selectedEntities)))
                                history.register(getState().map)
                                dispatch(Msg.UpdateHistory(history.state))
                            }
                        }
                    }

                    is Intent.Toolkit -> {
                        dispatch(Msg.UpdateToolkit(getState().toolkit.reduce(intent)))
                        getState().toolkit.run { toolkit.setTool(tool, shape) }
                    }

                    is Intent.Palette -> dispatch(Msg.UpdatePalette(getState().palette.reduce(intent)))

                    is Intent.VisLayers -> dispatch(Msg.UpdateVisLayers(getState().visLayers.reduce(intent)))

                    is Intent.WallpaperAndFlooring -> {
                        dispatch(
                            when (intent) {
                                is Intent.WallpaperAndFlooring.ChooseWallpaper -> {
                                    engine.wallpaper = intent.wallpaper
                                    Msg.UpdateMap(getState().map.copy(wallpaper = intent.wallpaper))
                                }

                                is Intent.WallpaperAndFlooring.ChooseFlooring -> {
                                    engine.flooring = intent.flooring
                                    Msg.UpdateMap(getState().map.copy(flooring = intent.flooring))
                                }
                            }
                        )

                        history.register(getState().map)
                        dispatch(Msg.UpdateHistory(history.state))
                    }

                    is Intent.Options -> dispatch(Msg.UpdateOptions(getState().options.reduce(intent)))

                    else -> UNREACHABLE()
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) = Unit
    }

    private val reducer = Reducer<State, Msg> { msg ->
        when (msg) {
            is Msg.UpdateHistory -> copy(history = msg.state)
            is Msg.UpdateMap -> copy(map = msg.state)
            is Msg.UpdateToolkit -> copy(toolkit = msg.state)
            is Msg.UpdatePalette -> copy(palette = msg.state)
            is Msg.UpdateVisLayers -> copy(visLayers = msg.state)
            is Msg.UpdateOptions -> copy(options = msg.state)
        }
    }
}