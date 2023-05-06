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
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import me.azimmuradov.svc.cartographer.CartographerIntent
import me.azimmuradov.svc.cartographer.modules.options.OptionsState
import me.azimmuradov.svc.cartographer.modules.toolkit.ToolkitState
import me.azimmuradov.svc.cartographer.res.*
import me.azimmuradov.svc.engine.Flooring
import me.azimmuradov.svc.engine.Wallpaper
import me.azimmuradov.svc.engine.geometry.*
import me.azimmuradov.svc.engine.layer.coordinates
import me.azimmuradov.svc.engine.layers.LayeredEntitiesData
import me.azimmuradov.svc.engine.layers.flatten
import me.azimmuradov.svc.metadata.EntityPage.Companion.UNIT
import me.azimmuradov.svc.utils.drawSprite
import me.azimmuradov.svc.utils.toRect
import kotlin.math.floor
import kotlin.math.roundToInt


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BoxScope.EditorLayout(
    layoutSprite: Sprite,
    layoutSize: Rect,
    visibleEntities: LayeredEntitiesData,
    selectedEntities: LayeredEntitiesData,
    wallpaper: Wallpaper?,
    flooring: Flooring?,
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
            // .size(layoutSprite.size.width.dp * 2, layoutSprite.size.height.dp * 2)
            .fillMaxSize()
            .align(Alignment.Center)
            .clipToBounds()
            .background(color = Color.White)
            .onPointerEvent(PointerEventType.Move) {
                hoveredCoordinate = it.changes.first().position.toCoordinateStrict()
            }
            .onPointerEvent(PointerEventType.Exit) { hoveredCoordinate = UNDEFINED }
            .onPointerEvent(eventType = PointerEventType.Press) {
                val current = it.changes.first().position.toCoordinateStrict()
                    .takeUnless { it == UNDEFINED } ?: return@onPointerEvent
                prevDragCoordinate = current
                intentConsumer(CartographerIntent.Engine.Start(current))
            }
            .onPointerEvent(eventType = PointerEventType.Release) {
                prevDragCoordinate = UNDEFINED
                intentConsumer(CartographerIntent.Engine.End)
            }
            .pointerInput(toolkit.tool) {
                detectDragGestures(
                    onDrag = { change, _ ->
                        val current = change.position.toCoordinate()
                        if (current != prevDragCoordinate) {
                            prevDragCoordinate = current
                            intentConsumer(CartographerIntent.Engine.Continue(current))
                        }
                    },
                    onDragCancel = {
                        prevDragCoordinate = UNDEFINED
                        intentConsumer(CartographerIntent.Engine.End)
                    }
                )
            }
    ) {
        workingOffset = Offset(x = size.width * 9 / imgW, y = size.height * 58 / imgH)
        val a = Offset(x = size.width * 9 / imgW, y = size.height * 10 / imgH)
        stepSize = (size.height - workingOffset.y) / nH

        val hoveredId = hoveredCoordinate

        val cellSize = Size(stepSize, stepSize)
        val offsetsW = List(size = nW + 1) { workingOffset.x + it * stepSize }
        val offsetsH = List(size = nH * 2 + 1) { (it - nH) to (workingOffset.y + (it - nH) * stepSize) }.toMap()


        // Bottom layer

        // Flooring

        val off1 = (List(nW) { workingOffset.x + stepSize * 2 * it })
            .map { it.roundToInt() }

        val off2 = (List(nH) { workingOffset.y + stepSize * 2 * (it - 1) })
            .map { it.roundToInt() }

        off1.zipWithNext().forEach { (st1, en1) ->
            off2.zipWithNext().forEach { (st2, en2) ->
                val sprite = flooring(flooring ?: Flooring.all().first())

                drawImage(
                    image = sprite.image,
                    srcOffset = sprite.offset,
                    srcSize = sprite.size,
                    dstOffset = IntOffset(x = st1, y = st2),
                    dstSize = IntSize(width = (en1 - st1), height = (en2 - st2)),
                    filterQuality = FilterQuality.None,
                )
            }
        }


        // Wallpaper

        val off = (List(nW) { workingOffset.x + stepSize * it } + (size.width * (imgW - 9) / imgW))
            .map { it.roundToInt() }

        off.zipWithNext().forEach { (st, en) ->
            val sprite = wallpaper(wallpaper ?: Wallpaper.all().first())

            drawImage(
                image = sprite.image,
                srcOffset = sprite.offset,
                srcSize = sprite.size,
                dstOffset = IntOffset(x = st, y = a.y.roundToInt()),
                dstSize = IntSize(width = (en - st), height = (stepSize * 3).roundToInt()),
                filterQuality = FilterQuality.None,
            )
        }


        // Entities

        for ((_, objs) in visibleEntities.all) {
            for (e in objs.sortedBy { it.place.y }) {
                val sprite = EntitySpritesProvider.spriteBy(e.rectObject)
                val rect = (sprite.size / UNIT).toRect()
                drawSprite(
                    sprite = sprite,
                    offset = IntOffset(
                        x = offsetsW[e.place.x].toInt(),
                        y = offsetsH[e.place.y - (rect.h - e.rectObject.size.h)]!!.toInt()
                    ),
                    layoutSize = Size(
                        width = (cellSize.width * rect.w).coerceAtLeast(1f),
                        height = (cellSize.height * rect.h).coerceAtLeast(1f)
                    ),
                )
            }
        }


        // Beeps and Bops

        for (e in selectedEntities.flatten().coordinates) {
            drawRect(
                color = Color.Blue,
                topLeft = Offset(x = offsetsW[e.x], y = offsetsH[e.y]!!),
                size = cellSize,
                alpha = 0.3f
            )
        }

        when (toolkit) {
            is ToolkitState.Hand.Point.Acting -> {
                for (e in toolkit.heldEntities.flatten()) {
                    val sprite = EntitySpritesProvider.spriteBy(e.rectObject)
                    val rect = (sprite.size / UNIT).toRect()
                    drawSprite(
                        sprite = sprite,
                        offset = IntOffset(
                            x = offsetsW[e.place.x].toInt() + 2,
                            y = offsetsH[e.place.y - (rect.h - e.rectObject.size.h)]!!.toInt() + 2
                        ),
                        layoutSize = Size(
                            width = (cellSize.width * rect.w - 4).coerceAtLeast(1f),
                            height = (cellSize.height * rect.h - 4).coerceAtLeast(1f)
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
                        topLeft = Offset(offsetsW[c.x], offsetsH[c.y]!!),
                        size = cellSize,
                        alpha = 0.1f,
                    )
                }
                for (c in toolkit.entitiesToDelete) {
                    drawRect(
                        color = Color.Red,
                        topLeft = Offset(offsetsW[c.x], offsetsH[c.y]!!),
                        size = cellSize,
                        alpha = 0.5f,
                    )
                }
                for (e in toolkit.entitiesToDraw) {
                    val sprite = EntitySpritesProvider.spriteBy(e.rectObject)
                    val rect = (sprite.size / UNIT).toRect()
                    drawSprite(
                        sprite = sprite,
                        offset = IntOffset(
                            x = offsetsW[e.place.x].toInt(),
                            y = offsetsH[e.place.y - (rect.h - e.rectObject.size.h)]!!.toInt()
                        ),
                        layoutSize = Size(
                            width = (cellSize.width * rect.w).coerceAtLeast(1f),
                            height = (cellSize.height * rect.h).coerceAtLeast(1f)
                        ),
                        alpha = 0.7f,
                    )
                }
            }

            is ToolkitState.Eraser.Shape.Acting -> {
                val coordinates = toolkit.placedShape.coordinates
                for (c in coordinates) {
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(offsetsW[c.x], offsetsH[c.y]!!),
                        size = cellSize,
                        alpha = 0.1f,
                    )
                }
                for (c in toolkit.entitiesToDelete) {
                    drawRect(
                        color = Color.Red,
                        topLeft = Offset(offsetsW[c.x], offsetsH[c.y]!!),
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
                            topLeft = Offset(offsetsW[c.x], offsetsH[c.y]!!),
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
            for (y in (0..nH).map(offsetsH::getValue)) {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(x = workingOffset.x, y),
                    end = Offset(size.width - workingOffset.x, y),
                    pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(2f, 2f)),
                )
            }
        }


        // Hovered cell & Axis

        if (hoveredId != UNDEFINED) {
            val (hoveredX, hoveredY) = hoveredId

            drawRect(
                color = hoveredColor,
                topLeft = Offset(offsetsW[hoveredX], offsetsH[hoveredY]!!),
                size = cellSize,
                alpha = 0.3f,
            )


            // Axis

            if (options.showAxis) {
                drawRect(
                    topLeft = Offset(offsetsW[hoveredX], workingOffset.y),
                    size = Size(cellSize.width, size.height - workingOffset.y),
                    color = Color.DarkGray,
                    style = Stroke(width = 2f)
                )
                drawRect(
                    topLeft = Offset(workingOffset.x, offsetsH[hoveredY]!!),
                    size = Size(size.width - workingOffset.x * 2, cellSize.height),
                    color = Color.DarkGray,
                    style = Stroke(width = 2f)
                )
            }
        }


        // Top layer

        drawSprite(
            sprite = layoutSprite,
            layoutSize = size
        )
    }
}


private val UNDEFINED: Coordinate = xy(-1, -1)
