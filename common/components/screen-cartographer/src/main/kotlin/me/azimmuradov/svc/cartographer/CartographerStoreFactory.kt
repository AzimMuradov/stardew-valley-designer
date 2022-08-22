/*
 * Copyright 2021-2022 Azim Muradov
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

package me.azimmuradov.svc.cartographer

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import me.azimmuradov.svc.cartographer.modules.map.MapState
import me.azimmuradov.svc.cartographer.modules.map.SvcEngineImpl
import me.azimmuradov.svc.cartographer.modules.history.*
import me.azimmuradov.svc.cartographer.modules.options.OptionsState
import me.azimmuradov.svc.cartographer.modules.options.reduce
import me.azimmuradov.svc.cartographer.modules.palette.PaletteState
import me.azimmuradov.svc.cartographer.modules.palette.reduce
import me.azimmuradov.svc.cartographer.modules.toolkit.*
import me.azimmuradov.svc.cartographer.modules.vislayers.VisLayersState
import me.azimmuradov.svc.cartographer.modules.vislayers.reduce
import me.azimmuradov.svc.cartographer.utils.UNREACHABLE
import me.azimmuradov.svc.engine.layout.Layout
import me.azimmuradov.svc.engine.svcEngineOf
import me.azimmuradov.svc.cartographer.CartographerIntent as Intent
import me.azimmuradov.svc.cartographer.CartographerLabel as Label
import me.azimmuradov.svc.cartographer.CartographerState as State


// private val logger = KotlinLogging.logger(name = "SVC-LOGGER")

internal typealias CartographerStore = Store<Intent, State, Label>


// TODO : SVC behaviour

class CartographerStoreFactory(private val storeFactory: StoreFactory) {

    fun create(layout: Layout): CartographerStore = object : CartographerStore by storeFactory.create(
        name = "CartographerStore",
        initialState = State.default(layout),
        bootstrapper = BootstrapperImpl(),
        executorFactory = { ExecutorImpl(layout) },
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
        override fun invoke() {}
    }

    private class ExecutorImpl(layout: Layout) : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        private val history = HistoryManagerImpl(MapState.default(layout))

        private val engine = SvcEngineImpl(svcEngineOf(layout))

        private val toolkit = ToolkitImpl(engine, layout)

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

                    is Intent.Options -> dispatch(Msg.UpdateOptions(getState().options.reduce(intent)))

                    else -> UNREACHABLE()
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {}
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