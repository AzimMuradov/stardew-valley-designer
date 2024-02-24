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

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.rememberDialogState
import io.stardewvalleydesigner.ui.component.designdialogs.DialogWindowMenuContent


@Composable
fun DialogWindowMenu(
    onCloseRequest: () -> Unit,
    visible: Boolean,
    title: String,
    topMenuButton: @Composable () -> Unit,
    filePickerBar: (@Composable () -> Unit)?,
    mainPart: @Composable BoxScope.() -> Unit,
    acceptLayoutBar: @Composable () -> Unit,
) {
    topMenuButton()

    DialogWindow(
        onCloseRequest = onCloseRequest,
        state = rememberDialogState(size = DpSize(width = 800.dp, height = 600.dp)),
        visible = visible,
        title = title,
        resizable = false,
    ) {
        DialogWindowMenuContent(
            modifier = Modifier.fillMaxSize(),
            filePickerBar,
            mainPart,
            acceptLayoutBar,
        )
    }
}
