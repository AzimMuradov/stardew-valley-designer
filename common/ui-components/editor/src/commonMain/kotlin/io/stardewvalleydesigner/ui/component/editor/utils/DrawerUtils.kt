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

package io.stardewvalleydesigner.ui.component.editor.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import io.stardewvalleydesigner.data.Season
import io.stardewvalleydesigner.data.SpritePage
import io.stardewvalleydesigner.data.SpritePage.Companion.UNIT
import io.stardewvalleydesigner.engine.Flooring
import io.stardewvalleydesigner.engine.Wallpaper
import io.stardewvalleydesigner.engine.entity.PlacedEntity
import io.stardewvalleydesigner.engine.layer.LayerType
import io.stardewvalleydesigner.engine.layer.toLayerType
import io.stardewvalleydesigner.engine.layers.LayeredEntitiesData
import io.stardewvalleydesigner.ui.component.editor.res.ImageResourcesProvider.flooringSpriteBy
import io.stardewvalleydesigner.ui.component.editor.res.ImageResourcesProvider.wallpaperSpriteBy
import io.stardewvalleydesigner.ui.component.editor.res.Sprite
import io.stardewvalleydesigner.ui.component.editor.res.SpriteUtils
import kotlin.math.roundToInt


object DrawerUtils {

    val placedEntityComparator: Comparator<PlacedEntity<*>> =
        compareBy<PlacedEntity<*>> { (e, _) -> e.type.toLayerType().ordinal }
            .thenBy { (_, place) -> place.y }

    fun tint(color: Color) = ColorFilter.tint(
        color = color,
        blendMode = BlendMode.Modulate
    )


    internal fun DrawScope.drawEntityContained(
        sprite: Sprite,
        offset: IntOffset = IntOffset.Zero,
        layoutSize: Size,
        scale: Float = 1.0f,
    ) {
        val (spriteW, spriteH) = sprite.size
        val (layoutW, layoutH) = layoutSize

        val spriteRatio = sprite.size.ratio
        val layoutRatio = layoutSize.ratio

        val dstSize = if (spriteRatio > layoutRatio) {
            Size(width = layoutW, height = spriteH * layoutW / spriteW)
        } else {
            Size(width = spriteW * layoutH / spriteH, height = layoutH)
        }.toIntSize()

        val dstOffset = offset + Offset(
            x = (layoutW - dstSize.width) / 2,
            y = (layoutH - dstSize.height) / 2,
        ).toIntOffset()

        when (sprite) {
            is Sprite.Image -> drawImage(sprite, dstOffset, dstSize, scale)
            is Sprite.ChestImage -> drawChestImage(sprite, dstOffset, dstSize, scale)
        }
    }

    internal fun DrawScope.drawSpriteStretched(
        sprite: Sprite,
        offset: IntOffset = IntOffset.Zero,
        layoutSize: IntSize,
        scale: Float = 1.0f,
        alpha: Float = 1.0f,
    ) {
        when (sprite) {
            is Sprite.Image -> drawImage(
                sprite = sprite,
                dstOffset = offset,
                dstSize = layoutSize,
                scale = scale,
                alpha = alpha
            )

            is Sprite.ChestImage -> drawChestImage(
                sprite = sprite,
                dstOffset = offset,
                dstSize = layoutSize,
                scale = scale,
                alpha = alpha
            )
        }
    }

    internal fun DrawScope.drawEntityStretched(
        entity: PlacedEntity<*>,
        sprite: Sprite,
        renderSpritesFully: Boolean,
        grid: CoordinateGrid,
        scale: Float = 1.0f,
        paddingInPx: UInt = 0u,
        alpha: Float = 1.0f,
    ) {
        val (e, place) = entity

        val sprite2 = sprite.run {
            if (renderSpritesFully) {
                this
            } else {
                when (this) {
                    is Sprite.Image -> copy(
                        offset = offset.copy(y = offset.y + (size.height - e.size.h * UNIT)),
                        size = e.size.toIntSize() * UNIT
                    )

                    is Sprite.ChestImage -> copy(
                        offset = offset.copy(y = offset.y + (size.height - e.size.h * UNIT)),
                        coverOffset = coverOffset.copy(y = coverOffset.y + (size.height - e.size.h * UNIT)),
                        size = e.size.toIntSize() * UNIT
                    )
                }
            }
        }

        val rect = (sprite2.size / UNIT).toRect()

        val padding = IntOffset(paddingInPx.toInt(), paddingInPx.toInt())

        val offsetTopLeft = grid[place.x, place.y - (rect.h - e.size.h)].toIntOffset() + padding
        val offsetBottomRight = grid[place.x + rect.w, place.y + e.size.h].toIntOffset() - padding

        drawSpriteStretched(
            sprite = sprite2,
            offset = offsetTopLeft,
            layoutSize = IntSize(offsetTopLeft, offsetBottomRight),
            scale = scale,
            alpha = alpha,
        )
    }


    internal fun DrawScope.drawVisibleEntities(
        entityMaps: Map<SpritePage, ImageBitmap>,
        entities: LayeredEntitiesData,
        season: Season,
        visibleLayers: Set<LayerType<*>>,
        renderSpritesFully: Boolean,
        grid: CoordinateGrid,
        scale: Float = 1.0f,
    ) {
        val spriteMaps = SpriteUtils.calculateSprite(entityMaps, entities, visibleLayers, season)

        for ((entity, sprite) in spriteMaps) {
            drawEntityStretched(entity, sprite, renderSpritesFully, grid, scale)
        }
    }

    internal fun DrawScope.drawFlooring(
        wallsAndFloors: ImageBitmap,
        flooring: Flooring?,
        nW: Int, nH: Int,
        stepSize: Float,
    ) {
        val xs = List(size = (nW + 1) / 2 + 1) { -stepSize + stepSize * 2 * it }.map { it.roundToInt() }
        val ys = List(size = (nH + 1) / 2) { stepSize * 2 * (it + 1) }.map { it.roundToInt() }

        val sprite = flooringSpriteBy(wallsAndFloors, flooring ?: Flooring.all().first())

        for ((left, right) in xs.zipWithNext()) {
            for ((top, bottom) in ys.zipWithNext()) {
                drawImage(
                    image = sprite.image,
                    srcOffset = sprite.offset,
                    srcSize = sprite.size,
                    dstOffset = IntOffset(x = left, y = top),
                    dstSize = IntSize(width = right - left, height = bottom - top),
                    filterQuality = FilterQuality.None,
                )
            }
        }
    }

    internal fun DrawScope.drawWallpaper(
        wallsAndFloors: ImageBitmap,
        wallpaper: Wallpaper?,
        nW: Int,
        stepSize: Float,
    ) {
        val xs = List(size = nW + 1) { stepSize * it }.map { it.roundToInt() }
        val top = stepSize.roundToInt()
        val bottom = (stepSize * 4).roundToInt()
        val height = bottom - top

        val sprite = wallpaperSpriteBy(wallsAndFloors, wallpaper ?: Wallpaper.all().first())

        for ((left, right) in xs.zipWithNext()) {
            drawImage(
                image = sprite.image,
                srcOffset = sprite.offset,
                srcSize = sprite.size,
                dstOffset = IntOffset(x = left, y = top),
                dstSize = IntSize(width = right - left, height),
                filterQuality = FilterQuality.None,
            )
        }
    }


    private fun DrawScope.drawImage(
        sprite: Sprite.Image,
        dstOffset: IntOffset, dstSize: IntSize,
        scale: Float,
        alpha: Float = 1.0f,
    ) {
        drawSprite(
            sprite, srcOffset = sprite.offset,
            dstOffset, dstSize,
            scale = scale,
            alpha = alpha,
        )
    }

    private fun DrawScope.drawChestImage(
        sprite: Sprite.ChestImage,
        dstOffset: IntOffset, dstSize: IntSize,
        scale: Float,
        alpha: Float = 1.0f,
    ) {
        drawSprite(
            sprite, srcOffset = sprite.offset,
            dstOffset, dstSize,
            scale = scale,
            colorFilter = tint(sprite.tint),
            alpha = alpha,
        )
        drawSprite(
            sprite, srcOffset = sprite.coverOffset,
            dstOffset, dstSize,
            scale = scale,
            alpha = alpha,
        )
    }

    private fun DrawScope.drawSprite(
        sprite: Sprite, srcOffset: IntOffset,
        dstOffset: IntOffset, dstSize: IntSize,
        scale: Float,
        colorFilter: ColorFilter? = null,
        alpha: Float = 1.0f,
    ) {
        drawImage(
            image = sprite.image,
            srcOffset = srcOffset, srcSize = sprite.size,
            dstOffset, dstSize,
            alpha,
            colorFilter = colorFilter,
            filterQuality = if (sprite.size.width * 1.5 < dstSize.width * scale) {
                FilterQuality.None
            } else {
                FilterQuality.Medium
            }
        )
    }
}
