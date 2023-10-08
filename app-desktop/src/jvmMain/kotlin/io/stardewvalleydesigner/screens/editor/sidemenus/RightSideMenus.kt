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

package io.stardewvalleydesigner.screens.editor.sidemenus

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.editor.modules.map.LayoutState
import io.stardewvalleydesigner.engine.layer.LayerType
import io.stardewvalleydesigner.engine.layer.allEntityTypes
import io.stardewvalleydesigner.uilib.sidemenus.FixedSideMenus
import io.stardewvalleydesigner.editor.EditorIntent as Intent


@Composable
fun RightSideMenus(
    visibleLayers: Set<LayerType<*>>,
    onVisibilityChange: (LayerType<*>, Boolean) -> Unit,
    layout: LayoutState,
    width: Dp,
    intentConsumer: (Intent) -> Unit,
) {
    FixedSideMenus(width) {
        menu(padding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
            LayersVisibility(
                allowedLayers = LayerType.all.filterTo(mutableSetOf()) { lType ->
                    lType.allEntityTypes().any { eType -> eType !in layout.disallowedTypes }
                },
                visibleLayers = visibleLayers,
                onVisibilityChange = onVisibilityChange
            )
        }
        if (layout.type.isShed()) {
            menu(modifier = Modifier.height(300.dp)) {
                WallpaperAndFlooringSelection(
                    onWallpaperSelection = { intentConsumer(Intent.WallpaperAndFlooring.ChooseWallpaper(it)) },
                    onFlooringSelection = { intentConsumer(Intent.WallpaperAndFlooring.ChooseFlooring(it)) },
                )
            }
        }
    }
}
