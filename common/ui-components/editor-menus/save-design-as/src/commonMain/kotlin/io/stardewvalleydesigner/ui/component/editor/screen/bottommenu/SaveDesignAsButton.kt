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

import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SaveAs
import androidx.compose.runtime.*
import io.stardewvalleydesigner.LoggerUtils.logger
import io.stardewvalleydesigner.cmplib.filedialogs.FileSaver
import io.stardewvalleydesigner.component.editor.modules.map.MapState
import io.stardewvalleydesigner.designformat.models.Options
import io.stardewvalleydesigner.designformat.models.Palette
import io.stardewvalleydesigner.kmplib.dispatcher.PlatformDispatcher
import io.stardewvalleydesigner.kmplib.fs.FileSystem
import io.stardewvalleydesigner.kmplib.fs.getSvdSavesDir
import io.stardewvalleydesigner.ui.component.editor.screen.bottommenu.savedesignas.*
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock


@Composable
fun SaveDesignAsButton(
    map: MapState,
    playerName: String,
    farmName: String,
    palette: Palette,
    options: Options,
    snackbarHostState: SnackbarHostState,
    designSaveAbsolutePath: String?,
    onDesignSaveAbsolutePathChanged: (String?) -> Unit,
) {
    val wordList = GlobalSettings.strings

    var pathname: String? by remember { mutableStateOf(null) }

    var showFileSaver by remember { mutableStateOf(false) }

    BottomMenuIconButton(
        tooltip = wordList.buttonSaveDesignAsTooltip,
        icon = Icons.Rounded.SaveAs,
        onClick = {
            if (!showFileSaver) {
                pathname = if (designSaveAbsolutePath != null) {
                    designSaveAbsolutePath
                } else {
                    val filename = "design-${Clock.System.nowFormatted()}.$JSON_FORMAT"
                    FileSystem.relative(FileSystem.getSvdSavesDir(), filename) ?: filename
                }
                logger.debug { pathname }
                showFileSaver = true
            }
        }
    )

    val scope = rememberCoroutineScope()

    var bytes: ByteArray? by remember(showFileSaver) { mutableStateOf(null) }

    LaunchedEffect(showFileSaver) {
        bytes = if (showFileSaver) {
            withContext(PlatformDispatcher.IO) {
                DesignSaver.serializeDesignToBytes(
                    map,
                    playerName,
                    farmName,
                    palette,
                    options,
                )
            }
        } else {
            null
        }
    }

    if (showFileSaver) {
        bytes?.let { processedBytes ->
            FileSaver(
                title = wordList.saveDesignAsTitle,
                defaultPathAndFile = pathname,
                extensions = listOf(JSON_FORMAT),
                bytes = processedBytes,
            ) { result ->
                showFileSaver = false

                if (result != null) {
                    onDesignSaveAbsolutePathChanged(result.absolutePath)

                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(message = wordList.saveDesignAsNotificationMessage(result.absolutePath))
                    }
                }
            }
        }
    }
}
