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

package io.stardewvalleydesigner.ui.component.editor.screen.sidemenus

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.cmplib.sidemenus.FixedSideMenus
import io.stardewvalleydesigner.component.editor.modules.map.LayoutState
import io.stardewvalleydesigner.data.Season
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.engine.layer.LayerType
import io.stardewvalleydesigner.engine.layer.allEntityTypes
import io.stardewvalleydesigner.component.editor.EditorIntent as Intent


@Composable
internal fun RightSideMenus(
    visibleLayers: Set<LayerType<*>>,
    onVisibilityChange: (LayerType<*>, Boolean) -> Unit,
    chosenSeason: Season,
    onSeasonChosen: (Season) -> Unit,
    layout: LayoutState,
    onEntitySelection: (Entity<*>) -> Unit,
    width: Dp,
    intentConsumer: (Intent) -> Unit,
) {
    FixedSideMenus(width) {
        menu {
            SeasonsMenu(chosenSeason, onSeasonChosen)
        }
        menu(padding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
            LayersVisibility(
                allowedLayers = LayerType.all.filterTo(mutableSetOf()) { lType ->
                    lType.allEntityTypes().any { eType -> eType !in layout.disallowedTypes }
                },
                visibleLayers = visibleLayers,
                onVisibilityChange = onVisibilityChange
            )
        }
        menu {
            AnySelection(
                layoutType = layout.type,
                season = chosenSeason,
                disallowedTypes = layout.disallowedTypes,
                onEntitySelection = onEntitySelection,
                onWallpaperSelection = { intentConsumer(Intent.WallpaperAndFlooring.ChooseWallpaper(it)) },
                onFlooringSelection = { intentConsumer(Intent.WallpaperAndFlooring.ChooseFlooring(it)) },
            )
        }
    }
}
