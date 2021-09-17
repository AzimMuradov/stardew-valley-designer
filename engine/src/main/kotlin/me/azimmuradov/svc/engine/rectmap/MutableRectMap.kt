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

package me.azimmuradov.svc.engine.rectmap


// TODO : Use a more efficient data structure


/**
 * Mutable rectangular map that contains rectangular objects.
 */
interface MutableRectMap<T, RO : RectObject<T>> : RectMap<T, RO> {

    override val behaviour: MutableRectMapBehaviour


    // Modification Operations

    fun put(key: Coordinate, value: RO): RO?

    fun remove(key: Coordinate): RO?


    // Bulk Modification Operations

    fun putAll(from: Map<out Coordinate, RO>)

    fun removeAll(keys: Iterable<Coordinate>)

    fun clear()
}

operator fun <T, RO : RectObject<T>> MutableRectMap<T, RO>.set(key: Coordinate, value: RO) {
    put(key, value)
}

fun <T, RO : RectObject<T>> MutableRectMap<T, RO>.move(oldKey: Coordinate, newKey: Coordinate): RO? =
    get(oldKey)?.let { value ->
        put(newKey, value).also { if (get(newKey) == null) put(oldKey, value) }
    }

// TODO
// fun <T, RO : RectObject<T>> MutableRectMap<T, RO>.moveAll(oldKeysToNewKeys: Map<Coordinate, Coordinate>)