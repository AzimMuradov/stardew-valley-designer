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
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.azimmuradov.svc.cartographer.res.wallpaper
import me.azimmuradov.svc.utils.drawSprite


@Composable
fun WallpaperSelection() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(300.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Divider(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Wallpaper",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Divider(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))

        Box(Modifier.fillMaxSize()) {
            val state = rememberLazyGridState()

            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 8),
                modifier = Modifier.fillMaxSize().padding(end = 12.dp),
                state = state,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                items(items = Wallpaper.all()) { (n) ->
                    Box(
                        modifier = Modifier
                            .aspectRatio(ratio = 1f / 3f)
                            .fillMaxSize()
                            .drawBehind {
                                drawSprite(
                                    sprite = wallpaper(index = n.toInt()),
                                    layoutSize = size
                                )
                            }
                    )
                }
            }
            VerticalScrollbar(
                adapter = rememberScrollbarAdapter(scrollState = state),
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
            )
        }
    }
}

data class Wallpaper(val n: UByte) {

    init {
        require(n < COUNT)
    }

    companion object {

        fun all() = List(size = COUNT.toInt()) { Wallpaper(it.toUByte()) }

        val COUNT: UByte = 112u
    }
}
