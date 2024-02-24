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
import com.arkivanov.mvikotlin.extensions.coroutines.states
import io.stardewvalleydesigner.component.dialog.opensvsave.*
import io.stardewvalleydesigner.kmplib.fs.*
import io.stardewvalleydesigner.ui.component.designdialogs.*
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings


@Composable
fun RowScope.SaveImportMenu(component: OpenSvSaveComponent) {
    val store = component.store
    val observedState by store.states.collectAsState(component.store.state)
    val state = observedState

    val wordList = GlobalSettings.strings

    val svSavesPath by remember { mutableStateOf(JvmFileSystem.getSvSavesDir().endSep().takeIfExists()) }

    DialogWindowMenu(
        onCloseRequest = { store.accept(OpenSvSaveIntent.CloseMenu) },
        visible = state !is OpenSvSaveState.NotOpened,
        title = wordList.saveImportWindowTitle,
        topMenuButton = {
            TopMenuButton(
                text = wordList.buttonSaveImportText,
                icon = Icons.Filled.Save,
                onClick = { store.accept(OpenSvSaveIntent.OpenMenu) }
            )
        },
        filePickerBar = {
            FilePickerBar(
                buttonText = wordList.saveImportSelectSaveFileButton,
                filePickerTitle = wordList.saveImportSelectSaveFileTitle,
                placeholderText = wordList.saveImportSelectSaveFilePlaceholder,
                defaultPathAndFile = svSavesPath,
                onFilePicked = { text, absolutePath ->
                    store.accept(OpenSvSaveIntent.LoadSave(text, absolutePath))
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
                        onChoose = { store.accept(OpenSvSaveIntent.ChooseDesign(it)) },
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
                    val (_, playerName, farmName, layoutType) = state.chosenLayout.value
                    val playerNameWithDefault = playerName.takeIf { it.isNotBlank() } ?: "??"
                    val farmNameWithDefault = farmName.takeIf { it.isNotBlank() } ?: "??"
                    "$playerNameWithDefault, $farmNameWithDefault ${wordList.farm}, ${wordList.layout(layoutType)}"
                } else {
                    ""
                },
                buttonText = wordList.chooseLayout,
                buttonEnabled = state is OpenSvSaveState.Loaded,
                placeholderText = "",
                onClick = { store.accept(OpenSvSaveIntent.AcceptChosen) },
            )
        },
    )
}
