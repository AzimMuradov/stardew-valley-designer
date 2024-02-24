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

package io.stardewvalleydesigner.screens.mainmenu.rightside.topmenu

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import com.arkivanov.mvikotlin.extensions.coroutines.states
import io.stardewvalleydesigner.component.dialog.newdesign.*
import io.stardewvalleydesigner.ui.component.designdialogs.AcceptDesignBar
import io.stardewvalleydesigner.ui.component.designdialogs.DesignsGrid
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings


@Composable
fun RowScope.NewDesignMenu(component: NewDesignComponent) {
    val store = component.store
    val observedState by store.states.collectAsState(component.store.state)
    val state = observedState

    val wordList = GlobalSettings.strings

    DialogWindowMenu(
        onCloseRequest = { store.accept(NewDesignIntent.CloseMenu) },
        visible = state is NewDesignState.Opened,
        title = wordList.newDesignWindowTitle,
        topMenuButton = {
            TopMenuButton(
                text = wordList.buttonNewDesignText,
                icon = Icons.Filled.Add,
                onClick = { store.accept(NewDesignIntent.OpenMenu) }
            )
        },
        filePickerBar = null,
        mainPart = {
            if (state is NewDesignState.Opened) {
                DesignsGrid(
                    designs = state.availableDesigns,
                    chosenDesign = state.chosenDesign,
                    onChoose = { store.accept(NewDesignIntent.ChooseDesign(it)) }
                )
            }
        },
        acceptLayoutBar = {
            AcceptDesignBar(
                textFieldText = if (state is NewDesignState.Opened) {
                    wordList.layout(state.chosenDesign.value.layout)
                } else {
                    ""
                },
                buttonText = wordList.chooseLayout,
                placeholderText = "",
                onClick = { store.accept(NewDesignIntent.AcceptChosen) },
            )
        },
    )
}
