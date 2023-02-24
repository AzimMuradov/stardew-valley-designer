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
import me.azimmuradov.svc.engine.geometry.*
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layers.*
import me.azimmuradov.svc.engine.layout.Layout
import me.azimmuradov.svc.engine.layout.respectsLayout
import me.azimmuradov.svc.engine.rectmap.coordinates
import kotlin.properties.Delegates


class Hand(private val engine: SvcEngine) : Tool {

    private lateinit var initMovedEntities: LayeredEntitiesData

    private var start: Coordinate by Delegates.notNull()

    private var isSelected by Delegates.notNull<Boolean>()

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
        initMovedEntities = if (coordinate in flattenSelectedEntities.coordinates) {
            isSelected = true
            engine.removeAll(flattenSelectedEntities)
        } else {
            isSelected = false
            engine.remove(coordinate).run { layeredEntitiesData { type -> setOfNotNull(entityOrNullBy(type)) } }
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
        val flattenMovedEntities = initMovedEntities.flatten().map { (entity, place) ->
            PlacedEntity(entity, place = place + (coordinate - start))
        }
        heldEntities = flattenMovedEntities.layeredData()

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
    ): ActionReturn? {
        val flattenMovedEntities = heldEntities.flatten()

        return if (flattenMovedEntities.all { it respectsLayout layout }) {
            engine.putAll(heldEntities.toLayeredEntities())
            ActionReturn(
                toolkit = ToolkitState.Hand.Point.Idle,
                selectedEntities = if (isSelected) heldEntities else selectedEntities
            )
        } else {
            engine.putAll(initMovedEntities.toLayeredEntities())
            ActionReturn(
                toolkit = ToolkitState.Hand.Point.Idle,
                selectedEntities = initMovedEntities
            )
        }
    }


    override fun dispose() {
        initMovedEntities = LayeredEntitiesData()
        start = Coordinate.ZERO
        isSelected = false
        heldEntities = LayeredEntitiesData()
    }
}
