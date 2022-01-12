/*
 * Copyright 2021-2022 Azim Muradov
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

import me.azimmuradov.svc.engine.geometry.Coordinate
import me.azimmuradov.svc.engine.geometry.Rect


fun <RO : RectObject> rectMapOf(size: Rect): RectMap<RO> = RectMapImpl(MutableRectMapImpl(size))

fun <RO : RectObject> mutableRectMapOf(size: Rect): MutableRectMap<RO> = MutableRectMapImpl(size)


// Actual private implementations

private class RectMapImpl<out RO : RectObject>(rectMap: RectMap<RO>) : RectMap<RO> by rectMap

private class MutableRectMapImpl<RO : RectObject>(override val size: Rect) : MutableRectMap<RO> {

    private val map = mutableMapOf<Coordinate, PlacedRectObject<RO>>()


    // Query Operations

    override operator fun get(c: Coordinate) = map[c]


    // Bulk Query Operations

    override fun getAll(cs: Iterable<Coordinate>) = cs.mapNotNullTo(mutableSetOf(), map::get)


    // Views

    override val objects get() = map.values.toSet()


    // Modification Operations

    override fun put(obj: PlacedRectObject<RO>): Set<PlacedRectObject<RO>> {
        require(obj in size) { "Object coordinates are out of `RectMap` bounds." }

        val cs = obj.coordinates
        val replaced = removeAll(cs)
        map.putAll(cs.associateWith { obj })

        return replaced
    }

    override fun remove(c: Coordinate): PlacedRectObject<RO>? {
        val removed = map[c]
        removed?.coordinates?.forEach(map::remove)
        return removed
    }


    // Bulk Modification Operations

    override fun putAll(objs: DisjointRectObjects<RO>) = objs.flatMapTo(mutableSetOf(), this::put)

    override fun removeAll(cs: Iterable<Coordinate>) = cs.mapNotNullTo(mutableSetOf(), this::remove)

    override fun clear() = map.clear()
}