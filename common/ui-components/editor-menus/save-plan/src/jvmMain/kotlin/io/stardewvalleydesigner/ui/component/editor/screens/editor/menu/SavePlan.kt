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

package io.stardewvalleydesigner.ui.component.editor.screens.editor.menu

import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import io.stardewvalleydesigner.LoggerUtils
import io.stardewvalleydesigner.editor.modules.map.MapState
import io.stardewvalleydesigner.kmplib.dispatcher.PlatformDispatcher
import io.stardewvalleydesigner.ui.component.editor.screens.editor.bottommenu.BottomMenuIconButton
import io.stardewvalleydesigner.ui.component.editor.screens.editor.menu.saveplan.DesignSaver
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.file.Files
import java.nio.file.Path


@Composable
fun SavePlanButton(
    map: MapState,
    snackbarHostState: SnackbarHostState,
    designSaveAbsolutePath: String?,
) {
    val wordList = GlobalSettings.strings

    val scope = rememberCoroutineScope()

    BottomMenuIconButton(
        tooltip = wordList.buttonSavePlanTooltip,
        icon = Icons.Rounded.Save,
        enabled = designSaveAbsolutePath != null && File(designSaveAbsolutePath).run { exists() && isFile },
        onClick = {
            LoggerUtils.logger.debug { "designSaveAbsolutePath: $designSaveAbsolutePath" }
            LoggerUtils.logger.debug { "snackbarHostState: ${snackbarHostState.currentSnackbarData}" }
            if (designSaveAbsolutePath != null) {
                scope.launch {
                    withContext(PlatformDispatcher.IO) {
                        Files.createDirectories(Path.of(designSaveAbsolutePath).parent)
                        File(designSaveAbsolutePath).writeText(DesignSaver.serializePlanToString(map))
                    }

                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(message = wordList.savePlanNotificationMessage)
                }
            }
        }
    )
}
