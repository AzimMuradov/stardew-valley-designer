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

package io.stardewvalleydesigner.component.editor.menus

import io.stardewvalleydesigner.cmplib.menu.Menu
import io.stardewvalleydesigner.cmplib.menu.MenuElement
import io.stardewvalleydesigner.cmplib.menu.MenuElement.Item
import io.stardewvalleydesigner.cmplib.menu.MenuElement.Submenu
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.engine.entity.EntityType


typealias EntitySelectionMenu = Menu<EntitySelectionRoot, EntitySelectionRoot, Entity<*>>
typealias EntitySelectionMenuElement = MenuElement<EntitySelectionRoot, Entity<*>>

enum class EntitySelectionRoot {

    // Buildings Menu

    Buildings,
    BuildingsBarns,
    BuildingsCoops,
    BuildingsSheds,
    BuildingsCabins,
    BuildingsMagical,


    // Common Equipment Menu

    // TODO : Add submenus
    CommonEquipment,


    // Furniture Menu

    Furniture,
    FurnitureOutdoor,
    FurnitureUniversal,
    FurnitureUniversalChairs,
    FurnitureUniversalBenches,
    FurnitureUniversalTables,
    FurnitureUniversalHousePlants,
    FurnitureUniversalFreestandingDecorativePlants,
    FurnitureUniversalTorches,
    FurnitureUniversalOtherDecorations,
    FurnitureUniversalCatalogues,
    FurnitureIndoor,
    FurnitureIndoorBookcases,
    FurnitureIndoorFireplaces,
    FurnitureIndoorLamps,
    FurnitureIndoorTVs,
    FurnitureIndoorFishTanks,
    FurnitureIndoorCouches,
    FurnitureIndoorArmchairs,
    FurnitureIndoorDressers,
    FurnitureHouse,
    FurnitureFloor,


    // Farm Elements

    FarmElements,
    FarmElementsCrops,
    FarmElementsCropsSpring,
    FarmElementsCropsSummer,
    FarmElementsCropsFall,
    FarmElementsCropsSpecial,
    FarmElementsForaging,
    FarmElementsScarecrows,
    FarmElementsSprinklers,


    // Terrain Elements Menu

    TerrainElements,
    TerrainElementsFloors,
    TerrainElementsFences,
    TerrainElementsSigns,
    TerrainElementsLighting,
}


// Filtering

fun EntitySelectionMenu.filterElements(
    disallowedTypes: Set<EntityType>,
): EntitySelectionMenu = Menu(root, elements.mapNotNull { it.filter(disallowedTypes) })

private fun EntitySelectionMenuElement.filter(
    disallowedTypes: Set<EntityType>,
): EntitySelectionMenuElement? = when (this) {
    is Submenu -> elements
        .mapNotNull { it.filter(disallowedTypes) }
        .takeIf(List<EntitySelectionMenuElement>::isNotEmpty)
        ?.let { Submenu(root, it) }

    is Item -> takeIf { it.value.type !in disallowedTypes }
}
