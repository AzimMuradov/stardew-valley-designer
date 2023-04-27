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

package me.azimmuradov.svc.cartographer.modules.toolkit.tools

import me.azimmuradov.svc.cartographer.modules.toolkit.*
import me.azimmuradov.svc.engine.*
import me.azimmuradov.svc.engine.entity.Entity
import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.geometry.CanonicalCorners
import me.azimmuradov.svc.engine.geometry.Coordinate
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layer.coordinates
import me.azimmuradov.svc.engine.layers.LayeredEntitiesData
import me.azimmuradov.svc.engine.layers.flatten
import me.azimmuradov.svc.engine.layout.Layout
import kotlin.properties.Delegates


class EraserShape(private val engine: SvcEngine, private val shape: ShapeType) : Tool {

    private var start: Coordinate by Delegates.notNull()

    private lateinit var entitiesToDelete: List<PlacedEntity<*>>


    override fun start(
        coordinate: Coordinate,
        layout: Layout,
        currentEntity: Entity<*>?,
        selectedEntities: LayeredEntitiesData,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn {
        start = coordinate

        val placedShape = shape.projectTo(CanonicalCorners(coordinate, coordinate))

        entitiesToDelete = engine.getAll(placedShape.coordinates).flatten()

        return ActionReturn(
            toolkit = ToolkitState.Eraser.Shape.Acting(
                placedShape = placedShape,
                entitiesToDelete = entitiesToDelete.coordinates
            ),
            selectedEntities = selectedEntities
        )
    }

    override fun keep(
        coordinate: Coordinate,
        layout: Layout,
        currentEntity: Entity<*>?,
        selectedEntities: LayeredEntitiesData,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn {
        val placedShape = shape.projectTo(CanonicalCorners.fromTwoCoordinates(start, coordinate))

        entitiesToDelete = engine.getAll(placedShape.coordinates).flatten()

        return ActionReturn(
            toolkit = ToolkitState.Eraser.Shape.Acting(
                placedShape = placedShape,
                entitiesToDelete = entitiesToDelete.coordinates
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
        engine.removeAll(entitiesToDelete)
        return ActionReturn(
            toolkit = ToolkitState.Eraser.Shape.Idle(shape),
            selectedEntities = selectedEntities
        )
    }


    override fun dispose() {
        start = Coordinate.ZERO
        entitiesToDelete = emptyList()
    }
}
