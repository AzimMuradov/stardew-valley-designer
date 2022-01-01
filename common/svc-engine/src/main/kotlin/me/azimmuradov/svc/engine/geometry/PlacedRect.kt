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

package me.azimmuradov.svc.engine.geometry

import me.azimmuradov.svc.engine.overlapsWith
import kotlin.math.abs

data class PlacedRect(
    val place: Coordinate,
    val rect: Rect,
) {

    val coordinates: List<Coordinate> by lazy {
        val (x, y) = place
        val (w, h) = rect

        val xs = x until x + w
        val ys = y until y + h

        (xs * ys).map(Pair<Int, Int>::toCoordinate)
    }

    operator fun component3(): List<Coordinate> = coordinates


    companion object
}


// Static factories

fun PlacedRect.Companion.fromTwoCoordinates(a: Coordinate, b: Coordinate): PlacedRect = PlacedRect(
    place = xy(
        x = minOf(a.x, b.x),
        y = minOf(a.y, b.y)
    ),
    rect = rectOf(
        w = abs(a.x - b.x) + 1,
        h = abs(a.y - b.y) + 1
    ),
)

fun Rect.placeIt(there: Coordinate): PlacedRect = PlacedRect(place = there, rect = this)


// Operators

val PlacedRect.corners: Pair<Coordinate, Coordinate>
    get() = place to place + rect.toVector() - vec(x = 1, y = 1)

operator fun PlacedRect.contains(other: PlacedRect): Boolean {
    val (lb, rt) = corners
    // lb in other.rect
    return false
}

fun PlacedRect.overlapsWith(other: PlacedRect): Boolean =
    this.coordinates overlapsWith other.coordinates


// Private utils

// Cartesian product

private operator fun <A, B> Iterable<A>.times(other: Iterable<B>): List<Pair<A, B>> =
    flatMap { a -> other.map { b -> a to b } }