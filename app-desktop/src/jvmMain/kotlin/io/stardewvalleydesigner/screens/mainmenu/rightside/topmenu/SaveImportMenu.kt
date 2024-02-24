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
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.component.mainmenu.MainMenuIntent
import io.stardewvalleydesigner.component.mainmenu.MainMenuState
import io.stardewvalleydesigner.kmplib.fs.*
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings


@Composable
fun RowScope.SaveImportMenu(
    state: MainMenuState,
    intentConsumer: (MainMenuIntent) -> Unit,
) {
    val wordList = GlobalSettings.strings

    val svSavesPath by remember { mutableStateOf(JvmFileSystem.getSvSavesDir().endSep().takeIfExists()) }

    DialogWindowMenu(
        onCloseRequest = { intentConsumer(MainMenuIntent.SaveLoaderMenu.Cancel) },
        visible = state is MainMenuState.SaveLoaderMenu,
        title = wordList.saveImportWindowTitle,
        topMenuButton = {
            TopMenuButton(
                text = wordList.buttonSaveImportText,
                icon = Icons.Filled.Save,
                onClick = { intentConsumer(MainMenuIntent.SaveLoaderMenu.OpenMenu) }
            )
        },
        filePickerBar = {
            FilePickerBar(
                buttonText = wordList.saveImportSelectSaveFileButton,
                filePickerTitle = wordList.saveImportSelectSaveFileTitle,
                placeholderText = wordList.saveImportSelectSaveFilePlaceholder,
                defaultPathAndFile = svSavesPath,
                onFilePicked = { text, absolutePath ->
                    intentConsumer(MainMenuIntent.SaveLoaderMenu.LoadSave(text, absolutePath))
                },
            )
        },
        mainPart = {
            if (state is MainMenuState.SaveLoaderMenu) {
                when (state) {
                    MainMenuState.SaveLoaderMenu.Empty -> {
                        Text(
                            text = wordList.saveImportPlaceholder,
                            modifier = Modifier.padding(20.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h6,
                        )
                    }

                    MainMenuState.SaveLoaderMenu.Loading -> {
                        CircularProgressIndicator(strokeCap = StrokeCap.Round)
                    }

                    is MainMenuState.SaveLoaderMenu.Loaded -> {
                        LayoutsGrid(
                            layouts = state.availableLayouts,
                            chosenLayout = state.chosenLayout,
                            onChoose = { intentConsumer(MainMenuIntent.SaveLoaderMenu.ChooseLayout(it)) },
                        )
                    }

                    MainMenuState.SaveLoaderMenu.Error -> {
                        Text(
                            text = wordList.saveImportPlaceholderError,
                            modifier = Modifier.padding(20.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h6,
                        )
                    }
                }
            }
        },
        acceptLayoutBar = {
            AcceptLayoutBar(
                textFieldText = if (state is MainMenuState.SaveLoaderMenu.Loaded) {
                    val (_, playerName, farmName, layoutType) = state.chosenLayout.value
                    val playerNameWithDefault = playerName.takeIf { it.isNotBlank() } ?: "??"
                    val farmNameWithDefault = farmName.takeIf { it.isNotBlank() } ?: "??"
                    "$playerNameWithDefault, $farmNameWithDefault ${wordList.farm}, ${wordList.layout(layoutType)}"
                } else {
                    ""
                },
                buttonText = wordList.chooseLayout,
                buttonEnabled = state is MainMenuState.SaveLoaderMenu.Loaded,
                placeholderText = "",
                onClick = { intentConsumer(MainMenuIntent.SaveLoaderMenu.AcceptChosen) },
            )
        },
    )
}
