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
import androidx.compose.runtime.Composable
import io.stardewvalleydesigner.component.mainmenu.MainMenuIntent
import io.stardewvalleydesigner.component.mainmenu.MainMenuState
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings


@Composable
fun RowScope.NewDesignMenu(
    state: MainMenuState,
    intentConsumer: (MainMenuIntent) -> Unit,
) {
    val wordList = GlobalSettings.strings

    DialogWindowMenu(
        onCloseRequest = { intentConsumer(MainMenuIntent.NewDesignMenu.Cancel) },
        visible = state is MainMenuState.NewDesignMenu.Idle,
        title = wordList.newDesignWindowTitle,
        topMenuButton = {
            TopMenuButton(
                text = wordList.buttonNewDesignText,
                icon = Icons.Filled.Add,
                onClick = { intentConsumer(MainMenuIntent.NewDesignMenu.OpenMenu) }
            )
        },
        filePickerBar = null,
        mainPart = {
            if (state is MainMenuState.NewDesignMenu.Idle) {
                LayoutsGrid(
                    layouts = state.availableLayouts,
                    chosenLayout = state.chosenLayout,
                    onChoose = { intentConsumer(MainMenuIntent.NewDesignMenu.ChooseLayout(it)) }
                )
            }
        },
        acceptLayoutBar = {
            AcceptLayoutBar(
                textFieldText = if (state is MainMenuState.NewDesignMenu.Idle) {
                    wordList.layout(state.chosenLayout.value.layout)
                } else {
                    ""
                },
                buttonText = wordList.chooseLayout,
                placeholderText = "",
                onClick = { intentConsumer(MainMenuIntent.NewDesignMenu.AcceptChosen) },
            )
        },
    )
}
