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

package io.stardewvalleydesigner.ui.component.editor.screen.bottommenu.savedesignasimg

import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.IntOffset
import io.stardewvalleydesigner.component.editor.modules.map.MapState
import io.stardewvalleydesigner.data.SpritePage.Companion.UNIT
import io.stardewvalleydesigner.engine.Flooring
import io.stardewvalleydesigner.engine.Wallpaper
import io.stardewvalleydesigner.engine.entity.PlacedEntity
import io.stardewvalleydesigner.kmplib.png.PngUtils
import io.stardewvalleydesigner.ui.component.editor.res.*
import io.stardewvalleydesigner.ui.component.editor.utils.DrawerUtils


internal object DesignRenderer {

    suspend fun generateDesignAsPngBytes(
        map: MapState,
        sprites: List<Pair<PlacedEntity<*>, Sprite>>,
        wallsAndFloors: ImageBitmap,
        layoutSprite: LayoutSprite,
    ): ByteArray {
        val bitmap = render(map, sprites, wallsAndFloors, layoutSprite)
        val pixels = IntArray(bitmap.width * bitmap.height).apply { bitmap.readPixels(buffer = this) }
        val pngBytes = PngUtils.generatePngBytes(pixels, bitmap.width, bitmap.height)

        return pngBytes
    }


    private fun render(
        map: MapState,
        sprites: List<Pair<PlacedEntity<*>, Sprite>>,
        wallsAndFloors: ImageBitmap,
        layoutSprite: LayoutSprite,
    ): ImageBitmap {
        val layout = map.layout
        val (nW, nH) = layout.size

        val imageBitmap = ImageBitmap(layoutSprite.size.width, layoutSprite.size.height)

        Canvas(imageBitmap).run {
            drawImageRect(image = layoutSprite.bgImage, paint = Paint())

            if (layout.type.isShed()) {
                val fl = ImageResourcesProvider.flooringSpriteBy(wallsAndFloors, map.flooring ?: Flooring.all().first())
                val wp =
                    ImageResourcesProvider.wallpaperSpriteBy(wallsAndFloors, map.wallpaper ?: Wallpaper.all().first())

                for (x in List(size = (nW + 1) / 2 + 1) { -1 + 2 * it }) {
                    for (y in List(size = (nH + 1) / 2) { 2 * (it + 1) }) {
                        drawImageRect(
                            image = fl.image,
                            srcOffset = fl.offset,
                            srcSize = fl.size,
                            dstOffset = UNIT * IntOffset(x, y),
                            paint = Paint()
                        )
                    }
                }
                for (x in 0..nW) {
                    drawImageRect(
                        image = wp.image,
                        srcOffset = wp.offset,
                        srcSize = wp.size,
                        dstOffset = UNIT * IntOffset(x, y = 1),
                        paint = Paint()
                    )
                }
            }

            for ((placedEntity, sprite) in sprites) {
                val (e, place) = placedEntity
                val rectH = sprite.size.height / UNIT

                when (sprite) {
                    is Sprite.Image -> drawImageRect(
                        image = sprite.image,
                        srcOffset = sprite.offset,
                        srcSize = sprite.size,
                        dstOffset = UNIT * IntOffset(x = place.x, y = place.y - (rectH - e.size.h)),
                        paint = Paint()
                    )

                    is Sprite.ChestImage -> {
                        drawImageRect(
                            image = sprite.image,
                            srcOffset = sprite.offset,
                            srcSize = sprite.size,
                            dstOffset = UNIT * IntOffset(x = place.x, y = place.y - (rectH - e.size.h)),
                            paint = Paint().apply { colorFilter = DrawerUtils.tint(sprite.tint) }
                        )
                        drawImageRect(
                            image = sprite.image,
                            srcOffset = sprite.coverOffset,
                            srcSize = sprite.size,
                            dstOffset = UNIT * IntOffset(x = place.x, y = place.y - (rectH - e.size.h)),
                            paint = Paint()
                        )
                    }
                }
            }

            drawImageRect(image = layoutSprite.fgImage, paint = Paint())
        }

        return imageBitmap
    }

    private operator fun Int.times(size: IntOffset): IntOffset = IntOffset(x = this * size.x, y = this * size.y)
}
