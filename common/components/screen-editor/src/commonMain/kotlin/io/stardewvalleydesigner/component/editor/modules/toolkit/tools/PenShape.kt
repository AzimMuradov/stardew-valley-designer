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
import io.stardewvalleydesigner.engine.entity.PlacedEntity
import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.layer.*
import io.stardewvalleydesigner.engine.layers.LayeredEntitiesData
import io.stardewvalleydesigner.engine.layers.flatten
import io.stardewvalleydesigner.engine.layout.respectsLayout
import kotlin.properties.Delegates


class PenShape(private val engine: EditorEngine, private val shape: ShapeType) : Tool {

    private var start: Coordinate by Delegates.notNull()

    private lateinit var entitiesToDraw: MutableSet<PlacedEntity<*>>


    override fun start(
        coordinate: Coordinate,
        currentEntity: Entity<*>?,
        selectedEntities: LayeredEntitiesData,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn? {
        start = coordinate

        if (currentEntity != null) {
            val placedShape = shape.projectTo(start, coordinate)

            entitiesToDraw = placedShape
                .coordinates
                .asSequence()
                .map(currentEntity::placeIt)
                .filterTo(mutableSetOf()) { it respectsLayout engine.layout }

            return ActionReturn(
                toolkit = ToolkitState.Pen.Shape.Acting(
                    placedShape = placedShape,
                    entitiesToDraw = entitiesToDraw,
                    entitiesToDelete = engine
                        .getReplacedBy(entitiesToDraw.toList().asDisjointUnsafe())
                        .flatten()
                        .coordinates
                ),
                currentEntity = currentEntity,
                selectedEntities = selectedEntities
            )
        } else {
            return null
        }
    }

    override fun keep(
        coordinate: Coordinate,
        currentEntity: Entity<*>?,
        selectedEntities: LayeredEntitiesData,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn {
        val placedShape = shape.projectTo(start, coordinate)

        val cs = mutableSetOf<Coordinate>()

        entitiesToDraw = placedShape
            .coordinates
            .asSequence()
            .sortedWith(compareBy(Coordinate::x).thenBy(Coordinate::y))
            .map(currentEntity!!::placeIt)
            .filter { it respectsLayout engine.layout }
            .filterTo(mutableSetOf()) {
                if (it.coordinates.any(cs::contains)) {
                    false
                } else {
                    cs += it.coordinates
                    true
                }
            }

        return ActionReturn(
            toolkit = ToolkitState.Pen.Shape.Acting(
                placedShape = placedShape,
                entitiesToDraw = entitiesToDraw,
                entitiesToDelete = engine
                    .getReplacedBy(entitiesToDraw.toList().asDisjointUnsafe())
                    .flatten()
                    .coordinates
            ),
            currentEntity = currentEntity,
            selectedEntities = selectedEntities
        )
    }

    override fun end(
        currentEntity: Entity<*>?,
        selectedEntities: LayeredEntitiesData,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn {
        engine.putAll(entitiesToDraw.toList().asDisjointUnsafe())
        return ActionReturn(
            toolkit = ToolkitState.Pen.Shape.Idle(shape),
            currentEntity = currentEntity,
            selectedEntities = selectedEntities
        )
    }


    override fun dispose() {
        start = Coordinate.ZERO
        entitiesToDraw = mutableSetOf()
    }
}
