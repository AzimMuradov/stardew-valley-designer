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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FileOpen
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.stardewvalleydesigner.component.dialog.opendesign.OpenDesignIntent
import io.stardewvalleydesigner.component.dialog.opendesign.OpenDesignState
import io.stardewvalleydesigner.engine.layout.LayoutsProvider
import io.stardewvalleydesigner.ui.component.designdialogs.*
import io.stardewvalleydesigner.ui.component.editor.screen.LayoutPreview
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OpenDesignButton(
    state: OpenDesignState,
    intentConsumer: (OpenDesignIntent) -> Unit,
) {
    val wordList = GlobalSettings.strings

    BottomMenuIconButton(
        tooltip = wordList.buttonOpenDesignText,
        icon = Icons.Rounded.FileOpen,
        onClick = { intentConsumer(OpenDesignIntent.OpenMenu) },
    )

    if (state !is OpenDesignState.NotOpened) {
        Dialog(
            onDismissRequest = { intentConsumer(OpenDesignIntent.CloseMenu) },
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
                        buttonText = wordList.openDesignSelectDesignButton,
                        filePickerTitle = wordList.openDesignSelectDesignTitle,
                        placeholderText = wordList.openDesignSelectDesignPlaceholder,
                        defaultPathAndFile = null,
                        onFilePicked = { text, absolutePath ->
                            intentConsumer(OpenDesignIntent.LoadDesign(text, absolutePath))
                        },
                        fileFormat = "json"
                    )
                },
                mainPart = {
                    when (state) {
                        OpenDesignState.Empty -> {
                            Text(
                                text = wordList.openDesignPlaceholder,
                                modifier = Modifier.padding(20.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.h6
                            )
                        }

                        OpenDesignState.Loading -> {
                            CircularProgressIndicator(strokeCap = StrokeCap.Round)
                        }

                        is OpenDesignState.Loaded -> {
                            val (_, _, _, season, layoutType, entities, wallpaper, flooring) = state.layout.value
                            LayoutPreview(
                                layout = LayoutsProvider.layoutOf(layoutType),
                                season = season,
                                entities = entities,
                                wallpaper = wallpaper,
                                flooring = flooring,
                            )
                        }

                        OpenDesignState.Error -> {
                            Text(
                                text = wordList.openDesignPlaceholderError,
                                modifier = Modifier.padding(20.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.h6
                            )
                        }

                        OpenDesignState.NotOpened -> Unit
                    }
                },
                acceptLayoutBar = {
                    AcceptDesignBar(
                        textFieldText = if (state is OpenDesignState.Loaded) {
                            val (_, playerName, farmName, _, layoutType) = state.layout.value
                            val playerNameWithDefault = playerName.takeIf { it.isNotBlank() } ?: "??"
                            val farmNameWithDefault = farmName.takeIf { it.isNotBlank() } ?: "??"
                            "$playerNameWithDefault, $farmNameWithDefault ${wordList.farm}, ${wordList.layout(layoutType)}"
                        } else {
                            ""
                        },
                        buttonText = wordList.ok,
                        buttonEnabled = state is OpenDesignState.Loaded,
                        placeholderText = "",
                        onClick = {
                            intentConsumer(OpenDesignIntent.Accept)
                            intentConsumer(OpenDesignIntent.CloseMenu)
                        }
                    )
                },
            )
        }
    }
}
