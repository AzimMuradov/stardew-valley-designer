/*
 * Copyright 2021-2023 Azim Muradov
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

@file:Suppress("NOTHING_TO_INLINE")

package me.azimmuradov.svc.engine

import me.azimmuradov.svc.engine.geometry.Coordinate
import me.azimmuradov.svc.engine.geometry.Rect


// Public utils

infix fun Iterable<Coordinate>.overlapsWith(other: Iterable<Coordinate>) = any { it in other }

infix fun Iterable<Coordinate>.notOverlapsWith(other: Iterable<Coordinate>) = none { it in other }


// Internal utils

@PublishedApi
internal inline fun packInts(val1: Int, val2: Int): Long {
    return (val1.toLong() shl 32) or (val2.toLong() and 0xFFFFFFFF)
}

@PublishedApi
internal inline fun unpackInt1(value: Long) = (value shr 32).toInt()

@PublishedApi
internal inline fun unpackInt2(value: Long) = (value and 0xFFFFFFFF).toInt()


internal operator fun Rect.contains(coordinate: Coordinate) = with(coordinate) {
    x in 0 until w && y in 0 until h
}


internal fun impossible(): Nothing = error("Impossible state.")
