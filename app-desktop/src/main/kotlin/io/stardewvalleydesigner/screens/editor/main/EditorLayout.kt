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

package io.stardewvalleydesigner.screens.editor.main

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
import io.stardewvalleydesigner.editor.EditorIntent
import io.stardewvalleydesigner.editor.modules.map.MapState
import io.stardewvalleydesigner.editor.modules.options.OptionsState
import io.stardewvalleydesigner.editor.modules.toolkit.ToolkitState
import io.stardewvalleydesigner.editor.res.EntitySpritesProvider
import io.stardewvalleydesigner.editor.res.LayoutSpritesProvider.layoutSpriteBy
import io.stardewvalleydesigner.engine.geometry.*
import io.stardewvalleydesigner.engine.layer.LayerType
import io.stardewvalleydesigner.engine.layer.coordinates
import io.stardewvalleydesigner.engine.layers.flatten
import io.stardewvalleydesigner.metadata.EntityPage.Companion.UNIT
import io.stardewvalleydesigner.utils.DrawerUtils.drawFlooring
import io.stardewvalleydesigner.utils.DrawerUtils.drawSprite
import io.stardewvalleydesigner.utils.DrawerUtils.drawVisibleEntities
import io.stardewvalleydesigner.utils.DrawerUtils.drawWallpaper
import io.stardewvalleydesigner.utils.DrawerUtils.placedEntityComparator
import io.stardewvalleydesigner.utils.toIntSize
import io.stardewvalleydesigner.utils.toRect
import kotlin.math.floor


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BoxScope.EditorLayout(
    map: MapState,
    visibleLayers: Set<LayerType<*>>,
    toolkit: ToolkitState,
    options: OptionsState,
    intentConsumer: (EditorIntent) -> Unit,
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
                intentConsumer(EditorIntent.Engine.Start(current))
            }
            .onPointerEvent(eventType = PointerEventType.Release) {
                prevDragCoordinate = UNDEFINED
                intentConsumer(EditorIntent.Engine.End)
            }
            .pointerInput(toolkit.tool) {
                detectDragGestures(
                    onDrag = { change, _ ->
                        val current = change.position.toCoordinate()
                        if (current != prevDragCoordinate) {
                            prevDragCoordinate = current
                            intentConsumer(EditorIntent.Engine.Continue(current))
                        }
                    },
                    onDragCancel = {
                        prevDragCoordinate = UNDEFINED
                        intentConsumer(EditorIntent.Engine.End)
                    }
                )
            }
    ) {
        stepSize = size.height / nH

        val hoveredId = hoveredCoordinate

        val cellSize = Size(stepSize, stepSize)
        val offsetsW = List(size = nW + 1) { it * stepSize }
        val offsetsH = (-nH..nH).associateWith { it * stepSize }


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

        drawVisibleEntities(
            entities = map.entities,
            visibleLayers = visibleLayers,
            renderSpritesFully = options.showSpritesFully,
            offsetsW = offsetsW,
            offsetsH = offsetsH,
            cellSize = cellSize
        )


        // Beeps and Bops

        for (e in map.selectedEntities.flatten().coordinates) {
            drawRect(
                color = Color.Blue,
                topLeft = Offset(x = offsetsW[e.x], y = offsetsH.getValue(e.y)),
                size = cellSize,
                alpha = 0.3f
            )
        }

        when (toolkit) {
            is ToolkitState.Hand.Point.Acting -> {
                val sorted = toolkit.heldEntities.flatten().sortedWith(placedEntityComparator)
                for ((e, place) in sorted) {
                    val sprite = EntitySpritesProvider.spriteBy(e).run {
                        if (options.showSpritesFully) {
                            this
                        } else {
                            copy(
                                offset = offset.copy(y = offset.y + (size.height - e.size.h * UNIT)),
                                size = e.size.toIntSize() * UNIT
                            )
                        }
                    }
                    val rect = (sprite.size / UNIT).toRect()
                    drawSprite(
                        sprite = sprite,
                        offset = IntOffset(
                            x = offsetsW[place.x].toInt() + 2,
                            y = offsetsH.getValue(place.y - (rect.h - e.size.h)).toInt() + 2
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
                for (c in toolkit.placedShape.coordinates) {
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(offsetsW[c.x], offsetsH.getValue(c.y)),
                        size = cellSize,
                        alpha = 0.1f,
                    )
                }
                for (c in toolkit.entitiesToDelete) {
                    drawRect(
                        color = Color.Red,
                        topLeft = Offset(offsetsW[c.x], offsetsH.getValue(c.y)),
                        size = cellSize,
                        alpha = 0.5f,
                    )
                }
                for ((e, place) in toolkit.entitiesToDraw) {
                    val sprite = EntitySpritesProvider.spriteBy(e).run {
                        if (options.showSpritesFully) {
                            this
                        } else {
                            copy(
                                offset = offset.copy(y = offset.y + (size.height - e.size.h * UNIT)),
                                size = e.size.toIntSize() * UNIT
                            )
                        }
                    }
                    val rect = (sprite.size / UNIT).toRect()
                    drawSprite(
                        sprite = sprite,
                        offset = IntOffset(
                            x = offsetsW[place.x].toInt(),
                            y = offsetsH.getValue(place.y - (rect.h - e.size.h)).toInt()
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
                for (c in toolkit.placedShape.coordinates) {
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(offsetsW[c.x], offsetsH.getValue(c.y)),
                        size = cellSize,
                        alpha = 0.1f,
                    )
                }
                for (c in toolkit.entitiesToDelete) {
                    drawRect(
                        color = Color.Red,
                        topLeft = Offset(offsetsW[c.x], offsetsH.getValue(c.y)),
                        size = cellSize,
                        alpha = 0.5f,
                    )
                }
            }

            is ToolkitState.Select.Shape -> {
                if (toolkit is ToolkitState.Select.Shape.Acting) {
                    for (c in toolkit.placedShape.coordinates) {
                        drawRect(
                            color = Color.Black,
                            topLeft = Offset(offsetsW[c.x], offsetsH.getValue(c.y)),
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
            for (y in (0..nH).map(offsetsH::getValue)) {
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
                topLeft = Offset(offsetsW[hoveredX], offsetsH.getValue(hoveredY)),
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
                    topLeft = Offset(x = 0f, y = offsetsH.getValue(hoveredY)),
                    size = Size(size.width, cellSize.height),
                    color = Color.DarkGray,
                    style = Stroke(width = 2f)
                )
            }
        }
    }
}


private val UNDEFINED: Coordinate = xy(Int.MAX_VALUE, Int.MAX_VALUE)
