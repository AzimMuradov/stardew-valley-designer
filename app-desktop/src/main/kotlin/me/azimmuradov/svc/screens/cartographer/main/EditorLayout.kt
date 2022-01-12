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

package me.azimmuradov.svc.screens.cartographer.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.unit.toOffset
import me.azimmuradov.svc.cartographer.state.ToolkitState
import me.azimmuradov.svc.cartographer.wishes.SvcWish
import me.azimmuradov.svc.components.screens.cartographer.Options
import me.azimmuradov.svc.engine.geometry.*
import me.azimmuradov.svc.engine.layers.LayeredEntitiesData
import me.azimmuradov.svc.engine.layers.flatten
import me.azimmuradov.svc.utils.drawSpriteBy
import me.azimmuradov.svc.utils.toIntOffset
import kotlin.math.floor


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BoxScope.EditorLayout(
    layoutSize: Rect,
    visibleEntities: LayeredEntitiesData,
    toolkit: ToolkitState,
    options: Options,
    wishConsumer: (SvcWish) -> Unit,
) {
    val (w, h) = layoutSize

    var stepSize by remember { mutableStateOf(-1f) }
    var hoveredCoordinate by remember { mutableStateOf(UNDEFINED) }
    var prevDragCoordinate by remember { mutableStateOf(UNDEFINED) }


    val hoveredColor = MaterialTheme.colors.secondary

    fun Offset.toCoordinate() = xy(
        x = floor(x / stepSize).toInt().coerceIn(0 until w),
        y = floor(y / stepSize).toInt().coerceIn(0 until h),
    )


    Canvas(
        modifier = Modifier
            .aspectRatio(layoutSize.aspectRatio)
            .fillMaxSize()
            .align(Alignment.Center)
            // .clipToBounds()
            .background(color = Color.White)
            .pointerMoveFilter(
                onEnter = { false },
                onMove = { offset -> hoveredCoordinate = offset.toCoordinate(); false },
                onExit = { hoveredCoordinate = UNDEFINED; false },
            )
            .pointerInput(toolkit.tool) {
                detectDragGesturesImmediately(
                    onDragStart = { offset ->
                        val current = offset.toCoordinate()
                        prevDragCoordinate = current
                        wishConsumer(SvcWish.Act.Start(current))
                    },
                    onDrag = { change, _ ->
                        val current = change.position.toCoordinate()
                        if (current != prevDragCoordinate) {
                            prevDragCoordinate = current
                            wishConsumer(SvcWish.Act.Continue(current))
                        }
                    },
                    onDragEnd = {
                        prevDragCoordinate = UNDEFINED
                        wishConsumer(SvcWish.Act.End)
                    },
                    onDragCancel = {
                        prevDragCoordinate = UNDEFINED
                        wishConsumer(SvcWish.Act.End)
                    },
                )
            }
    ) {
        stepSize = size.width / w

        val hoveredId = hoveredCoordinate

        val cellSize = Size(stepSize, stepSize)
        val offsetsW = List(size = w + 1) { it * stepSize }
        val offsetsH = List(size = h + 1) { it * stepSize }


        // Entities

        for ((_, objs) in visibleEntities.all) {
            for (e in objs) {
                val offset = e.place.toIntOffset() * stepSize

                drawSpriteBy(
                    entity = e.rectObject,
                    offset = offset.copy(x = offset.x + 1, y = offset.y + 1),
                    layoutSize = Size(
                        width = (cellSize.width - 2).coerceAtLeast(1f),
                        height = (cellSize.height - 2).coerceAtLeast(1f)
                    ),
                )
            }
        }


        // Beeps and Bops

        when (toolkit) {
            is ToolkitState.Hand.Point.Idle -> {
                for (e in toolkit.selectedEntities.flatten()) {
                    val offset = e.place.toIntOffset() * stepSize

                    drawRect(
                        color = Color.Blue,
                        topLeft = offset.toOffset(),
                        size = cellSize,
                        alpha = 0.5f
                    )
                }
            }
            is ToolkitState.Hand.Point.Acting -> {
                val es = toolkit.heldEntities.flatten()
                for (e in es) {
                    val offset = e.place.toIntOffset() * stepSize
                    drawSpriteBy(
                        entity = e.rectObject,
                        offset = offset.copy(x = offset.x + 2, y = offset.y + 2),
                        layoutSize = Size(
                            width = (cellSize.width - 4).coerceAtLeast(1f),
                            height = (cellSize.height - 4).coerceAtLeast(1f)
                        ),
                        alpha = 0.7f,
                    )
                }
            }
            is ToolkitState.Pen.Shape.Acting -> {
                val coordinates = toolkit.placedShape.coordinates
                for (c in coordinates) {
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(offsetsW[c.x], offsetsH[c.y]),
                        size = cellSize,
                        alpha = 0.1f,
                    )
                }
                for (c in toolkit.entitiesToDelete) {
                    drawRect(
                        color = Color.Red,
                        topLeft = Offset(offsetsW[c.x], offsetsH[c.y]),
                        size = cellSize,
                        alpha = 0.5f,
                    )
                }
                for (e in toolkit.entitiesToDraw) {
                    val offset = e.place.toIntOffset() * stepSize

                    drawSpriteBy(
                        entity = e.rectObject,
                        offset = offset.copy(x = offset.x + 1, y = offset.y + 1),
                        layoutSize = Size(
                            width = (cellSize.width - 2).coerceAtLeast(1f),
                            height = (cellSize.height - 2).coerceAtLeast(1f)
                        ),
                        alpha = 0.7f
                    )
                }
            }
            is ToolkitState.Eraser.Shape.Acting -> {
                val coordinates = toolkit.placedShape.coordinates
                for (c in coordinates) {
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(offsetsW[c.x], offsetsH[c.y]),
                        size = cellSize,
                        alpha = 0.1f,
                    )
                }
                for (c in toolkit.entitiesToDelete) {
                    drawRect(
                        color = Color.Red,
                        topLeft = Offset(offsetsW[c.x], offsetsH[c.y]),
                        size = cellSize,
                        alpha = 0.5f,
                    )
                }
            }
            is ToolkitState.Select.Shape -> {
                if (toolkit is ToolkitState.Select.Shape.Acting) {
                    val coordinates = toolkit.placedShape.coordinates
                    for (c in coordinates) {
                        drawRect(
                            color = Color.Black,
                            topLeft = Offset(offsetsW[c.x], offsetsH[c.y]),
                            size = cellSize,
                            alpha = 0.1f,
                        )
                    }
                }
                for (e in toolkit.selectedEntities.flatten()) {
                    val offset = e.place.toIntOffset() * stepSize

                    drawRect(
                        color = Color.Blue,
                        topLeft = offset.toOffset(),
                        size = cellSize,
                        alpha = 0.5f
                    )
                }
            }
            else -> Unit
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


private val Rect.aspectRatio get() = w.toFloat() / h.toFloat()