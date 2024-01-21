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

package io.stardewvalleydesigner.engine.layer

import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.layout.Layout
import io.stardewvalleydesigner.engine.layout.respects


fun <EType : EntityType> layerOf(layout: Layout): Layer<EType> = LayerImpl(MutableLayerImpl(layout))

fun <EType : EntityType> mutableLayerOf(layout: Layout): MutableLayer<EType> = MutableLayerImpl(layout)


// Actual private implementations

private class LayerImpl<EType : EntityType>(layer: Layer<EType>) : Layer<EType> by layer

private class MutableLayerImpl<EType : EntityType>(layout: Layout) : MutableLayer<EType> {

    override val size = layout.size

    override val layoutRules = layout


    private val map = mutableMapOf<Coordinate, PlacedEntity<EType>>()


    // Query Operations

    override operator fun get(c: Coordinate) = map[c]


    // Bulk Query Operations

    override fun getAll(cs: Iterable<Coordinate>) = cs.mapNotNullTo(mutableSetOf(), map::get)


    // Views

    override val objects get() = map.values.toSet()


    // Modification Operations

    override fun put(obj: PlacedEntity<EType>): Set<PlacedEntity<EType>> {
        require(obj in size) { "Object coordinates are out of `RectMap` bounds." }
        require(obj respects layoutRules) { "The object ($obj) failed to satisfy layout rules." }

        val cs = obj.coordinates
        val replaced = removeAll(cs)
        map.putAll(cs.associateWith { obj })

        return replaced
    }

    override fun remove(c: Coordinate): PlacedEntity<EType>? {
        val removed = map[c]
        removed?.coordinates?.forEach(map::remove)
        return removed
    }


    // Bulk Modification Operations

    override fun putAll(objs: DisjointRectObjects<Entity<EType>>) = objs.flatMapTo(mutableSetOf(), this::put)

    override fun removeAll(cs: Iterable<Coordinate>) = cs.mapNotNullTo(mutableSetOf(), this::remove)

    override fun clear() = map.clear()
}
