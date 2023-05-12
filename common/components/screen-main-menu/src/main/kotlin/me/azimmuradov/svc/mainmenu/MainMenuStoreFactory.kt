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
import kotlinx.coroutines.*
import me.azimmuradov.svc.engine.SvcEngine
import me.azimmuradov.svc.engine.layout.LayoutType
import me.azimmuradov.svc.engine.layout.LayoutsProvider.layoutOf
import me.azimmuradov.svc.engine.svcEngineOf
import me.azimmuradov.svc.save.SaveDataSerializers
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
        data object ToMainMenu : Msg
        data object OpenNewPlanMenu : Msg
        data class ChooseLayoutFromNewPlanMenu(val layout: SvcEngine) : Msg
        data object OpenSaveLoaderMenu : Msg
        data object ShowLoadingInSaveLoaderMenu : Msg
        data class ShowSuccessInSaveLoaderMenu(val layouts: List<SvcEngine>) : Msg
        data object ShowErrorInSaveLoaderMenu : Msg
        data class ChooseLayoutFromSaveLoaderMenu(val layout: SvcEngine) : Msg
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {

        override fun invoke() = Unit
    }

    private class ExecutorImpl(
        private val onCartographerScreenCall: (SvcEngine) -> Unit,
    ) : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.NewPlanMenu.OpenMenu -> dispatch(Msg.OpenNewPlanMenu)

                is Intent.NewPlanMenu.ChooseLayout -> dispatch(Msg.ChooseLayoutFromNewPlanMenu(intent.layout))

                Intent.NewPlanMenu.AcceptChosen -> onCartographerScreenCall(
                    (getState() as State.NewPlanMenu.Idle).chosenLayout!!
                )

                Intent.NewPlanMenu.Cancel -> dispatch(Msg.ToMainMenu)


                Intent.SaveLoaderMenu.OpenMenu -> dispatch(Msg.OpenSaveLoaderMenu)

                is Intent.SaveLoaderMenu.LoadSave -> {
                    dispatch(Msg.ShowLoadingInSaveLoaderMenu)

                    CoroutineScope(Dispatchers.IO).launch {
                        val parsed = try {
                            SaveDataSerializers.parse(intent.path.trim())
                        } catch (e: Exception) {
                            null
                        }

                        dispatch(
                            if (parsed != null) {
                                Msg.ShowSuccessInSaveLoaderMenu(parsed)
                            } else {
                                Msg.ShowErrorInSaveLoaderMenu
                            }
                        )
                    }
                }

                is Intent.SaveLoaderMenu.ChooseLayout -> dispatch(Msg.ChooseLayoutFromSaveLoaderMenu(intent.layout))

                Intent.SaveLoaderMenu.AcceptChosen -> {
                    val layout = (getState() as State.SaveLoaderMenu.Idle).chosenLayout!!

                    dispatch(Msg.ToMainMenu)

                    onCartographerScreenCall(layout)
                }

                Intent.SaveLoaderMenu.Cancel -> dispatch(Msg.ToMainMenu)
            }
        }

        override fun executeAction(action: Action, getState: () -> State) = Unit
    }

    private val reducer = Reducer<State, Msg> { msg ->
        when (msg) {
            Msg.OpenNewPlanMenu -> State.NewPlanMenu.Idle(
                availableLayouts = listOf(
                    svcEngineOf(layoutOf(LayoutType.Shed)),
                    svcEngineOf(layoutOf(LayoutType.BigShed)),
                ),
                chosenLayout = null
            )

            is Msg.ChooseLayoutFromNewPlanMenu -> (this as State.NewPlanMenu.Idle).copy(
                chosenLayout = msg.layout
            )


            Msg.OpenSaveLoaderMenu -> State.SaveLoaderMenu.Idle(
                availableLayouts = null,
                chosenLayout = null
            )

            Msg.ShowLoadingInSaveLoaderMenu -> State.SaveLoaderMenu.Loading

            is Msg.ShowSuccessInSaveLoaderMenu -> State.SaveLoaderMenu.Idle(
                availableLayouts = msg.layouts,
                chosenLayout = null
            )

            Msg.ShowErrorInSaveLoaderMenu -> State.SaveLoaderMenu.Error

            is Msg.ChooseLayoutFromSaveLoaderMenu -> (this as State.SaveLoaderMenu.Idle).copy(
                chosenLayout = msg.layout
            )


            Msg.ToMainMenu -> State.Idle
        }
    }
}
