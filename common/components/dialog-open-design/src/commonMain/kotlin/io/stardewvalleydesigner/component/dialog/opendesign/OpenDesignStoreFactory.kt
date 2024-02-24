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

package io.stardewvalleydesigner.component.dialog.opendesign

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.stardewvalleydesigner.LoggerUtils.logger
import io.stardewvalleydesigner.component.dialog.opendesign.OpenDesignState
import io.stardewvalleydesigner.designformat.*
import io.stardewvalleydesigner.designformat.models.Design
import io.stardewvalleydesigner.kmplib.dispatcher.PlatformDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import io.stardewvalleydesigner.component.dialog.opendesign.OpenDesignIntent as Intent
import io.stardewvalleydesigner.component.dialog.opendesign.OpenDesignState as State


internal typealias OpenDesignStore = Store<Intent, State, Nothing>


class OpenDesignStoreFactory(private val storeFactory: StoreFactory) {

    fun create(
        onDesignChosen: (Design, designPath: String?) -> Unit,
    ): OpenDesignStore = object : OpenDesignStore by storeFactory.create(
        name = "OpenDesignStore",
        initialState = OpenDesignState.NotOpened,
        executorFactory = { ExecutorImpl(onDesignChosen) },
        reducer = reducer
    ) {}


    private sealed interface Msg {
        data object OpenMenu : Msg
        data object ShowLoading : Msg
        data class ShowSuccess(val design: Wrapper<Design>, val absolutePath: String?) : Msg
        data object ShowError : Msg
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

                is Intent.LoadDesign -> {
                    dispatch(Msg.ShowLoading)

                    scope.launch {
                        val parsed = try {
                            withContext(PlatformDispatcher.IO) {
                                DesignFormatSerializer.deserialize(intent.text).wrapped()
                            }
                        } catch (e: Exception) {
                            logger.warn { e.stackTraceToString() }
                            null
                        }

                        dispatch(
                            if (parsed != null) {
                                Msg.ShowSuccess(parsed, intent.absolutePath)
                            } else {
                                Msg.ShowError
                            }
                        )
                    }
                }

                Intent.Accept -> {
                    val (layout, absolutePath) = state() as State.Loaded

                    dispatch(Msg.CloseMenu)

                    onDesignChosen(layout.value, absolutePath)
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
                layout = msg.design,
                absolutePath = msg.absolutePath,
            )

            Msg.ShowError -> State.Error

            Msg.CloseMenu -> State.NotOpened
        }
    }
}
