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

@file:Suppress("NOTHING_TO_INLINE")

package me.azimmuradov.svc.cartographer.toolkit

import me.azimmuradov.svc.engine.rectmap.Coordinate
import me.azimmuradov.svc.engine.rectmap.xy


/**
 * 2D vector.
 */
@JvmInline
value class Vector2 internal constructor(@PublishedApi internal val packedValue: Long) {

    inline val x: Int get() = unpackInt1(packedValue)

    inline val y: Int get() = unpackInt2(packedValue)


    operator fun component1(): Int = x

    operator fun component2(): Int = y


    fun copy(x: Int = this.x, y: Int = this.y): Vector2 = vec(x, y)


    override fun toString(): String = "(x = $x, y = $y)"


    companion object {

        val ZERO: Vector2 = vec(x = 0, y = 0)
    }
}

fun vec(x: Int, y: Int): Vector2 = Vector2(packInts(x, y))

operator fun Coordinate.minus(other: Coordinate): Vector2 = vec(x = this.x - other.x, y = this.y - other.y)


operator fun Vector2.unaryMinus(): Vector2 = vec(-x, -y)


operator fun Coordinate.plus(other: Vector2): Coordinate = xy(x = this.x + other.x, y = this.y + other.y)

operator fun Coordinate.minus(other: Vector2): Coordinate = xy(x = this.x - other.x, y = this.y - other.y)


fun Vector2.toPair(): Pair<Int, Int> = x to y

fun Pair<Int, Int>.toVector2(): Vector2 = vec(first, second)


// For packing/unpacking

@PublishedApi
internal inline fun packInts(val1: Int, val2: Int): Long {
    return (val1.toLong() shl 32) or (val2.toLong() and 0xFFFFFFFF)
}

@PublishedApi
internal inline fun unpackInt1(value: Long) = (value shr 32).toInt()

@PublishedApi
internal inline fun unpackInt2(value: Long) = (value and 0xFFFFFFFF).toInt()