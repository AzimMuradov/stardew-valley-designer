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
import androidx.compose.material.icons.rounded.Image
import androidx.compose.runtime.*
import io.stardewvalleydesigner.cmplib.filedialogs.FileSaver
import io.stardewvalleydesigner.component.editor.modules.map.MapState
import io.stardewvalleydesigner.component.editor.modules.vislayers.VisLayersState
import io.stardewvalleydesigner.kmplib.fs.FileSystem
import io.stardewvalleydesigner.kmplib.fs.getSvdImagesDir
import io.stardewvalleydesigner.kmplib.png.PNG_FORMAT
import io.stardewvalleydesigner.ui.component.editor.res.ImageResources
import io.stardewvalleydesigner.ui.component.editor.res.ImageResourcesProvider
import io.stardewvalleydesigner.ui.component.editor.screen.bottommenu.savedesignasimg.DesignRenderer
import io.stardewvalleydesigner.ui.component.editor.screen.bottommenu.savedesignasimg.nowFormatted
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock


@Composable
fun SaveDesignAsImageButton(
    map: MapState,
    visibleLayers: VisLayersState,
    snackbarHostState: SnackbarHostState,
) {
    val wordList = GlobalSettings.strings

    var pathname: String? by remember { mutableStateOf(null) }

    var showFileSaver by remember { mutableStateOf(false) }

    BottomMenuIconButton(
        tooltip = wordList.buttonSaveDesignAsImageTooltip,
        icon = Icons.Rounded.Image,
        onClick = {
            if (!showFileSaver) {
                pathname = run {
                    val filename = "design-${Clock.System.nowFormatted()}.${PNG_FORMAT}"
                    FileSystem.relative(FileSystem.getSvdImagesDir(), filename)
                }
                showFileSaver = true
            }
        }
    )

    val scope = rememberCoroutineScope()

    val entities = ImageResources.entities
    val wallsAndFloors = ImageResources.wallsAndFloors
    val layoutSprite = ImageResourcesProvider.layoutSpriteBy(map.layout.type)

    if (showFileSaver) {
        FileSaver(
            title = wordList.saveDesignAsImageTitle,
            defaultPathAndFile = pathname,
            extensions = listOf(PNG_FORMAT),
            bytes = {
                DesignRenderer.generateDesignAsPngBytes(
                    map, visibleLayers.visibleLayers,
                    entities, wallsAndFloors, layoutSprite,
                )
            },
        ) { result ->
            showFileSaver = false

            if (result != null) {
                scope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(message = wordList.saveDesignAsImageNotificationMessage(result.absolutePath))
                }
            }
        }
    }
}