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


fun Coordinate.toVector(): Vector = vec(x, y)

fun Vector.toCoordinate(): Coordinate = xy(x, y)

fun Coordinate.toRect(): Rect = rectOf(w = x, h = y)

fun Vector.toRect(): Rect = rectOf(w = x, h = y)

fun Rect.toCoordinate(): Coordinate = xy(x = w, y = h)

fun Rect.toVector(): Vector = vec(x = w, y = h)


operator fun Coordinate.minus(other: Coordinate): Vector = vec(x = this.x - other.x, y = this.y - other.y)


operator fun Coordinate.plus(other: Vector): Coordinate = xy(x = this.x + other.x, y = this.y + other.y)

operator fun Coordinate.minus(other: Vector): Coordinate = xy(x = this.x - other.x, y = this.y - other.y)


operator fun Vector.plus(other: Vector): Vector = vec(x = this.x + other.x, y = this.y + other.y)

operator fun Vector.minus(other: Vector): Vector = vec(x = this.x - other.x, y = this.y - other.y)
