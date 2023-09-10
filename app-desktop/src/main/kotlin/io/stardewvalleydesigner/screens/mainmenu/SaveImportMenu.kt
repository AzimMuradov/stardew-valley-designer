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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.IconOpacity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState
import io.stardewvalleydesigner.LoggerUtils
import io.stardewvalleydesigner.mainmenu.MainMenuIntent
import io.stardewvalleydesigner.mainmenu.MainMenuState
import io.stardewvalleydesigner.utils.GlobalSettings
import io.stardewvalleydesigner.utils.filedialogs.FilePicker
import java.io.File
import java.util.*
import java.io.File.separator as sep


@Composable
fun RowScope.SaveImportMenu(
    state: MainMenuState,
    intentConsumer: (MainMenuIntent) -> Unit,
) {
    val wordList = GlobalSettings.strings

    TopMenuButton(
        text = wordList.buttonSaveImportText,
        icon = Icons.Filled.Save,
        onClick = { intentConsumer(MainMenuIntent.SaveLoaderMenu.OpenMenu) }
    )

    Dialog(
        onCloseRequest = { intentConsumer(MainMenuIntent.SaveLoaderMenu.Cancel) },
        state = rememberDialogState(
            size = DpSize(width = 800.dp, height = 600.dp)
        ),
        visible = state is MainMenuState.SaveLoaderMenu,
        title = wordList.saveImportWindowTitle,
        resizable = false
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            SaveFileLoader(intentConsumer)

            LayoutChoosingMenu(
                modifier = Modifier.fillMaxHeight().weight(1f),
                layouts = (state as? MainMenuState.SaveLoaderMenu.Idle)?.availableLayouts,
                placeholder = if (state !is MainMenuState.SaveLoaderMenu.Error) {
                    wordList.saveImportPlaceholder
                } else {
                    wordList.saveImportPlaceholderError
                },
                chosenLayout = (state as? MainMenuState.SaveLoaderMenu.Idle)?.chosenLayout,
                okText = wordList.chooseLayout,
                onLayoutChosen = { intentConsumer(MainMenuIntent.SaveLoaderMenu.ChooseLayout(it)) },
                onOk = { intentConsumer(MainMenuIntent.SaveLoaderMenu.AcceptChosen) },
                isLoading = state is MainMenuState.SaveLoaderMenu.Loading
            )
        }
    }
}


@Composable
private fun SaveFileLoader(intentConsumer: (MainMenuIntent.SaveLoaderMenu) -> Unit) {
    val wordList = GlobalSettings.strings

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = MaterialTheme.shapes.large
            )
            .background(MaterialTheme.colors.secondary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var pathString by remember { mutableStateOf("") }

        Row(
            modifier = Modifier.fillMaxWidth().height(56.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = pathString,
                onValueChange = {},
                modifier = Modifier.weight(1f).height(56.dp),
                readOnly = true,
                placeholder = { Text(wordList.saveImportTextFieldLabel) },
                trailingIcon = null,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.onPrimary,
                    backgroundColor = MaterialTheme.colors.secondaryVariant,
                    cursorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    trailingIconColor = MaterialTheme.colors.onPrimary.copy(IconOpacity),
                    placeholderColor = MaterialTheme.colors.onPrimary.copy(ContentAlpha.medium)
                )
            )

            var showFilePicker by remember { mutableStateOf(false) }

            if (showFilePicker) {
                FilePicker(
                    title = "Choose savedata",
                    defaultPathAndFile = pathString.takeIf(String::isNotBlank) ?: savePath ?: homePath
                ) { path ->
                    showFilePicker = false
                    path?.let {
                        pathString = it.first().toString()
                        intentConsumer(MainMenuIntent.SaveLoaderMenu.LoadSave(pathString))
                    }
                }
            }

            Button(
                onClick = { showFilePicker = true },
                modifier = Modifier.height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.FileUpload,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    wordList.selectSaveFile,
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}


private val homePath: String = """${System.getProperty("user.home")}${sep}"""

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
