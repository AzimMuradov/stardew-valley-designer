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

package me.azimmuradov.svc.screens.cartographer.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerMoveFilter
import me.azimmuradov.svc.cartographer.Svc
import me.azimmuradov.svc.components.screens.cartographer.Options
import me.azimmuradov.svc.settings.Settings
import me.azimmuradov.svc.utils.drawSpriteBy
import me.azimmuradov.svc.utils.toIntOffset
import kotlin.math.floor


@Composable
fun EditorLayout(
    svc: Svc,
    options: Options,
    settings: Settings,
    modifier: Modifier = Modifier,
) {
    val (w, h) = svc.layout.size

    val undefined = -1 to -1

    var mutStepSize by remember { mutableStateOf(-1f) }
    var mutHoveredId by remember { mutableStateOf(undefined) }
    var mutClickedId by remember { mutableStateOf(undefined) }


    val hoveredColor = MaterialTheme.colors.secondary
    val clickedColor = MaterialTheme.colors.secondaryVariant

    Canvas(
        modifier = modifier
            .pointerMoveFilter(
                onMove = { (offsetX, offsetY) ->
                    mutHoveredId = floor(offsetX / mutStepSize).toInt() to floor(offsetY / mutStepSize).toInt()
                    false
                },
                onExit = {
                    mutHoveredId = undefined
                    false
                },
                onEnter = { false },
            )
            .clickable(
                onClick = {
                    mutClickedId = mutHoveredId

                    val inUse = svc.palette.inUse
                    if (inUse != null) svc.put(inUse, mutClickedId.toCoordinate())
                }
            )
    ) {
        mutStepSize = size.width / w

        val stepSize = mutStepSize
        val hoveredId = mutHoveredId
        val clickedId = mutClickedId

        val cellSize = Size(stepSize, stepSize)
        val offsetsW = List(size = w + 1) { it * stepSize }
        val offsetsH = List(size = h + 1) { it * stepSize }


        for ((_, layer) in svc.layers()) {
            for (e in layer.objs) {
                drawSpriteBy(
                    e.rectObj.obj,
                    offset = e.place.toIntOffset() * stepSize,
                    layoutSize = cellSize,
                )
            }
        }


        // Grid

        if (options.showGrid) {
            for (x in offsetsW) {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(x, 0f),
                    end = Offset(x, size.height),
                    pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(2f, 2f)),
                )
            }
            for (y in offsetsH) {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(2f, 2f)),
                )
            }
        }


        // Hovered cell

        if (hoveredId != undefined) {
            val (hoveredX, hoveredY) = hoveredId

            drawRect(
                color = hoveredColor,
                topLeft = Offset(offsetsW[hoveredX], offsetsH[hoveredY]),
                size = cellSize,
                alpha = 0.5f,
            )


            // Axis

            if (options.showAxis) {
                for (x in hoveredX..hoveredX + 1) {
                    drawLine(
                        color = Color.DarkGray,
                        start = Offset(offsetsW[x], 0f),
                        end = Offset(offsetsW[x], size.height),
                    )
                }
                for (y in hoveredY..hoveredY + 1) {
                    drawLine(
                        color = Color.DarkGray,
                        start = Offset(0f, offsetsH[y]),
                        end = Offset(size.width, offsetsH[y]),
                    )
                }
            }
        }


        // Clicked cell

        // if (clickedId != undefined) {
        //     val (clickedX, clickedY) = clickedId
        //
        //     drawRect(
        //         color = clickedColor,
        //         topLeft = Offset(offsetsW[clickedX], offsetsH[clickedY]),
        //         size = cellSize,
        //     )
        // }


        // Borders

        drawRect(
            color = Color.Black,
            topLeft = Offset(0f, 0f),
            size = Size(width = offsetsW.last(), height = offsetsH.last()),
            style = Stroke(),
        )
    }
}