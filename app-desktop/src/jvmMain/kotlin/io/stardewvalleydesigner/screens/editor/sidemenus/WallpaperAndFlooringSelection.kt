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

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.editor.res.flooring
import io.stardewvalleydesigner.editor.res.wallpaper
import io.stardewvalleydesigner.engine.Flooring
import io.stardewvalleydesigner.engine.Wallpaper
import io.stardewvalleydesigner.utils.*
import io.stardewvalleydesigner.utils.DrawerUtils.drawSpriteStretched


@Composable
fun ColumnScope.WallpaperAndFlooringSelection(
    onWallpaperSelection: (Wallpaper) -> Unit,
    onFlooringSelection: (Flooring) -> Unit,
) {
    val wordList = GlobalSettings.strings

    var chosenTab by remember { mutableStateOf(Tab.Wallpaper) }

    val wallpaperTabBg by animateColorAsState(
        if (chosenTab == Tab.Wallpaper) {
            MaterialTheme.colors.primarySurface.copy(alpha = 0.15f)
        } else {
            Color.Transparent
        }
    )

    val flooringTabBg by animateColorAsState(
        if (chosenTab == Tab.Flooring) {
            MaterialTheme.colors.primarySurface.copy(alpha = 0.15f)
        } else {
            Color.Transparent
        }
    )

    TabRow(
        selectedTabIndex = chosenTab.ordinal,
        modifier = Modifier.fillMaxWidth().height(36.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Tab(
            selected = chosenTab == Tab.Wallpaper,
            onClick = { chosenTab = Tab.Wallpaper },
            modifier = Modifier.weight(1f).fillMaxHeight().background(wallpaperTabBg)
        ) {
            Text(
                wordList.wallpapersTabTitle,
                style = MaterialTheme.typography.subtitle1
            )
        }
        Tab(
            selected = chosenTab == Tab.Flooring,
            onClick = { chosenTab = Tab.Flooring },
            modifier = Modifier.weight(1f).fillMaxHeight().background(flooringTabBg)
        ) {
            Text(
                wordList.flooringTabTitle,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Box(Modifier.fillMaxSize()) {
        val wallpaperState = rememberLazyGridState()
        val flooringState = rememberLazyGridState()

        when (chosenTab) {
            Tab.Wallpaper -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(count = 8),
                    modifier = Modifier.fillMaxSize().padding(end = 12.dp),
                    state = wallpaperState,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(items = Wallpaper.all()) { w ->
                        Box(
                            modifier = Modifier
                                .pointerHoverIcon(PointerIcon.Hand)
                                .bounceClickable { onWallpaperSelection(w) }
                                .aspectRatio(ratio = 1f / 3f)
                                .fillMaxSize()
                                .drawBehind {
                                    drawSpriteStretched(
                                        sprite = wallpaper(w),
                                        layoutSize = size.toIntSize()
                                    )
                                }
                        )
                    }
                }
                VerticalScrollbar(
                    adapter = rememberScrollbarAdapter(scrollState = wallpaperState),
                    modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
                )
            }

            Tab.Flooring -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(count = 4),
                    modifier = Modifier.fillMaxSize().padding(end = 12.dp),
                    state = flooringState,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(items = Flooring.all()) { f ->
                        Box(
                            modifier = Modifier
                                .pointerHoverIcon(PointerIcon.Hand)
                                .bounceClickable { onFlooringSelection(f) }
                                .aspectRatio(ratio = 1f)
                                .fillMaxSize()
                                .drawBehind {
                                    drawSpriteStretched(
                                        sprite = flooring(f),
                                        layoutSize = size.toIntSize()
                                    )
                                }
                        )
                    }
                }
                VerticalScrollbar(
                    adapter = rememberScrollbarAdapter(scrollState = flooringState),
                    modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
                )
            }
        }
    }
}


private enum class Tab { Wallpaper, Flooring }
