/*
 * Copyright 2021-2021 Azim Muradov
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

package me.azimmuradov.svc.ui.screens.cartographer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.input.pointer.pointerMoveFilter
import me.azimmuradov.svc.components.cartographer.Cartographer
import kotlin.math.floor


@Composable
fun EditorLayout(component: Cartographer, modifier: Modifier = Modifier) {
    val (w, h) = component.models.value.editor.layout.size

    var stepSize by remember { mutableStateOf(-1f) }
    var hoveredId by remember { mutableStateOf(-1 to -1) }
    var clickedId by remember { mutableStateOf(-1 to -1) }

    // Configs

    val showGrid by remember { mutableStateOf(true) }


    Canvas(
        modifier = modifier
            .pointerMoveFilter(
                onMove = { (offsetX, offsetY) ->
                    hoveredId = floor(offsetX / stepSize).toInt() to floor(offsetY / stepSize).toInt()
                    false
                },
                onExit = {
                    hoveredId = -1 to -1
                    false
                },
                onEnter = { false },
            )
            .clickable(
                onClick = { clickedId = hoveredId }
            )
    ) {
        stepSize = size.width / w

        val offsetsW = List(size = w) { it * stepSize }
        val offsetsH = List(size = h) { it * stepSize }

        if (showGrid) {
            for (x in offsetsW.drop(1)) {
                drawLine(
                    color = Color.Black.copy(alpha = 0.3f),
                    start = Offset(x, 0f),
                    end = Offset(x, size.height),
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(2f, 2f),
                    ),
                )
            }
            for (y in offsetsH.drop(1)) {
                drawLine(
                    color = Color.Black.copy(alpha = 0.3f),
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(2f, 2f),
                    ),
                )
            }
        }


        // hovered

        if (-1 to -1 != hoveredId) {
            drawRect(
                color = Color.Magenta.copy(alpha = 0.2f),
                topLeft = Offset(offsetsW[hoveredId.first], offsetsH[hoveredId.second]),
                size = Size(width = stepSize, height = stepSize),
            )
        }


        // clicked

        if (-1 to -1 != clickedId) {
            drawRect(
                color = Color.Red.copy(alpha = 0.2f),
                topLeft = Offset(offsetsW[clickedId.first], offsetsH[clickedId.second]),
                size = Size(width = stepSize, height = stepSize),
            )
        }
    }
}