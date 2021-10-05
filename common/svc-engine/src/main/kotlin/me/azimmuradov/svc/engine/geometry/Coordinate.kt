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

import me.azimmuradov.svc.engine.*


/**
 * 2D coordinate.
 */
@JvmInline
value class Coordinate internal constructor(@PublishedApi internal val packedValue: Long) {

    inline val x: Int get() = unpackInt1(packedValue)

    inline val y: Int get() = unpackInt2(packedValue)


    operator fun component1(): Int = x

    operator fun component2(): Int = y


    fun copy(x: Int = this.x, y: Int = this.y): Coordinate = xy(x, y)


    override fun toString(): String = "(x = $x, y = $y)"


    companion object {

        val ZERO: Coordinate = xy(x = 0, y = 0)
    }
}


/**
 * Creates a 2D coordinate.
 */
fun xy(x: Int, y: Int): Coordinate = Coordinate(packInts(x, y))


// Pair interop

fun Coordinate.toPair(): Pair<Int, Int> = x to y

fun Pair<Int, Int>.toCoordinate(): Coordinate = xy(first, second)