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

package me.azimmuradov.svc.screens.cartographer.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.unit.IntOffset
import me.azimmuradov.svc.cartographer.CartographerIntent
import me.azimmuradov.svc.cartographer.modules.options.OptionsState
import me.azimmuradov.svc.cartographer.modules.toolkit.ToolkitState
import me.azimmuradov.svc.cartographer.res.*
import me.azimmuradov.svc.cartographer.res.providers.*
import me.azimmuradov.svc.engine.geometry.*
import me.azimmuradov.svc.engine.layers.LayeredEntitiesData
import me.azimmuradov.svc.engine.layers.flatten
import me.azimmuradov.svc.engine.layout.LayoutType
import me.azimmuradov.svc.utils.*
import kotlin.math.floor


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BoxScope.EditorLayout(
    layoutSprite: Sprite,
    layoutSize: Rect,
    visibleEntities: LayeredEntitiesData,
    selectedEntities: LayeredEntitiesData,
    toolkit: ToolkitState,
    options: OptionsState,
    intentConsumer: (CartographerIntent) -> Unit,
) {
    val (imgW, imgH) = layoutSprite.size
    val (nW, nH) = layoutSize

    var workingOffset by remember { mutableStateOf(Offset.Unspecified) }
    var workingField by remember { mutableStateOf(Size.Unspecified) }
    var stepSize by remember { mutableStateOf(-1f) }
    var hoveredCoordinate by remember { mutableStateOf(UNDEFINED) }
    var prevDragCoordinate by remember { mutableStateOf(UNDEFINED) }


    val hoveredColor = MaterialTheme.colors.secondary

    fun Offset.toCoordinateStrict() = if (
        x in workingOffset.x..workingOffset.x + stepSize * nW &&
        y in workingOffset.y..workingOffset.y + stepSize * nH
    ) {
        xy(
            x = floor((x - workingOffset.x) / stepSize).toInt().coerceIn(0 until nW),
            y = floor((y - workingOffset.y) / stepSize).toInt().coerceIn(0 until nH),
        )
    } else {
        UNDEFINED
    }

    fun Offset.toCoordinate() = xy(
        x = floor((x - workingOffset.x) / stepSize).toInt().coerceIn(0 until nW),
        y = floor((y - workingOffset.y) / stepSize).toInt().coerceIn(0 until nH),
    )


    Canvas(
        modifier = Modifier
            .aspectRatio(layoutSprite.size.toRect().aspectRatio)
            .fillMaxSize()
            .align(Alignment.Center)
            .clipToBounds()
            .background(color = Color.White)
            .onPointerEvent(PointerEventType.Move) {
                hoveredCoordinate = it.changes.first().position.toCoordinateStrict()
            }
            .onPointerEvent(PointerEventType.Exit) { hoveredCoordinate = UNDEFINED }
            .pointerInput(toolkit.tool) {
                detectDragGesturesImmediately(
                    onDragStart = { offset ->
                        val current = offset.toCoordinateStrict()
                            .takeUnless { it == UNDEFINED } ?: return@detectDragGesturesImmediately
                        prevDragCoordinate = current
                        intentConsumer(CartographerIntent.Engine.Start(current))
                    },
                    onDrag = { change, _ ->
                        val current = change.position.toCoordinate()
                        if (current != prevDragCoordinate) {
                            prevDragCoordinate = current
                            intentConsumer(CartographerIntent.Engine.Continue(current))
                        }
                    },
                    onDragEnd = {
                        prevDragCoordinate = UNDEFINED
                        intentConsumer(CartographerIntent.Engine.End)
                    },
                    onDragCancel = {
                        prevDragCoordinate = UNDEFINED
                        intentConsumer(CartographerIntent.Engine.End)
                    },
                )
            }
    ) {
        workingOffset = Offset(x = size.width * 9 / imgW, y = size.height * 58 / imgH)
        val a = Offset(x = size.width * 9 / imgW, y = size.height * 10 / imgH)
        stepSize = (size.height - workingOffset.y) / nH

        val hoveredId = hoveredCoordinate

        val cellSize = Size(stepSize, stepSize)
        val offsetsW = List(size = nW + 1) { workingOffset.x + it * stepSize }
        val offsetsH = List(size = nH + 1) { workingOffset.y + it * stepSize }


        (1..10).scan(workingOffset.y.toInt() - stepSize.toInt() * 2) { acc, _ -> acc + stepSize.toInt() * 2 }.forEach { yy ->
            (1..10).scan(workingOffset.x.toInt()) { acc, _ -> acc + stepSize.toInt() * 2 }.forEach { xx ->
                drawSprite(
                    sprite = FloorSpritesProvider.floor(2),
                    offset = IntOffset(x = xx, y = yy),
                    layoutSize = Size(
                        width = (cellSize.width * 2).coerceAtLeast(1f),
                        height = (cellSize.height * 2).coerceAtLeast(1f)
                    ),
                )
            }
        }

        (1..nW + 1).scan(workingOffset.x.toInt()) { acc, _ -> acc + stepSize.toInt() }.forEach {
            drawSprite(
                sprite = WallpaperSpritesProvider.wallpaper(1),
                offset = IntOffset(x = it, y = a.y.toInt()),
                layoutSize = Size(
                    width = (cellSize.width).coerceAtLeast(1f),
                    height = (cellSize.height * 3).coerceAtLeast(1f)
                ),
            )
        }


        // Entities

        for ((_, objs) in visibleEntities.all) {
            for (e in objs) {
                drawSpriteBy(
                    entity = e.rectObject,
                    offset = IntOffset(
                        x = offsetsW[e.place.x].toInt() + 1,
                        y = offsetsH[e.place.y].toInt() + 1
                    ),
                    layoutSize = Size(
                        width = (cellSize.width - 2).coerceAtLeast(1f),
                        height = (cellSize.height - 2).coerceAtLeast(1f)
                    ),
                )
            }
        }


        // Beeps and Bops

        for (e in selectedEntities.flatten()) {
            drawRect(
                color = Color.Blue,
                topLeft = Offset(x = offsetsW[e.place.x], y = offsetsH[e.place.y]),
                size = cellSize,
                alpha = 0.3f
            )
        }

        when (toolkit) {
            is ToolkitState.Hand.Point.Acting -> {
                val es = toolkit.heldEntities.flatten()
                for (e in es) {
                    drawSpriteBy(
                        entity = e.rectObject,
                        offset = IntOffset(
                            x = offsetsW[e.place.x].toInt() + 2,
                            y = offsetsH[e.place.y].toInt() + 2
                        ),
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
                    drawSpriteBy(
                        entity = e.rectObject,
                        offset = IntOffset(
                            x = offsetsW[e.place.x].toInt() + 1,
                            y = offsetsH[e.place.y].toInt() + 1
                        ),
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
            }

            else -> Unit
        }


        // Grid

        if (options.showGrid) {
            for (x in offsetsW) {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(x, y = workingOffset.y),
                    end = Offset(x, size.height),
                    pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(2f, 2f)),
                )
            }
            for (y in offsetsH) {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(x = workingOffset.x, y),
                    end = Offset(size.width - workingOffset.x, y),
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
                drawRect(
                    topLeft = Offset(offsetsW[hoveredX] - 1, workingOffset.y - 1),
                    size = Size(cellSize.width, size.height - workingOffset.y),
                    color = Color.DarkGray,
                    style = Stroke(width = 4f)
                )
                drawRect(
                    topLeft = Offset(workingOffset.x - 1, offsetsH[hoveredY] - 1),
                    size = Size(size.width - workingOffset.x * 2, cellSize.height),
                    color = Color.DarkGray,
                    style = Stroke(width = 4f)
                )
            }
        }


        drawSprite(
            sprite = LayoutSpritesProvider.layoutSpriteBy(LayoutType.BigShed),
            layoutSize = size
        )
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
                    if (it.positionChange() != Offset.Zero) it.consume()
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
