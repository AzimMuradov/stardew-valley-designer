/*
 * Copyright 2021-2024 Azim Muradov
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

@file:Suppress("NOTHING_TO_INLINE", "MagicNumber")

package io.stardewvalleydesigner.engine

import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.geometry.Rect


// Public utils

infix fun Iterable<Coordinate>.overlapsWith(other: Iterable<Coordinate>) = any { it in other }

infix fun Iterable<Coordinate>.notOverlapsWith(other: Iterable<Coordinate>) = none { it in other }

inline fun packInts(val1: Int, val2: Int): Long = (val1.toLong() shl 32) or (val2.toLong() and 0xFFFFFFFF)

inline fun unpackInt1(value: Long) = (value shr 32).toInt()

inline fun unpackInt2(value: Long) = (value and 0xFFFFFFFF).toInt()


internal operator fun Rect.contains(coordinate: Coordinate) = with(coordinate) {
    x in 0 until w && y in 0 until h
}

internal inline fun <T, K, V : MutableCollection<T>, M : MutableMap<in K, V>> Iterable<T>.customGroupByTo(
    destination: M,
    keySelector: (T) -> K,
    valuesCollectionGenerator: () -> V,
): M {
    for (element in this) {
        val values = destination.getOrPut(
            key = keySelector(element),
            defaultValue = valuesCollectionGenerator
        )
        values.add(element)
    }
    return destination
}

internal inline fun <T, K, V : MutableCollection<T>, M : MutableMap<in K, V>> Sequence<T>.customGroupByTo(
    destination: M,
    keySelector: (T) -> K,
    valuesCollectionGenerator: () -> V,
): M {
    for (element in this) {
        val values = destination.getOrPut(
            key = keySelector(element),
            defaultValue = valuesCollectionGenerator
        )
        values.add(element)
    }
    return destination
}
