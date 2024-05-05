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

package io.stardewvalleydesigner.ui.component.editor.screen.topmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.stardewvalleydesigner.component.dialog.opensvsave.OpenSvSaveIntent
import io.stardewvalleydesigner.component.dialog.opensvsave.OpenSvSaveState
import io.stardewvalleydesigner.ui.component.designdialogs.*
import io.stardewvalleydesigner.ui.component.editor.res.SaveOpen
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OpenSvSaveButton(
    state: OpenSvSaveState,
    intentConsumer: (OpenSvSaveIntent) -> Unit,
) {
    val wordList = GlobalSettings.strings

    TopMenuIconButtonWithTooltip(
        tooltip = wordList.buttonSaveImportText,
        icon = Icons.Filled.SaveOpen,
        onClick = { intentConsumer(OpenSvSaveIntent.OpenMenu) },
    )

    if (state !is OpenSvSaveState.NotOpened) {
        Dialog(
            onDismissRequest = { intentConsumer(OpenSvSaveIntent.CloseMenu) },
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
                filePickerBar = {
                    FilePickerBar(
                        buttonText = wordList.saveImportSelectSaveFileButton,
                        filePickerTitle = wordList.saveImportSelectSaveFileTitle,
                        placeholderText = wordList.saveImportSelectSaveFilePlaceholder,
                        defaultPathAndFile = null,
                        onFilePicked = { text, absolutePath ->
                            intentConsumer(OpenSvSaveIntent.LoadSave(text, absolutePath))
                        },
                    )
                },
                mainPart = {
                    when (state) {
                        OpenSvSaveState.Empty -> {
                            Text(
                                text = wordList.saveImportPlaceholder,
                                modifier = Modifier.padding(20.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.h6,
                            )
                        }

                        OpenSvSaveState.Loading -> {
                            CircularProgressIndicator(strokeCap = StrokeCap.Round)
                        }

                        is OpenSvSaveState.Loaded -> {
                            DesignsGrid(
                                designs = state.availableLayouts,
                                chosenDesign = state.chosenLayout,
                                onChoose = { intentConsumer(OpenSvSaveIntent.ChooseDesign(it)) },
                            )
                        }

                        OpenSvSaveState.Error -> {
                            Text(
                                text = wordList.saveImportPlaceholderError,
                                modifier = Modifier.padding(20.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.h6,
                            )
                        }

                        OpenSvSaveState.NotOpened -> Unit
                    }
                },
                acceptLayoutBar = {
                    AcceptDesignBar(
                        textFieldText = if (state is OpenSvSaveState.Loaded) {
                            val (_, playerName, farmName, _, layoutType) = state.chosenLayout.value
                            val playerNameWithDefault = playerName.takeIf { it.isNotBlank() } ?: "??"
                            val farmNameWithDefault = farmName.takeIf { it.isNotBlank() } ?: "??"
                            "$playerNameWithDefault, $farmNameWithDefault ${wordList.farm}, ${wordList.layout(layoutType)}"
                        } else {
                            ""
                        },
                        buttonText = wordList.chooseLayout,
                        buttonEnabled = state is OpenSvSaveState.Loaded,
                        placeholderText = "",
                        onClick = {
                            intentConsumer(OpenSvSaveIntent.AcceptChosen)
                            intentConsumer(OpenSvSaveIntent.CloseMenu)
                        },
                    )
                },
            )
        }
    }
}
