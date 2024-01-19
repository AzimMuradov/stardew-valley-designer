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

package io.stardewvalleydesigner.ui.component.editor.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import io.stardewvalleydesigner.engine.Flooring
import io.stardewvalleydesigner.engine.Wallpaper
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.engine.entity.PlacedEntity
import io.stardewvalleydesigner.engine.layer.LayerType
import io.stardewvalleydesigner.engine.layer.toLayerType
import io.stardewvalleydesigner.engine.layers.LayeredEntitiesData
import io.stardewvalleydesigner.metadata.EntityPage
import io.stardewvalleydesigner.metadata.EntityPage.Companion.UNIT
import io.stardewvalleydesigner.ui.component.editor.res.ImageResourcesProvider.flooringSpriteBy
import io.stardewvalleydesigner.ui.component.editor.res.ImageResourcesProvider.spriteBy
import io.stardewvalleydesigner.ui.component.editor.res.ImageResourcesProvider.wallpaperSpriteBy
import io.stardewvalleydesigner.ui.component.editor.res.Sprite
import kotlin.math.roundToInt


object DrawerUtils {

    fun DrawScope.drawEntityContained(
        entityMaps: Map<EntityPage, ImageBitmap>,
        entity: Entity<*>,
        offset: IntOffset = IntOffset.Zero,
        layoutSize: Size,
    ) {
        val sprite = spriteBy(entityMaps, entity)
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
            is Sprite.Image -> drawImage(sprite, dstOffset, dstSize)
            is Sprite.TintedImage -> drawTintedImage(sprite, dstOffset, dstSize)
        }
    }

    fun DrawScope.drawSpriteStretched(
        sprite: Sprite,
        offset: IntOffset = IntOffset.Zero,
        layoutSize: IntSize,
        alpha: Float = 1.0f,
    ) {
        when (sprite) {
            is Sprite.Image -> drawImage(sprite, dstOffset = offset, dstSize = layoutSize, alpha)
            is Sprite.TintedImage -> drawTintedImage(sprite, dstOffset = offset, dstSize = layoutSize, alpha)
        }
    }

    fun DrawScope.drawEntityStretched(
        entityMaps: Map<EntityPage, ImageBitmap>,
        entity: PlacedEntity<*>,
        renderSpritesFully: Boolean,
        grid: CoordinateGrid,
        paddingInPx: UInt = 0u,
        alpha: Float = 1.0f,
    ) {
        val (e, place) = entity

        val sprite = spriteBy(entityMaps, e).run {
            if (renderSpritesFully) {
                this
            } else {
                when (this) {
                    is Sprite.Image -> copy(
                        offset = offset.copy(y = offset.y + (size.height - e.size.h * UNIT)),
                        size = e.size.toIntSize() * UNIT
                    )

                    is Sprite.TintedImage -> copy(
                        offset = offset.copy(y = offset.y + (size.height - e.size.h * UNIT)),
                        coverOffset = coverOffset.copy(y = coverOffset.y + (size.height - e.size.h * UNIT)),
                        size = e.size.toIntSize() * UNIT
                    )
                }
            }
        }

        val rect = (sprite.size / UNIT).toRect()

        val padding = IntOffset(paddingInPx.toInt(), paddingInPx.toInt())

        val offsetTopLeft = grid[place.x, place.y - (rect.h - e.size.h)].toIntOffset() + padding
        val offsetBottomRight = grid[place.x + rect.w, place.y + e.size.h].toIntOffset() - padding

        drawSpriteStretched(
            sprite = sprite,
            offset = offsetTopLeft,
            layoutSize = IntSize(offsetTopLeft, offsetBottomRight),
            alpha = alpha,
        )
    }


    fun DrawScope.drawVisibleEntities(
        entityMaps: Map<EntityPage, ImageBitmap>,
        entities: LayeredEntitiesData,
        visibleLayers: Set<LayerType<*>>,
        renderSpritesFully: Boolean,
        grid: CoordinateGrid,
    ) {
        val sorted = visibleLayers.flatMap(entities::entitiesBy).sortedWith(placedEntityComparator)

        for (entity in sorted) {
            drawEntityStretched(entityMaps, entity, renderSpritesFully, grid)
        }
    }


    fun DrawScope.drawFlooring(
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

    fun DrawScope.drawWallpaper(
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
        alpha: Float = 1.0f,
    ) {
        drawSprite(
            sprite, srcOffset = sprite.offset,
            dstOffset, dstSize,
            alpha = alpha
        )
    }

    private fun DrawScope.drawTintedImage(
        sprite: Sprite.TintedImage,
        dstOffset: IntOffset, dstSize: IntSize,
        alpha: Float = 1.0f,
    ) {
        drawSprite(
            sprite, srcOffset = sprite.offset,
            dstOffset, dstSize,
            colorFilter = tint(sprite.tint),
            alpha
        )
        drawSprite(
            sprite, srcOffset = sprite.coverOffset,
            dstOffset, dstSize,
            alpha = alpha
        )
    }

    private fun DrawScope.drawSprite(
        sprite: Sprite, srcOffset: IntOffset,
        dstOffset: IntOffset, dstSize: IntSize,
        colorFilter: ColorFilter? = null,
        alpha: Float = 1.0f,
    ) {
        drawImage(
            image = sprite.image,
            srcOffset = srcOffset, srcSize = sprite.size,
            dstOffset, dstSize,
            alpha,
            colorFilter = colorFilter,
            filterQuality = if (sprite.size.width * 1.5 < dstSize.width) {
                FilterQuality.None
            } else {
                FilterQuality.Medium
            }
        )
    }


    val placedEntityComparator: Comparator<PlacedEntity<*>> =
        compareBy<PlacedEntity<*>> { (e, _) -> e.type.toLayerType().ordinal }
            .thenBy { (_, place) -> place.y }

    fun tint(color: Color) = ColorFilter.tint(
        color = color,
        blendMode = BlendMode.Modulate
    )
}
