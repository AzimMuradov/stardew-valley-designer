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

package io.stardewvalleydesigner.ui.component.editor.screen.sidemenus

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.material.ripple.*
import androidx.compose.material.ripple.RippleTheme.Companion.defaultRippleColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.data.Season
import io.stardewvalleydesigner.engine.Flooring
import io.stardewvalleydesigner.engine.Wallpaper
import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.layout.LayoutType
import io.stardewvalleydesigner.ui.component.editor.res.*
import io.stardewvalleydesigner.ui.component.editor.res.ImageResourcesProvider.flooringSpriteBy
import io.stardewvalleydesigner.ui.component.editor.res.ImageResourcesProvider.wallpaperSpriteBy
import io.stardewvalleydesigner.ui.component.editor.utils.*
import io.stardewvalleydesigner.ui.component.editor.utils.DrawerUtils.drawSpriteStretched


@Composable
internal fun ColumnScope.AnySelection(
    layoutType: LayoutType,
    season: Season,
    disallowedTypes: Set<EntityType>,
    onEntitySelection: (Entity<*>) -> Unit,
    onWallpaperSelection: (Wallpaper) -> Unit,
    onFlooringSelection: (Flooring) -> Unit,
) {
    val spriteMaps = ImageResources.entities
    val wallsAndFloors = ImageResources.wallsAndFloors
    val walls2 = ImageResources.walls2
    val floors2 = ImageResources.floors2

    val tabsData = remember(disallowedTypes) {
        mapOf(
            Tab.Building to TabData(
                rowSize = 16u,
                icon = SpriteUtils.calculateSprite(spriteMaps, Building.Shed2(), season),
            ),
            Tab.Crop to TabData(
                rowSize = 8u,
                icon = SpriteUtils.calculateSprite(spriteMaps, Crop.SimpleCrop.Parsnip, season),
            ),
            Tab.Equipment to TabData(
                rowSize = 8u,
                icon = SpriteUtils.calculateSprite(spriteMaps, Equipment.SimpleEquipment.FarmComputer, season),
            ),
            Tab.Floor to TabData(
                rowSize = 6u,
                icon = SpriteUtils.calculateSprite(spriteMaps, Floor.CobblestonePath, season),
            ),
            Tab.Furniture to TabData(
                rowSize = 8u,
                icon = SpriteUtils.calculateSprite(
                    spriteMaps,
                    UniversalFurniture.SimpleUniversalFurniture.JunimoPlush,
                    season,
                ),
            ),
            Tab.Wallpaper to TabData(
                rowSize = 8u,
                icon = wallpaperSpriteBy(wallsAndFloors, walls2, Wallpaper(n = 0u)),
            ),
            Tab.Flooring to TabData(
                rowSize = 8u,
                icon = flooringSpriteBy(wallsAndFloors, floors2, Flooring(n = 0u)),
            ),
        )
    }

    val esTabs = remember(disallowedTypes) {
        mapOf(
            Tab.Building to mapOf(EntityWithoutFloorType.BuildingType to Building.all),
            Tab.Crop to mapOf(EntityWithoutFloorType.CropType to Crop.all),
            Tab.Equipment to mapOf(ObjectType.EquipmentType to Equipment.all),
            Tab.Floor to mapOf(FloorType to Floor.all),
            Tab.Furniture to mapOf(
                FloorFurnitureType to FloorFurniture.all,
                ObjectType.FurnitureType.HouseFurnitureType to HouseFurniture.all,
                ObjectType.FurnitureType.IndoorFurnitureType to IndoorFurniture.all,
                ObjectType.FurnitureType.UniversalFurnitureType to UniversalFurniture.all,
            ),
        ).mapValues { (_, entitiesByType) ->
            entitiesByType.filterNot { (type, _) ->
                type in disallowedTypes
            }
        }.filter { (_, entitiesByType) ->
            entitiesByType.isNotEmpty()
        }
    }

    val tabs = remember(layoutType) {
        if (layoutType.isShed()) {
            esTabs.keys + listOf(Tab.Wallpaper, Tab.Flooring)
        } else {
            esTabs.keys
        }
    }

    var chosenTab by remember(tabs) { mutableStateOf(tabs.first()) }

    val esTabStates = remember(esTabs) {
        mutableStateMapOf(*esTabs.keys.map { it to LazyListState() }.toTypedArray())
    }


    TabRow(
        selectedTabIndex = tabs.indexOf(chosenTab),
        modifier = Modifier.fillMaxWidth().height(48.dp),
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.primarySurface,
    ) {
        tabs.forEach { tab ->
            val bg by animateColorAsState(
                if (chosenTab == tab) {
                    MaterialTheme.colors.primarySurface.copy(alpha = 0.15f)
                } else {
                    Color.Transparent
                },
            )
            Tab(
                selected = chosenTab == tab,
                onClick = { chosenTab = tab },
                modifier = Modifier.weight(1f).fillMaxHeight().background(bg),
            ) {
                Sprite(
                    sprite = tabsData.getValue(tab).icon,
                    modifier = Modifier.fillMaxSize().padding(3.dp),
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    CompositionLocalProvider(LocalRippleTheme provides CustomRippleTheme) {
        Box(Modifier.fillMaxSize()) {
            when (chosenTab) {
                Tab.Wallpaper -> {
                    val wallpaperState = rememberLazyGridState()

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(count = 8),
                        modifier = Modifier.fillMaxSize().padding(end = 12.dp),
                        state = wallpaperState,
                    ) {
                        items(items = Wallpaper.all()) { w ->
                            Box(
                                modifier = Modifier
                                    .pointerHoverIcon(PointerIcon.Hand)
                                    .bounceClickable(
                                        interactionSource = remember(::MutableInteractionSource),
                                        indication = rememberRipple(),
                                    ) { onWallpaperSelection(w) }
                                    .aspectRatio(ratio = 1f / 3f)
                                    .fillMaxSize()
                                    .padding(4.dp)
                                    .drawBehind {
                                        drawSpriteStretched(
                                            sprite = wallpaperSpriteBy(wallsAndFloors, walls2, w),
                                            layoutSize = size.toIntSize()
                                        )
                                    }
                            )
                        }
                    }
                    VerticalScrollbar(
                        adapter = rememberScrollbarAdapter(scrollState = wallpaperState),
                        modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
                    )
                }

                Tab.Flooring -> {
                    val flooringState = rememberLazyGridState()

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(count = 4),
                        modifier = Modifier.fillMaxSize().padding(end = 12.dp),
                        state = flooringState,
                    ) {
                        items(items = Flooring.all()) { f ->
                            Box(
                                modifier = Modifier
                                    .pointerHoverIcon(PointerIcon.Hand)
                                    .bounceClickable(
                                        interactionSource = remember(::MutableInteractionSource),
                                        indication = rememberRipple(),
                                    ) { onFlooringSelection(f) }
                                    .aspectRatio(ratio = 1f)
                                    .fillMaxSize()
                                    .padding(4.dp)
                                    .drawBehind {
                                        drawSpriteStretched(
                                            sprite = flooringSpriteBy(wallsAndFloors, floors2, f),
                                            layoutSize = size.toIntSize()
                                        )
                                    }
                            )
                        }
                    }
                    VerticalScrollbar(
                        adapter = rememberScrollbarAdapter(scrollState = flooringState),
                        modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
                    )
                }

                else -> {
                    val es by remember(esTabs, chosenTab) {
                        mutableStateOf(esTabs.getValue(chosenTab))
                    }

                    EntitySelection(
                        state = esTabStates.getValue(chosenTab),
                        rowCapacity = tabsData.getValue(chosenTab).rowSize,
                        entities = es.values.flatten(),
                        season, disallowedTypes, onEntitySelection,
                    )
                }
            }
        }
    }
}


private enum class Tab {
    Building,
    Crop,
    Equipment,
    Floor,
    Furniture,
    Wallpaper,
    Flooring,
}

private data class TabData(
    val rowSize: UInt,
    val icon: Sprite,
)

private object CustomRippleTheme : RippleTheme {

    @Composable
    override fun defaultColor(): Color = defaultRippleColor(
        contentColor = LocalContentColor.current,
        lightTheme = MaterialTheme.colors.isLight
    )

    @Composable
    override fun rippleAlpha() = RippleAlpha(
        pressedAlpha = 0.13f,
        focusedAlpha = 0.13f,
        draggedAlpha = 0.10f,
        hoveredAlpha = 0.10f,
    )
}
