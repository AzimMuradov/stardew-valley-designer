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

package io.stardewvalleydesigner.component.dialog.opensvsave

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.stardewvalleydesigner.LoggerUtils.logger
import io.stardewvalleydesigner.component.dialog.opensvsave.OpenSvSaveState
import io.stardewvalleydesigner.designformat.Wrapper
import io.stardewvalleydesigner.designformat.models.Design
import io.stardewvalleydesigner.designformat.wrapped
import io.stardewvalleydesigner.kmplib.dispatcher.PlatformDispatcher
import io.stardewvalleydesigner.save.SaveDataParser
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import io.stardewvalleydesigner.component.dialog.opensvsave.OpenSvSaveIntent as Intent
import io.stardewvalleydesigner.component.dialog.opensvsave.OpenSvSaveState as State


internal typealias OpenSvSaveStore = Store<Intent, State, Nothing>


class OpenSvSaveStoreFactory(private val storeFactory: StoreFactory) {

    fun create(
        onDesignChosen: (Design, designPath: String?) -> Unit,
    ): OpenSvSaveStore = object : OpenSvSaveStore by storeFactory.create(
        name = "OpenSvSaveStore",
        initialState = OpenSvSaveState.NotOpened,
        executorFactory = { ExecutorImpl(onDesignChosen) },
        reducer = reducer
    ) {}


    private sealed interface Msg {
        data object OpenMenu : Msg
        data object ShowLoading : Msg
        data class ShowSuccess(val availableDesigns: List<Wrapper<Design>>) : Msg
        data object ShowError : Msg
        data class ChooseLayout(val design: Wrapper<Design>) : Msg
        data object CloseMenu : Msg
    }

    private class ExecutorImpl(
        private val onDesignChosen: (Design, designPath: String?) -> Unit,
    ) : CoroutineExecutor<Intent, Nothing, State, Msg, Nothing>(
        mainContext = PlatformDispatcher.Main,
    ) {

        override fun executeIntent(intent: Intent) {
            when (intent) {
                Intent.OpenMenu -> dispatch(Msg.OpenMenu)

                is Intent.LoadSave -> {
                    dispatch(Msg.ShowLoading)

                    scope.launch {
                        val parsed = try {
                            withContext(PlatformDispatcher.IO) {
                                SaveDataParser.parse(intent.text).map(Design::wrapped)
                            }
                        } catch (e: Exception) {
                            logger.warn { e.stackTraceToString() }
                            null
                        }

                        dispatch(
                            if (parsed != null) {
                                Msg.ShowSuccess(parsed)
                            } else {
                                Msg.ShowError
                            }
                        )
                    }
                }

                is Intent.ChooseDesign -> dispatch(Msg.ChooseLayout(intent.design))

                Intent.AcceptChosen -> {
                    val layout = (state() as State.Loaded).chosenLayout

                    onDesignChosen(layout.value, null)
                }

                Intent.CloseMenu -> dispatch(Msg.CloseMenu)
            }
        }

        override fun executeAction(action: Nothing) = Unit
    }

    private val reducer = Reducer<State, Msg> { msg ->
        when (msg) {
            Msg.OpenMenu -> State.Empty

            Msg.ShowLoading -> State.Loading

            is Msg.ShowSuccess -> State.Loaded(
                availableLayouts = msg.availableDesigns,
                chosenLayout = msg.availableDesigns.first()
            )

            Msg.ShowError -> State.Error

            is Msg.ChooseLayout -> (this as State.Loaded).copy(
                chosenLayout = msg.design
            )

            Msg.CloseMenu -> State.NotOpened
        }
    }
}
