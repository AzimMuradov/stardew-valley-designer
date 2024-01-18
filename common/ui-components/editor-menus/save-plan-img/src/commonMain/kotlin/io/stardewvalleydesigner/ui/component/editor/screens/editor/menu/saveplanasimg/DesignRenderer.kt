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

package io.stardewvalleydesigner.ui.component.editor.screens.editor.menu.saveplanasimg

import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.IntOffset
import io.stardewvalleydesigner.editor.modules.map.MapState
import io.stardewvalleydesigner.engine.Flooring
import io.stardewvalleydesigner.engine.Wallpaper
import io.stardewvalleydesigner.engine.layer.LayerType
import io.stardewvalleydesigner.kmplib.png.PngUtils
import io.stardewvalleydesigner.metadata.EntityPage
import io.stardewvalleydesigner.ui.component.editor.res.*
import io.stardewvalleydesigner.ui.component.editor.utils.DrawerUtils


internal object DesignRenderer {

    fun generatePlanAsPngBytes(
        map: MapState,
        visibleLayers: Set<LayerType<*>>,
        entityMaps: Map<EntityPage, ImageBitmap>,
        wallsAndFloors: ImageBitmap,
        layoutSprite: LayoutSprites,
    ): ByteArray {
        val bitmap = render(map, visibleLayers, entityMaps, wallsAndFloors, layoutSprite)
        val pixels = IntArray(bitmap.width * bitmap.height).apply { bitmap.readPixels(buffer = this) }
        val pngBytes = PngUtils.generatePngBytes(pixels, bitmap.width, bitmap.height)

        return pngBytes
    }


    private fun render(
        map: MapState,
        visibleLayers: Set<LayerType<*>>,
        entityMaps: Map<EntityPage, ImageBitmap>,
        wallsAndFloors: ImageBitmap,
        layoutSprite: LayoutSprites,
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
                            dstOffset = EntityPage.UNIT * IntOffset(x, y),
                            paint = Paint()
                        )
                    }
                }
                for (x in 0..nW) {
                    drawImageRect(
                        image = wp.image,
                        srcOffset = wp.offset,
                        srcSize = wp.size,
                        dstOffset = EntityPage.UNIT * IntOffset(x, y = 1),
                        paint = Paint()
                    )
                }
            }

            val sorted = visibleLayers.flatMap(map.entities::entitiesBy).sortedWith(DrawerUtils.placedEntityComparator)

            for ((e, place) in sorted) {
                val sprite = ImageResourcesProvider.spriteBy(entityMaps, e)
                val rectH = sprite.size.height / EntityPage.UNIT

                when (sprite) {
                    is Sprite.Image -> drawImageRect(
                        image = sprite.image,
                        srcOffset = sprite.offset,
                        srcSize = sprite.size,
                        dstOffset = EntityPage.UNIT * IntOffset(x = place.x, y = place.y - (rectH - e.size.h)),
                        paint = Paint()
                    )

                    is Sprite.TintedImage -> {
                        drawImageRect(
                            image = sprite.image,
                            srcOffset = sprite.offset,
                            srcSize = sprite.size,
                            dstOffset = EntityPage.UNIT * IntOffset(x = place.x, y = place.y - (rectH - e.size.h)),
                            paint = Paint().apply { colorFilter = DrawerUtils.tint(sprite.tint) }
                        )
                        drawImageRect(
                            image = sprite.image,
                            srcOffset = sprite.coverOffset,
                            srcSize = sprite.size,
                            dstOffset = EntityPage.UNIT * IntOffset(x = place.x, y = place.y - (rectH - e.size.h)),
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
