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

package io.svapi.editor.impl

import io.svapi.editor.Coordinate
import io.svapi.editor.Rect
import io.svapi.editor.xy


// Internal utils

internal operator fun Rect.contains(coordinate: Coordinate): Boolean = with(coordinate) {
    x in 0 until w && y in 0 until h
}


internal fun generateCoordinates(source: Coordinate, rect: Rect): List<Coordinate> {
    val (x, y) = source
    val (w, h) = rect

    val xs = x until x + w
    val ys = y until y + h

    return xs.cartesianProduct(ys).map { (x, y) -> xy(x, y) }
}


internal fun minMaxCoordinates(source: Coordinate, rect: Rect): Pair<Coordinate, Coordinate> {
    val (x, y) = source
    val (w, h) = rect

    return source to xy(x + w, y + h)
}

internal fun minMaxCoordinates(coordinates: Iterable<Coordinate>): Pair<Coordinate, Coordinate> {
    val first = coordinates.firstOrNull()

    requireNotNull(first)

    var (minX: Int, minY: Int) = first
    var (maxX: Int, maxY: Int) = first

    for ((x, y) in coordinates) {
        minX = minOf(minX, x)
        maxX = maxOf(maxX, x)
        minY = minOf(minY, y)
        maxY = maxOf(maxY, y)
    }

    return xy(minX, maxX) to xy(minY, maxY)
}


// Private utils

private fun <A, B> Iterable<A>.cartesianProduct(rhs: Iterable<B>): List<Pair<A, B>> =
    flatMap { e1 -> rhs.map { e2 -> e1 to e2 } }