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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.dirs.UserDirectories
import io.stardewvalleydesigner.component.mainmenu.MainMenuIntent
import io.stardewvalleydesigner.component.mainmenu.MainMenuState
import io.stardewvalleydesigner.engine.layout.LayoutsProvider.layoutOf
import io.stardewvalleydesigner.screens.mainmenu.rightside.LayoutPreview
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings
import kotlin.io.path.Path
import kotlin.io.path.exists
import java.io.File.separator as sep


@Composable
fun RowScope.OpenDesignMenu(
    state: MainMenuState,
    intentConsumer: (MainMenuIntent) -> Unit,
) {
    val wordList = GlobalSettings.strings

    DialogWindowMenu(
        onCloseRequest = { intentConsumer(MainMenuIntent.OpenDesignMenu.Cancel) },
        visible = state is MainMenuState.OpenDesignMenu,
        title = wordList.openDesignWindowTitle,
        topMenuButton = {
            TopMenuButton(
                text = wordList.buttonOpenDesignText,
                icon = Icons.Filled.FileOpen,
                onClick = { intentConsumer(MainMenuIntent.OpenDesignMenu.OpenMenu) }
            )
        },
        filePickerBar = {
            FilePickerBar(
                buttonText = wordList.openDesignSelectDesignButton,
                filePickerTitle = wordList.openDesignSelectDesignTitle,
                placeholderText = wordList.openDesignSelectDesignPlaceholder,
                defaultPathAndFile = savePath,
                onFilePicked = { text, absolutePath ->
                    intentConsumer(MainMenuIntent.OpenDesignMenu.LoadDesign(text, absolutePath))
                },
                fileFormat = "json"
            )
        },
        mainPart = {
            if (state is MainMenuState.OpenDesignMenu) {
                when (state) {
                    MainMenuState.OpenDesignMenu.Empty -> {
                        Text(
                            text = wordList.openDesignPlaceholder,
                            modifier = Modifier.padding(20.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h6
                        )
                    }

                    MainMenuState.OpenDesignMenu.Loading -> {
                        CircularProgressIndicator(strokeCap = StrokeCap.Round)
                    }

                    is MainMenuState.OpenDesignMenu.Loaded -> {
                        val (layoutType, layeredEntitiesData, wallpaper, flooring) = state.layout.value
                        LayoutPreview(
                            layout = layoutOf(layoutType),
                            entities = layeredEntitiesData,
                            wallpaper = wallpaper,
                            flooring = flooring,
                        )
                    }

                    MainMenuState.OpenDesignMenu.Error -> {
                        Text(
                            text = wordList.openDesignPlaceholderError,
                            modifier = Modifier.padding(20.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h6
                        )
                    }
                }
            }
        },
        acceptLayoutBar = {
            AcceptLayoutBar(
                textFieldText = if (state is MainMenuState.OpenDesignMenu.Loaded) {
                    wordList.layout(state.layout.value.layoutType)
                } else {
                    ""
                },
                buttonText = wordList.ok,
                buttonEnabled = state is MainMenuState.OpenDesignMenu.Loaded,
                placeholderText = "",
                onClick = { intentConsumer(MainMenuIntent.OpenDesignMenu.Accept) }
            )
        },
    )
}

private val savePath: String? = "${UserDirectories.get().documentDir}${sep}Stardew Valley Designer${sep}".takeIf {
    Path(it).exists()
}
