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

package io.stardewvalleydesigner.screens.editor.topmenu

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material.icons.rounded.SaveAs
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dirs.UserDirectories
import io.stardewvalleydesigner.cmplib.filedialogs.FileSaver
import io.stardewvalleydesigner.designformat.PlanFormatConverter
import io.stardewvalleydesigner.designformat.models.Plan
import io.stardewvalleydesigner.editor.modules.map.MapState
import io.stardewvalleydesigner.engine.layers.flatten
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings
import io.stardewvalleydesigner.utils.*
import kotlinx.coroutines.launch
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.io.File.separator as sep


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SavePlanButton(
    map: MapState,
    snackbarHostState: SnackbarHostState,
    planPath: String?,
) {
    val wordList = GlobalSettings.strings

    var pathname by remember { mutableStateOf(planPath ?: "") }

    var showFileSaver by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Box(modifier = Modifier) {
        TooltipArea(wordList.buttonSavePlanTooltip) {
            TopMenuIconButton(
                icon = Icons.Rounded.Save,
                enabled = File(pathname).run { exists() && isFile },
                onClick = {
                    scope.launch {
                        savePlan(pathname, map)
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(message = wordList.savePlanNotificationMessage)
                    }
                }
            )
        }
    }

    Spacer(Modifier.size(4.dp))

    Box(modifier = Modifier) {
        TooltipArea(wordList.buttonSavePlanAsTooltip) {
            TopMenuIconButton(
                icon = Icons.Rounded.SaveAs,
                onClick = {
                    val defaultDir = "${UserDirectories.get().documentDir}${sep}Stardew Valley Designer${sep}"
                    Files.createDirectories(Path.of(defaultDir))
                    pathname = run {
                        val filename = "design-${nowFormatted()}.json"
                        "$defaultDir$sep$filename"
                    }
                    showFileSaver = true
                }
            )
        }
    }

    if (showFileSaver) {
        FileSaver(
            title = wordList.savePlanAsTitle,
            defaultPathAndFile = pathname,
            extensions = listOf("json"),
        ) { path ->
            showFileSaver = false
            path?.let {
                scope.launch {
                    pathname = path.toString()
                    savePlan(pathname, map)
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(message = wordList.savePlanAsNotificationMessage(pathname))
                }
            }
        }
    }
}


private fun savePlan(pathname: String, map: MapState) {
    Files.createDirectories(Path.of(pathname).parent)
    File(pathname).writeText(serializePlan(map))
}

private fun serializePlan(map: MapState): String = PlanFormatConverter.stringify(map.toPlan())

private fun MapState.toPlan(): Plan = Plan(
    entities = entities.flatten(),
    wallpaper = wallpaper,
    flooring = flooring,
    layout = layout.type,
)
