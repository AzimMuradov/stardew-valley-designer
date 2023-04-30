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

package me.azimmuradov.svc.engine

import me.azimmuradov.svc.engine.entity.*
import me.azimmuradov.svc.engine.geometry.Coordinate
import me.azimmuradov.svc.engine.layer.*
import me.azimmuradov.svc.engine.layers.*
import me.azimmuradov.svc.engine.layout.Layout


fun svcEngineOf(layout: Layout): SvcEngine = SvcEngineImpl(layout)


// Actual private implementations

private class SvcEngineImpl(layout: Layout) : SvcEngine {

    override val layers = mutableLayersOf(layout)


    // Operations

    override fun <EType : EntityType> get(type: LayerType<EType>, c: Coordinate) = layers.layerBy(type)[c]

    override fun put(obj: PlacedEntity<*>): LayeredEntitiesData {
        val objLayer = obj.layerType

        val replaced = buildList {
            addAll(objLayer.incompatibleLayers.flatMap { removeAll(it, obj.coordinates) })

            try {
                addAll(layers.layerBy(objLayer).put(obj))
            } catch (e: IllegalArgumentException) {
                putAll(layered())
                throw e
            }
        }.layeredData()

        return replaced
    }

    override fun <EType : EntityType> remove(type: LayerType<EType>, c: Coordinate) = layers.layerBy(type).remove(c)


    // Bulk Operations

    override fun <EType : EntityType> getAll(type: LayerType<EType>, cs: Iterable<Coordinate>) =
        layers.layerBy(type).getAll(cs)

    override fun <EType : EntityType> putAll(objs: DisjointEntities<EType>) =
        objs.flatMap { put(it).flatten() }.layeredData()

    override fun <EType : EntityType> removeAll(type: LayerType<EType>, cs: Iterable<Coordinate>) =
        layers.layerBy(type).removeAll(cs)

    override fun clear(type: LayerType<*>) = layers.layerBy(type).clear()
}
