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
 * Rectangular map that contains rectangular objects.
 */
interface RectMap<out T, out RO : RectObject<T>> {

    val rect: Rect // != size (for compatability with the `Map` interface)

    val behaviour: RectMapBehaviour


    // Query Operations

    operator fun get(key: Coordinate): RO?


    // Bulk Query Operations

    fun getAll(keys: Iterable<Coordinate>): List<RO>


    // Views

    val keys: Set<Coordinate>

    val values: Collection<RO>

    val entries: Set<Map.Entry<Coordinate, RO>>
}

val RectMap<*, *>.objsNumber: Int get() = keys.size

fun RectMap<*, *>.isEmpty(): Boolean = keys.isEmpty()