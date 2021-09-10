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

import me.azimmuradov.svc.components.cartographer.menus.entityselection.EntitySelectionMenuTitle
import me.azimmuradov.svc.engine.llsvc.entity.ids.*


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

        private val entitySelectionMenuTitles: MutableMap<EntitySelectionMenuTitle, String> = mutableMapOf()

        private val entities: MutableMap<SvcEntityId<*>, String> = mutableMapOf()


        override fun menuTitle(x: EntitySelectionMenuTitle): String = entitySelectionMenuTitles[x] ?: when (x) {

            // Buildings Menu

            EntitySelectionMenuTitle.Buildings -> "Здания"
            EntitySelectionMenuTitle.BuildingsBarns -> "Хлева"
            EntitySelectionMenuTitle.BuildingsCoops -> "Птичники"
            EntitySelectionMenuTitle.BuildingsSheds -> "Сараи"
            EntitySelectionMenuTitle.BuildingsCabins -> "Домики"
            EntitySelectionMenuTitle.BuildingsMagical -> "Магические здания"


            // Common Equipment Menu

            EntitySelectionMenuTitle.CommonEquipment -> "Общее оборудование"


            // Furniture Menu

            EntitySelectionMenuTitle.Furniture -> "Мебель"


            // Farm Elements

            EntitySelectionMenuTitle.FarmElements -> "Элементы фермы"
            EntitySelectionMenuTitle.FarmElementsCrops -> "Культуры"
            EntitySelectionMenuTitle.FarmElementsSpringCrops -> "Весенние культуры"
            EntitySelectionMenuTitle.FarmElementsSummerCrops -> "Летние культуры"
            EntitySelectionMenuTitle.FarmElementsFallCrops -> "Осенние культуры"
            EntitySelectionMenuTitle.FarmElementsSpecialCrops -> "Особые культуры"
            EntitySelectionMenuTitle.FarmElementsForaging -> "Собирательство"
            EntitySelectionMenuTitle.FarmElementsScarecrows -> "Пугала"
            EntitySelectionMenuTitle.FarmElementsSprinklers -> "Спринклеры"


            // Terrain Menu

            EntitySelectionMenuTitle.TerrainElements -> "Элементы ландшафта"
            EntitySelectionMenuTitle.TerrainElementsFloors -> "Покрытие"
            EntitySelectionMenuTitle.TerrainElementsFences -> "Заборы"
            EntitySelectionMenuTitle.TerrainElementsSigns -> "Знаки"
            EntitySelectionMenuTitle.TerrainElementsLighting -> "Освещение"
        }.also { entitySelectionMenuTitles[x] = it }

        override fun entity(x: SvcEntityId<*>): String = entities[x] ?: when (x) {

            // Floor

            Floor.WoodFloor -> TODO()
            Floor.RusticPlankFloor -> TODO()
            Floor.StrawFloor -> TODO()
            Floor.WeatheredFloor -> TODO()
            Floor.CrystalFloor -> TODO()
            Floor.StoneFloor -> TODO()
            Floor.StoneWalkwayFloor -> TODO()
            Floor.BrickFloor -> TODO()

            Floor.WoodPath -> TODO()
            Floor.GravelPath -> TODO()
            Floor.CobblestonePath -> TODO()
            Floor.SteppingStonePath -> TODO()
            Floor.CrystalPath -> TODO()

            Floor.Grass -> TODO()


            // Floor Furniture

            is FloorFurniture -> TODO()


            // Equipment

            // Common Equipment
            // (Artisan Equipment + Other Tools + Refining Equipment + Misc + Storage Equipment)

            Equipment.SimpleEquipment.MayonnaiseMachine -> TODO()
            Equipment.SimpleEquipment.BeeHouse -> TODO()
            Equipment.SimpleEquipment.PreservesJar -> TODO()
            Equipment.SimpleEquipment.CheesePress -> TODO()
            Equipment.SimpleEquipment.Loom -> TODO()
            Equipment.SimpleEquipment.Keg -> TODO()
            Equipment.SimpleEquipment.OilMaker -> TODO()
            Equipment.SimpleEquipment.Cask -> TODO()

            Equipment.SimpleEquipment.GardenPot -> TODO()
            Equipment.SimpleEquipment.Heater -> TODO()
            Equipment.SimpleEquipment.AutoGrabber -> TODO()
            Equipment.SimpleEquipment.AutoPetter -> TODO()

            Equipment.SimpleEquipment.CharcoalKiln -> TODO()
            Equipment.SimpleEquipment.Crystalarium -> TODO()
            Equipment.SimpleEquipment.Furnace -> TODO()
            Equipment.SimpleEquipment.LightningRod -> TODO()
            Equipment.SimpleEquipment.SolarPanel -> TODO()
            Equipment.SimpleEquipment.RecyclingMachine -> TODO()
            Equipment.SimpleEquipment.SeedMaker -> TODO()
            Equipment.SimpleEquipment.SlimeIncubator -> TODO()
            Equipment.SimpleEquipment.OstrichIncubator -> TODO()
            Equipment.SimpleEquipment.SlimeEggPress -> TODO()
            Equipment.SimpleEquipment.WormBin -> TODO()
            Equipment.SimpleEquipment.BoneMill -> TODO()
            Equipment.SimpleEquipment.GeodeCrusher -> TODO()
            Equipment.SimpleEquipment.WoodChipper -> TODO()

            Equipment.SimpleEquipment.MiniJukebox -> TODO()
            Equipment.SimpleEquipment.MiniObelisk -> TODO()
            Equipment.SimpleEquipment.FarmComputer -> TODO()
            Equipment.SimpleEquipment.Hopper -> TODO()
            Equipment.SimpleEquipment.Deconstructor -> TODO()
            Equipment.SimpleEquipment.CoffeeMaker -> TODO()
            Equipment.SimpleEquipment.Telephone -> TODO()
            Equipment.SimpleEquipment.SewingMachine -> TODO()
            Equipment.SimpleEquipment.Workbench -> TODO()
            Equipment.SimpleEquipment.MiniShippingBin -> TODO()

            // TODO (?) : CrabPot,
            // TODO (?) : Tapper,
            // TODO (?) : HeavyTapper,

            is Equipment.Chest -> TODO()
            is Equipment.StoneChest -> TODO()
            Equipment.SimpleEquipment.JunimoChest -> TODO()

            // Farm Elements (Scarecrows + Sprinklers)

            Equipment.SimpleEquipment.Scarecrow -> TODO()
            Equipment.SimpleEquipment.DeluxeScarecrow -> TODO()
            Equipment.SimpleEquipment.Rarecrow1 -> TODO()
            Equipment.SimpleEquipment.Rarecrow2 -> TODO()
            Equipment.SimpleEquipment.Rarecrow3 -> TODO()
            Equipment.SimpleEquipment.Rarecrow4 -> TODO()
            Equipment.SimpleEquipment.Rarecrow5 -> TODO()
            Equipment.SimpleEquipment.Rarecrow6 -> TODO()
            Equipment.SimpleEquipment.Rarecrow7 -> TODO()
            Equipment.SimpleEquipment.Rarecrow8 -> TODO()

            Equipment.SimpleEquipment.Sprinkler -> TODO()
            Equipment.SimpleEquipment.QualitySprinkler -> TODO()
            Equipment.SimpleEquipment.IridiumSprinkler -> TODO()
            Equipment.SimpleEquipment.IridiumSprinklerWithPressureNozzle -> TODO()

            // Terrain Elements (Fences + Signs + Lighting)

            Equipment.SimpleEquipment.Gate -> TODO()
            Equipment.SimpleEquipment.WoodFence -> TODO()
            Equipment.SimpleEquipment.StoneFence -> TODO()
            Equipment.SimpleEquipment.IronFence -> TODO()
            Equipment.SimpleEquipment.HardwoodFence -> TODO()

            Equipment.SimpleEquipment.WoodSign -> TODO()
            Equipment.SimpleEquipment.StoneSign -> TODO()
            Equipment.SimpleEquipment.DarkSign -> TODO()

            Equipment.SimpleEquipment.Torch -> TODO()
            Equipment.SimpleEquipment.Campfire -> TODO()
            Equipment.SimpleEquipment.WoodenBrazier -> TODO()
            Equipment.SimpleEquipment.StoneBrazier -> TODO()
            Equipment.SimpleEquipment.GoldBrazier -> TODO()
            Equipment.SimpleEquipment.CarvedBrazier -> TODO()
            Equipment.SimpleEquipment.StumpBrazier -> TODO()
            Equipment.SimpleEquipment.BarrelBrazier -> TODO()
            Equipment.SimpleEquipment.SkullBrazier -> TODO()
            Equipment.SimpleEquipment.MarbleBrazier -> TODO()
            Equipment.SimpleEquipment.WoodLampPost -> TODO()
            Equipment.SimpleEquipment.IronLampPost -> TODO()
            Equipment.SimpleEquipment.JackOLantern -> TODO()

            // Furniture

            Equipment.SimpleEquipment.BasicLog -> TODO()
            Equipment.SimpleEquipment.LogSection -> TODO()
            Equipment.SimpleEquipment.OrnamentalHayBale -> TODO()
            Equipment.SimpleEquipment.SignOfTheVessel -> TODO()
            Equipment.SimpleEquipment.WickedStatue -> TODO()
            Equipment.SimpleEquipment.BigGreenCane -> TODO()
            Equipment.SimpleEquipment.BigRedCane -> TODO()
            Equipment.SimpleEquipment.GreenCanes -> TODO()
            Equipment.SimpleEquipment.RedCanes -> TODO()
            Equipment.SimpleEquipment.MixedCane -> TODO()
            Equipment.SimpleEquipment.LawnFlamingo -> TODO()
            Equipment.SimpleEquipment.PlushBunny -> TODO()
            Equipment.SimpleEquipment.SeasonalDecor -> TODO()
            Equipment.SimpleEquipment.TubOFlowers -> TODO()
            Equipment.SimpleEquipment.SeasonalPlant1 -> TODO()
            Equipment.SimpleEquipment.SeasonalPlant2 -> TODO()
            Equipment.SimpleEquipment.SeasonalPlant3 -> TODO()
            Equipment.SimpleEquipment.SeasonalPlant4 -> TODO()
            Equipment.SimpleEquipment.SeasonalPlant5 -> TODO()
            Equipment.SimpleEquipment.SeasonalPlant6 -> TODO()
            Equipment.SimpleEquipment.DrumBlock -> TODO()
            Equipment.SimpleEquipment.FluteBlock -> TODO()
            Equipment.SimpleEquipment.GraveStone -> TODO()
            Equipment.SimpleEquipment.StoneCairn -> TODO()
            Equipment.SimpleEquipment.StoneFrog -> TODO()
            Equipment.SimpleEquipment.StoneJunimo -> TODO()
            Equipment.SimpleEquipment.StoneOwl -> TODO()
            Equipment.SimpleEquipment.StoneParrot -> TODO()
            Equipment.SimpleEquipment.SuitOfArmor -> TODO()
            Equipment.SimpleEquipment.Foroguemon -> TODO()
            Equipment.SimpleEquipment.HMTGF -> TODO()
            Equipment.SimpleEquipment.PinkyLemon -> TODO()
            Equipment.SimpleEquipment.SolidGoldLewis -> TODO()
            Equipment.SimpleEquipment.StatueOfEndlessFortune -> TODO()
            Equipment.SimpleEquipment.StatueOfPerfection -> TODO()
            Equipment.SimpleEquipment.StatueOfTruePerfection -> TODO()
            Equipment.SimpleEquipment.SodaMachine -> TODO()
            Equipment.SimpleEquipment.StardewHeroTrophy -> TODO()
            Equipment.SimpleEquipment.JunimoKartArcadeSystem -> TODO()
            Equipment.SimpleEquipment.PrairieKingArcadeSystem -> TODO()
            Equipment.SimpleEquipment.SingingStone -> TODO()
            Equipment.SimpleEquipment.SecretStoneOwl -> TODO()
            Equipment.SimpleEquipment.SecretStrangeCapsule -> TODO()
            Equipment.SimpleEquipment.SecretEmptyCapsule -> TODO()


            // House Furniture

            is HouseFurniture -> TODO()


            // Indoor Furniture

            is IndoorFurniture -> TODO()


            // Universal Furniture

            is UniversalFurniture -> TODO()


            // Buildings

            // Barns

            Building.SimpleBuilding.Barn1 -> "Хлев"
            Building.SimpleBuilding.Barn2 -> "Большой хлев"
            is Building.Barn3 -> "Элитный хлев"

            // Coops

            Building.SimpleBuilding.Coop1 -> "Птичник"
            Building.SimpleBuilding.Coop2 -> "Большой птичник"
            is Building.Coop3 -> "Элитный птичник"

            // Sheds

            Building.SimpleBuilding.Shed1 -> "Сарай"
            is Building.Shed2 -> "Большой сарай"

            // Cabins

            Building.SimpleBuilding.StoneCabin1 -> "Домик"
            Building.SimpleBuilding.StoneCabin2 -> "Домик (Улучшение 1)"
            is Building.StoneCabin3 -> "Домик (Улучшение 2)"

            Building.SimpleBuilding.PlankCabin1 -> "Домик"
            Building.SimpleBuilding.PlankCabin2 -> "Домик (Улучшение 1)"
            is Building.PlankCabin3 -> "Домик (Улучшение 2)"

            Building.SimpleBuilding.LogCabin1 -> "Домик"
            Building.SimpleBuilding.LogCabin2 -> "Домик (Улучшение 1)"
            is Building.LogCabin3 -> "Домик (Улучшение 2)"

            // Magical Buildings

            Building.SimpleBuilding.EarthObelisk -> "Обелиск земли"
            Building.SimpleBuilding.WaterObelisk -> "Обелиск воды"
            Building.SimpleBuilding.DesertObelisk -> "Обелиск пустыни"
            Building.SimpleBuilding.IslandObelisk -> "Обелиск острова"
            Building.SimpleBuilding.JunimoHut -> "Домик Джунимо"
            Building.SimpleBuilding.GoldClock -> "Золотые часы"

            // Others

            Building.SimpleBuilding.Mill -> "Мельница"
            Building.SimpleBuilding.Silo -> "Силосная башня"
            Building.SimpleBuilding.Well -> "Колодец"
            is Building.Stable -> "Стойло"
            is Building.FishPond -> "Рыбный пруд"
            Building.SimpleBuilding.SlimeHutch -> "Вольер для слаймов"
            Building.SimpleBuilding.ShippingBin -> "Ящик для отправки"
        }.also { entities[x] = it }
    }

    override val cartographer: Language.Cartographer = Cartographer
}