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

package io.stardewvalleydesigner.data


data class SpriteOffset(val x: Int, val y: Int)


operator fun SpriteOffset.times(multiplier: Int) = SpriteOffset(x = x * multiplier, y = y * multiplier)

operator fun SpriteOffset.div(divider: Int) = SpriteOffset(x = x / divider, y = y / divider)
