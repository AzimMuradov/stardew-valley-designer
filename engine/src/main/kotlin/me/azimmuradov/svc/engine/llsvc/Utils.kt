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

package me.azimmuradov.svc.engine.llsvc

import me.azimmuradov.svc.engine.Coordinate
import me.azimmuradov.svc.engine.Rect
import me.azimmuradov.svc.engine.xy


// Public utils

data class Color(val r: UByte, val g: UByte, val b: UByte)


// Internal utils

internal fun Rect.coordinatesFrom(c: Coordinate): List<Coordinate> {
    val (x, y) = c

    val xs = x until x + w
    val ys = y until y + h

    return xs.cartesianProduct(ys).map { (x, y) -> Coordinate(x, y) }
}

internal operator fun Rect.contains(coordinate: Coordinate): Boolean = with(coordinate) {
    x in 0 until w && y in 0 until h
}

internal operator fun Rect.contains(coordinates: Iterable<Coordinate>): Boolean {
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


// Private utils

private fun <A, B> Iterable<A>.cartesianProduct(rhs: Iterable<B>): List<Pair<A, B>> =
    flatMap { e1 -> rhs.map { e2 -> e1 to e2 } }