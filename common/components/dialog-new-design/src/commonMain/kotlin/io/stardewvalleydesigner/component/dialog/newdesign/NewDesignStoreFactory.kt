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

package io.stardewvalleydesigner.component.dialog.newdesign

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.stardewvalleydesigner.component.dialog.newdesign.NewDesignState
import io.stardewvalleydesigner.data.Season
import io.stardewvalleydesigner.designformat.Wrapper
import io.stardewvalleydesigner.designformat.models.*
import io.stardewvalleydesigner.designformat.wrapped
import io.stardewvalleydesigner.engine.layers.LayeredEntitiesData
import io.stardewvalleydesigner.engine.layout.LayoutType
import io.stardewvalleydesigner.kmplib.dispatcher.PlatformDispatcher
import io.stardewvalleydesigner.component.dialog.newdesign.NewDesignIntent as Intent
import io.stardewvalleydesigner.component.dialog.newdesign.NewDesignState as State


internal typealias NewDesignStore = Store<Intent, State, Nothing>


class NewDesignStoreFactory(private val storeFactory: StoreFactory) {

    fun create(
        onDesignChosen: (Design, designPath: String?) -> Unit,
    ): NewDesignStore = object : NewDesignStore by storeFactory.create(
        name = "NewDesignStore",
        initialState = NewDesignState.NotOpened,
        executorFactory = { ExecutorImpl(onDesignChosen) },
        reducer = reducer
    ) {}


    private sealed interface Msg {
        data object OpenMenu : Msg
        data class ChooseDesign(val design: Wrapper<Design>) : Msg
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

                is Intent.ChooseDesign -> dispatch(Msg.ChooseDesign(intent.design))

                Intent.AcceptChosen -> onDesignChosen(
                    (state() as State.Opened).chosenDesign.value,
                    null,
                )

                Intent.CloseMenu -> dispatch(Msg.CloseMenu)
            }
        }

        override fun executeAction(action: Nothing) = Unit
    }

    private val reducer = Reducer<State, Msg> { msg ->
        when (msg) {
            Msg.OpenMenu -> {
                val availableLayouts = LayoutType.entries.map { layoutType ->
                    Design(
                        playerName = "",
                        farmName = "",
                        season = Season.Spring,
                        layout = layoutType,
                        entities = LayeredEntitiesData(),
                        wallpaper = null,
                        flooring = null,
                        palette = Palette.default(),
                        options = Options.default(),
                    )
                }.map(Design::wrapped)

                State.Opened(
                    availableDesigns = availableLayouts,
                    chosenDesign = availableLayouts.first()
                )
            }

            is Msg.ChooseDesign -> (this as State.Opened).copy(
                chosenDesign = msg.design,
            )

            Msg.CloseMenu -> State.NotOpened
        }
    }
}
