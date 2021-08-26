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

package me.azimmuradov.svc.svapi.editor.impl

import me.azimmuradov.svc.svapi.editor.Coordinate
import me.azimmuradov.svc.svapi.editor.Rect
import me.azimmuradov.svc.svapi.editor.xy


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


internal fun <K, V> MutableMap<K, V>.removeAll(keys: Iterable<K>) {
    for (k in keys) {
        remove(k)
    }
}


// Private utils

private fun <A, B> Iterable<A>.cartesianProduct(rhs: Iterable<B>): List<Pair<A, B>> =
    flatMap { e1 -> rhs.map { e2 -> e1 to e2 } }