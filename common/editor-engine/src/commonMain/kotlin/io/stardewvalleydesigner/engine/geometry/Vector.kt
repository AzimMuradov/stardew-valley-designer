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

package io.stardewvalleydesigner.engine.geometry

import io.stardewvalleydesigner.engine.*
import kotlin.math.hypot


/**
 * 2D vector.
 */
@JvmInline
value class Vector internal constructor(@PublishedApi internal val packedValue: Long) {

    inline val x: Int get() = unpackInt1(packedValue)

    inline val y: Int get() = unpackInt2(packedValue)


    operator fun component1(): Int = x

    operator fun component2(): Int = y


    fun copy(x: Int = this.x, y: Int = this.y): Vector = vec(x, y)


    override fun toString(): String = "(x=$x, y=$y)"


    companion object {

        val ZERO: Vector = vec(x = 0, y = 0)
    }
}


/**
 * Creates a 2D vector.
 */
fun vec(x: Int, y: Int): Vector = Vector(packInts(x, y))


/**
 * Length of the vector.
 */
val Vector.length get() = hypot(x.toFloat(), y.toFloat())


// Pair interop

fun Vector.toPair(): Pair<Int, Int> = x to y

fun Pair<Int, Int>.toVector2(): Vector = vec(first, second)
