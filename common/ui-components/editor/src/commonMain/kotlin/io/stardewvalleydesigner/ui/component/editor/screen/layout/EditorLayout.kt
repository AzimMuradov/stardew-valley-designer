/*
 * Copyright 2021-2024 Azim Muradov
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

package io.stardewvalleydesigner.ui.component.editor.screen.layout

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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.*
import io.stardewvalleydesigner.component.editor.EditorIntent
import io.stardewvalleydesigner.component.editor.menus.OptionsItemValue.Toggleable
import io.stardewvalleydesigner.component.editor.modules.map.MapState
import io.stardewvalleydesigner.component.editor.modules.options.OptionsState
import io.stardewvalleydesigner.component.editor.modules.toolkit.ToolkitState
import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.geometry.*
import io.stardewvalleydesigner.engine.geometry.shapes.*
import io.stardewvalleydesigner.engine.layer.LayerType
import io.stardewvalleydesigner.engine.layer.coordinates
import io.stardewvalleydesigner.engine.layers.flatten
import io.stardewvalleydesigner.engine.layers.flattenSequence
import io.stardewvalleydesigner.metadata.EntityPage
import io.stardewvalleydesigner.ui.component.editor.res.ImageResources
import io.stardewvalleydesigner.ui.component.editor.res.ImageResourcesProvider.layoutSpriteBy
import io.stardewvalleydesigner.ui.component.editor.utils.*
import io.stardewvalleydesigner.ui.component.editor.utils.DrawerUtils.drawEntityStretched
import io.stardewvalleydesigner.ui.component.editor.utils.DrawerUtils.drawFlooring
import io.stardewvalleydesigner.ui.component.editor.utils.DrawerUtils.drawVisibleEntities
import io.stardewvalleydesigner.ui.component.editor.utils.DrawerUtils.drawWallpaper
import io.stardewvalleydesigner.ui.component.editor.utils.DrawerUtils.placedEntityComparator
import kotlin.math.floor


@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun EditorLayout(
    map: MapState,
    visibleLayers: Set<LayerType<*>>,
    toolkit: ToolkitState,
    options: OptionsState,
    currCoordinate: Coordinate,
    onCurrCoordinateChanged: (Coordinate) -> Unit,
    intentConsumer: (EditorIntent) -> Unit,
) {
    val entityMaps = ImageResources.entities
    val wallsAndFloors = ImageResources.wallsAndFloors

    val layout = map.layout
    val (nW, nH) = layout.size
    val layoutSprite = layoutSpriteBy(layout.type)

    val hoveredColor = MaterialTheme.colors.secondary

    var cellSide by remember { mutableStateOf(-1f) }
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
                onCurrCoordinateChanged(event.changes.first().position.toCoordinateStrict())
            }
            .onPointerEvent(PointerEventType.Exit) { onCurrCoordinateChanged(UNDEFINED) }
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
            drawFlooring(wallsAndFloors, map.flooring, nW, nH, cellSide)
            drawWallpaper(wallsAndFloors, map.wallpaper, nW, cellSide)
        }

        drawVisibleEntities(
            entityMaps = entityMaps,
            entities = map.entities,
            visibleLayers = visibleLayers,
            renderSpritesFully = options.toggleables.getValue(Toggleable.ShowSpritesFully),
            grid = grid
        )

        drawSpecificSpritesAndEffects(entityMaps, map, toolkit, options, grid, cellSize)


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
    entityMaps: Map<EntityPage, ImageBitmap>,
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

            for (entity in sorted) {
                drawEntityStretched(
                    entityMaps = entityMaps,
                    entity = entity,
                    renderSpritesFully = options.toggleables.getValue(Toggleable.ShowSpritesFully),
                    grid = grid,
                    paddingInPx = 2u,
                    alpha = 0.7f
                )
            }
        }

        is ToolkitState.Pen.Shape.Acting -> {
            drawNeutralArea(toolkit.placedShape.coordinates, grid, cellSize)
            drawConflictArea(toolkit.entitiesToDelete, grid, cellSize)

            for (entity in toolkit.entitiesToDraw) {
                drawEntityStretched(
                    entityMaps = entityMaps,
                    entity = entity,
                    renderSpritesFully = options.toggleables.getValue(Toggleable.ShowSpritesFully),
                    grid = grid,
                    alpha = 0.7f
                )
            }
        }

        is ToolkitState.Eraser.Shape.Acting -> {
            drawNeutralArea(toolkit.placedShape.coordinates, grid, cellSize)
            drawConflictArea(toolkit.entitiesToDelete, grid, cellSize)
        }

        is ToolkitState.Select.Shape.Acting -> {
            drawNeutralArea(toolkit.placedShape.coordinates, grid, cellSize)
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
    val es = (map.entities.flattenSequence() + map.selectedEntities.flattenSequence())
        .toList()
        .asSequence()

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
                // TODO : Equipment.SimpleEquipment.IridiumSprinklerWithPressureNozzle,
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
                alpha = 0.4f
            )
            drawRect(
                topLeft = Offset(x = 0f, y = offsetY),
                size = Size(size.width, cellSize.height),
                color = Color.DarkGray,
                alpha = 0.4f
            )
        }
    }
}


private fun DrawScope.drawNeutralArea(
    coordinates: Set<Coordinate>,
    grid: CoordinateGrid,
    cellSize: Size,
) {
    for (c in coordinates) {
        drawRect(
            color = Color.Black,
            topLeft = grid[c],
            size = cellSize,
            alpha = 0.2f,
        )
    }
}

private fun DrawScope.drawConflictArea(
    coordinates: Set<Coordinate>,
    grid: CoordinateGrid,
    cellSize: Size,
) {
    for (c in coordinates) {
        drawRect(
            color = Color.Red,
            topLeft = grid[c],
            size = cellSize,
            alpha = 0.5f,
        )
    }
}


private val UNDEFINED: Coordinate = xy(Int.MAX_VALUE, Int.MAX_VALUE)
