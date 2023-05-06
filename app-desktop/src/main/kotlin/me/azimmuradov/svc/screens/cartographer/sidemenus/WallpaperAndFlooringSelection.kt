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

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.azimmuradov.svc.cartographer.res.flooring
import me.azimmuradov.svc.cartographer.res.wallpaper
import me.azimmuradov.svc.engine.Flooring
import me.azimmuradov.svc.engine.Wallpaper
import me.azimmuradov.svc.utils.bounceClick
import me.azimmuradov.svc.utils.drawSprite


@Composable
fun WallpaperAndFlooringSelection(
    onWallpaperSelection: (Wallpaper) -> Unit,
    onFlooringSelection: (Flooring) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(300.dp),
    ) {
        var chosenTab by remember { mutableStateOf(Tab.Wallpaper) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = { chosenTab = Tab.Wallpaper },
                modifier = Modifier.weight(1f).heightIn(max = 36.dp)
            ) {
                Text(text = "Wallpaper", fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { chosenTab = Tab.Flooring },
                modifier = Modifier.weight(1f).heightIn(max = 36.dp)
            ) {
                Text(text = "Flooring", fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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
                                    .bounceClick { onWallpaperSelection(w) }
                                    .aspectRatio(ratio = 1f / 3f)
                                    .fillMaxSize()
                                    .drawBehind {
                                        drawSprite(
                                            sprite = wallpaper(index = w.n.toInt()),
                                            layoutSize = size
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
                                    .bounceClick { onFlooringSelection(f) }
                                    .aspectRatio(ratio = 1f)
                                    .fillMaxSize()
                                    .drawBehind {
                                        drawSprite(
                                            sprite = flooring(index = f.n.toInt()),
                                            layoutSize = size
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
}


private enum class Tab { Wallpaper, Flooring }
