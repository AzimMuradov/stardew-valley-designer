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

package me.azimmuradov.svc.mainmenu

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import me.azimmuradov.svc.engine.SvcEngine
import me.azimmuradov.svc.engine.layout.LayoutType
import me.azimmuradov.svc.engine.layout.LayoutsProvider.layoutOf
import me.azimmuradov.svc.engine.svcEngineOf
import me.azimmuradov.svc.mainmenu.MainMenuIntent as Intent
import me.azimmuradov.svc.mainmenu.MainMenuLabel as Label
import me.azimmuradov.svc.mainmenu.MainMenuState as State


internal typealias MainMenuStore = Store<Intent, State, Label>


// TODO : SVC behaviour

class MainMenuStoreFactory(private val storeFactory: StoreFactory) {

    fun create(
        onCartographerScreenCall: (SvcEngine) -> Unit,
    ): MainMenuStore = object : MainMenuStore by storeFactory.create(
        name = "MainMenuStore",
        initialState = State.default(),
        bootstrapper = BootstrapperImpl(),
        executorFactory = { ExecutorImpl(onCartographerScreenCall) },
        reducer = reducer
    ) {}


    private sealed interface Action

    private sealed interface Msg {
        data object OpenMenu : Msg
        data class ChooseLayout(val layout: SvcEngine) : Msg
        data class UpdateState(val state: State) : Msg
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {

        override fun invoke() = Unit
    }

    private class ExecutorImpl(
        private val onCartographerScreenCall: (SvcEngine) -> Unit,
    ) : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.NewPlan.OpenMenu -> dispatch(Msg.OpenMenu)

                is Intent.NewPlan.ChooseLayout -> dispatch(Msg.ChooseLayout(intent.layout))

                Intent.NewPlan.Cancel -> dispatch(Msg.UpdateState(State.Idle))

                Intent.NewPlan.CreateNewPlan -> onCartographerScreenCall(
                    (getState() as State.NewPlanMenu.Idle).chosenLayout!!
                )

                is Intent.SaveData.Load -> publish(Label.LoadSaveData(intent.path))
            }
        }

        override fun executeAction(action: Action, getState: () -> State) = Unit
    }

    private val reducer = Reducer<State, Msg> { msg ->
        when (msg) {
            Msg.OpenMenu -> State.NewPlanMenu.Idle(
                availableLayouts = listOf(svcEngineOf(layoutOf(LayoutType.BigShed))),
                chosenLayout = null
            )

            is Msg.ChooseLayout -> if (this is State.NewPlanMenu.Idle) {
                copy(chosenLayout = msg.layout)
            } else {
                State.NewPlanMenu.Idle(
                    availableLayouts = listOf(svcEngineOf(layoutOf(LayoutType.BigShed))),
                    chosenLayout = msg.layout
                )
            }

            is Msg.UpdateState -> msg.state
        }
    }
}
