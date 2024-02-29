/*
 * Copyright 2021-2024 Azim Muradov
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

package io.stardewvalleydesigner.component.editor

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.stardewvalleydesigner.component.editor.EditorState
import io.stardewvalleydesigner.component.editor.modules.history.*
import io.stardewvalleydesigner.component.editor.modules.map.*
import io.stardewvalleydesigner.component.editor.modules.options.reduce
import io.stardewvalleydesigner.component.editor.modules.palette.reduce
import io.stardewvalleydesigner.component.editor.modules.toolkit.*
import io.stardewvalleydesigner.component.editor.modules.vislayers.VisLayersState
import io.stardewvalleydesigner.component.editor.modules.vislayers.reduce
import io.stardewvalleydesigner.designformat.models.Options
import io.stardewvalleydesigner.designformat.models.Palette
import io.stardewvalleydesigner.kmplib.dispatcher.PlatformDispatcher
import io.stardewvalleydesigner.metadata.Season
import io.stardewvalleydesigner.component.editor.EditorIntent as Intent
import io.stardewvalleydesigner.component.editor.EditorLabel as Label
import io.stardewvalleydesigner.component.editor.EditorState as State


internal typealias EditorStore = Store<Intent, State, Label>


// TODO : Editor behaviour

class EditorStoreFactory(private val storeFactory: StoreFactory) {

    fun create(initialState: EditorState): EditorStore = object : EditorStore by storeFactory.create(
        name = "EditorStore",
        initialState = initialState,
        bootstrapper = BootstrapperImpl(),
        executorFactory = { ExecutorImpl(initialState) },
        reducer = reducer
    ) {}


    private sealed interface Action

    private sealed interface Msg {
        data class UpdateHistory(val state: HistoryState) : Msg
        data class UpdateMap(val state: MapState) : Msg
        data class UpdatePlayerName(val state: String) : Msg
        data class UpdateFarmName(val state: String) : Msg
        data class UpdateSeason(val state: Season) : Msg
        data class UpdateToolkit(val state: ToolkitState) : Msg
        data class UpdatePalette(val state: Palette) : Msg
        data class UpdateVisLayers(val state: VisLayersState) : Msg
        data class UpdateOptions(val state: Options) : Msg
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {

        override fun invoke() = Unit
    }

    private class ExecutorImpl(
        initialState: EditorState,
    ) : CoroutineExecutor<Intent, Action, State, Msg, Label>(
        mainContext = PlatformDispatcher.Main,
    ) {

        private val history = HistoryManagerImpl(
            initialMapState = initialState.map,
        )

        private val engine = EditorEngineImpl(
            engine = initialState.map.generateEngine(),
        )

        private val toolkit = ToolkitImpl(
            engine = engine,
            initialState = initialState.toolkit
        )


        override fun executeIntent(intent: Intent) {
            when (intent) {
                is Intent.History -> {
                    var isSnapshotChanged = false
                    when (intent) {
                        Intent.History.GoBack -> {
                            if (history.canGoBack) isSnapshotChanged = true
                            history.goBackIfCan()
                        }

                        Intent.History.GoForward -> {
                            if (history.canGoForward) isSnapshotChanged = true
                            history.goForwardIfCan()
                        }
                    }
                    if (isSnapshotChanged) {
                        val snapshot = history.currentMapState
                        engine.pushState(snapshot)
                        dispatch(Msg.UpdateMap(snapshot))
                        dispatch(Msg.UpdateHistory(history.state))
                    }
                }

                is Intent.Engine -> {
                    val actionReturn = state().let { (_, map, _, _, _, _, palette, visLayers) ->
                        toolkit.runAction(
                            action = intent,
                            currentEntity = palette.inUse,
                            selectedEntities = map.selectedEntities,
                            visLayers = visLayers.visibleLayers,
                        )
                    }
                    if (actionReturn != null) {
                        dispatch(Msg.UpdateToolkit(actionReturn.toolkit))
                        dispatch(Msg.UpdatePalette(state().palette.copy(inUse = actionReturn.currentEntity)))
                        dispatch(Msg.UpdateMap(engine.pullState(actionReturn.selectedEntities)))

                        if (intent == Intent.Engine.End) {
                            history.register(state().map)
                            dispatch(Msg.UpdateHistory(history.state))
                        }
                    }
                }

                is Intent.PlayerName.Change -> dispatch(Msg.UpdatePlayerName(intent.name))

                is Intent.FarmName.Change -> dispatch(Msg.UpdateFarmName(intent.name))

                is Intent.SeasonMenu.Change -> dispatch(Msg.UpdateSeason(intent.season))

                is Intent.Toolkit -> {
                    dispatch(Msg.UpdateToolkit(state().toolkit.reduce(intent)))
                    state().toolkit.run { toolkit.setTool(tool, shape) }
                }

                is Intent.Palette -> dispatch(Msg.UpdatePalette(state().palette.reduce(intent)))

                is Intent.VisLayers.ChangeVisibility -> dispatch(Msg.UpdateVisLayers(state().visLayers.reduce(intent)))

                is Intent.WallpaperAndFlooring -> {
                    dispatch(
                        when (intent) {
                            is Intent.WallpaperAndFlooring.ChooseWallpaper -> {
                                engine.wallpaper = intent.wallpaper
                                Msg.UpdateMap(state().map.copy(wallpaper = intent.wallpaper))
                            }

                            is Intent.WallpaperAndFlooring.ChooseFlooring -> {
                                engine.flooring = intent.flooring
                                Msg.UpdateMap(state().map.copy(flooring = intent.flooring))
                            }
                        }
                    )

                    history.register(state().map)
                    dispatch(Msg.UpdateHistory(history.state))
                }

                is Intent.Options.Toggle -> dispatch(Msg.UpdateOptions(state().options.reduce(intent)))
            }
        }

        override fun executeAction(action: Action) = Unit
    }

    private val reducer = Reducer<State, Msg> { msg ->
        when (msg) {
            is Msg.UpdateHistory -> copy(history = msg.state)
            is Msg.UpdateMap -> copy(map = msg.state)
            is Msg.UpdatePlayerName -> copy(playerName = msg.state)
            is Msg.UpdateFarmName -> copy(farmName = msg.state)
            is Msg.UpdateSeason -> copy(season = msg.state)
            is Msg.UpdateToolkit -> copy(toolkit = msg.state)
            is Msg.UpdatePalette -> copy(palette = msg.state)
            is Msg.UpdateVisLayers -> copy(visLayers = msg.state)
            is Msg.UpdateOptions -> copy(options = msg.state)
        }
    }
}
