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

package me.azimmuradov.svc.screens.cartographer.sidemenus

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import me.azimmuradov.svc.cartographer.modules.map.LayoutState
import me.azimmuradov.svc.cartographer.modules.palette.PaletteState
import me.azimmuradov.svc.cartographer.modules.toolkit.ToolkitState
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layer.allEntityTypes
import me.azimmuradov.svc.cartographer.CartographerIntent as Intent


@Composable
fun LeftSideMenus(
    toolkit: ToolkitState,
    palette: PaletteState,
    width: Dp,
    intentConsumer: (Intent) -> Unit,
) {
    FixedSideMenus(width) {
        menu { Toolbar(toolkit, intentConsumer) }
        menu { Palette(palette, intentConsumer) }
    }
}

@Composable
fun RightSideMenus(
    visibleLayers: Set<LayerType<*>>,
    onVisibilityChange: (LayerType<*>, Boolean) -> Unit,
    layout: LayoutState,
    width: Dp,
    intentConsumer: (Intent) -> Unit,
) {
    FixedSideMenus(width) {
        menu {
            LayersVisibility(
                allowedLayers = LayerType.all.filterTo(mutableSetOf()) { lType ->
                    lType.allEntityTypes().any { eType -> eType !in layout.disallowedTypes }
                },
                visibleLayers = visibleLayers,
                onVisibilityChange = onVisibilityChange
            )
        }
        menu {
            WallpaperAndFlooringSelection(
                onWallpaperSelection = { intentConsumer(Intent.WallpaperAndFlooring.ChooseWallpaper(it)) },
                onFlooringSelection = { intentConsumer(Intent.WallpaperAndFlooring.ChooseFlooring(it)) },
            )
        }
    }
}


@Composable
private fun FixedSideMenus(width: Dp, content: SideMenusBuilder.() -> Unit) {
    SideMenus(Modifier.fillMaxHeight().width(width), content)
}
