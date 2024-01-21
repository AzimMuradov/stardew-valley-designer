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

package io.stardewvalleydesigner.utils

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import io.stardewvalleydesigner.engine.geometry.Rect
import io.stardewvalleydesigner.engine.geometry.rectOf
import kotlin.math.roundToInt


fun Size.toIntSize(): IntSize = IntSize(width.roundToInt(), height.roundToInt())

fun IntSize.toRect(): Rect = rectOf(width, height)


operator fun Int.times(size: IntOffset): IntOffset = IntOffset(x = this * size.x, y = this * size.y)
