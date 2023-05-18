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
import me.azimmuradov.svc.cartographer.CartographerIntent
import me.azimmuradov.svc.cartographer.modules.map.MapState
import me.azimmuradov.svc.cartographer.modules.options.OptionsState
import me.azimmuradov.svc.cartographer.modules.toolkit.ToolkitState
import me.azimmuradov.svc.cartographer.res.EntitySpritesProvider
import me.azimmuradov.svc.cartographer.res.LayoutSpritesProvider.layoutSpriteBy
import me.azimmuradov.svc.engine.geometry.*
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layer.coordinates
import me.azimmuradov.svc.engine.layers.flatten
import me.azimmuradov.svc.metadata.EntityPage.Companion.UNIT
import me.azimmuradov.svc.utils.DrawerUtils.drawFlooring
import me.azimmuradov.svc.utils.DrawerUtils.drawSprite
import me.azimmuradov.svc.utils.DrawerUtils.drawVisibleEntities
import me.azimmuradov.svc.utils.DrawerUtils.drawWallpaper
import me.azimmuradov.svc.utils.toIntSize
import me.azimmuradov.svc.utils.toRect
import kotlin.math.floor


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BoxScope.EditorLayout(
    map: MapState,
    visibleLayers: Set<LayerType<*>>,
    toolkit: ToolkitState,
    options: OptionsState,
    intentConsumer: (CartographerIntent) -> Unit,
) {
    val layout = map.layout
    val (nW, nH) = layout.size
    val layoutSprite = layoutSpriteBy(layout.type)

    val hoveredColor = MaterialTheme.colors.secondary

    var stepSize by remember { mutableStateOf(-1f) }
    var hoveredCoordinate by remember { mutableStateOf(UNDEFINED) }
    var prevDragCoordinate by remember { mutableStateOf(UNDEFINED) }


    fun Offset.toCoordinate() = xy(
        x = floor(x / stepSize).toInt().coerceIn(0 until nW),
        y = floor(y / stepSize).toInt().coerceIn(0 until nH),
    )

    fun Offset.toCoordinateStrict() = if (x in 0f..stepSize * nW && y in 0f..stepSize * nH) {
        toCoordinate()
    } else {
        UNDEFINED
    }


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
        stepSize = size.height / nH

        val hoveredId = hoveredCoordinate

        val cellSize = Size(stepSize, stepSize)
        val offsetsW = List(size = nW + 1) { it * stepSize }
        val offsetsH = List(size = nH + 1) { it * stepSize }


        // Background

        drawImage(
            image = layoutSprite.bgImage,
            srcSize = layoutSprite.size,
            dstSize = size.toIntSize(),
            filterQuality = FilterQuality.None,
        )


        // Main content

        if (layout.type.isShed()) {
            drawFlooring(map.flooring, nW, nH, stepSize)
            drawWallpaper(map.wallpaper, nW, stepSize)
        }

        drawVisibleEntities(map.entities, visibleLayers, offsetsW, offsetsH, cellSize)


        // Beeps and Bops

        for (e in map.selectedEntities.flatten().coordinates) {
            drawRect(
                color = Color.Blue,
                topLeft = Offset(x = offsetsW[e.x], y = offsetsH[e.y]),
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
                            y = offsetsH[e.place.y - (rect.h - e.rectObject.size.h)].toInt() + 2
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
                    val sprite = EntitySpritesProvider.spriteBy(e.rectObject)
                    val rect = (sprite.size / UNIT).toRect()
                    drawSprite(
                        sprite = sprite,
                        offset = IntOffset(
                            x = offsetsW[e.place.x].toInt(),
                            y = offsetsH[e.place.y - (rect.h - e.rectObject.size.h)].toInt()
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


        // Foreground

        drawImage(
            image = layoutSprite.fgImage,
            srcSize = layoutSprite.size,
            dstSize = size.toIntSize(),
            filterQuality = FilterQuality.None,
        )


        // Grid

        if (options.showGrid) {
            for (x in offsetsW) {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(x, y = 0f),
                    end = Offset(x, y = size.height),
                    pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(2f, 2f)),
                )
            }
            for (y in (0..nH).map(offsetsH::get)) {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(x = 0f, y),
                    end = Offset(x = size.width, y),
                    pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(2f, 2f)),
                )
            }
        }

        // Hovered cell & Axis

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
                    topLeft = Offset(x = offsetsW[hoveredX], y = 0f),
                    size = Size(cellSize.width, size.height),
                    color = Color.DarkGray,
                    style = Stroke(width = 2f)
                )
                drawRect(
                    topLeft = Offset(x = 0f, y = offsetsH[hoveredY]),
                    size = Size(size.width, cellSize.height),
                    color = Color.DarkGray,
                    style = Stroke(width = 2f)
                )
            }
        }
    }
}


private val UNDEFINED: Coordinate = xy(-1, -1)
