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

package io.svapi.editor

import java.util.function.BiFunction
import java.util.function.Function


fun <T> rectMapOf(
    size: Rect,
    map: Map<Coordinate, T> = mapOf(),
): RectMap<T> = RectMapImpl(rect = size, map)

fun <T> mutableRectMapOf(
    size: Rect,
    map: MutableMap<Coordinate, T> = mutableMapOf(),
): MutableRectMap<T> = MutableRectMapImpl(rect = size, map)


private class RectMapImpl<T>(
    override val rect: Rect,
    private val map: Map<Coordinate, T>,
) : RectMap<T>, Map<Coordinate, T> by map {

    init {
        require(map.keys.all { it in rect }, ::ERR_MSG_COORDINATE_OUT_OF_BOUNDS)
    }
}

private class MutableRectMapImpl<T>(
    override val rect: Rect,
    private val map: MutableMap<Coordinate, T>,
) : MutableRectMap<T>, MutableMap<Coordinate, T> by map {

    init {
        require(map.keys.all { it in rect }, ::ERR_MSG_COORDINATE_OUT_OF_BOUNDS)
    }


    override fun compute(key: Coordinate, remappingFunction: BiFunction<in Coordinate, in T?, out T?>): T? {
        require(key in rect, ::ERR_MSG_COORDINATE_OUT_OF_BOUNDS)
        return map.compute(key, remappingFunction)
    }

    override fun computeIfAbsent(key: Coordinate, mappingFunction: Function<in Coordinate, out T>): T {
        require(key in rect, ::ERR_MSG_COORDINATE_OUT_OF_BOUNDS)
        return map.computeIfAbsent(key, mappingFunction)
    }

    override fun merge(key: Coordinate, value: T, remappingFunction: BiFunction<in T, in T, out T?>): T? {
        requireNotNull(value)
        require(key in rect, ::ERR_MSG_COORDINATE_OUT_OF_BOUNDS)
        return map.merge(key, value, remappingFunction)
    }

    override fun put(key: Coordinate, value: T): T? {
        require(key in rect, ::ERR_MSG_COORDINATE_OUT_OF_BOUNDS)
        return map.put(key, value)
    }

    override fun putAll(from: Map<out Coordinate, T>) {
        require(from.keys.all { it in rect }, ::ERR_MSG_COORDINATE_OUT_OF_BOUNDS)
        return map.putAll(from)
    }

    override fun putIfAbsent(key: Coordinate, value: T): T? {
        require(key in rect, ::ERR_MSG_COORDINATE_OUT_OF_BOUNDS)
        return map.putIfAbsent(key, value)
    }
}


private const val ERR_MSG_COORDINATE_OUT_OF_BOUNDS: String = "Coordinate is out of bounds"