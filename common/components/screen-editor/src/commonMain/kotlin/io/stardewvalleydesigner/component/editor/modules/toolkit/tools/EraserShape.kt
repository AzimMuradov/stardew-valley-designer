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

package io.stardewvalleydesigner.component.editor.modules.toolkit.tools

import io.stardewvalleydesigner.component.editor.modules.toolkit.*
import io.stardewvalleydesigner.engine.*
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.layer.*
import io.stardewvalleydesigner.engine.layers.flatten
import kotlin.properties.Delegates


class EraserShape(private val engine: EditorEngine, private val shape: ShapeType) : Tool {

    private var start: Coordinate by Delegates.notNull()

    private lateinit var entitiesToDelete: List<PlacedEntity<*>>


    override fun start(
        coordinate: Coordinate,
        currentEntity: Entity<*>?,
        selectedEntities: List<PlacedEntity<*>>,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn {
        start = coordinate

        val placedShape = shape.projectTo(start, coordinate)

        entitiesToDelete = engine.getAll(placedShape.coordinates, visLayers).flatten()

        return ActionReturn(
            toolkit = ToolkitState.Eraser.Shape.Acting(
                placedShape = placedShape,
                entitiesToDelete = entitiesToDelete.coordinates
            ),
            currentEntity = currentEntity,
            selectedEntities = selectedEntities
        )
    }

    override fun keep(
        coordinate: Coordinate,
        currentEntity: Entity<*>?,
        selectedEntities: List<PlacedEntity<*>>,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn {
        val placedShape = shape.projectTo(start, coordinate)

        entitiesToDelete = engine.getAll(placedShape.coordinates, visLayers).flatten()

        return ActionReturn(
            toolkit = ToolkitState.Eraser.Shape.Acting(
                placedShape = placedShape,
                entitiesToDelete = entitiesToDelete.coordinates
            ),
            currentEntity = currentEntity,
            selectedEntities = selectedEntities
        )
    }

    override fun end(
        currentEntity: Entity<*>?,
        selectedEntities: List<PlacedEntity<*>>,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn {
        engine.removeAll(entitiesToDelete)
        return ActionReturn(
            toolkit = ToolkitState.Eraser.Shape.Idle(shape),
            currentEntity = currentEntity,
            selectedEntities = selectedEntities
        )
    }


    override fun dispose() {
        start = Coordinate.ZERO
        entitiesToDelete = emptyList()
    }
}
