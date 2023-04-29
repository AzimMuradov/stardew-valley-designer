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
import me.azimmuradov.svc.engine.geometry.*
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layer.coordinates
import me.azimmuradov.svc.engine.layers.*
import me.azimmuradov.svc.engine.layout.Layout
import me.azimmuradov.svc.engine.layout.respectsLayout
import kotlin.properties.Delegates


class Hand(private val engine: SvcEngine) : Tool {

    private lateinit var initMovedEntities: LayeredEntitiesData

    private var start: Coordinate by Delegates.notNull()

    private var isSelected: Boolean by Delegates.notNull()

    private lateinit var heldEntities: LayeredEntitiesData


    override fun start(
        coordinate: Coordinate,
        layout: Layout,
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

        return if (heldEntities.flatten().isNotEmpty()) {
            ActionReturn(
                toolkit = ToolkitState.Hand.Point.Acting(heldEntities),
                selectedEntities = heldEntities
            )
        } else {
            null
        }
    }

    override fun keep(
        coordinate: Coordinate,
        layout: Layout,
        currentEntity: Entity<*>?,
        selectedEntities: LayeredEntitiesData,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn {
        heldEntities = initMovedEntities
            .flatten()
            .map { it.copy(place = it.place + (coordinate - start)) }
            .filter { it respectsLayout layout }
            .layeredData()

        return ActionReturn(
            toolkit = ToolkitState.Hand.Point.Acting(heldEntities),
            selectedEntities = heldEntities
        )
    }

    override fun end(
        layout: Layout,
        currentEntity: Entity<*>?,
        selectedEntities: LayeredEntitiesData,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn {
        engine.putAll(heldEntities.toLayeredEntities())
        return ActionReturn(
            toolkit = ToolkitState.Hand.Point.Idle,
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
