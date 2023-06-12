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

package io.stardewvalleydesigner.screens.editor.topmenubar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import dev.dirs.UserDirectories
import io.stardewvalleydesigner.editor.modules.map.MapState
import io.stardewvalleydesigner.editor.res.*
import io.stardewvalleydesigner.editor.res.LayoutSpritesProvider.layoutSpriteBy
import io.stardewvalleydesigner.engine.Flooring
import io.stardewvalleydesigner.engine.Wallpaper
import io.stardewvalleydesigner.engine.layer.LayerType
import io.stardewvalleydesigner.metadata.EntityPage.Companion.UNIT
import io.stardewvalleydesigner.utils.*
import io.stardewvalleydesigner.utils.DrawerUtils.placedEntityComparator
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.imageio.ImageIO
import java.io.File.separator as sep


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScreenshotButton(map: MapState, visibleLayers: Set<LayerType<*>>) {
    val wordList = GlobalSettings.strings

    Box(modifier = Modifier.aspectRatio(1f).fillMaxHeight()) {
        TooltipArea(wordList.buttonMakeScreenshotTooltip) {
            TopMenuIconButton(
                icon = Icons.Rounded.Image,
                onClick = { makeScreenshot(map, visibleLayers) }
            )
        }
    }
}


private fun makeScreenshot(map: MapState, visibleLayers: Set<LayerType<*>>) {
    val layout = map.layout
    val layoutSprite = layoutSpriteBy(layout.type)
    val (nW, nH) = layout.size

    val imageBitmap = ImageBitmap(layoutSprite.size.width, layoutSprite.size.height)

    Canvas(imageBitmap).run {
        val offsetsW = List(size = nW + 1) { it * UNIT }
        val offsetsH = List(size = nH + 1) { it * UNIT }

        drawImageRect(image = layoutSprite.bgImage, paint = Paint())

        if (layout.type.isShed()) {
            val off1 = List(nW) { -UNIT + UNIT * 2 * it }
            val off2 = List(nH) { UNIT * 2 * (it + 1) }

            off1.zipWithNext().forEach { (st1, en1) ->
                off2.zipWithNext().forEach { (st2, en2) ->
                    val sprite = flooring(map.flooring ?: Flooring.all().first())

                    drawImageRect(
                        image = sprite.image,
                        srcOffset = sprite.offset,
                        srcSize = sprite.size,
                        dstOffset = IntOffset(x = st1, y = st2),
                        dstSize = IntSize(width = (en1 - st1), height = (en2 - st2)),
                        paint = Paint()
                    )
                }
            }
        }

        if (layout.type.isShed()) {
            val off = List(size = nW + 1) { UNIT * it }

            off.zipWithNext().forEach { (st, en) ->
                val sprite = wallpaper(map.wallpaper ?: Wallpaper.all().first())

                drawImageRect(
                    image = sprite.image,
                    srcOffset = sprite.offset,
                    srcSize = sprite.size,
                    dstOffset = IntOffset(x = st, y = UNIT),
                    dstSize = IntSize(width = (en - st), height = UNIT * 3),
                    paint = Paint()
                )
            }
        }

        val sorted = visibleLayers.flatMap(map.entities::entitiesBy).sortedWith(placedEntityComparator)

        for ((e, place) in sorted) {
            val sprite = EntitySpritesProvider.spriteBy(e)
            val rect = (sprite.size / UNIT).toRect()

            drawImageRect(
                image = sprite.image,
                srcOffset = sprite.offset,
                srcSize = sprite.size,
                dstOffset = IntOffset(
                    x = offsetsW[place.x],
                    y = offsetsH[place.y - (rect.h - e.size.h)]
                ),
                dstSize = sprite.size,
                paint = Paint()
            )
        }

        drawImageRect(image = layoutSprite.fgImage, paint = Paint())
    }


    val formatted = DateTimeFormatter
        .ofPattern("yyyy-MM-dd-HH-mm-ss")
        .withZone(ZoneId.systemDefault())
        .format(Instant.now())

    val dir = "${UserDirectories.get().pictureDir}${sep}Stardew Valley Designer"
    val filename = "design-$formatted.png"

    Files.createDirectories(Path.of(dir))

    ImageIO.write(
        imageBitmap.toAwtImage(),
        "png",
        File("$dir$sep$filename")
    )
}