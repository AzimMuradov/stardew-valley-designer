/*
 * Copyright 2021-2021 Azim Muradov
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

@file:Suppress("UNCHECKED_CAST")

package me.azimmuradov.svc.engine

import me.azimmuradov.svc.engine.entity.*
import me.azimmuradov.svc.engine.layer.*
import me.azimmuradov.svc.engine.layout.Layout
import me.azimmuradov.svc.engine.rectmap.Coordinate


fun svcEngineOf(layout: Layout): SvcEngine = SvcEngineImpl(layout)


// Actual private implementations

private class SvcEngineImpl(override val layout: Layout) : SvcEngine {

    override val layers = mapOf(
        LayerType.Floor to mutableLayerOf<FloorType>(layout),
        LayerType.FloorFurniture to mutableLayerOf<FloorFurnitureType>(layout),
        LayerType.Object to mutableLayerOf<ObjectType>(layout),
        LayerType.EntityWithoutFloor to mutableLayerOf<EntityWithoutFloorType>(layout),
    )

    fun layerOf(type: LayerType<*>) = layers.getValue(type)


    // Operations

    override fun get(type: LayerType<*>, c: Coordinate) = layerOf(type)[c]

    override fun put(obj: PlacedEntity<*>): Map<LayerType<*>, List<PlacedEntity<*>>> {
        val (entity, _, coordinates) = obj

        val entityWithFloorGroup = listOf(
            LayerType.Floor,
            LayerType.FloorFurniture,
            LayerType.Object,
        )
        val entityWithoutFloorGroup = listOf(LayerType.EntityWithoutFloor)

        val entityLayerType = entity.type.toLayerType()
        val entityLayer = layerOf(entityLayerType)

        val replaced = mutableListOf<PlacedEntity<*>>()

        replaced += when (entityLayerType) {
            LayerType.Object, LayerType.Floor, LayerType.FloorFurniture -> {
                entityWithoutFloorGroup.flatMap { layerOf(it).removeAll(coordinates) }
            }
            LayerType.EntityWithoutFloor -> {
                entityWithFloorGroup.flatMap { layerOf(it).removeAll(coordinates) }
            }
        }

        replaced += try {
            (entityLayer as MutableLayer<in EntityType>).put(obj)
        } catch (e: IllegalArgumentException) {
            putAll(replaced)
            throw e
        }

        return replaced.toMap()
    }

    override fun remove(type: LayerType<*>, c: Coordinate) = layerOf(type).remove(c)


    // Bulk Operations

    override fun getAll(type: LayerType<*>, cs: Iterable<Coordinate>) = layerOf(type).getAll(cs)

    override fun putAll(objs: Iterable<PlacedEntity<*>>) =
        objs.map(this::put).fold(mapOf(), Map<LayerType<*>, List<PlacedEntity<*>>>::plus)

    override fun removeAll(type: LayerType<*>, cs: Iterable<Coordinate>) = layerOf(type).removeAll(cs)

    override fun clear(type: LayerType<*>) = layerOf(type).clear()
}