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

package me.azimmuradov.svc.cartographer.res

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize


fun common(index: Int) = commonMapSpriteProvider.sprite(x = index % 24, y = index / 24, w = 1, h = 1)

fun craftable(index: Int) = craftableMapSpriteProvider.sprite(x = index % 8, y = index / 8 * 2, w = 1, h = 2)

fun furniture(x: Int, y: Int, w: Int, h: Int) = furnitureMapSpriteProvider.sprite(x, y, w, h)

fun flooring(index: Int): Sprite {
    val (i, j) = (index % 8) to (index / 8)
    val (w, h) = flooringObjectSpriteSize

    return Sprite(
        image = ImageProvider.imageOf(ImageFile.WallsAndFloors),
        offset = IntOffset(x = i * w, y = 336 + j * h),
        size = flooringObjectSpriteSize,
    )
}

fun wallpaper(index: Int): Sprite {
    val (i, j) = (index % 16) to (index / 16)
    val (w, h) = wallpaperObjectSpriteSize

    return Sprite(
        image = ImageProvider.imageOf(ImageFile.WallsAndFloors),
        offset = IntOffset(x = i * w, y = j * h),
        size = wallpaperObjectSpriteSize,
    )
}


private val commonMapSpriteProvider = MapSpriteProvider(ImageProvider.imageOf(ImageFile.CommonObjects))

private val craftableMapSpriteProvider = MapSpriteProvider(ImageProvider.imageOf(ImageFile.Craftables))

private val furnitureMapSpriteProvider = MapSpriteProvider(ImageProvider.imageOf(ImageFile.Furniture))

private val flooringObjectSpriteSize: IntSize = IntSize(width = 32, height = 32)

private val wallpaperObjectSpriteSize: IntSize = IntSize(width = 16, height = 48)
