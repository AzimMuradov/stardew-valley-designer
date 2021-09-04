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

package me.azimmuradov.svc.settings.languages

import me.azimmuradov.svc.components.cartographer.menus.MenuTitle
import me.azimmuradov.svc.engine.impl.entity.ids.*


object En : Language {

    override val appName: String = "Stardew Valley Cartographer"
    override val authorName: String = "Azim Muradov"

    override val currentLanguage: String = "English"

    override val menuScreen: String = "Menu"
    override val cartographerScreen: String = "Cartographer"


    object MenuButtons : Language.MenuButtons {
        override val newPlan: String = "New Plan"
        override val plans: String = "Plans"

        override val resources: String = "Resources"
        override val settings: String = "Settings"
        override val about: String = "About"
        override val donate: String = "Donate"
    }

    override val menuButtons: Language.MenuButtons = MenuButtons


    // Cartographer Screen

    object Cartographer : Language.Cartographer {

        private val menuTitles: MutableMap<MenuTitle, String> = mutableMapOf()

        private val entities: MutableMap<CartographerEntityId<*>, String> = mutableMapOf()


        override fun menuTitle(x: MenuTitle): String = menuTitles[x] ?: when (x) {
            MenuTitle.Buildings -> "Buildings"
            MenuTitle.BuildingsBarns -> "Barns"
            MenuTitle.BuildingsCoops -> "Coops"
            MenuTitle.BuildingsSheds -> "Sheds"
            MenuTitle.BuildingsCabins -> "Cabins"
            MenuTitle.BuildingsMagical -> "Magical buildings"
        }.also { menuTitles[x] = it }

        override fun entity(x: CartographerEntityId<*>): String = entities[x] ?: when (x) {
            Building.SimpleBuilding.Barn1 -> "Barn"
            Building.SimpleBuilding.Barn2 -> "Big Barn"
            is Building.Barn3 -> "Deluxe Barn"
            Building.SimpleBuilding.Coop1 -> "Coop"
            Building.SimpleBuilding.Coop2 -> "Big Coop"
            is Building.Coop3 -> "Deluxe Coop"
            Building.SimpleBuilding.Shed1 -> "Shed"
            is Building.Shed2 -> "Big Shed"
            Building.SimpleBuilding.StoneCabin1 -> "Stone Cabin"
            Building.SimpleBuilding.StoneCabin2 -> "Stone Cabin (Upgrade 1)"
            is Building.StoneCabin3 -> "Stone Cabin (Upgrade 2)"
            Building.SimpleBuilding.PlankCabin1 -> "Plank Cabin"
            Building.SimpleBuilding.PlankCabin2 -> "Plank Cabin (Upgrade 1)"
            is Building.PlankCabin3 -> "Plank Cabin (Upgrade 2)"
            Building.SimpleBuilding.LogCabin1 -> "Log Cabin"
            Building.SimpleBuilding.LogCabin2 -> "Log Cabin (Upgrade 1)"
            is Building.LogCabin3 -> "Log Cabin (Upgrade 2)"
            Building.SimpleBuilding.EarthObelisk -> "Earth Obelisk"
            Building.SimpleBuilding.WaterObelisk -> "Water Obelisk"
            Building.SimpleBuilding.DesertObelisk -> "Desert Obelisk"
            Building.SimpleBuilding.IslandObelisk -> "Island Obelisk"
            Building.SimpleBuilding.JunimoHut -> "Junimo Hut"
            Building.SimpleBuilding.GoldClock -> "Gold Clock"
            Building.SimpleBuilding.Mill -> "Mill"
            Building.SimpleBuilding.Silo -> "Silo"
            Building.SimpleBuilding.Well -> "Well"
            is Building.Stable -> "Stable"
            is Building.FishPond -> "Fish Pond"
            Building.SimpleBuilding.SlimeHutch -> "Slime Hutch"
            Building.SimpleBuilding.ShippingBin -> "Shipping Bin"

            is Equipment -> TODO()
            is Floor -> TODO()
            is FloorFurniture -> TODO()
            is HouseFurniture -> TODO()
            is IndoorFurniture -> TODO()
            is UniversalFurniture -> TODO()
        }.also { entities[x] = it }
    }

    override val cartographer: Language.Cartographer = Cartographer
}