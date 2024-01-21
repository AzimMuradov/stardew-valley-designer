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
import io.stardewvalleydesigner.engine.geometry.*
import io.stardewvalleydesigner.engine.layer.LayerType
import io.stardewvalleydesigner.engine.layer.coordinates
import io.stardewvalleydesigner.engine.layers.*
import io.stardewvalleydesigner.engine.layout.respectsLayout
import kotlin.properties.Delegates


class Hand(private val engine: EditorEngine) : Tool {

    private lateinit var initMovedEntities: LayeredEntitiesData

    private var start: Coordinate by Delegates.notNull()

    private var isSelected: Boolean by Delegates.notNull()

    private lateinit var heldEntities: LayeredEntitiesData


    override fun start(
        coordinate: Coordinate,
        currentEntity: Entity<*>?,
        selectedEntities: LayeredEntitiesData,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn? {
        val flattenSelectedEntities = selectedEntities.flatten()
        start = coordinate
        isSelected = coordinate in flattenSelectedEntities.coordinates
        initMovedEntities = if (isSelected) {
            engine.removeAll(flattenSelectedEntities)
        } else {
            engine.remove(coordinate, visLayers).toLayeredEntitiesData()
        }
        heldEntities = initMovedEntities

        return if (heldEntities.flattenSequence().any()) {
            ActionReturn(
                toolkit = ToolkitState.Hand.Point.Acting(heldEntities),
                currentEntity = currentEntity,
                selectedEntities = heldEntities
            )
        } else {
            null
        }
    }

    override fun keep(
        coordinate: Coordinate,
        currentEntity: Entity<*>?,
        selectedEntities: LayeredEntitiesData,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn {
        heldEntities = initMovedEntities
            .flattenSequence()
            .map { it.copy(place = it.place + (coordinate - start)) }
            .filter { it respectsLayout engine.layout }
            .layeredData()

        return ActionReturn(
            toolkit = ToolkitState.Hand.Point.Acting(heldEntities),
            currentEntity = currentEntity,
            selectedEntities = heldEntities
        )
    }

    override fun end(
        currentEntity: Entity<*>?,
        selectedEntities: LayeredEntitiesData,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn {
        engine.putAll(heldEntities.toLayeredEntities())
        return ActionReturn(
            toolkit = ToolkitState.Hand.Point.Idle,
            currentEntity = currentEntity,
            selectedEntities = if (isSelected) heldEntities else LayeredEntitiesData()
        )
    }


    override fun dispose() {
        initMovedEntities = LayeredEntitiesData()
        start = Coordinate.ZERO
        isSelected = false
        heldEntities = LayeredEntitiesData()
    }
}
