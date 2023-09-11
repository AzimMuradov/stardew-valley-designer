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
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.IntOffset
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
import io.stardewvalleydesigner.utils.DrawerUtils.tint
import io.stardewvalleydesigner.utils.filedialogs.FileSaver
import kotlinx.coroutines.launch
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
fun SavePlanAsImageButton(
    map: MapState,
    visibleLayers: Set<LayerType<*>>,
    snackbarHostState: SnackbarHostState,
) {
    val wordList = GlobalSettings.strings

    var pathname by remember { mutableStateOf("") }

    var showFileSaver by remember { mutableStateOf(false) }

    Box(modifier = Modifier.aspectRatio(1f).fillMaxHeight()) {
        TooltipArea(wordList.buttonSavePlanAsImageTooltip) {
            TopMenuIconButton(
                icon = Icons.Rounded.Image,
                onClick = {
                    val defaultDir = "${UserDirectories.get().pictureDir}${sep}Stardew Valley Designer${sep}"
                    Files.createDirectories(Path.of(defaultDir))
                    pathname = run {
                        val now = Instant.now()
                        val formatted = DateTimeFormatter
                            .ofPattern("yyyy-MM-dd-HH-mm-ss")
                            .withZone(ZoneId.systemDefault())
                            .format(now)
                        val filename = "design-$formatted.png"
                        return@run "$defaultDir$sep$filename"
                    }
                    showFileSaver = true
                }
            )
        }
    }

    val scope = rememberCoroutineScope()

    if (showFileSaver) {
        FileSaver(
            title = "Choose where to save",
            defaultPathAndFile = pathname,
            extensions = listOf("png"),
        ) { path ->
            showFileSaver = false
            path?.let {
                scope.launch {
                    savePlanAsImage(path.toString(), map, visibleLayers)
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(message = "Saved to \"$path\"")
                }
            }
        }
    }
}


private fun savePlanAsImage(pathname: String, map: MapState, visibleLayers: Set<LayerType<*>>): String {
    Files.createDirectories(Path.of(pathname).parent)

    ImageIO.write(
        render(map, visibleLayers).toAwtImage(),
        "png",
        File(pathname)
    )

    return pathname
}

private fun render(map: MapState, visibleLayers: Set<LayerType<*>>): ImageBitmap {
    val layout = map.layout
    val layoutSprite = layoutSpriteBy(layout.type)
    val (nW, nH) = layout.size

    val imageBitmap = ImageBitmap(layoutSprite.size.width, layoutSprite.size.height)

    Canvas(imageBitmap).run {
        drawImageRect(image = layoutSprite.bgImage, paint = Paint())

        if (layout.type.isShed()) {
            val fl = flooring(map.flooring ?: Flooring.all().first())
            val wp = wallpaper(map.wallpaper ?: Wallpaper.all().first())

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

        val sorted = visibleLayers.flatMap(map.entities::entitiesBy).sortedWith(placedEntityComparator)

        for ((e, place) in sorted) {
            val sprite = EntitySpritesProvider.spriteBy(e)
            val rectH = sprite.size.height / UNIT

            when (sprite) {
                is Sprite.Image -> drawImageRect(
                    image = sprite.image,
                    srcOffset = sprite.offset,
                    srcSize = sprite.size,
                    dstOffset = UNIT * IntOffset(x = place.x, y = place.y - (rectH - e.size.h)),
                    paint = Paint()
                )

                is Sprite.TintedImage -> {
                    drawImageRect(
                        image = sprite.image,
                        srcOffset = sprite.offset,
                        srcSize = sprite.size,
                        dstOffset = UNIT * IntOffset(x = place.x, y = place.y - (rectH - e.size.h)),
                        paint = Paint().apply { colorFilter = tint(sprite.tint) }
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
