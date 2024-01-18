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
import io.stardewvalleydesigner.LoggerUtils.logger
import io.stardewvalleydesigner.designformat.PlanFormatConverter
import io.stardewvalleydesigner.engine.EditorEngineData
import io.stardewvalleydesigner.engine.layers.LayeredEntitiesData
import io.stardewvalleydesigner.engine.layers.layeredData
import io.stardewvalleydesigner.engine.layout.LayoutType
import io.stardewvalleydesigner.save.SaveDataParser
import kotlinx.coroutines.*
import kotlinx.coroutines.swing.Swing
import io.stardewvalleydesigner.mainmenu.MainMenuIntent as Intent
import io.stardewvalleydesigner.mainmenu.MainMenuLabel as Label
import io.stardewvalleydesigner.mainmenu.MainMenuState as State


internal typealias MainMenuStore = Store<Intent, State, Label>


class MainMenuStoreFactory(private val storeFactory: StoreFactory) {

    fun create(
        onEditorScreenCall: (EditorEngineData, planPath: String?) -> Unit,
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
        data class ChooseLayoutFromNewPlanMenu(val layout: Wrapper<EditorEngineData>) : Msg

        data object OpenOpenPlanMenu : Msg
        data object ShowLoadingInOpenPlanMenu : Msg
        data class ShowSuccessInOpenPlanMenu(val layout: Wrapper<EditorEngineData>, val planPath: String?) : Msg
        data object ShowErrorInOpenPlanMenu : Msg

        data object OpenSaveLoaderMenu : Msg
        data object ShowLoadingInSaveLoaderMenu : Msg
        data class ShowSuccessInSaveLoaderMenu(val layouts: List<Wrapper<EditorEngineData>>) : Msg
        data object ShowErrorInSaveLoaderMenu : Msg
        data class ChooseLayoutFromSaveLoaderMenu(val layout: Wrapper<EditorEngineData>) : Msg
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {

        override fun invoke() = Unit
    }

    private class ExecutorImpl(
        private val onEditorScreenCall: (EditorEngineData, planPath: String?) -> Unit,
    ) : CoroutineExecutor<Intent, Action, State, Msg, Label>(mainContext = Dispatchers.Swing) {

        override fun executeIntent(intent: Intent) {
            when (intent) {
                Intent.NewPlanMenu.OpenMenu -> dispatch(Msg.OpenNewPlanMenu)

                is Intent.NewPlanMenu.ChooseLayout -> dispatch(Msg.ChooseLayoutFromNewPlanMenu(intent.layout))

                Intent.NewPlanMenu.AcceptChosen -> onEditorScreenCall(
                    (state() as State.NewPlanMenu.Idle).chosenLayout.value,
                    null,
                )

                Intent.NewPlanMenu.Cancel -> dispatch(Msg.ToMainMenu)


                Intent.OpenPlanMenu.OpenMenu -> dispatch(Msg.OpenOpenPlanMenu)

                is Intent.OpenPlanMenu.LoadPlan -> {
                    dispatch(Msg.ShowLoadingInOpenPlanMenu)

                    scope.launch {
                        val parsed = try {
                            withContext(Dispatchers.IO) {
                                val (_, entities, wallpaper, flooring, layout) = PlanFormatConverter.parse(
                                    text = intent.text
                                )
                                return@withContext EditorEngineData(
                                    layoutType = layout,
                                    layeredEntitiesData = entities.layeredData(),
                                    wallpaper = wallpaper,
                                    flooring = flooring
                                ).wrapped()
                            }
                        } catch (e: Exception) {
                            logger.warn { e.stackTraceToString() }
                            null
                        }

                        dispatch(
                            if (parsed != null) {
                                Msg.ShowSuccessInOpenPlanMenu(parsed, intent.absolutePath)
                            } else {
                                Msg.ShowErrorInOpenPlanMenu
                            }
                        )
                    }
                }

                Intent.OpenPlanMenu.Accept -> {
                    val (layout, planPath) = state() as State.OpenPlanMenu.Loaded

                    dispatch(Msg.ToMainMenu)

                    onEditorScreenCall(layout.value, planPath)
                }

                Intent.OpenPlanMenu.Cancel -> dispatch(Msg.ToMainMenu)


                Intent.SaveLoaderMenu.OpenMenu -> dispatch(Msg.OpenSaveLoaderMenu)

                is Intent.SaveLoaderMenu.LoadSave -> {
                    dispatch(Msg.ShowLoadingInSaveLoaderMenu)

                    scope.launch {
                        val parsed = try {
                            withContext(Dispatchers.IO) {
                                SaveDataParser.parse(intent.text).map(EditorEngineData::wrapped)
                            }
                        } catch (e: Exception) {
                            logger.warn { e.stackTraceToString() }
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
                    val layout = (state() as State.SaveLoaderMenu.Loaded).chosenLayout

                    onEditorScreenCall(layout.value, null)
                }

                Intent.SaveLoaderMenu.Cancel -> dispatch(Msg.ToMainMenu)
            }
        }

        override fun executeAction(action: Action) = Unit
    }

    private val reducer = Reducer<State, Msg> { msg ->
        when (msg) {
            Msg.OpenNewPlanMenu -> {
                val availableLayouts = LayoutType.entries.map { layoutType ->
                    EditorEngineData(
                        layoutType = layoutType,
                        layeredEntitiesData = LayeredEntitiesData(),
                        wallpaper = null,
                        flooring = null,
                    )
                }.map(EditorEngineData::wrapped)

                State.NewPlanMenu.Idle(
                    availableLayouts = availableLayouts,
                    chosenLayout = availableLayouts.first()
                )
            }

            is Msg.ChooseLayoutFromNewPlanMenu -> (this as State.NewPlanMenu.Idle).copy(
                chosenLayout = msg.layout
            )


            Msg.OpenOpenPlanMenu -> State.OpenPlanMenu.Empty

            Msg.ShowLoadingInOpenPlanMenu -> State.OpenPlanMenu.Loading

            is Msg.ShowSuccessInOpenPlanMenu -> State.OpenPlanMenu.Loaded(
                layout = msg.layout,
                planPath = msg.planPath
            )

            Msg.ShowErrorInOpenPlanMenu -> State.OpenPlanMenu.Error


            Msg.OpenSaveLoaderMenu -> State.SaveLoaderMenu.Empty

            Msg.ShowLoadingInSaveLoaderMenu -> State.SaveLoaderMenu.Loading

            is Msg.ShowSuccessInSaveLoaderMenu -> State.SaveLoaderMenu.Loaded(
                availableLayouts = msg.layouts,
                chosenLayout = msg.layouts.first()
            )

            Msg.ShowErrorInSaveLoaderMenu -> State.SaveLoaderMenu.Error

            is Msg.ChooseLayoutFromSaveLoaderMenu -> (this as State.SaveLoaderMenu.Loaded).copy(
                chosenLayout = msg.layout
            )


            Msg.ToMainMenu -> State.Idle
        }
    }
}
