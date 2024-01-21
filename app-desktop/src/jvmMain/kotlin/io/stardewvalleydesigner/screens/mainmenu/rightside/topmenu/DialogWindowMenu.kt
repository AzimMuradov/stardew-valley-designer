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

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*


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

    val dialogState = rememberDialogState(size = DialogWindowDefaults.size)

    LaunchedEffect(visible) {
        if (visible) {
            dialogState.position = WindowPosition(Alignment.Center)
        }
    }

    DialogWindow(
        onCloseRequest = onCloseRequest,
        state = rememberDialogState(size = DialogWindowDefaults.size),
        visible = visible,
        title = title,
        resizable = false
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            filePickerBar?.invoke()

            Spacer(Modifier.size(16.dp))

            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center,
                    content = mainPart,
                )
                acceptLayoutBar()
            }
        }
    }
}
