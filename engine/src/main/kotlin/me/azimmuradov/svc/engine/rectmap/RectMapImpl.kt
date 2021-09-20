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

import me.azimmuradov.svc.engine.contains


fun <T> rectMapOf(size: Rect): RectMap<T> = RectMapImpl(MutableRectMapImpl(size))

fun <T> mutableRectMapOf(size: Rect): MutableRectMap<T> = MutableRectMapImpl(size)


// Actual private implementations

private class RectMapImpl<out T>(rectMap: RectMap<T>) : RectMap<T> by rectMap

private class MutableRectMapImpl<T>(
    override val size: Rect,
) : MutableRectMap<T> {

    private val map = mutableMapOf<Coordinate, PlacedRectObject<T>>()


    // Query Operations

    override operator fun get(c: Coordinate) = map[c]


    // Bulk Query Operations

    override fun getAll(cs: Iterable<Coordinate>) = cs
        .mapNotNullTo(mutableSetOf(), map::get)
        .toList()


    // Views

    override val occupiedCoordinates: Set<Coordinate> get() = map.keys


    // Modification Operations

    override fun put(obj: PlacedRectObject<T>): List<PlacedRectObject<T>> {
        val (_, _, coordinates) = obj
        val (min, max) = obj.minMaxCorners()

        requireNotOutOfBounds(min in size && max in size)

        val replaced = removeAll(coordinates)
        map.putAll(coordinates.associateWith { obj })

        return replaced
    }

    override fun remove(c: Coordinate) = map[c]?.also { map.removeAll(it.coordinates) }


    // Bulk Modification Operations

    override fun putAll(objs: Iterable<PlacedRectObject<T>>) = objs.flatMap(this::put).toList()

    override fun removeAll(cs: Iterable<Coordinate>) = cs.mapNotNull(this::remove)

    override fun clear() = map.clear()


    companion object {

        fun PlacedRectObject<*>.minMaxCorners(): Pair<Coordinate, Coordinate> {
            val (x, y) = place
            val (w, h) = rectObj.size

            return place to xy(x + w - 1, y + h - 1)
        }

        fun requireNotOutOfBounds(value: Boolean) = require(value) { TODO() }
    }
}


private fun <K> MutableMap<K, *>.removeAll(keys: Iterable<K>) = keys.forEach(this::remove)