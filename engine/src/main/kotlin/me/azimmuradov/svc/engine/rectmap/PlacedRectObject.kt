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


data class PlacedRectObject<out T>(val rectObj: RectObject<T>, val place: Coordinate) {

    val coordinates: List<Coordinate> by lazy {
        val (x, y) = place
        val (w, h) = rectObj.size

        val xs = x until x + w
        val ys = y until y + h

        (xs * ys).map(Pair<Int, Int>::toCoordinate)
    }

    operator fun component3() = coordinates


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlacedRectObject<*>

        // `rectObj` and `place` are swapped for efficiency

        if (place != other.place) return false
        if (rectObj != other.rectObj) return false

        return true
    }

    override fun hashCode(): Int {
        var result = rectObj.hashCode()
        result = 31 * result + place.hashCode()
        return result
    }
}


// Cartesian product

private operator fun <A, B> Iterable<A>.times(other: Iterable<B>): List<Pair<A, B>> =
    flatMap { a -> other.map { b -> a to b } }