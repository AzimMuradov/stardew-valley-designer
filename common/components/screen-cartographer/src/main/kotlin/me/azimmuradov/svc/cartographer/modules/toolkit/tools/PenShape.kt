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

package me.azimmuradov.svc.cartographer.modules.toolkit.tools

import me.azimmuradov.svc.cartographer.modules.toolkit.*
import me.azimmuradov.svc.engine.SvcEngine
import me.azimmuradov.svc.engine.entity.Entity
import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.geometry.CanonicalCorners
import me.azimmuradov.svc.engine.geometry.Coordinate
import me.azimmuradov.svc.engine.getReplacedBy
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layers.LayeredEntitiesData
import me.azimmuradov.svc.engine.layers.flatten
import me.azimmuradov.svc.engine.layout.Layout
import me.azimmuradov.svc.engine.rectmap.*
import kotlin.properties.Delegates


class PenShape(private val engine: SvcEngine, private val shape: ShapeType) : Tool {

    private var start: Coordinate by Delegates.notNull()

    private lateinit var entitiesToDraw: MutableSet<PlacedEntity<*>>


    override fun start(
        coordinate: Coordinate,
        layout: Layout,
        currentEntity: Entity<*>?,
        selectedEntities: LayeredEntitiesData,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn? {
        start = coordinate

        if (currentEntity != null) {
            val placedShape = shape.projectTo(CanonicalCorners(coordinate, coordinate))
            val disallowedCoordinates = layout.disallowedTypesMap
                .mapNotNullTo(mutableSetOf()) { (c, types) ->
                    c.takeIf { currentEntity.type in types }
                } + layout.disallowedCoordinates
            entitiesToDraw = placedShape.coordinates.filter {
                it !in disallowedCoordinates
            }.mapTo(mutableSetOf(), currentEntity::placeIt)

            return ActionReturn(
                toolkit = ToolkitState.Pen.Shape.Acting(
                    placedShape = placedShape,
                    entitiesToDraw = entitiesToDraw,
                    entitiesToDelete = engine
                        .getReplacedBy(entitiesToDraw.toList().asDisjoint())
                        .flatten()
                        .coordinates
                ),
                selectedEntities = selectedEntities
            )
        } else {
            return null
        }
    }

    override fun keep(
        coordinate: Coordinate,
        layout: Layout,
        currentEntity: Entity<*>?,
        selectedEntities: LayeredEntitiesData,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn {
        val placedShape = shape.projectTo(
            CanonicalCorners.fromTwoCoordinates(start, coordinate)
        )
        val disallowedCoordinates = layout.disallowedTypesMap
            .mapNotNullTo(mutableSetOf()) { (c, types) ->
                c.takeIf { currentEntity!!.type in types }
            } + layout.disallowedCoordinates
        entitiesToDraw = placedShape.coordinates.filter {
            it !in disallowedCoordinates
        }.mapTo(mutableSetOf(), currentEntity!!::placeIt)
        return ActionReturn(
            toolkit = ToolkitState.Pen.Shape.Acting(
                placedShape = placedShape,
                entitiesToDraw = entitiesToDraw,
                entitiesToDelete = engine
                    .getReplacedBy(entitiesToDraw.toList().asDisjoint())
                    .flatten()
                    .coordinates
            ),
            selectedEntities = selectedEntities
        )
    }

    override fun end(
        layout: Layout,
        currentEntity: Entity<*>?,
        selectedEntities: LayeredEntitiesData,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn {
        engine.putAll(entitiesToDraw.toList().asDisjoint())
        return ActionReturn(
            toolkit = ToolkitState.Pen.Shape.Idle(shape),
            selectedEntities = selectedEntities
        )
    }


    override fun dispose() {
        start = Coordinate.ZERO
        entitiesToDraw = mutableSetOf()
    }
}