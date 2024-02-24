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

package io.stardewvalleydesigner.component.mainmenu

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.stardewvalleydesigner.LoggerUtils.logger
import io.stardewvalleydesigner.designformat.DesignFormatConverter
import io.stardewvalleydesigner.designformat.models.*
import io.stardewvalleydesigner.engine.layers.LayeredEntitiesData
import io.stardewvalleydesigner.engine.layout.LayoutType
import io.stardewvalleydesigner.save.SaveDataParser
import kotlinx.coroutines.*
import kotlinx.coroutines.swing.Swing
import java.nio.file.Files
import kotlin.io.path.Path
import io.stardewvalleydesigner.component.mainmenu.MainMenuIntent as Intent
import io.stardewvalleydesigner.component.mainmenu.MainMenuLabel as Label
import io.stardewvalleydesigner.component.mainmenu.MainMenuState as State


internal typealias MainMenuStore = Store<Intent, State, Label>


class MainMenuStoreFactory(private val storeFactory: StoreFactory) {

    fun create(
        onEditorScreenCall: (Design, designPath: String?) -> Unit,
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
        data object OpenNewDesignMenu : Msg
        data class ChooseLayoutFromNewDesignMenu(val layout: Wrapper<Design>) : Msg

        data object OpenOpenDesignMenu : Msg
        data object ShowLoadingInOpenDesignMenu : Msg
        data class ShowSuccessInOpenDesignMenu(val layout: Wrapper<Design>, val absolutePath: String?) : Msg
        data object ShowErrorInOpenDesignMenu : Msg

        data object OpenSaveLoaderMenu : Msg
        data object ShowLoadingInSaveLoaderMenu : Msg
        data class ShowSuccessInSaveLoaderMenu(val layouts: List<Wrapper<Design>>) : Msg
        data object ShowErrorInSaveLoaderMenu : Msg
        data class ChooseLayoutFromSaveLoaderMenu(val layout: Wrapper<Design>) : Msg
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {

        override fun invoke() = Unit
    }

    private class ExecutorImpl(
        private val onEditorScreenCall: (Design, designPath: String?) -> Unit,
    ) : CoroutineExecutor<Intent, Action, State, Msg, Label>(mainContext = Dispatchers.Swing) {

        override fun executeIntent(intent: Intent) {
            when (intent) {
                Intent.NewDesignMenu.OpenMenu -> dispatch(Msg.OpenNewDesignMenu)

                is Intent.NewDesignMenu.ChooseLayout -> dispatch(Msg.ChooseLayoutFromNewDesignMenu(intent.layout))

                Intent.NewDesignMenu.AcceptChosen -> onEditorScreenCall(
                    (state() as State.NewDesignMenu.Idle).chosenLayout.value,
                    null,
                )

                Intent.NewDesignMenu.Cancel -> dispatch(Msg.ToMainMenu)


                Intent.OpenDesignMenu.OpenMenu -> dispatch(Msg.OpenOpenDesignMenu)

                is Intent.OpenDesignMenu.LoadDesign -> {
                    dispatch(Msg.ShowLoadingInOpenDesignMenu)

                    scope.launch {
                        val parsed = try {
                            withContext(Dispatchers.IO) {
                                DesignFormatConverter.parse(text = intent.text).wrapped()
                            }
                        } catch (e: Exception) {
                            logger.warn { e.stackTraceToString() }
                            null
                        }

                        dispatch(
                            if (parsed != null) {
                                Msg.ShowSuccessInOpenDesignMenu(parsed, intent.absolutePath)
                            } else {
                                Msg.ShowErrorInOpenDesignMenu
                            }
                        )
                    }
                }

                Intent.OpenDesignMenu.Accept -> {
                    val (layout, absolutePath) = state() as State.OpenDesignMenu.Loaded

                    dispatch(Msg.ToMainMenu)

                    onEditorScreenCall(layout.value, absolutePath)
                }

                Intent.OpenDesignMenu.Cancel -> dispatch(Msg.ToMainMenu)


                Intent.SaveLoaderMenu.OpenMenu -> dispatch(Msg.OpenSaveLoaderMenu)

                is Intent.SaveLoaderMenu.LoadSave -> {
                    dispatch(Msg.ShowLoadingInSaveLoaderMenu)

                    scope.launch {
                        val parsed = try {
                            withContext(Dispatchers.IO) {
                                SaveDataParser.parse(intent.text).map(Design::wrapped)
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


                is Intent.UserDesignsMenu.OpenDesign -> {
                    onEditorScreenCall(intent.design, intent.designPath)
                }

                is Intent.UserDesignsMenu.DeleteDesign -> {
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            Files.deleteIfExists(Path(intent.designPath))
                        }
                    }
                }
            }
        }

        override fun executeAction(action: Action) = Unit
    }

    private val reducer = Reducer<State, Msg> { msg ->
        when (msg) {
            Msg.OpenNewDesignMenu -> {
                val availableLayouts = LayoutType.entries.map { layoutType ->
                    Design(
                        playerName = "",
                        farmName = "",
                        layout = layoutType,
                        entities = LayeredEntitiesData(),
                        wallpaper = null,
                        flooring = null,
                        palette = Palette.default(),
                        options = Options.default(),
                    )
                }.map(Design::wrapped)

                State.NewDesignMenu.Idle(
                    availableLayouts = availableLayouts,
                    chosenLayout = availableLayouts.first()
                )
            }

            is Msg.ChooseLayoutFromNewDesignMenu -> (this as State.NewDesignMenu.Idle).copy(
                chosenLayout = msg.layout
            )


            Msg.OpenOpenDesignMenu -> State.OpenDesignMenu.Empty

            Msg.ShowLoadingInOpenDesignMenu -> State.OpenDesignMenu.Loading

            is Msg.ShowSuccessInOpenDesignMenu -> State.OpenDesignMenu.Loaded(
                layout = msg.layout,
                absolutePath = msg.absolutePath
            )

            Msg.ShowErrorInOpenDesignMenu -> State.OpenDesignMenu.Error


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
