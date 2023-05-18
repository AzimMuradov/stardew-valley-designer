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

package me.azimmuradov.svc.utils

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import me.azimmuradov.svc.cartographer.res.*
import me.azimmuradov.svc.engine.Flooring
import me.azimmuradov.svc.engine.Wallpaper
import me.azimmuradov.svc.engine.entity.Entity
import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layer.toLayerType
import me.azimmuradov.svc.engine.layers.LayeredEntitiesData
import me.azimmuradov.svc.metadata.EntityPage
import kotlin.math.roundToInt


object DrawerUtils {

    fun DrawScope.drawSpriteBy(
        entity: Entity<*>,
        offset: IntOffset = IntOffset.Zero,
        layoutSize: Size,
        alpha: Float = 1.0f,
    ) {
        drawSprite(
            sprite = EntitySpritesProvider.spriteBy(entity),
            offset = offset,
            layoutSize = layoutSize,
            alpha = alpha,
        )
    }

    fun DrawScope.drawSprite(
        sprite: Sprite,
        offset: IntOffset = IntOffset.Zero,
        layoutSize: Size,
        alpha: Float = 1.0f,
    ) {
        val (spriteW, spriteH) = sprite.size
        val (layoutW, layoutH) = layoutSize

        val spriteRatio = sprite.size.ratio
        val layoutRatio = layoutSize.ratio

        val dstSize = if (spriteRatio > layoutRatio) {
            IntSize(width = layoutW.roundToInt(), height = (spriteH * layoutW / spriteW).roundToInt())
        } else {
            IntSize(width = (spriteW * layoutH / spriteH).roundToInt(), height = layoutH.roundToInt())
        }

        val dstOffset = offset + IntOffset(
            x = ((layoutW - dstSize.width) / 2).roundToInt(),
            y = ((layoutH - dstSize.height) / 2).roundToInt(),
        )

        drawImage(
            image = sprite.image,
            srcOffset = sprite.offset,
            srcSize = sprite.size,
            dstOffset = dstOffset,
            dstSize = dstSize,
            alpha = alpha,
            style = Fill,
            colorFilter = null,
            blendMode = DrawScope.DefaultBlendMode,
            filterQuality = if (spriteW * 1.5 < dstSize.width) FilterQuality.None else FilterQuality.Medium,
        )
    }


    fun DrawScope.drawVisibleEntities(
        entities: LayeredEntitiesData,
        visibleLayers: Set<LayerType<*>>,
        offsetsW: List<Float>,
        offsetsH: List<Float>,
        cellSize: Size,
    ) {
        val sorted = visibleLayers.flatMap(entities::entitiesBy).sortedWith(placedEntityComparator)

        for ((e, place) in sorted) {
            val sprite = EntitySpritesProvider.spriteBy(e)
            val rect = (sprite.size / EntityPage.UNIT).toRect()
            drawSprite(
                sprite = sprite,
                offset = IntOffset(
                    x = offsetsW[place.x].toInt(),
                    y = offsetsH[place.y - (rect.h - e.size.h)].toInt()
                ),
                layoutSize = Size(
                    width = (cellSize.width * rect.w).coerceAtLeast(1f),
                    height = (cellSize.height * rect.h).coerceAtLeast(1f)
                ),
            )
        }
    }


    fun DrawScope.drawFlooring(
        flooring: Flooring?,
        nW: Int,
        nH: Int,
        stepSize: Float,
    ) {
        val off1 = List(nW) { -stepSize + stepSize * 2 * it }.map { it.roundToInt() }
        val off2 = List(nH) { stepSize * 2 * (it + 1) }.map { it.roundToInt() }

        off1.zipWithNext().forEach { (st1, en1) ->
            off2.zipWithNext().forEach { (st2, en2) ->
                val sprite = flooring(flooring ?: Flooring.all().first())

                drawImage(
                    image = sprite.image,
                    srcOffset = sprite.offset,
                    srcSize = sprite.size,
                    dstOffset = IntOffset(x = st1, y = st2),
                    dstSize = IntSize(width = (en1 - st1), height = (en2 - st2)),
                    filterQuality = FilterQuality.None,
                )
            }
        }
    }

    fun DrawScope.drawWallpaper(
        wallpaper: Wallpaper?,
        nW: Int,
        stepSize: Float,
    ) {
        val off = (List(nW) { stepSize * it } + size.width).map { it.roundToInt() }

        off.zipWithNext().forEach { (st, en) ->
            val sprite = wallpaper(wallpaper ?: Wallpaper.all().first())

            drawImage(
                image = sprite.image,
                srcOffset = sprite.offset,
                srcSize = sprite.size,
                dstOffset = IntOffset(x = st, y = stepSize.roundToInt()),
                dstSize = IntSize(width = (en - st), height = (stepSize * 3).roundToInt()),
                filterQuality = FilterQuality.None,
            )
        }
    }


    private val placedEntityComparator: Comparator<PlacedEntity<*>> = Comparator
        .comparing<PlacedEntity<*>, Int> { (_, p) -> p.y }
        .thenComparing { (e, _) -> e.type.toLayerType().ordinal }
}
