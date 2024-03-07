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

package io.stardewvalleydesigner.ui.component.editor.res

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import io.stardewvalleydesigner.component.editor.modules.toolkit.ShapeType
import io.stardewvalleydesigner.component.editor.modules.toolkit.ToolType
import io.stardewvalleydesigner.data.*
import io.stardewvalleydesigner.engine.Flooring
import io.stardewvalleydesigner.engine.Wallpaper
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.engine.layout.LayoutType
import io.stardewvalleydesigner.kmplib.dispatcher.PlatformDispatcher
import io.stardewvalleydesigner.ui.component.editor.utils.*
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.skia.Image
import stardew_valley_designer.common.ui_components.editor.generated.resources.Res


object ImageResourcesProvider {

    @Composable
    fun spriteBy(
        sprites: CachedMap<SpritePage, ImageBitmap>,
        entity: Entity<*>,
        qualifier: SpriteQualifier,
    ): Sprite = EntityDataProvider.entityToData(
        QualifiedEntity(entity, qualifier)
    ).let { spriteId ->
        when (spriteId) {
            is SpriteId.RegularSprite -> Sprite.Image(
                image = sprites.get(spriteId.page) { page ->
                    entitySpriteMapOf(page)
                },
                offset = spriteId.offset.toIntOffset(),
                size = spriteId.size.toIntSize(),
            )

            is SpriteId.ChestSprite -> Sprite.ChestImage(
                image = sprites.get(spriteId.page) { page ->
                    entitySpriteMapOf(page)
                },
                size = spriteId.size.toIntSize(),
                tint = spriteId.tint.toComposeColor(),
                offset = spriteId.offset.toIntOffset(),
                coverOffset = spriteId.coverOffset.toIntOffset(),
            )
        }
    }

    @Composable
    fun layoutSpriteBy(type: LayoutType, season: Season): LayoutSprite =
        ImageResources.layouts.get(type to season) { (type, season) ->
            layoutSpriteOf(type, season)
        }

    fun flooringSpriteBy(wallsAndFloors: ImageBitmap, fl: Flooring): Sprite.Image {
        val flooringObjectSpriteSize = IntSize(width = 32, height = 32)
        val index = fl.n.toInt()
        val (i, j) = (index % 8) to (index / 8)
        val (w, h) = flooringObjectSpriteSize

        return Sprite.Image(
            image = wallsAndFloors,
            offset = IntOffset(x = i * w, y = 336 + j * h),
            size = flooringObjectSpriteSize,
        )
    }

    fun wallpaperSpriteBy(wallsAndFloors: ImageBitmap, wp: Wallpaper): Sprite.Image {
        val wallpaperObjectSpriteSize = IntSize(width = 16, height = 48)
        val index = wp.n.toInt()
        val (i, j) = (index % 16) to (index / 16)
        val (w, h) = wallpaperObjectSpriteSize

        return Sprite.Image(
            image = wallsAndFloors,
            offset = IntOffset(x = i * w, y = j * h),
            size = wallpaperObjectSpriteSize,
        )
    }

    @Composable
    fun toolImageBy(type: ToolType): ImageBitmap = ImageResources.tools.getValue(type)

    @Composable
    fun shapeImageBy(type: ShapeType?): ImageBitmap = ImageResources.shapes.getValue(type)


    @Composable
    internal fun entitySpriteMaps(): CachedMap<SpritePage, ImageBitmap> =
        CachedMap { img ->
            img != emptyImageBitmap
        }

    @Composable
    private fun entitySpriteMapOf(page: SpritePage): ImageBitmap = when (page) {
        SpritePage.CommonObjects -> rememberImageResource("entities/common-objects.png")
        SpritePage.Craftables -> rememberImageResource("entities/craftables.png")
        SpritePage.Furniture -> rememberImageResource("entities/furniture.png")
        SpritePage.Flooring -> rememberImageResource("entities/flooring.png")
        SpritePage.FlooringWinter -> rememberImageResource("entities/flooring-winter.png")
        SpritePage.Fence1 -> rememberImageResource("entities/fence-1.png")
        SpritePage.Fence2 -> rememberImageResource("entities/fence-2.png")
        SpritePage.Fence3 -> rememberImageResource("entities/fence-3.png")
        SpritePage.Fence5 -> rememberImageResource("entities/fence-5.png")
        SpritePage.Crops -> rememberImageResource("entities/crops.png")

        SpritePage.Barn1 -> rememberImageResource("buildings/barn.png")
        SpritePage.Barn2 -> rememberImageResource("buildings/big-barn.png")
        SpritePage.Barn3 -> rememberImageResource("buildings/deluxe-barn.png")

        SpritePage.Coop1 -> rememberImageResource("buildings/coop.png")
        SpritePage.Coop2 -> rememberImageResource("buildings/big-coop.png")
        SpritePage.Coop3 -> rememberImageResource("buildings/deluxe-coop.png")

        SpritePage.Shed -> rememberImageResource("buildings/shed.png")
        SpritePage.BigShed -> rememberImageResource("buildings/big-shed.png")

        SpritePage.StoneCabin -> rememberImageResource("buildings/stone-cabin.png")
        SpritePage.PlankCabin -> rememberImageResource("buildings/plank-cabin.png")
        SpritePage.LogCabin -> rememberImageResource("buildings/log-cabin.png")

        SpritePage.EarthObelisk -> rememberImageResource("buildings/earth-obelisk.png")
        SpritePage.WaterObelisk -> rememberImageResource("buildings/water-obelisk.png")
        SpritePage.DesertObelisk -> rememberImageResource("buildings/desert-obelisk.png")
        SpritePage.IslandObelisk -> rememberImageResource("buildings/island-obelisk.png")
        SpritePage.JunimoHut -> rememberImageResource("buildings/junimo-hut.png")
        SpritePage.GoldClock -> rememberImageResource("buildings/gold-clock.png")

        SpritePage.Mill -> rememberImageResource("buildings/mill.png")
        SpritePage.Silo -> rememberImageResource("buildings/silo.png")
        SpritePage.Well -> rememberImageResource("buildings/well.png")
        SpritePage.Stable -> rememberImageResource("buildings/stable.png")
        SpritePage.FishPond -> rememberImageResource("buildings/fish-pond.png")
        SpritePage.SlimeHutch -> rememberImageResource("buildings/slime-hutch.png")
        SpritePage.ShippingBin -> rememberImageResource("buildings/shipping-bin.png")
    }


    @Composable
    internal fun layoutSprites(): CachedMap<Pair<LayoutType, Season>, LayoutSprite> =
        CachedMap { (fg, bg) ->
            fg != emptyImageBitmap && bg != emptyImageBitmap
        }

    @Composable
    internal fun layoutSpriteOf(type: LayoutType, season: Season): LayoutSprite = when (type) {
        LayoutType.StandardFarm -> farmLayoutOf(season, "standard")
        LayoutType.RiverlandFarm -> farmLayoutOf(season, "riverland")
        LayoutType.ForestFarm -> farmLayoutOf(season, "forest")
        LayoutType.HillTopFarm -> farmLayoutOf(season, "hill-top")
        LayoutType.WildernessFarm -> farmLayoutOf(season, "wilderness")
        LayoutType.FourCornersFarm -> farmLayoutOf(season, "four-corners")

        LayoutType.Shed -> LayoutSprite(
            fgImage = rememberImageResource("layouts/shed-fg-light.png"),
            bgImage = rememberImageResource("layouts/shed-bg-light.png"),
        )

        LayoutType.BigShed -> LayoutSprite(
            fgImage = rememberImageResource("layouts/big-shed-fg-light.png"),
            bgImage = rememberImageResource("layouts/big-shed-bg-light.png"),
        )
    }

    @Composable
    private fun farmLayoutOf(season: Season, name: String) = when (season) {
        Season.Spring -> LayoutSprite(
            fgImage = rememberImageResource("layouts/farm-$name-spring-fg.png"),
            bgImage = rememberImageResource("layouts/farm-$name-spring-bg.png"),
        )

        Season.Summer -> LayoutSprite(
            fgImage = rememberImageResource("layouts/farm-$name-summer-fg.png"),
            bgImage = rememberImageResource("layouts/farm-$name-summer-bg.png"),
        )

        Season.Fall -> LayoutSprite(
            fgImage = rememberImageResource("layouts/farm-$name-fall-fg.png"),
            bgImage = rememberImageResource("layouts/farm-$name-fall-bg.png"),
        )

        Season.Winter -> LayoutSprite(
            fgImage = rememberImageResource("layouts/farm-$name-winter-fg.png"),
            bgImage = rememberImageResource("layouts/farm-$name-winter-bg.png"),
        )
    }


    @Composable
    internal fun wallsAndFloorsSprite(): ImageBitmap = rememberImageResource("layouts/walls-and-floors.png")

    @Composable
    internal fun toolImages(): Map<ToolType, ImageBitmap> = mapOf(
        ToolType.Hand to rememberImageResource("tools/hand.png"),
        ToolType.Pen to rememberImageResource("tools/pen.png"),
        ToolType.Eraser to rememberImageResource("tools/eraser.png"),
        ToolType.Select to rememberImageResource("tools/select.png"),
        ToolType.EyeDropper to rememberImageResource("tools/eye-dropper.png"),
    )

    @Composable
    internal fun shapeImages(): Map<ShapeType?, ImageBitmap> = mapOf(
        null to rememberImageResource("shapes/point.png"),
        ShapeType.Rect to rememberImageResource("shapes/rect.png"),
        ShapeType.RectOutline to rememberImageResource("shapes/rect-outline.png"),
        ShapeType.Ellipse to rememberImageResource("shapes/ellipse.png"),
        ShapeType.EllipseOutline to rememberImageResource("shapes/ellipse-outline.png"),
        ShapeType.Diamond to rememberImageResource("shapes/diamond.png"),
        ShapeType.DiamondOutline to rememberImageResource("shapes/diamond-outline.png"),
        ShapeType.Line to rememberImageResource("shapes/line.png"),
    )


    @OptIn(ExperimentalResourceApi::class)
    @Composable
    internal fun rememberImageResource(path: String): ImageBitmap {
        var bytes: ByteArray? by remember { mutableStateOf(null) }
        LaunchedEffect(path) {
            bytes = withContext(PlatformDispatcher.IO) { Res.readBytes(path = "files/$path") }
        }
        val image by remember(bytes) {
            mutableStateOf(
                bytes
                    ?.let(Image.Companion::makeFromEncoded)
                    ?.toComposeImageBitmap() ?: emptyImageBitmap
            )
        }

        return image
    }

    private val emptyImageBitmap: ImageBitmap by lazy { ImageBitmap(1, 1) }
}
