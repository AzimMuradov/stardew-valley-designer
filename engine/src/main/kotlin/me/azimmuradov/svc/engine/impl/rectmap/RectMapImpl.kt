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

package me.azimmuradov.svc.engine.impl.rectmap

import me.azimmuradov.svc.engine.Coordinate
import me.azimmuradov.svc.engine.Rect
import me.azimmuradov.svc.engine.RectMap
import me.azimmuradov.svc.engine.impl.contains
import me.azimmuradov.svc.engine.xy


// TODO : Use efficient data structure

fun <T> rectMapOf(
    size: Rect,
    map: Map<Coordinate, T> = mapOf(),
): RectMap<T> = RectMapImpl(rect = size, map)

// fun <T> mutableRectMapOf(
//     size: Rect,
//     map: MutableMap<Coordinate, T> = mutableMapOf(),
// ): MutableRectMap<T> = MutableRectMapImpl(rect = size, map)


private class RectMapImpl<out T>(
    override val rect: Rect,
    private val map: Map<Coordinate, T>,
) : RectMap<T>, Map<Coordinate, T> by map {

    init {
        withChecks(map.keys in rect, onSuccess = {})
    }
}

// private class MutableRectMapImpl<T>(
//     override val rect: Rect,
//     private val map: MutableMap<Coordinate, T>,
// ) : MutableRectMap<T>, MutableMap<Coordinate, T> by map {
//
//     init {
//         actOnSuccessOf(check = map.keys in rect) {}
//     }
//
//
//     override fun put(key: Coordinate, value: T): T? = actOnSuccessOf(check = key in rect) {
//         map.put(key, value)
//     }
//
//     override fun putAll(from: Map<out Coordinate, T>) = actOnSuccessOf(check = from.keys in rect) {
//         map.putAll(from)
//     }
// }


private inline fun <T> withChecks(checkCondition: Boolean, onSuccess: () -> T): T {
    require(checkCondition) { "Coordinate is out of bounds" }
    return onSuccess()
}

private operator fun Rect.contains(coordinates: Iterable<Coordinate>): Boolean {
    // Check that iterable is not empty
    val first = coordinates.firstOrNull() ?: return true

    val (min, max) = run {
        var (minX: Int, minY: Int) = first
        var (maxX: Int, maxY: Int) = first

        for ((x, y) in coordinates) {
            minX = minOf(minX, x)
            maxX = maxOf(maxX, x)
            minY = minOf(minY, y)
            maxY = maxOf(maxY, y)
        }

        xy(minX, minY) to xy(maxX, maxY)
    }

    return contains(min) && contains(max)
}