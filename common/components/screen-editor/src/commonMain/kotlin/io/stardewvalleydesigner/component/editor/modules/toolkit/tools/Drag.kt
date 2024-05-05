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
import io.stardewvalleydesigner.engine.layer.*
import io.stardewvalleydesigner.engine.layers.flatten
import io.stardewvalleydesigner.engine.layout.respects
import kotlin.properties.Delegates


class Drag(private val engine: EditorEngine) : Tool {

    private lateinit var initMovedEntities: List<PlacedEntity<*>>

    private var start: Coordinate by Delegates.notNull()

    private var isSelected: Boolean by Delegates.notNull()

    private lateinit var heldEntities: List<PlacedEntity<*>>


    override fun start(
        coordinate: Coordinate,
        currentEntity: Entity<*>?,
        selectedEntities: List<PlacedEntity<*>>,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn? {
        start = coordinate
        isSelected = coordinate in selectedEntities.coordinates
        initMovedEntities = if (isSelected) {
            engine.removeAll(selectedEntities)
        } else {
            engine.remove(coordinate, visLayers).flatten()
        }
        heldEntities = initMovedEntities

        return if (heldEntities.isNotEmpty()) {
            ActionReturn(
                toolkit = ToolkitState.Drag.Point.Acting(heldEntities),
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
        selectedEntities: List<PlacedEntity<*>>,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn {
        heldEntities = initMovedEntities
            .asSequence()
            .map { it.copy(place = it.place + (coordinate - start)) }
            .filter { it respects engine.layout }
            .toList()

        return ActionReturn(
            toolkit = ToolkitState.Drag.Point.Acting(heldEntities),
            currentEntity = currentEntity,
            selectedEntities = heldEntities
        )
    }

    override fun end(
        currentEntity: Entity<*>?,
        selectedEntities: List<PlacedEntity<*>>,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn {
        engine.putAll(heldEntities)
        return ActionReturn(
            toolkit = ToolkitState.Drag.Point.Idle,
            currentEntity = currentEntity,
            selectedEntities = if (isSelected) heldEntities else emptyList()
        )
    }


    override fun dispose() {
        initMovedEntities = emptyList()
        start = Coordinate.ZERO
        isSelected = false
        heldEntities = emptyList()
    }
}
