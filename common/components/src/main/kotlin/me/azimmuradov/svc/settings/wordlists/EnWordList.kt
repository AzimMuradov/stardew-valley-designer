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

package me.azimmuradov.svc.settings.wordlists

import me.azimmuradov.svc.cartographer.modules.toolkit.ToolType
import me.azimmuradov.svc.components.screens.cartographer.menus.EntitySelectionRoot
import me.azimmuradov.svc.engine.entity.*
import me.azimmuradov.svc.engine.layer.LayerType


object EnWordList : WordList {

    override val application: String = "Stardew Valley Cartographer"

    override val author: String = "Azim Muradov"


    // Cartographer Screen

    override fun menuTitle(root: EntitySelectionRoot): String = menuTitles[root] ?: when (root) {

        // Buildings Menu

        EntitySelectionRoot.Buildings -> "Buildings"
        EntitySelectionRoot.BuildingsBarns -> "Barns"
        EntitySelectionRoot.BuildingsCoops -> "Coops"
        EntitySelectionRoot.BuildingsSheds -> "Sheds"
        EntitySelectionRoot.BuildingsCabins -> "Cabins"
        EntitySelectionRoot.BuildingsMagical -> "Magical buildings"


        // Common Equipment Menu

        EntitySelectionRoot.CommonEquipment -> "Common equipment"


        // Furniture Menu

        EntitySelectionRoot.Furniture -> "Furniture"


        // Farm Elements

        EntitySelectionRoot.FarmElements -> "Farm elements"
        EntitySelectionRoot.FarmElementsCrops -> "Crops"
        EntitySelectionRoot.FarmElementsSpringCrops -> "Spring crops"
        EntitySelectionRoot.FarmElementsSummerCrops -> "Summer crops"
        EntitySelectionRoot.FarmElementsFallCrops -> "Fall crops"
        EntitySelectionRoot.FarmElementsSpecialCrops -> "Special crops"
        EntitySelectionRoot.FarmElementsForaging -> "Foraging"
        EntitySelectionRoot.FarmElementsScarecrows -> "Scarecrows"
        EntitySelectionRoot.FarmElementsSprinklers -> "Sprinklers"


        // Terrain Elements Menu

        EntitySelectionRoot.TerrainElements -> "Terrain Elements"
        EntitySelectionRoot.TerrainElementsFloors -> "Floors"
        EntitySelectionRoot.TerrainElementsFences -> "Fences"
        EntitySelectionRoot.TerrainElementsSigns -> "Signs"
        EntitySelectionRoot.TerrainElementsLighting -> "Lighting"
    }.also { menuTitles[root] = it }

    override fun entity(e: Entity<*>): String = entities[e] ?: when (e) {

        // Floor

        Floor.WoodFloor -> "Wood floor"
        Floor.RusticPlankFloor -> "Rustic plank floor"
        Floor.StrawFloor -> "Straw floor"
        Floor.WeatheredFloor -> "Weathered floor"
        Floor.CrystalFloor -> "Crystal floor"
        Floor.StoneFloor -> "Stone floor"
        Floor.StoneWalkwayFloor -> "Stone walkway floor"
        Floor.BrickFloor -> "Brick floor"

        Floor.WoodPath -> "Wood path"
        Floor.GravelPath -> "Gravel path"
        Floor.CobblestonePath -> "Cobblestone path"
        Floor.SteppingStonePath -> "Stepping stone path"
        Floor.CrystalPath -> "Crystal path"

        Floor.Grass -> "Grass"


        // Floor Furniture

        is FloorFurniture -> TODO()


        // Equipment

        // Common Equipment
        // (Artisan Equipment + Other Tools + Refining Equipment + Misc + Storage Equipment)

        Equipment.SimpleEquipment.MayonnaiseMachine -> "Mayonnaise machine"
        Equipment.SimpleEquipment.BeeHouse -> "Bee house"
        Equipment.SimpleEquipment.PreservesJar -> "Preserves jar"
        Equipment.SimpleEquipment.CheesePress -> "Cheese press"
        Equipment.SimpleEquipment.Loom -> "Loom"
        Equipment.SimpleEquipment.Keg -> "Keg"
        Equipment.SimpleEquipment.OilMaker -> "Oil maker"
        Equipment.SimpleEquipment.Cask -> "Cask"

        Equipment.SimpleEquipment.GardenPot -> "Garden pot"
        Equipment.SimpleEquipment.Heater -> "Heater"
        Equipment.SimpleEquipment.AutoGrabber -> "Auto grabber"
        Equipment.SimpleEquipment.AutoPetter -> "Auto petter"

        Equipment.SimpleEquipment.CharcoalKiln -> "Charcoal kiln"
        Equipment.SimpleEquipment.Crystalarium -> "Crystalarium"
        Equipment.SimpleEquipment.Furnace -> "Furnace"
        Equipment.SimpleEquipment.LightningRod -> "Lightning rod"
        Equipment.SimpleEquipment.SolarPanel -> "Solar panel"
        Equipment.SimpleEquipment.RecyclingMachine -> "Recycling machine"
        Equipment.SimpleEquipment.SeedMaker -> "Seed maker"
        Equipment.SimpleEquipment.SlimeIncubator -> "Slime incubator"
        Equipment.SimpleEquipment.OstrichIncubator -> "Ostrich incubator"
        Equipment.SimpleEquipment.SlimeEggPress -> "Slime egg press"
        Equipment.SimpleEquipment.WormBin -> "Worm bin"
        Equipment.SimpleEquipment.BoneMill -> "Bone mill"
        Equipment.SimpleEquipment.GeodeCrusher -> "Geode crusher"
        Equipment.SimpleEquipment.WoodChipper -> "Wood chipper"

        Equipment.SimpleEquipment.MiniJukebox -> "Mini jukebox"
        Equipment.SimpleEquipment.MiniObelisk -> "Mini obelisk"
        Equipment.SimpleEquipment.FarmComputer -> "Farm computer"
        Equipment.SimpleEquipment.Hopper -> "Hopper"
        Equipment.SimpleEquipment.Deconstructor -> "Deconstructor"
        Equipment.SimpleEquipment.CoffeeMaker -> "Coffee maker"
        Equipment.SimpleEquipment.Telephone -> "Telephone"
        Equipment.SimpleEquipment.SewingMachine -> "Sewing machine"
        Equipment.SimpleEquipment.Workbench -> "Workbench"
        Equipment.SimpleEquipment.MiniShippingBin -> "Mini shipping bin"

        // TODO (?) : CrabPot,
        // TODO (?) : Tapper,
        // TODO (?) : HeavyTapper,

        is Equipment.Chest -> "Chest"
        is Equipment.StoneChest -> "Stone chest"
        Equipment.SimpleEquipment.JunimoChest -> "Junimo chest"

        // Farm Elements (Scarecrows + Sprinklers)

        Equipment.SimpleEquipment.Scarecrow -> "Scarecrow"
        Equipment.SimpleEquipment.DeluxeScarecrow -> "Deluxe scarecrow"
        Equipment.SimpleEquipment.Rarecrow1 -> "Rarecrow 1"
        Equipment.SimpleEquipment.Rarecrow2 -> "Rarecrow 2"
        Equipment.SimpleEquipment.Rarecrow3 -> "Rarecrow 3"
        Equipment.SimpleEquipment.Rarecrow4 -> "Rarecrow 4"
        Equipment.SimpleEquipment.Rarecrow5 -> "Rarecrow 5"
        Equipment.SimpleEquipment.Rarecrow6 -> "Rarecrow 6"
        Equipment.SimpleEquipment.Rarecrow7 -> "Rarecrow 7"
        Equipment.SimpleEquipment.Rarecrow8 -> "Rarecrow 8"

        Equipment.SimpleEquipment.Sprinkler -> "Sprinkler"
        Equipment.SimpleEquipment.QualitySprinkler -> "Quality sprinkler"
        Equipment.SimpleEquipment.IridiumSprinkler -> "Iridium sprinkler"
        Equipment.SimpleEquipment.IridiumSprinklerWithPressureNozzle -> "Iridium sprinkler with pressure nozzle"

        // Terrain Elements (Fences + Signs + Lighting)

        Equipment.SimpleEquipment.Gate -> "Gate"
        Equipment.SimpleEquipment.WoodFence -> "Wood fence"
        Equipment.SimpleEquipment.StoneFence -> "Stone fence"
        Equipment.SimpleEquipment.IronFence -> "Iron fence"
        Equipment.SimpleEquipment.HardwoodFence -> "Hardwood fence"

        Equipment.SimpleEquipment.WoodSign -> "Wood sign"
        Equipment.SimpleEquipment.StoneSign -> "Stone sign"
        Equipment.SimpleEquipment.DarkSign -> "Dark sign"

        Equipment.SimpleEquipment.Torch -> "Torch"
        Equipment.SimpleEquipment.Campfire -> "Campfire"
        Equipment.SimpleEquipment.WoodenBrazier -> "Wooden brazier"
        Equipment.SimpleEquipment.StoneBrazier -> "Stone brazier"
        Equipment.SimpleEquipment.GoldBrazier -> "Gold brazier"
        Equipment.SimpleEquipment.CarvedBrazier -> "Carved brazier"
        Equipment.SimpleEquipment.StumpBrazier -> "Stump brazier"
        Equipment.SimpleEquipment.BarrelBrazier -> "Barrel brazier"
        Equipment.SimpleEquipment.SkullBrazier -> "Skull brazier"
        Equipment.SimpleEquipment.MarbleBrazier -> "Marble brazier"
        Equipment.SimpleEquipment.WoodLampPost -> "Wood lamp-post"
        Equipment.SimpleEquipment.IronLampPost -> "Iron lamp-post"
        Equipment.SimpleEquipment.JackOLantern -> "Jack-O-Lantern"

        // Furniture

        Equipment.SimpleEquipment.BasicLog -> "Basic log"
        Equipment.SimpleEquipment.LogSection -> "Log section"
        Equipment.SimpleEquipment.OrnamentalHayBale -> "Ornamental hay bale"
        Equipment.SimpleEquipment.SignOfTheVessel -> "Sign of the vessel"
        Equipment.SimpleEquipment.WickedStatue -> "Wicked statue"
        Equipment.SimpleEquipment.BigGreenCane -> "Big green cane"
        Equipment.SimpleEquipment.BigRedCane -> "Big red cane"
        Equipment.SimpleEquipment.GreenCanes -> "Green canes"
        Equipment.SimpleEquipment.RedCanes -> "Red canes"
        Equipment.SimpleEquipment.MixedCane -> "Mixed cane"
        Equipment.SimpleEquipment.LawnFlamingo -> "Lawn flamingo"
        Equipment.SimpleEquipment.PlushBunny -> "Plush bunny"
        Equipment.SimpleEquipment.SeasonalDecor -> "Seasonal decor"
        Equipment.SimpleEquipment.TubOFlowers -> "Tub o' flowers"
        Equipment.SimpleEquipment.SeasonalPlant1 -> "Seasonal plant 1"
        Equipment.SimpleEquipment.SeasonalPlant2 -> "Seasonal plant 2"
        Equipment.SimpleEquipment.SeasonalPlant3 -> "Seasonal plant 3"
        Equipment.SimpleEquipment.SeasonalPlant4 -> "Seasonal plant 4"
        Equipment.SimpleEquipment.SeasonalPlant5 -> "Seasonal plant 5"
        Equipment.SimpleEquipment.SeasonalPlant6 -> "Seasonal plant 6"
        Equipment.SimpleEquipment.DrumBlock -> "Drum block"
        Equipment.SimpleEquipment.FluteBlock -> "Flute block"
        Equipment.SimpleEquipment.GraveStone -> "Grave stone"
        Equipment.SimpleEquipment.StoneCairn -> "Stone cairn"
        Equipment.SimpleEquipment.StoneFrog -> "Stone frog"
        Equipment.SimpleEquipment.StoneJunimo -> "Stone junimo"
        Equipment.SimpleEquipment.StoneOwl -> "Stone owl"
        Equipment.SimpleEquipment.StoneParrot -> "Stone parrot"
        Equipment.SimpleEquipment.SuitOfArmor -> "Suit of armor"
        Equipment.SimpleEquipment.Foroguemon -> "Foroguemon"
        Equipment.SimpleEquipment.HMTGF -> "HMTGF"
        Equipment.SimpleEquipment.PinkyLemon -> "Pinky lemon"
        Equipment.SimpleEquipment.SolidGoldLewis -> "Solid gold Lewis"
        Equipment.SimpleEquipment.StatueOfEndlessFortune -> "Statue of endless fortune"
        Equipment.SimpleEquipment.StatueOfPerfection -> "Statue of perfection"
        Equipment.SimpleEquipment.StatueOfTruePerfection -> "Statue of true perfection"
        Equipment.SimpleEquipment.SodaMachine -> "Soda machine"
        Equipment.SimpleEquipment.StardewHeroTrophy -> "Stardew hero trophy"
        Equipment.SimpleEquipment.JunimoKartArcadeSystem -> "Junimo kart arcade system"
        Equipment.SimpleEquipment.PrairieKingArcadeSystem -> "Prairie king arcade system"
        Equipment.SimpleEquipment.SingingStone -> "Singing stone"
        Equipment.SimpleEquipment.SecretStoneOwl -> "Stone owl"
        Equipment.SimpleEquipment.SecretStrangeCapsule -> "Strange capsule"
        Equipment.SimpleEquipment.SecretEmptyCapsule -> "Empty capsule"


        // House Furniture

        is HouseFurniture -> TODO()


        // Indoor Furniture

        is IndoorFurniture -> TODO()


        // Universal Furniture

        is UniversalFurniture -> TODO()


        // Buildings

        // Barns

        Building.SimpleBuilding.Barn1 -> "Barn"
        Building.SimpleBuilding.Barn2 -> "Big Barn"
        is Building.Barn3 -> "Deluxe Barn"

        // Coops

        Building.SimpleBuilding.Coop1 -> "Coop"
        Building.SimpleBuilding.Coop2 -> "Big Coop"
        is Building.Coop3 -> "Deluxe Coop"

        // Sheds

        Building.SimpleBuilding.Shed1 -> "Shed"
        is Building.Shed2 -> "Big Shed"

        // Cabins

        Building.SimpleBuilding.StoneCabin1 -> "Stone Cabin"
        Building.SimpleBuilding.StoneCabin2 -> "Stone Cabin (Upgrade 1)"
        is Building.StoneCabin3 -> "Stone Cabin (Upgrade 2)"

        Building.SimpleBuilding.PlankCabin1 -> "Plank Cabin"
        Building.SimpleBuilding.PlankCabin2 -> "Plank Cabin (Upgrade 1)"
        is Building.PlankCabin3 -> "Plank Cabin (Upgrade 2)"

        Building.SimpleBuilding.LogCabin1 -> "Log Cabin"
        Building.SimpleBuilding.LogCabin2 -> "Log Cabin (Upgrade 1)"
        is Building.LogCabin3 -> "Log Cabin (Upgrade 2)"

        // Magical Buildings

        Building.SimpleBuilding.EarthObelisk -> "Earth Obelisk"
        Building.SimpleBuilding.WaterObelisk -> "Water Obelisk"
        Building.SimpleBuilding.DesertObelisk -> "Desert Obelisk"
        Building.SimpleBuilding.IslandObelisk -> "Island Obelisk"
        Building.SimpleBuilding.JunimoHut -> "Junimo Hut"
        Building.SimpleBuilding.GoldClock -> "Gold Clock"

        // Others

        Building.SimpleBuilding.Mill -> "Mill"
        Building.SimpleBuilding.Silo -> "Silo"
        Building.SimpleBuilding.Well -> "Well"
        is Building.Stable -> "Stable"
        is Building.FishPond -> "Fish Pond"
        Building.SimpleBuilding.SlimeHutch -> "Slime Hutch"
        Building.SimpleBuilding.ShippingBin -> "Shipping Bin"
    }.also { entities[e] = it }

    override fun tool(type: ToolType?): String = tools[type] ?: when (type) {
        null -> "No tool selected"
        ToolType.Hand -> "Hand"
        ToolType.Pen -> "Pen"
        ToolType.Eraser -> "Eraser"
        ToolType.EyeDropper -> "Eye Dropper"
        ToolType.RectSelect -> "Rectangle Selection"
        ToolType.EllipseSelect -> "Ellipse Selection"
    }.also { tools[type] = it }

    override fun layer(type: LayerType<*>): String = layers[type] ?: when (type) {
        LayerType.Floor -> "Floor layer"
        LayerType.FloorFurniture -> "Floor furniture layer"
        LayerType.Object -> "Objects layer"
        LayerType.EntityWithoutFloor -> "Entities without floor layer"
    }.also { layers[type] = it }


    // Private

    private val menuTitles: MutableMap<EntitySelectionRoot, String> = mutableMapOf()

    private val entities: MutableMap<Entity<*>, String> = mutableMapOf()

    private val tools: MutableMap<ToolType?, String> = mutableMapOf()

    private val layers: MutableMap<LayerType<*>, String> = mutableMapOf()
}