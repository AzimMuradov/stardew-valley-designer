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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.unit.IntOffset
import io.stardewvalleydesigner.editor.EditorIntent
import io.stardewvalleydesigner.editor.menus.OptionsItemValue.Toggleable
import io.stardewvalleydesigner.editor.modules.map.MapState
import io.stardewvalleydesigner.editor.modules.options.OptionsState
import io.stardewvalleydesigner.editor.modules.toolkit.ToolkitState
import io.stardewvalleydesigner.editor.res.EntitySpritesProvider
import io.stardewvalleydesigner.editor.res.LayoutSpritesProvider.layoutSpriteBy
import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.geometry.*
import io.stardewvalleydesigner.engine.geometry.shapes.*
import io.stardewvalleydesigner.engine.layer.LayerType
import io.stardewvalleydesigner.engine.layer.coordinates
import io.stardewvalleydesigner.engine.layers.flatten
import io.stardewvalleydesigner.metadata.EntityPage.Companion.UNIT
import io.stardewvalleydesigner.utils.*
import io.stardewvalleydesigner.utils.DrawerUtils.drawFlooring
import io.stardewvalleydesigner.utils.DrawerUtils.drawSprite
import io.stardewvalleydesigner.utils.DrawerUtils.drawVisibleEntities
import io.stardewvalleydesigner.utils.DrawerUtils.drawWallpaper
import io.stardewvalleydesigner.utils.DrawerUtils.placedEntityComparator
import kotlin.math.floor


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditorLayout(
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

    var cellSide by remember { mutableStateOf(-1f) }
    var currCoordinate by remember { mutableStateOf(UNDEFINED) }
    var prevDragCoordinate by remember { mutableStateOf(UNDEFINED) }


    fun Offset.toCoordinate() = xy(
        x = floor(x / cellSide).toInt().coerceIn(0 until nW),
        y = floor(y / cellSide).toInt().coerceIn(0 until nH),
    )

    fun Offset.toCoordinateStrict() = if (x in 0f..cellSide * nW && y in 0f..cellSide * nH) {
        toCoordinate()
    } else {
        UNDEFINED
    }


    Canvas(
        modifier = Modifier
            .aspectRatio(layoutSprite.size.toRect().aspectRatio)
            .fillMaxSize()
            .clipToBounds()
            .background(color = MaterialTheme.colors.surface)
            // Hovered cell
            .onPointerEvent(PointerEventType.Move) { event ->
                currCoordinate = event.changes.first().position.toCoordinateStrict()
            }
            .onPointerEvent(PointerEventType.Exit) { currCoordinate = UNDEFINED }
            // Tools logic
            .onPointerEvent(eventType = PointerEventType.Press) { event ->
                val current = event.changes.first().position.toCoordinateStrict()
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
                            intentConsumer(EditorIntent.Engine.Keep(current))
                        }
                    },
                    onDragCancel = {
                        prevDragCoordinate = UNDEFINED
                        intentConsumer(EditorIntent.Engine.End)
                    }
                )
            }
    ) {
        cellSide = size.height / nH

        val cellSize = Size(cellSide, cellSide)
        val grid = CoordinateGrid(cellSide)


        // Background

        drawImage(
            image = layoutSprite.bgImage,
            srcSize = layoutSprite.size,
            dstSize = size.toIntSize(),
            filterQuality = FilterQuality.None,
        )


        // Main content

        if (layout.type.isShed()) {
            drawFlooring(map.flooring, nW, nH, cellSide)
            drawWallpaper(map.wallpaper, nW, cellSide)
        }

        drawVisibleEntities(
            entities = map.entities,
            visibleLayers = visibleLayers,
            renderSpritesFully = options.toggleables.getValue(Toggleable.ShowSpritesFully),
            grid = grid,
            cellSize = cellSize
        )

        drawSpecificSpritesAndEffects(map, toolkit, options, grid, cellSize)


        // Foreground

        drawImage(
            image = layoutSprite.fgImage,
            srcSize = layoutSprite.size,
            dstSize = size.toIntSize(),
            filterQuality = FilterQuality.None,
        )


        // Hints and visual guides

        drawAreasOfEffects(map, options, grid, cellSize)

        drawGrid(options, grid, nW, nH)

        drawHoveredCellAndAxis(options, grid, cellSize, currCoordinate, hoveredColor)
    }
}


private fun DrawScope.drawSpecificSpritesAndEffects(
    map: MapState,
    toolkit: ToolkitState,
    options: OptionsState,
    grid: CoordinateGrid,
    cellSize: Size,
) {
    for (c in map.selectedEntities.flatten().coordinates) {
        drawRect(
            color = Color.Blue,
            topLeft = grid[c],
            size = cellSize,
            alpha = 0.3f
        )
    }

    when (toolkit) {
        is ToolkitState.Hand.Point.Acting -> {
            val sorted = toolkit.heldEntities.flatten().sortedWith(placedEntityComparator)
            for ((e, place) in sorted) {
                val sprite = EntitySpritesProvider.spriteBy(e).run {
                    if (options.toggleables.getValue(Toggleable.ShowSpritesFully)) {
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
                        x = grid.getX(place.x).toInt() + 2,
                        y = grid.getY(place.y - (rect.h - e.size.h)).toInt() + 2
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
                    topLeft = grid[c],
                    size = cellSize,
                    alpha = 0.1f,
                )
            }
            for (c in toolkit.entitiesToDelete) {
                drawRect(
                    color = Color.Red,
                    topLeft = grid[c],
                    size = cellSize,
                    alpha = 0.5f,
                )
            }
            for ((e, place) in toolkit.entitiesToDraw) {
                val sprite = EntitySpritesProvider.spriteBy(e).run {
                    if (options.toggleables.getValue(Toggleable.ShowSpritesFully)) {
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
                        x = grid.getX(place.x).toInt(),
                        y = grid.getY(place.y - (rect.h - e.size.h)).toInt()
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
                    topLeft = grid[c],
                    size = cellSize,
                    alpha = 0.1f,
                )
            }
            for (c in toolkit.entitiesToDelete) {
                drawRect(
                    color = Color.Red,
                    topLeft = grid[c],
                    size = cellSize,
                    alpha = 0.5f,
                )
            }
        }

        is ToolkitState.Select.Shape.Acting -> {
            for (c in toolkit.placedShape.coordinates) {
                drawRect(
                    color = Color.Black,
                    topLeft = grid[c],
                    size = cellSize,
                    alpha = 0.1f,
                )
            }
        }

        else -> Unit
    }
}

private fun DrawScope.drawAreasOfEffects(
    map: MapState,
    options: OptionsState,
    grid: CoordinateGrid,
    cellSize: Size,
) {
    val es = map.entities.flatten().asSequence() + map.selectedEntities.flatten().asSequence()

    fun drawAreaOfEffect(
        allowedEs: Set<Entity<*>>,
        getAreaOfEffect: (PlacedEntity<*>) -> Iterable<Coordinate>,
        color: Color,
    ) {
        for (c in es.filter { (e, _) -> e in allowedEs }.flatMapTo(mutableSetOf(), getAreaOfEffect)) {
            drawRect(
                color = color,
                topLeft = grid[c],
                size = cellSize,
                alpha = 0.3f,
            )
        }
    }

    if (options.toggleables.getValue(Toggleable.ShowScarecrowsAreaOfEffect)) {
        drawAreaOfEffect(
            allowedEs = setOf(
                Equipment.SimpleEquipment.Scarecrow,
                Equipment.SimpleEquipment.DeluxeScarecrow,
                Equipment.SimpleEquipment.Rarecrow1,
                Equipment.SimpleEquipment.Rarecrow2,
                Equipment.SimpleEquipment.Rarecrow3,
                Equipment.SimpleEquipment.Rarecrow4,
                Equipment.SimpleEquipment.Rarecrow5,
                Equipment.SimpleEquipment.Rarecrow6,
                Equipment.SimpleEquipment.Rarecrow7,
                Equipment.SimpleEquipment.Rarecrow8,
            ),
            getAreaOfEffect = { (e, place) ->
                val r = if (e == Equipment.SimpleEquipment.DeluxeScarecrow) 17 else 9

                PlacedRectStrategy.from(
                    place,
                    radius = if (e == Equipment.SimpleEquipment.DeluxeScarecrow) 16u else 8u
                ).filter { (it - place).length < r }
            },
            color = Color.Green,
        )
    }

    if (options.toggleables.getValue(Toggleable.ShowSprinklersAreaOfEffect)) {
        drawAreaOfEffect(
            allowedEs = setOf(
                Equipment.SimpleEquipment.Sprinkler,
                Equipment.SimpleEquipment.QualitySprinkler,
                Equipment.SimpleEquipment.IridiumSprinkler,
                // Equipment.SimpleEquipment.IridiumSprinklerWithPressureNozzle,
            ),
            getAreaOfEffect = { (e, place) ->
                when (e) {
                    Equipment.SimpleEquipment.Sprinkler -> setOf(
                        place - vec(1, 0),
                        place - vec(0, 1),
                        place,
                        place + vec(1, 0),
                        place + vec(0, 1),
                    )

                    Equipment.SimpleEquipment.QualitySprinkler -> PlacedRectStrategy.from(
                        place,
                        radius = 1u
                    )

                    Equipment.SimpleEquipment.IridiumSprinkler -> PlacedRectStrategy.from(
                        place,
                        radius = 2u
                    )

                    else -> PlacedRectStrategy.from(
                        place,
                        radius = 3u
                    )
                }
            },
            color = Color.Blue,
        )
    }

    if (options.toggleables.getValue(Toggleable.ShowBeeHousesAreaOfEffect)) {
        drawAreaOfEffect(
            allowedEs = setOf(Equipment.SimpleEquipment.BeeHouse),
            getAreaOfEffect = { (_, place) ->
                PlacedDiamondStrategy.from(place, radius = 5u)
            },
            color = Color.Yellow,
        )
    }

    if (options.toggleables.getValue(Toggleable.ShowJunimoHutsAreaOfEffect)) {
        drawAreaOfEffect(
            allowedEs = setOf(Building.SimpleBuilding.JunimoHut),
            getAreaOfEffect = { (_, place) ->
                PlacedRectStrategy.from(center = place + vec(x = 1, y = 1), radius = 8u)
            },
            color = Color(0xFF8000FF),
        )
    }
}

private fun DrawScope.drawGrid(
    options: OptionsState,
    grid: CoordinateGrid,
    nW: Int, nH: Int,
) {
    if (options.toggleables.getValue(Toggleable.ShowGrid)) {
        for (x in (0..nW).map(grid::getX)) {
            drawLine(
                color = Color.Gray,
                alpha = 0.6f,
                start = Offset(x, y = 0f),
                end = Offset(x, y = size.height),
            )
        }
        for (y in (0..nH).map(grid::getY)) {
            drawLine(
                color = Color.Gray,
                alpha = 0.6f,
                start = Offset(x = 0f, y),
                end = Offset(x = size.width, y),
            )
        }
    }
}

private fun DrawScope.drawHoveredCellAndAxis(
    options: OptionsState,
    grid: CoordinateGrid,
    cellSize: Size,
    currCoordinate: Coordinate,
    hoveredColor: Color,
) {
    if (currCoordinate != UNDEFINED) {
        val offset = grid[currCoordinate]
        val (offsetX, offsetY) = offset

        drawRect(
            color = hoveredColor,
            topLeft = offset,
            size = cellSize,
            alpha = 0.3f,
        )

        // Axis

        if (options.toggleables.getValue(Toggleable.ShowAxis)) {
            drawRect(
                topLeft = Offset(x = offsetX, y = 0f),
                size = Size(cellSize.width, size.height),
                color = Color.DarkGray,
                alpha = 0.3f,
                style = Fill
            )
            drawRect(
                topLeft = Offset(x = 0f, y = offsetY),
                size = Size(size.width, cellSize.height),
                color = Color.DarkGray,
                alpha = 0.3f,
                style = Fill
            )
        }
    }
}

private val UNDEFINED: Coordinate = xy(Int.MAX_VALUE, Int.MAX_VALUE)
