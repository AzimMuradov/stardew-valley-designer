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
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.mvikotlin.extensions.coroutines.states
import io.github.irgaly.kfswatch.KfsDirectoryWatcher
import io.stardewvalleydesigner.component.dialog.opendesign.*
import io.stardewvalleydesigner.engine.layout.LayoutsProvider.layoutOf
import io.stardewvalleydesigner.kmplib.fs.*
import io.stardewvalleydesigner.ui.component.designdialogs.AcceptDesignBar
import io.stardewvalleydesigner.ui.component.designdialogs.FilePickerBar
import io.stardewvalleydesigner.ui.component.editor.screen.LayoutPreview
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map


@Composable
fun RowScope.OpenDesignMenu(component: OpenDesignComponent) {
    val store = component.store
    val observedState by store.states.collectAsState(component.store.state)
    val state = observedState

    val wordList = GlobalSettings.strings

    val scope = rememberCoroutineScope()
    val watcher by remember(scope) {
        mutableStateOf(KfsDirectoryWatcher(scope, dispatcher = Dispatchers.IO))
    }

    val docsDir: String by remember { mutableStateOf(JvmFileSystem.getDocsDir()) }

    LaunchedEffect(watcher) {
        watcher.add(docsDir)
    }

    val svdSavesPath by watcher.onEventFlow
        .filter { it.path == "Stardew Valley Designer" }
        .map { svdSavesPath() }
        .collectAsState(initial = svdSavesPath())

    DialogWindowMenu(
        onCloseRequest = { store.accept(OpenDesignIntent.CloseMenu) },
        visible = state !is OpenDesignState.NotOpened,
        title = wordList.openDesignWindowTitle,
        topMenuButton = {
            TopMenuButton(
                text = wordList.buttonOpenDesignText,
                icon = Icons.Filled.FileOpen,
                onClick = { store.accept(OpenDesignIntent.OpenMenu) }
            )
        },
        filePickerBar = {
            FilePickerBar(
                buttonText = wordList.openDesignSelectDesignButton,
                filePickerTitle = wordList.openDesignSelectDesignTitle,
                placeholderText = wordList.openDesignSelectDesignPlaceholder,
                defaultPathAndFile = svdSavesPath,
                onFilePicked = { text, absolutePath ->
                    store.accept(OpenDesignIntent.LoadDesign(text, absolutePath))
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
                        val (_, _, _, layoutType, entities, wallpaper, flooring) = state.layout.value
                        LayoutPreview(
                            layout = layoutOf(layoutType),
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
                    val (_, playerName, farmName, layoutType) = state.layout.value
                    val playerNameWithDefault = playerName.takeIf { it.isNotBlank() } ?: "??"
                    val farmNameWithDefault = farmName.takeIf { it.isNotBlank() } ?: "??"
                    "$playerNameWithDefault, $farmNameWithDefault ${wordList.farm}, ${wordList.layout(layoutType)}"
                } else {
                    ""
                },
                buttonText = wordList.ok,
                buttonEnabled = state is OpenDesignState.Loaded,
                placeholderText = "",
                onClick = { store.accept(OpenDesignIntent.Accept) }
            )
        },
    )
}

private fun svdSavesPath() = JvmFileSystem.getSvdSavesDir().endSep().takeIfExists()
