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

package io.stardewvalleydesigner.screens.mainmenu

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.rememberDialogState
import io.stardewvalleydesigner.mainmenu.MainMenuIntent
import io.stardewvalleydesigner.mainmenu.MainMenuState
import io.stardewvalleydesigner.utils.GlobalSettings


@Composable
fun RowScope.NewPlanMenu(
    state: MainMenuState,
    intentConsumer: (MainMenuIntent) -> Unit,
) {
    val wordList = GlobalSettings.strings

    TopMenuButton(
        text = wordList.buttonNewPlanText,
        icon = Icons.Filled.Add,
        onClick = { intentConsumer(MainMenuIntent.NewPlanMenu.OpenMenu) }
    )

    DialogWindow(
        onCloseRequest = { intentConsumer(MainMenuIntent.NewPlanMenu.Cancel) },
        state = rememberDialogState(
            size = DpSize(width = 800.dp, height = 600.dp)
        ),
        visible = state is MainMenuState.NewPlanMenu.Idle,
        title = wordList.newPlanWindowTitle,
        resizable = false
    ) {
        LayoutChoosingMenu(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            layouts = (state as? MainMenuState.NewPlanMenu.Idle)?.availableLayouts,
            placeholder = "",
            chosenLayout = (state as? MainMenuState.NewPlanMenu.Idle)?.chosenLayout,
            okText = wordList.chooseLayout,
            onLayoutChosen = { intentConsumer(MainMenuIntent.NewPlanMenu.ChooseLayout(it)) },
            onOk = { intentConsumer(MainMenuIntent.NewPlanMenu.AcceptChosen) },
        )
    }
}
