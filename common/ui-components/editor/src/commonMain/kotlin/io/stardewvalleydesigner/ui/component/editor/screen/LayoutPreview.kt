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

package io.stardewvalleydesigner.ui.component.editor.screen

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
import androidx.compose.ui.unit.IntSize
import io.stardewvalleydesigner.data.Season
import io.stardewvalleydesigner.engine.Flooring
import io.stardewvalleydesigner.engine.Wallpaper
import io.stardewvalleydesigner.engine.geometry.*
import io.stardewvalleydesigner.engine.layers.LayeredEntities
import io.stardewvalleydesigner.engine.layout.Layout
import io.stardewvalleydesigner.ui.component.editor.res.ImageResources
import io.stardewvalleydesigner.ui.component.editor.res.ImageResourcesProvider.layoutSpriteBy
import io.stardewvalleydesigner.ui.component.editor.utils.CoordinateGrid
import io.stardewvalleydesigner.ui.component.editor.utils.DrawerUtils.drawFlooring
import io.stardewvalleydesigner.ui.component.editor.utils.DrawerUtils.drawVisibleEntities
import io.stardewvalleydesigner.ui.component.editor.utils.DrawerUtils.drawWallpaper
import kotlin.math.roundToInt


@Composable
fun BoxScope.LayoutPreview(
    layout: Layout,
    season: Season,
    entities: LayeredEntities,
    wallpaper: Wallpaper?,
    flooring: Flooring?,
) {
    val entityMaps = ImageResources.entities
    val wallsAndFloors = ImageResources.wallsAndFloors
    val walls2 = ImageResources.walls2
    val floors2 = ImageResources.floors2

    val (nW, nH) = layout.area
    val layoutSprite = layoutSpriteBy(layout.type, season)

    var cellSide by remember { mutableStateOf(-1f) }


    Canvas(
        modifier = Modifier
            .aspectRatio(layoutSprite.size.toRect().aspectRatio)
            .fillMaxSize()
            .align(Alignment.Center)
            .clipToBounds()
            .background(color = Color.White)
    ) {
        cellSide = size.height / nH

        val grid = CoordinateGrid(cellSide)


        // Background

        drawImage(
            image = layoutSprite.bgImage,
            srcSize = layoutSprite.size,
            dstSize = size.toIntSize(),
            filterQuality = FilterQuality.High,
        )


        // Main content

        if (layout.type.isShed()) {
            drawFlooring(wallsAndFloors, floors2, flooring, nW, nH, cellSide)
            drawWallpaper(wallsAndFloors, walls2, wallpaper, nW, cellSide)
        }

        drawVisibleEntities(
            entityMaps = entityMaps,
            season = season,
            entities = entities,
            renderSpritesFully = true,
            grid = grid
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


private fun Size.toIntSize(): IntSize = IntSize(width.roundToInt(), height.roundToInt())

private fun IntSize.toRect(): Rect = rectOf(width, height)
