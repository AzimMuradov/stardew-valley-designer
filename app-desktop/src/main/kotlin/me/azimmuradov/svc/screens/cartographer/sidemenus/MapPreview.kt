/*
 * Copyright 2021-2022 Azim Muradov
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

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.azimmuradov.svc.engine.geometry.Rect
import me.azimmuradov.svc.engine.layers.LayeredEntitiesData
import me.azimmuradov.svc.utils.drawSpriteBy
import me.azimmuradov.svc.utils.toIntOffset


@Composable
fun MapPreview(
    layoutSize: Rect,
    entities: LayeredEntitiesData,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(
                top = 20.dp,
                start = 12.dp,
                end = 12.dp,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Divider(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Preview")
            Spacer(modifier = Modifier.width(8.dp))
            Divider(modifier = Modifier.weight(1f))
        }


        Spacer(Modifier.height(8.dp))


        val (w, h) = layoutSize

        Canvas(
            Modifier
                .padding(6.dp)
                .border(width = Dp.Hairline, color = Color.Black)
                .padding(6.dp)
                .aspectRatio(w.toFloat() / h.toFloat())
        ) {
            val stepSize = size.width / w
            val cellSize = Size(stepSize, stepSize)


            // Entities

            for ((_, objs) in entities.all) {
                for (e in objs) {
                    val offset = e.place.toIntOffset() * stepSize

                    drawSpriteBy(
                        entity = e.rectObject,
                        offset = offset,
                        layoutSize = cellSize,
                    )
                }
            }
        }
    }
}