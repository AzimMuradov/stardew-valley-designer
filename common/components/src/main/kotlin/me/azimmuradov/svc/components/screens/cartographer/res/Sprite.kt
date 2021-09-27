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

package me.azimmuradov.svc.components.screens.cartographer.res

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize


data class Sprite(
    val image: ImageBitmap,
    val offset: IntOffset,
    val size: IntSize,
)


private val commonObjectSpriteSize: IntSize = IntSize(width = 16, height = 16)

internal fun common(index: Int): Sprite {
    val (i, j) = (index % 24) to (index / 24)
    val (w, h) = commonObjectSpriteSize

    return Sprite(
        image = images.getValue(ImageFile.CommonObjects),
        offset = IntOffset(x = i * w, y = j * h),
        size = commonObjectSpriteSize,
    )
}


private val craftableSpriteSize: IntSize = IntSize(width = 16, height = 32)

internal fun craftable(index: Int): Sprite {
    val (i, j) = (index % 8) to (index / 8)
    val (w, h) = craftableSpriteSize

    return Sprite(
        image = images.getValue(ImageFile.Craftables),
        offset = IntOffset(x = i * w, y = j * h),
        size = craftableSpriteSize,
    )
}


private enum class ImageFile {
    CommonObjects,
    Craftables,
    Furniture,
}

private val images = mapOf(
    ImageFile.CommonObjects to useResource("entities/common-objects.png", ::loadImageBitmap),
    ImageFile.Craftables to useResource("entities/craftables.png", ::loadImageBitmap),
    ImageFile.Furniture to useResource("entities/furniture.png", ::loadImageBitmap),
)