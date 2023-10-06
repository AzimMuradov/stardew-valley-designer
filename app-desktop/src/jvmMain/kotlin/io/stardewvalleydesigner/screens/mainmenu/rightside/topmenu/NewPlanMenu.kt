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

package io.stardewvalleydesigner.screens.mainmenu.rightside.topmenu

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import io.stardewvalleydesigner.mainmenu.MainMenuIntent
import io.stardewvalleydesigner.mainmenu.MainMenuState
import io.stardewvalleydesigner.utils.GlobalSettings


@Composable
fun RowScope.NewPlanMenu(
    state: MainMenuState,
    intentConsumer: (MainMenuIntent) -> Unit,
) {
    val wordList = GlobalSettings.strings

    DialogWindowMenu(
        onCloseRequest = { intentConsumer(MainMenuIntent.NewPlanMenu.Cancel) },
        visible = state is MainMenuState.NewPlanMenu.Idle,
        title = wordList.newPlanWindowTitle,
        topMenuButton = {
            TopMenuButton(
                text = wordList.buttonNewPlanText,
                icon = Icons.Filled.Add,
                onClick = { intentConsumer(MainMenuIntent.NewPlanMenu.OpenMenu) }
            )
        },
        filePickerBar = null,
        mainPart = {
            if (state is MainMenuState.NewPlanMenu.Idle) {
                LayoutsGrid(
                    layouts = state.availableLayouts,
                    chosenLayout = state.chosenLayout,
                    onChoose = { intentConsumer(MainMenuIntent.NewPlanMenu.ChooseLayout(it)) }
                )
            }
        },
        acceptLayoutBar = {
            AcceptLayoutBar(
                textFieldText = "",
                buttonText = wordList.chooseLayout,
                placeholderText = "",
                onClick = { intentConsumer(MainMenuIntent.NewPlanMenu.AcceptChosen) },
            )
        },
    )
}
