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
 * Rectangle with no coordinate to place.
 */
@JvmInline
value class Rect internal constructor(@PublishedApi internal val packedValue: Long) {

    inline val w: Int get() = unpackInt1(packedValue)

    inline val h: Int get() = unpackInt2(packedValue)


    operator fun component1(): Int = w

    operator fun component2(): Int = h


    fun copy(w: Int = this.w, h: Int = this.h): Rect = rectOf(w, h)


    override fun toString(): String = "(w=$w, h=$h)"


    companion object {

        val ZERO: Rect = rectOf(w = 0, h = 0)
    }
}


/**
 * Creates a rectangle.
 */
fun rectOf(w: Int, h: Int): Rect = Rect(packInts(w, h))


// Pair interop

fun Rect.toPair(): Pair<Int, Int> = w to h

fun Pair<Int, Int>.toRect(): Rect = rectOf(first, second)