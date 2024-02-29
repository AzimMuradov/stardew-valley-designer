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
import io.stardewvalleydesigner.engine.Flooring
import io.stardewvalleydesigner.engine.Wallpaper
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.engine.layout.LayoutType
import io.stardewvalleydesigner.metadata.*
import io.stardewvalleydesigner.ui.component.editor.utils.*
import io.stardewvalleydesigner.ui.component.themes.ThemeVariant
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.skia.Image
import stardew_valley_designer.common.ui_components.editor.generated.resources.Res


object ImageResourcesProvider {

    fun spriteBy(
        spriteMaps: Map<SpritePage, ImageBitmap>,
        entity: Entity<*>,
        qualifier: SpriteQualifier,
    ): Sprite = EntityDataProvider.entityToData(
        QualifiedEntity(entity, qualifier)
    ).let { spriteId ->
        when (spriteId) {
            is SpriteId.RegularSprite -> Sprite.Image(
                image = spriteMaps.getValue(spriteId.page),
                offset = spriteId.offset.toIntOffset(),
                size = spriteId.size.toIntSize(),
            )

            is SpriteId.ChestSprite -> Sprite.ChestImage(
                image = spriteMaps.getValue(spriteId.page),
                size = spriteId.size.toIntSize(),
                tint = spriteId.tint.toComposeColor(),
                offset = spriteId.offset.toIntOffset(),
                coverOffset = spriteId.coverOffset.toIntOffset(),
            )
        }
    }

    @Composable
    fun layoutSpriteBy(type: LayoutType): LayoutSprite = ImageResources.layouts.getValue(type)

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
    internal fun entitySpriteMaps(): Map<SpritePage, ImageBitmap> = mapOf(
        SpritePage.CommonObjects to rememberImageResource("entities/common-objects.png"),
        SpritePage.Craftables to rememberImageResource("entities/craftables.png"),
        SpritePage.Furniture to rememberImageResource("entities/furniture.png"),
        SpritePage.Flooring to rememberImageResource("entities/flooring.png"),
        SpritePage.FlooringWinter to rememberImageResource("entities/flooring-winter.png"),
        SpritePage.Fence1 to rememberImageResource("entities/fence-1.png"),
        SpritePage.Fence2 to rememberImageResource("entities/fence-2.png"),
        SpritePage.Fence3 to rememberImageResource("entities/fence-3.png"),
        SpritePage.Fence5 to rememberImageResource("entities/fence-5.png"),
        SpritePage.Crops to rememberImageResource("entities/crops.png"),

        SpritePage.Barn1 to rememberImageResource("buildings/barn.png"),
        SpritePage.Barn2 to rememberImageResource("buildings/big-barn.png"),
        SpritePage.Barn3 to rememberImageResource("buildings/deluxe-barn.png"),

        SpritePage.Coop1 to rememberImageResource("buildings/coop.png"),
        SpritePage.Coop2 to rememberImageResource("buildings/big-coop.png"),
        SpritePage.Coop3 to rememberImageResource("buildings/deluxe-coop.png"),

        SpritePage.Shed to rememberImageResource("buildings/shed.png"),
        SpritePage.BigShed to rememberImageResource("buildings/big-shed.png"),

        SpritePage.StoneCabin to rememberImageResource("buildings/stone-cabin.png"),
        SpritePage.PlankCabin to rememberImageResource("buildings/plank-cabin.png"),
        SpritePage.LogCabin to rememberImageResource("buildings/log-cabin.png"),

        SpritePage.EarthObelisk to rememberImageResource("buildings/earth-obelisk.png"),
        SpritePage.WaterObelisk to rememberImageResource("buildings/water-obelisk.png"),
        SpritePage.DesertObelisk to rememberImageResource("buildings/desert-obelisk.png"),
        SpritePage.IslandObelisk to rememberImageResource("buildings/island-obelisk.png"),
        SpritePage.JunimoHut to rememberImageResource("buildings/junimo-hut.png"),
        SpritePage.GoldClock to rememberImageResource("buildings/gold-clock.png"),

        SpritePage.Mill to rememberImageResource("buildings/mill.png"),
        SpritePage.Silo to rememberImageResource("buildings/silo.png"),
        SpritePage.Well to rememberImageResource("buildings/well.png"),
        SpritePage.Stable to rememberImageResource("buildings/stable.png"),
        SpritePage.FishPond to rememberImageResource("buildings/fish-pond.png"),
        SpritePage.SlimeHutch to rememberImageResource("buildings/slime-hutch.png"),
        SpritePage.ShippingBin to rememberImageResource("buildings/shipping-bin.png"),
    )

    @Composable
    internal fun layoutSprites(themeVariant: ThemeVariant): Map<LayoutType, LayoutSprite> = mapOf(
        LayoutType.Shed to LayoutSprite(
            fgImage = rememberImageResource("layouts/shed-fg-light.png"),
            bgImage = rememberImageResource("layouts/shed-bg-light.png"),
        ),
        LayoutType.BigShed to LayoutSprite(
            fgImage = rememberImageResource("layouts/big-shed-fg-light.png"),
            bgImage = rememberImageResource("layouts/big-shed-bg-light.png"),
        ),
        LayoutType.StandardFarm to LayoutSprite(
            fgImage = rememberImageResource("layouts/standard-farm-fg-spring.png"),
            bgImage = rememberImageResource("layouts/standard-farm-bg-spring.png"),
        ),
    )

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
            bytes = Res.readBytes(path = "files/$path")
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
