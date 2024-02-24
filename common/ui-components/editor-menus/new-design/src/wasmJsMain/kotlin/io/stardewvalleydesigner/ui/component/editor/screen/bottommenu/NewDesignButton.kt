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
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.stardewvalleydesigner.component.dialog.newdesign.NewDesignIntent
import io.stardewvalleydesigner.component.dialog.newdesign.NewDesignState
import io.stardewvalleydesigner.ui.component.designdialogs.*
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NewDesignButton(
    state: NewDesignState,
    intentConsumer: (NewDesignIntent) -> Unit,
) {
    val wordList = GlobalSettings.strings

    BottomMenuIconButton(
        tooltip = wordList.buttonNewDesignText,
        icon = Icons.Rounded.Add,
        onClick = { intentConsumer(NewDesignIntent.OpenMenu) },
    )

    if (state is NewDesignState.Opened) {
        Dialog(
            onDismissRequest = { intentConsumer(NewDesignIntent.CloseMenu) },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                usePlatformInsets = false,
            ),
        ) {
            DialogWindowMenuContent(
                modifier = Modifier
                    .size(width = 800.dp, height = 600.dp)
                    .background(
                        color = MaterialTheme.colors.background,
                        shape = MaterialTheme.shapes.large,
                    ),
                filePickerBar = null,
                mainPart = {
                    DesignsGrid(
                        designs = state.availableDesigns,
                        chosenDesign = state.chosenDesign,
                        onChoose = { intentConsumer(NewDesignIntent.ChooseDesign(it)) }
                    )
                },
                acceptLayoutBar = {
                    AcceptDesignBar(
                        textFieldText = wordList.layout(state.chosenDesign.value.layout),
                        buttonText = wordList.chooseLayout,
                        placeholderText = "",
                        onClick = {
                            intentConsumer(NewDesignIntent.AcceptChosen)
                            intentConsumer(NewDesignIntent.CloseMenu)
                        },
                    )
                },
            )
        }
    }
}
