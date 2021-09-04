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


object Ru : Language {

    override val appName: String = "Картограф Stardew Valley"
    override val authorName: String = "Азим Мурадов"

    override val currentLanguage: String = "Русский"

    override val menuScreen: String = "Меню"
    override val cartographerScreen: String = "Картограф"


    object MenuButtons : Language.MenuButtons {
        override val newPlan: String = "Новый План"
        override val plans: String = "Планы"

        override val resources: String = "Ресурсы"
        override val settings: String = "Настройки"
        override val about: String = "О приложении"
        override val donate: String = "Поддержать разработчика"
    }

    override val menuButtons: Language.MenuButtons = MenuButtons


    // Cartographer Screen

    object Cartographer : Language.Cartographer {

        private val menuTitles: MutableMap<MenuTitle, String> = mutableMapOf()

        private val entities: MutableMap<CartographerEntityId<*>, String> = mutableMapOf()


        override fun menuTitle(x: MenuTitle): String = menuTitles[x] ?: when (x) {
            MenuTitle.Buildings -> "Здания"
            MenuTitle.BuildingsBarns -> "Хлева"
            MenuTitle.BuildingsCoops -> "Птичники"
            MenuTitle.BuildingsSheds -> "Сараи"
            MenuTitle.BuildingsCabins -> "Домики"
            MenuTitle.BuildingsMagical -> "Магические здания"
        }.also { menuTitles[x] = it }

        override fun entity(x: CartographerEntityId<*>): String = entities[x] ?: when (x) {
            Building.SimpleBuilding.Barn1 -> "Хлев"
            Building.SimpleBuilding.Barn2 -> "Большой хлев"
            is Building.Barn3 -> "Элитный хлев"
            Building.SimpleBuilding.Coop1 -> "Птичник"
            Building.SimpleBuilding.Coop2 -> "Большой птичник"
            is Building.Coop3 -> "Элитный птичник"
            Building.SimpleBuilding.Shed1 -> "Сарай"
            is Building.Shed2 -> "Большой сарай"
            Building.SimpleBuilding.StoneCabin1 -> "Домик"
            Building.SimpleBuilding.StoneCabin2 -> "Домик (Улучшение 1)"
            is Building.StoneCabin3 -> "Домик (Улучшение 2)"
            Building.SimpleBuilding.PlankCabin1 -> "Домик"
            Building.SimpleBuilding.PlankCabin2 -> "Домик (Улучшение 1)"
            is Building.PlankCabin3 -> "Домик (Улучшение 2)"
            Building.SimpleBuilding.LogCabin1 -> "Домик"
            Building.SimpleBuilding.LogCabin2 -> "Домик (Улучшение 1)"
            is Building.LogCabin3 -> "Домик (Улучшение 2)"
            Building.SimpleBuilding.EarthObelisk -> "Обелиск земли"
            Building.SimpleBuilding.WaterObelisk -> "Обелиск воды"
            Building.SimpleBuilding.DesertObelisk -> "Обелиск пустыни"
            Building.SimpleBuilding.IslandObelisk -> "Обелиск острова"
            Building.SimpleBuilding.JunimoHut -> "Домик Джунимо"
            Building.SimpleBuilding.GoldClock -> "Золотые часы"
            Building.SimpleBuilding.Mill -> "Мельница"
            Building.SimpleBuilding.Silo -> "Силосная башня"
            Building.SimpleBuilding.Well -> "Колодец"
            is Building.Stable -> "Стойло"
            is Building.FishPond -> "Рыбный пруд"
            Building.SimpleBuilding.SlimeHutch -> "Вольер для слаймов"
            Building.SimpleBuilding.ShippingBin -> "Ящик для отправки"

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