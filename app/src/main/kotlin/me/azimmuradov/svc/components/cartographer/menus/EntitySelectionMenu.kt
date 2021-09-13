/*
 * Copyright 2021-2021 Azim Muradov
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

package me.azimmuradov.svc.components.cartographer.menus

import me.azimmuradov.svc.engine.llsvc.entity.SvcEntityType
import me.azimmuradov.svc.engine.llsvc.entity.ids.SvcEntityId
import me.azimmuradov.svc.utils.menu.Menu
import me.azimmuradov.svc.utils.menu.MenuElement
import me.azimmuradov.svc.utils.menu.MenuElement.Item
import me.azimmuradov.svc.utils.menu.MenuElement.Submenu


typealias EntitySelectionMenu = Menu<EntitySelectionRoot, EntitySelectionRoot, SvcEntityId<*>>
typealias EntitySelectionMenuElement = MenuElement<EntitySelectionRoot, SvcEntityId<*>>
// typealias EntitySelectionSubmenu = Submenu<EntitySelectionMenuTitle, SvcEntityId<*>>
// typealias EntitySelectionItem = Item<EntitySelectionMenuTitle, SvcEntityId<*>>

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

    // TODO : Furniture Menu
    Furniture,


    // Farm Elements

    FarmElements,
    FarmElementsCrops,
    FarmElementsSpringCrops,
    FarmElementsSummerCrops,
    FarmElementsFallCrops,
    FarmElementsSpecialCrops,
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
    disallowedTypes: List<SvcEntityType>,
): EntitySelectionMenu = Menu(root, elements.mapNotNull { it.filter(disallowedTypes) })

private fun EntitySelectionMenuElement.filter(
    disallowedTypes: List<SvcEntityType>,
): EntitySelectionMenuElement? = when (this) {
    is Submenu -> elements
        .mapNotNull { it.filter(disallowedTypes) }
        .takeIf(List<EntitySelectionMenuElement>::isNotEmpty)
        ?.let { Submenu(root, it) }
    is Item -> takeIf { it.value.type !in disallowedTypes }
}