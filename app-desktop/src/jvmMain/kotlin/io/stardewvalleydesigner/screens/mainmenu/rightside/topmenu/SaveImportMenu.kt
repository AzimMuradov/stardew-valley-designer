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
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.LoggerUtils
import io.stardewvalleydesigner.mainmenu.MainMenuIntent
import io.stardewvalleydesigner.mainmenu.MainMenuState
import io.stardewvalleydesigner.utils.GlobalSettings
import java.io.File
import java.util.*
import java.io.File.separator as sep


@Composable
fun RowScope.SaveImportMenu(
    state: MainMenuState,
    intentConsumer: (MainMenuIntent) -> Unit,
) {
    val wordList = GlobalSettings.strings

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
                defaultPathAndFile = savePath,
                onFilePicked = { path -> intentConsumer(MainMenuIntent.SaveLoaderMenu.LoadSave(path)) },
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
                textFieldText = "",
                buttonText = wordList.chooseLayout,
                buttonEnabled = state is MainMenuState.SaveLoaderMenu.Loaded,
                placeholderText = "",
                onClick = { intentConsumer(MainMenuIntent.SaveLoaderMenu.AcceptChosen) },
            )
        },
    )
}


private val savePath: String? = run {
    val os = System.getProperty("os.name").uppercase(Locale.getDefault())
    val dataPath = if ("WIN" in os) {
        System.getenv("APPDATA")
    } else {
        "${System.getProperty("user.home")}${sep}.config"
    }

    "$dataPath${sep}StardewValley${sep}Saves${sep}".takeIf {
        LoggerUtils.logger.debug { "Saves path: $it" }
        File(it).exists()
    }
}
