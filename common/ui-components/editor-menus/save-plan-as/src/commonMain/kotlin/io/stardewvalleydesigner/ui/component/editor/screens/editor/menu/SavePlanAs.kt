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
import androidx.compose.material.icons.rounded.SaveAs
import androidx.compose.runtime.*
import io.stardewvalleydesigner.cmplib.filedialogs.FileSaver
import io.stardewvalleydesigner.editor.modules.map.MapState
import io.stardewvalleydesigner.kmplib.env.Environment
import io.stardewvalleydesigner.ui.component.editor.screens.editor.bottommenu.BottomMenuIconButton
import io.stardewvalleydesigner.ui.component.editor.screens.editor.menu.saveplanas.*
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock


@Composable
fun SavePlanAsButton(
    map: MapState,
    snackbarHostState: SnackbarHostState,
    designSaveAbsolutePath: String?,
    onDesignSaveAbsolutePathChanged: (String?) -> Unit,
) {
    val wordList = GlobalSettings.strings

    var pathname: String? by remember { mutableStateOf(null) }

    var showFileSaver by remember { mutableStateOf(false) }

    BottomMenuIconButton(
        tooltip = wordList.buttonSavePlanAsTooltip,
        icon = Icons.Rounded.SaveAs,
        onClick = {
            if (!showFileSaver) {
                pathname = if (designSaveAbsolutePath != null) {
                    designSaveAbsolutePath
                } else {
                    val filename = "design-${Clock.System.nowFormatted()}.$JSON_FORMAT"
                    Environment.relative(Environment.getSvdSavesDir(), filename)
                }
                showFileSaver = true
            }
        }
    )

    val scope = rememberCoroutineScope()

    if (showFileSaver) {
        FileSaver(
            title = wordList.savePlanAsTitle,
            defaultPathAndFile = pathname,
            extensions = listOf(JSON_FORMAT),
            bytes = { DesignSaver.serializePlanToBytes(map) },
        ) { result ->
            showFileSaver = false

            if (result != null) {
                onDesignSaveAbsolutePathChanged(result.absolutePath)

                scope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(message = wordList.savePlanAsNotificationMessage(result.absolutePath))
                }
            }
        }
    }
}
