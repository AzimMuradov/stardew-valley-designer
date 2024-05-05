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

import io.stardewvalleydesigner.engine.entity.EntityType
import io.stardewvalleydesigner.engine.geometry.Coordinate


class Layer<T : EntityType> {

    private val map = mutableMapOf<Coordinate, PlacedEntity<T>>()


    // Views

    val placedEntities: Set<PlacedEntity<T>> get() = map.values.toSet()


    // Operations

    operator fun get(c: Coordinate): PlacedEntity<T>? = map[c]

    fun put(entity: PlacedEntity<T>) {
        val cs = entity.coordinates
        removeAll(cs)
        map.putAll(cs.associateWith { entity })
    }

    fun remove(c: Coordinate): PlacedEntity<T>? {
        val removed = map[c]
        removed?.coordinates?.forEach(map::remove)
        return removed
    }


    // Bulk Operations

    fun getAll(
        cs: Iterable<Coordinate>,
    ): Set<PlacedEntity<T>> = cs.mapNotNullTo(mutableSetOf(), map::get)

    fun putAll(entities: Iterable<PlacedEntity<T>>) {
        entities.forEach(this::put)
    }

    fun removeAll(
        cs: Iterable<Coordinate>,
    ): Set<PlacedEntity<T>> = cs.mapNotNullTo(mutableSetOf(), this::remove)

    fun clear() {
        map.clear()
    }
}
