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

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import io.stardewvalleydesigner.engine.Flooring
import io.stardewvalleydesigner.engine.Wallpaper
import io.stardewvalleydesigner.engine.entity.Colors
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.engine.layout.LayoutType
import io.stardewvalleydesigner.metadata.*
import io.stardewvalleydesigner.ui.component.editor.utils.toComposeColor
import io.stardewvalleydesigner.ui.component.themes.ThemeVariant
import org.jetbrains.compose.resources.*


object ImageResourcesProvider {

    fun spriteBy(spriteMaps: Map<EntityPage, ImageBitmap>, entity: Entity<*>): Sprite =
        EntityDataProvider.entityToMetadata.getValue(entity).let { (id, offset, size) ->
            when (val flavor = id.flavor) {
                null -> spriteImage(id, offset, size, spriteMaps)

                is Colors.ChestColors -> {
                    when (val color = flavor.value) {
                        null -> spriteImage(id, offset, size, spriteMaps)

                        else -> Sprite.TintedImage(
                            image = spriteMaps.getValue(id.page),
                            size = size.let { (w, h) -> IntSize(w, h) },
                            tint = color.toComposeColor(),
                            offset = offset.let { (x, y) -> IntOffset(x, y) },
                            coverOffset = offset.let { (x, y) -> IntOffset(x, y + 32) }
                        )
                    }
                }

                // is Colors.FishPondColors -> TODO

                // is Colors.FlowerColors -> TODO

                // is FarmBuildingColors -> TODO

                // is Rotations.Rotations2 -> TODO

                // is Rotations.Rotations4 -> TODO

                else -> spriteImage(id, offset, size, spriteMaps)
            }
        }

    private fun spriteImage(
        id: EntityId,
        offset: EntityOffset,
        size: EntitySize,
        images: Map<EntityPage, ImageBitmap>,
    ) = Sprite.Image(
        image = images.getValue(id.page),
        offset = offset.let { (x, y) -> IntOffset(x, y) },
        size = size.let { (w, h) -> IntSize(w, h) },
    )


    @Composable
    fun layoutSpriteBy(type: LayoutType): LayoutSprites = ImageResources.layouts.getValue(type)


    fun flooringSpriteBy(image: ImageBitmap, fl: Flooring): Sprite.Image {
        val flooringObjectSpriteSize = IntSize(width = 32, height = 32)
        val index = fl.n.toInt()
        val (i, j) = (index % 8) to (index / 8)
        val (w, h) = flooringObjectSpriteSize

        return Sprite.Image(
            image = image,
            offset = IntOffset(x = i * w, y = 336 + j * h),
            size = flooringObjectSpriteSize,
        )
    }

    fun wallpaperSpriteBy(image: ImageBitmap, wp: Wallpaper): Sprite.Image {
        val wallpaperObjectSpriteSize = IntSize(width = 16, height = 48)
        val index = wp.n.toInt()
        val (i, j) = (index % 16) to (index / 16)
        val (w, h) = wallpaperObjectSpriteSize

        return Sprite.Image(
            image = image,
            offset = IntOffset(x = i * w, y = j * h),
            size = wallpaperObjectSpriteSize,
        )
    }


    @Composable
    internal fun entitySpriteMaps(): Map<EntityPage, ImageBitmap> = mapOf(
        EntityPage.CommonObjects to rememberSpriteResource("entities/common-objects.png"),
        EntityPage.Craftables to rememberSpriteResource("entities/craftables.png"),
        EntityPage.Furniture to rememberSpriteResource("entities/furniture.png"),
        EntityPage.Flooring to rememberSpriteResource("entities/flooring.png"),
        EntityPage.Crops to rememberSpriteResource("entities/crops.png"),

        EntityPage.Barn1 to rememberSpriteResource("buildings/barn.png"),
        EntityPage.Barn2 to rememberSpriteResource("buildings/big-barn.png"),
        EntityPage.Barn3 to rememberSpriteResource("buildings/deluxe-barn.png"),

        EntityPage.Coop1 to rememberSpriteResource("buildings/coop.png"),
        EntityPage.Coop2 to rememberSpriteResource("buildings/big-coop.png"),
        EntityPage.Coop3 to rememberSpriteResource("buildings/deluxe-coop.png"),

        EntityPage.Shed to rememberSpriteResource("buildings/shed.png"),
        EntityPage.BigShed to rememberSpriteResource("buildings/big-shed.png"),

        EntityPage.StoneCabin to rememberSpriteResource("buildings/stone-cabin.png"),
        EntityPage.PlankCabin to rememberSpriteResource("buildings/plank-cabin.png"),
        EntityPage.LogCabin to rememberSpriteResource("buildings/log-cabin.png"),

        EntityPage.EarthObelisk to rememberSpriteResource("buildings/earth-obelisk.png"),
        EntityPage.WaterObelisk to rememberSpriteResource("buildings/water-obelisk.png"),
        EntityPage.DesertObelisk to rememberSpriteResource("buildings/desert-obelisk.png"),
        EntityPage.IslandObelisk to rememberSpriteResource("buildings/island-obelisk.png"),
        EntityPage.JunimoHut to rememberSpriteResource("buildings/junimo-hut.png"),
        EntityPage.GoldClock to rememberSpriteResource("buildings/gold-clock.png"),

        EntityPage.Mill to rememberSpriteResource("buildings/mill.png"),
        EntityPage.Silo to rememberSpriteResource("buildings/silo.png"),
        EntityPage.Well to rememberSpriteResource("buildings/well.png"),
        EntityPage.Stable to rememberSpriteResource("buildings/stable.png"),
        EntityPage.FishPond to rememberSpriteResource("buildings/fish-pond.png"),
        EntityPage.SlimeHutch to rememberSpriteResource("buildings/slime-hutch.png"),
        EntityPage.ShippingBin to rememberSpriteResource("buildings/shipping-bin.png"),
    )

    @Composable
    internal fun wallsAndFloorsSprite(): ImageBitmap = rememberSpriteResource("layouts/walls-and-floors.png")

    @Composable
    internal fun layoutSprites(themeVariant: ThemeVariant): Map<LayoutType, LayoutSprites> = mapOf(
        LayoutType.Shed to LayoutSprites(
            fgImage = rememberSpriteResource("layouts/shed-fg-light.png"),
            bgImage = rememberSpriteResource("layouts/shed-bg-light.png"),
        ),
        LayoutType.BigShed to LayoutSprites(
            fgImage = rememberSpriteResource("layouts/big-shed-fg-light.png"),
            bgImage = rememberSpriteResource("layouts/big-shed-bg-light.png"),
        ),
        LayoutType.StandardFarm to LayoutSprites(
            fgImage = rememberSpriteResource("layouts/standard-farm-fg-spring.png"),
            bgImage = rememberSpriteResource("layouts/standard-farm-bg-spring.png"),
        ),
    )


    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun rememberSpriteResource(path: String) = resource(path).rememberImageBitmap().orEmpty()
}
