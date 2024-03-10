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

package io.stardewvalleydesigner.ui.component.editor.screen.bottommenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HelpButton() {
    val wordList = GlobalSettings.strings

    var show by remember { mutableStateOf(false) }

    BottomMenuIconButton(
        tooltip = wordList.buttonNewDesignText,
        icon = Icons.AutoMirrored.Filled.Help,
        onClick = { show = true },
    )

    if (show) {
        Dialog(
            onDismissRequest = { show = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                usePlatformInsets = false,
            ),
        ) {
            Column(
                Modifier
                    .size(width = 800.dp, height = 600.dp)
                    .background(
                        color = MaterialTheme.colors.background,
                        shape = MaterialTheme.shapes.large,
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Under construction...", style = MaterialTheme.typography.h5)
            }
        }
    }
}
