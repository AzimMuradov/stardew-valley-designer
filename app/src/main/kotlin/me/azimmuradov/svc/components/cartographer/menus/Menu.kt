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

import me.azimmuradov.svc.components.cartographer.menus.MenuElement.Menu
import me.azimmuradov.svc.components.cartographer.menus.MenuElement.Value
import me.azimmuradov.svc.engine.impl.entity.CartographerEntityType
import me.azimmuradov.svc.engine.impl.entity.ids.Building.*
import me.azimmuradov.svc.engine.impl.entity.ids.Building.SimpleBuilding.*
import me.azimmuradov.svc.engine.impl.entity.ids.CartographerEntityId
import me.azimmuradov.svc.engine.impl.entity.ids.Equipment.SimpleEquipment.*


data class MenuRoot(val title: MenuTitle, val elements: List<MenuElement>)

sealed interface MenuElement {

    data class Menu(val title: MenuTitle, val elements: List<MenuElement>) : MenuElement

    data class Value(val id: CartographerEntityId<*>) : MenuElement
}


enum class MenuTitle {

    // Buildings Menu

    Buildings,
    BuildingsBarns,
    BuildingsCoops,
    BuildingsSheds,
    BuildingsCabins,
    BuildingsMagical,
}


// Filtering

fun MenuRoot.filterElements(disallowedTypes: List<CartographerEntityType>): MenuRoot =
    MenuRoot(title, elements.mapNotNull { it.filter(disallowedTypes) })

private fun MenuElement.filter(disallowedTypes: List<CartographerEntityType>): MenuElement? = when (this) {
    is Menu -> elements
        .mapNotNull { it.filter(disallowedTypes) }
        .takeIf(List<MenuElement>::isNotEmpty)
        ?.let { Menu(title, it) }
    is Value -> takeIf { it.id.type !in disallowedTypes }
}