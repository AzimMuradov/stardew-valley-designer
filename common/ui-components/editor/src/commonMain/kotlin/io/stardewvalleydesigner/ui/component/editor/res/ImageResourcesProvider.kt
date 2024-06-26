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
import io.stardewvalleydesigner.ui.component.themes.ThemeVariant
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
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
    fun layoutSpriteBy(type: LayoutType, season: Season): LayoutSprite {
        val theme = ThemeVariant.LIGHT
        var sprite by remember(theme, type, season) {
            mutableStateOf(
                layoutSpritesCache[theme.ordinal][type.ordinal][season.ordinal] ?: emptyLayoutSprite
            )
        }
        LaunchedEffect(theme, type, season) {
            sprite = layoutSpriteOf(theme, type, season)
        }
        return sprite
    }

    fun wallpaperSpriteBy(wallsAndFloors: ImageBitmap, walls2: ImageBitmap, wp: Wallpaper): Sprite.Image {
        val wallpaperObjectSpriteSize = IntSize(width = 16, height = 48)
        val index = wp.n.toInt()
        val (i, j) = (index % 112).let { (it % 16) to (it / 16) }
        val (w, h) = wallpaperObjectSpriteSize

        return Sprite.Image(
            image = if (index < 112) wallsAndFloors else walls2,
            offset = IntOffset(x = i * w, y = j * h),
            size = wallpaperObjectSpriteSize,
        )
    }

    fun flooringSpriteBy(wallsAndFloors: ImageBitmap, floors2: ImageBitmap, fl: Flooring): Sprite.Image {
        val flooringObjectSpriteSize = IntSize(width = 32, height = 32)
        val index = fl.n.toInt()
        val (i, j) = (index % 88).let { (it % 8) to (it / 8) }
        val (w, h) = flooringObjectSpriteSize

        return Sprite.Image(
            image = if (index < 88) wallsAndFloors else floors2,
            offset = if (index < 88) {
                IntOffset(x = i * w, y = 336 + j * h)
            } else {
                IntOffset(x = i * w, y = j * h)
            },
            size = flooringObjectSpriteSize,
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


    private suspend fun layoutSpriteOf(
        theme: ThemeVariant,
        type: LayoutType,
        season: Season,
    ): LayoutSprite = when (type) {
        LayoutType.StandardFarm -> farmLayoutOf(type, name = "standard", season)
        LayoutType.RiverlandFarm -> farmLayoutOf(type, name = "riverland", season)
        LayoutType.ForestFarm -> farmLayoutOf(type, name = "forest", season)
        LayoutType.HillTopFarm -> farmLayoutOf(type, name = "hill-top", season)
        LayoutType.WildernessFarm -> farmLayoutOf(type, name = "wilderness", season)
        LayoutType.FourCornersFarm -> farmLayoutOf(type, name = "four-corners", season)
        LayoutType.Shed -> shedLayoutOf(theme, type, name = "shed")
        LayoutType.BigShed -> shedLayoutOf(theme, type, name = "big-shed")
    }

    private val layoutSpritesCache: List<List<MutableList<LayoutSprite?>>> = ThemeVariant.entries.map {
        LayoutType.entries.map {
            Season.entries.mapTo(mutableListOf()) {
                null
            }
        }
    }


    private suspend fun farmLayoutOf(
        type: LayoutType,
        name: String,
        season: Season,
    ) = loadLayoutSpriteOf(
        theme = null, type, season,
        fgPath = "layouts/farm-$name-${season.name.lowercase()}-fg.png",
        bgPath = "layouts/farm-$name-${season.name.lowercase()}-bg.png",
    )

    private suspend fun shedLayoutOf(
        theme: ThemeVariant,
        type: LayoutType,
        name: String,
    ) = loadLayoutSpriteOf(
        theme, type, season = null,
        fgPath = "layouts/$name-fg-${theme.name.lowercase()}.png",
        bgPath = "layouts/$name-bg-${theme.name.lowercase()}.png",
    )

    @Composable
    internal fun wallsAndFloorsSprite(): ImageBitmap = rememberImageResource("layouts/walls-and-floors.png")

    @Composable
    internal fun walls2Sprite(): ImageBitmap = rememberImageResource("layouts/walls-2.png")

    @Composable
    internal fun floors2Sprite(): ImageBitmap = rememberImageResource("layouts/floors-2.png")

    @Composable
    internal fun toolImages(): Map<ToolType, ImageBitmap> = mapOf(
        ToolType.Drag to rememberImageResource("tools/drag.png"),
        ToolType.Pen to rememberImageResource("tools/pen.png"),
        ToolType.Eraser to rememberImageResource("tools/eraser.png"),
        ToolType.Select to rememberImageResource("tools/select.png"),
        ToolType.EyeDropper to rememberImageResource("tools/eye-dropper.png"),
        ToolType.Hand to rememberImageResource("tools/hand.png"),
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

    private val layoutSpritesCacheMutex = Mutex()

    @OptIn(ExperimentalResourceApi::class)
    private suspend fun loadLayoutSpriteOf(
        theme: ThemeVariant?, // `null` means theme is irrelevant
        type: LayoutType,
        season: Season?, // `null` means season is irrelevant
        fgPath: String, bgPath: String,
    ): LayoutSprite {
        val themeIndex = theme?.ordinal ?: 0
        val typeIndex = type.ordinal
        val seasonIndex = season?.ordinal ?: 0

        return coroutineScope {
            layoutSpritesCache[themeIndex][typeIndex][seasonIndex] ?: layoutSpritesCacheMutex.withLock {
                layoutSpritesCache[themeIndex][typeIndex][seasonIndex] ?: run {
                    val fg = async {
                        withContext(PlatformDispatcher.IO) {
                            Image.makeFromEncoded(Res.readBytes(path = "files/$fgPath")).toComposeImageBitmap()
                        }
                    }
                    val bg = async {
                        withContext(PlatformDispatcher.IO) {
                            Image.makeFromEncoded(Res.readBytes(path = "files/$bgPath")).toComposeImageBitmap()
                        }
                    }
                    LayoutSprite(fg.await(), bg.await()).also { sprite ->
                        for (tI in theme?.ordinal?.let(::listOf) ?: ThemeVariant.entries.indices) {
                            for (sI in season?.ordinal?.let(::listOf) ?: Season.entries.indices) {
                                layoutSpritesCache[tI][typeIndex][sI] = sprite
                            }
                        }
                    }
                }
            }
        }
    }

    private val emptyImageBitmap: ImageBitmap by lazy { ImageBitmap(1, 1) }

    private val emptyLayoutSprite: LayoutSprite by lazy {
        LayoutSprite(emptyImageBitmap, emptyImageBitmap)
    }
}
