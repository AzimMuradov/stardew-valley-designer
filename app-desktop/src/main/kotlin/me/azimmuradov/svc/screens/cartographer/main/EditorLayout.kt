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
import androidx.compose.foundation.gestures.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.*
import me.azimmuradov.svc.cartographer.Svc
import me.azimmuradov.svc.cartographer.toolkit.Tool
import me.azimmuradov.svc.components.screens.cartographer.Options
import me.azimmuradov.svc.engine.rectmap.Coordinate
import me.azimmuradov.svc.engine.rectmap.xy
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

    var mutStepSize by remember { mutableStateOf(-1f) }
    var mutHoveredId by remember { mutableStateOf(UNDEFINED) }


    val hoveredColor = MaterialTheme.colors.secondary

    fun Offset.toCoordinate() = xy(
        floor(x / mutStepSize).toInt().coerceIn(0 until w),
        floor(y / mutStepSize).toInt().coerceIn(0 until h),
    )

    Canvas(
        modifier = modifier
            .pointerMoveFilter(
                onEnter = { false },
                onMove = { offset -> mutHoveredId = offset.toCoordinate(); false },
                onExit = { mutHoveredId = UNDEFINED; false },
            )
            .pointerInput(Unit) {
                detectDragGesturesImmediately(
                    onDragStart = { offset ->
                        svc.toolkit.tool?.start(offset.toCoordinate())
                    },
                    onDrag = { change, _ ->
                        val tool = svc.toolkit.tool
                        if (tool != null && tool.state == Tool.State.Acting) {
                            tool.keep(change.position.toCoordinate())
                        }
                    },
                    onDragEnd = {
                        val tool = svc.toolkit.tool
                        if (tool != null && tool.state == Tool.State.Acting) {
                            tool.end()
                        }
                    },
                    onDragCancel = {
                        val tool = svc.toolkit.tool
                        if (tool != null && tool.state == Tool.State.Acting) {
                            tool.end()
                        }
                    },
                )
            }
    ) {
        mutStepSize = size.width / w

        val stepSize = mutStepSize
        val hoveredId = mutHoveredId

        val cellSize = Size(stepSize, stepSize)
        val offsetsW = List(size = w + 1) { it * stepSize }
        val offsetsH = List(size = h + 1) { it * stepSize }


        // Entities

        for ((_, layer) in svc.layers) {
            for (e in layer.objs) {
                val offset = e.place.toIntOffset() * stepSize

                drawSpriteBy(
                    entity = e.rectObj,
                    offset = offset.copy(x = offset.x + 2, y = offset.y + 2),
                    layoutSize = Size(width = cellSize.width - 4, height = cellSize.height - 4),
                )
            }
        }


        // Grid

        if (options.showGrid) {
            for (x in offsetsW) {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(x, y = 0f),
                    end = Offset(x, size.height),
                    pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(2f, 2f)),
                )
            }
            for (y in offsetsH) {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(x = 0f, y),
                    end = Offset(size.width, y),
                    pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(2f, 2f)),
                )
            }
        }


        // Hovered cell

        if (hoveredId != UNDEFINED) {
            val (hoveredX, hoveredY) = hoveredId

            drawRect(
                color = hoveredColor,
                topLeft = Offset(offsetsW[hoveredX], offsetsH[hoveredY]),
                size = cellSize,
                alpha = 0.3f,
            )


            // Held entities

            for (e in svc.heldEntities) {
                val offset = e.place.toIntOffset() * stepSize

                drawSpriteBy(
                    entity = e.rectObj,
                    offset = offset.copy(x = offset.x + 4, y = offset.y + 4),
                    layoutSize = Size(width = cellSize.width - 8, height = cellSize.height - 8),
                    alpha = 0.7f,
                )
            }


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


        // Borders

        drawRect(color = Color.Black, style = Stroke())
    }
}


private val UNDEFINED: Coordinate = xy(-1, -1)

private suspend fun PointerInputScope.detectDragGesturesImmediately(
    onDragStart: (Offset) -> Unit = { },
    onDragEnd: () -> Unit = { },
    onDragCancel: () -> Unit = { },
    onDrag: (change: PointerInputChange, dragAmount: Offset) -> Unit,
) {
    forEachGesture {
        awaitPointerEventScope {
            val drag = awaitFirstDown(requireUnconsumed = false)

            onDragStart(drag.position)

            if (
                drag(drag.id) {
                    onDrag(it, it.positionChange())
                    it.consumePositionChange()
                }
            ) {
                onDragEnd()
            } else {
                onDragCancel()
            }
        }
    }
}