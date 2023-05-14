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

package me.azimmuradov.svc.screens.mainmenu

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import me.azimmuradov.svc.cartographer.res.*
import me.azimmuradov.svc.engine.Flooring
import me.azimmuradov.svc.engine.Wallpaper
import me.azimmuradov.svc.engine.geometry.Rect
import me.azimmuradov.svc.engine.geometry.aspectRatio
import me.azimmuradov.svc.engine.layers.LayeredEntitiesData
import me.azimmuradov.svc.metadata.EntityPage.Companion.UNIT
import me.azimmuradov.svc.utils.*
import kotlin.math.roundToInt


@Composable
fun BoxScope.LayoutPreview(
    layoutSprite: LayoutSprites,
    layoutSize: Rect,
    entities: LayeredEntitiesData,
    wallpaper: Wallpaper?,
    flooring: Flooring?,
) {
    val (nW, nH) = layoutSize

    var stepSize by remember { mutableStateOf(-1f) }


    Canvas(
        modifier = Modifier
            .aspectRatio(layoutSprite.size.toRect().aspectRatio)
            .fillMaxSize()
            .align(Alignment.Center)
            .clipToBounds()
            .background(color = Color.White)
    ) {
        stepSize = size.height / nH

        val cellSize = Size(stepSize, stepSize)
        val offsetsW = List(size = nW + 1) { it * stepSize }
        val offsetsH = List(size = nH + 1) { it * stepSize }


        // Bottom layer

        drawImage(
            image = layoutSprite.bgImage,
            srcSize = layoutSprite.size,
            dstSize = size.toIntSize(),
            filterQuality = FilterQuality.High,
        )


        // Flooring

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
                    filterQuality = FilterQuality.High,
                )
            }
        }


        // Wallpaper

        val off = (List(nW) { stepSize * it } + size.width).map { it.roundToInt() }

        off.zipWithNext().forEach { (st, en) ->
            val sprite = wallpaper(wallpaper ?: Wallpaper.all().first())

            drawImage(
                image = sprite.image,
                srcOffset = sprite.offset,
                srcSize = sprite.size,
                dstOffset = IntOffset(x = st, y = stepSize.roundToInt()),
                dstSize = IntSize(width = (en - st), height = (stepSize * 3).roundToInt()),
                filterQuality = FilterQuality.High,
            )
        }


        // Entities

        for ((_, objs) in entities.all) {
            for (e in objs.sortedBy { it.place.y }) {
                val sprite = EntitySpritesProvider.spriteBy(e.rectObject)
                val rect = (sprite.size / UNIT).toRect()
                drawSprite(
                    sprite = sprite,
                    offset = IntOffset(
                        x = offsetsW[e.place.x].toInt(),
                        y = offsetsH[e.place.y - (rect.h - e.rectObject.size.h)].toInt()
                    ),
                    layoutSize = Size(
                        width = (cellSize.width * rect.w).coerceAtLeast(1f),
                        height = (cellSize.height * rect.h).coerceAtLeast(1f)
                    ),
                )
            }
        }


        // Top layer

        drawImage(
            image = layoutSprite.fgImage,
            srcSize = layoutSprite.size,
            dstSize = size.toIntSize(),
            filterQuality = FilterQuality.High,
        )
    }
}
