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

package me.azimmuradov.svc.cartographer.res.providers

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import me.azimmuradov.svc.cartographer.res.*


object CraftableProvider {

    private val craftableSpriteSize: IntSize = IntSize(width = 16, height = 32)

    fun craftable(index: Int): Sprite {
        val (i, j) = (index % 8) to (index / 8)
        val (w, h) = craftableSpriteSize

        return Sprite(
            image = ImageProvider.imageOf(ImageFile.Craftables),
            offset = IntOffset(x = i * w, y = j * h),
            size = craftableSpriteSize,
        )
    }
}
