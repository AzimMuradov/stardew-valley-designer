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
import me.azimmuradov.svc.cartographer.res.LayoutSpritesProvider.layoutSpriteBy
import me.azimmuradov.svc.engine.Flooring
import me.azimmuradov.svc.engine.Wallpaper
import me.azimmuradov.svc.engine.geometry.aspectRatio
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layers.LayeredEntitiesData
import me.azimmuradov.svc.engine.layout.Layout
import me.azimmuradov.svc.utils.DrawerUtils.drawFlooring
import me.azimmuradov.svc.utils.DrawerUtils.drawVisibleEntities
import me.azimmuradov.svc.utils.DrawerUtils.drawWallpaper
import me.azimmuradov.svc.utils.toIntSize
import me.azimmuradov.svc.utils.toRect


@Composable
fun BoxScope.LayoutPreview(
    layout: Layout,
    entities: LayeredEntitiesData,
    wallpaper: Wallpaper?,
    flooring: Flooring?,
) {
    val (nW, nH) = layout.size
    val layoutSprite = layoutSpriteBy(layout.type)

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


        // Background

        drawImage(
            image = layoutSprite.bgImage,
            srcSize = layoutSprite.size,
            dstSize = size.toIntSize(),
            filterQuality = FilterQuality.High,
        )


        // Main content

        if (layout.type.isShed()) {
            drawFlooring(flooring, nW, nH, stepSize)
            drawWallpaper(wallpaper, nW, stepSize)
        }

        drawVisibleEntities(
            entities = entities,
            visibleLayers = LayerType.all,
            renderSpritesFully = true,
            offsetsW = offsetsW,
            offsetsH = offsetsH,
            cellSize = cellSize
        )


        // Foreground

        drawImage(
            image = layoutSprite.fgImage,
            srcSize = layoutSprite.size,
            dstSize = size.toIntSize(),
            filterQuality = FilterQuality.High,
        )
    }
}
