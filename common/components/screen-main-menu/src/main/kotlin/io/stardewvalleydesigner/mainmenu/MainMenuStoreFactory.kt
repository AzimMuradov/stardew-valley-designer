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

package io.stardewvalleydesigner.mainmenu

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.stardewvalleydesigner.engine.EditorEngine
import io.stardewvalleydesigner.engine.editorEngineOf
import io.stardewvalleydesigner.engine.layout.LayoutType
import io.stardewvalleydesigner.engine.layout.LayoutsProvider.layoutOf
import io.stardewvalleydesigner.save.SaveDataSerializers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import io.stardewvalleydesigner.mainmenu.MainMenuIntent as Intent
import io.stardewvalleydesigner.mainmenu.MainMenuLabel as Label
import io.stardewvalleydesigner.mainmenu.MainMenuState as State


internal typealias MainMenuStore = Store<Intent, State, Label>


class MainMenuStoreFactory(private val storeFactory: StoreFactory) {

    fun create(
        onEditorScreenCall: (EditorEngine) -> Unit,
    ): MainMenuStore = object : MainMenuStore by storeFactory.create(
        name = "MainMenuStore",
        initialState = State.default(),
        bootstrapper = BootstrapperImpl(),
        executorFactory = { ExecutorImpl(onEditorScreenCall) },
        reducer = reducer
    ) {}


    private sealed interface Action

    private sealed interface Msg {
        data object ToMainMenu : Msg
        data object OpenNewPlanMenu : Msg
        data class ChooseLayoutFromNewPlanMenu(val layout: EditorEngine) : Msg
        data object OpenSaveLoaderMenu : Msg
        data object ShowLoadingInSaveLoaderMenu : Msg
        data class ShowSuccessInSaveLoaderMenu(val layouts: List<EditorEngine>) : Msg
        data object ShowErrorInSaveLoaderMenu : Msg
        data class ChooseLayoutFromSaveLoaderMenu(val layout: EditorEngine) : Msg
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {

        override fun invoke() = Unit
    }

    private class ExecutorImpl(
        private val onEditorScreenCall: (EditorEngine) -> Unit,
    ) : CoroutineExecutor<Intent, Action, State, Msg, Label>(mainContext = Dispatchers.Default) {

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.NewPlanMenu.OpenMenu -> dispatch(Msg.OpenNewPlanMenu)

                is Intent.NewPlanMenu.ChooseLayout -> dispatch(Msg.ChooseLayoutFromNewPlanMenu(intent.layout))

                Intent.NewPlanMenu.AcceptChosen -> onEditorScreenCall(
                    (getState() as State.NewPlanMenu.Idle).chosenLayout!!
                )

                Intent.NewPlanMenu.Cancel -> dispatch(Msg.ToMainMenu)


                Intent.SaveLoaderMenu.OpenMenu -> dispatch(Msg.OpenSaveLoaderMenu)

                is Intent.SaveLoaderMenu.LoadSave -> {
                    dispatch(Msg.ShowLoadingInSaveLoaderMenu)

                    scope.launch {
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

                    onEditorScreenCall(layout)
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
                    editorEngineOf(layoutOf(LayoutType.Shed)),
                    editorEngineOf(layoutOf(LayoutType.BigShed)),
                    editorEngineOf(layoutOf(LayoutType.StandardFarm)),
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
