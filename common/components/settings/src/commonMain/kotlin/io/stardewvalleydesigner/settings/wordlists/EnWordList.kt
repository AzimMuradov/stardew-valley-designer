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

package io.stardewvalleydesigner.settings.wordlists

import io.stardewvalleydesigner.component.editor.menus.EntitySelectionRoot
import io.stardewvalleydesigner.component.editor.modules.toolkit.ShapeType
import io.stardewvalleydesigner.component.editor.modules.toolkit.ToolType
import io.stardewvalleydesigner.designformat.models.OptionsItemValue
import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.layer.LayerType
import io.stardewvalleydesigner.engine.layout.LayoutType


data object EnWordList : WordList {

    override val application: String = "Stardew Valley Designer"

    override val author: String = "Azim Muradov"


    override val mainMenuTitle: String = "Main Menu"


    // General

    override val ok: String = "OK"

    override val yes: String = "YES"

    override val no: String = "NO"

    override val delete: String = "DELETE"

    override val farm: String = "farm"

    override fun layout(type: LayoutType): String = when (type) {
        LayoutType.StandardFarm -> "Standard Farm"
        LayoutType.RiverlandFarm -> "Riverland Farm"
        LayoutType.ForestFarm -> "Forest Farm"
        LayoutType.HillTopFarm -> "Hill-top Farm"
        LayoutType.WildernessFarm -> "Wilderness Farm"
        LayoutType.FourCornersFarm -> "Four Corners Farm"
        LayoutType.Shed -> "Shed"
        LayoutType.BigShed -> "Big Shed"
    }


    // Main menu screen

    override val buttonStardewValleyText: String = "Stardew Valley"

    override val buttonStardewValleyTooltip: String = "Open official Stardew Valley site"

    override val buttonSwitchThemeText: String = "Switch theme"

    override val buttonSwitchThemeTooltip: String = "WIP"

    override val buttonSettingsText: String = "Settings"

    override val buttonSettingsTooltip: String = "WIP"

    override val buttonDonateText: String = "Donate"

    override val buttonDonateTooltip: String = "WIP"

    override val buttonInfoText: String = "Info"

    override val buttonInfoTooltip: String = "Show information about this project"

    override val infoApplicationDescription: String =
        "The goal of this project is to provide a finely tuned editor for designing your farm and the interior of all its buildings."

    override val infoApplicationAuthor: String = "Application author"

    override val infoApplicationSource: String = "Application source"

    override val infoCurrentVersion: String = "Current version"

    override val infoChangelog: String = "Changelog"

    override val infoConcernedApeTwitter: String = "ConcernedApe twitter"

    override val infoSVText1: String = "Stardew Valley is developed by "

    override val infoSVText2: String = " and self-published on most platforms."

    override val infoSVText3: String =
        "Most of the sprites and icons used in this app, including the app icon, are from the original game."

    override val infoBugs: String = "If you encounter a bug or have any questions, please let me know."


    override val buttonNewDesignText: String = "New design"

    override val newDesignWindowTitle: String = "Create new design"


    override val buttonOpenDesignText: String = "Open design"

    override val openDesignWindowTitle: String = "Open design"

    override val openDesignPlaceholder: String = "Load your design"

    override val openDesignPlaceholderError: String = "Something went wrong, try again or choose another file"

    override val openDesignSelectDesignButton: String = "SELECT DESIGN"

    override val openDesignSelectDesignTitle: String = "Select design"

    override val openDesignSelectDesignPlaceholder: String = "path to your design"


    override val buttonSaveImportText: String = "Import from save"

    override val saveImportWindowTitle: String = "Import design from save"

    override val saveImportPlaceholder: String = "Load your save file and then choose from available layouts"

    override val saveImportPlaceholderError: String = "Something went wrong, try again or choose another file"

    override val saveImportSelectSaveFileButton: String = "SELECT SAVE FILE"

    override val saveImportSelectSaveFileTitle: String = "Select save file"

    override val saveImportSelectSaveFilePlaceholder: String = "path to your save file"


    override val chooseLayout: String = "CHOOSE LAYOUT"


    override fun designNoDesignsAtPath(path: String): String = """
        |You have no designs yet :)
        |
        |Try creating a new design and save it at the
        |"$path".
    """.trimMargin()

    override val designCardFilename: String = "Filename"

    override val designCardDate: String = "Date"

    override val designCardPlayerName: String = "Player name"

    override val designCardFarmName: String = "Farm name"

    override val designCardLayout: String = "Layout"

    override val designCardOpen: String = "OPEN"

    override val designCardDeleteDialogTitle: String = "Delete the design from your computer?"

    override val designCardDeleteDialogText: String =
        "It will delete the design from your computer. The action cannot be undone."


    // Editor Screen

    override fun menuTitle(root: EntitySelectionRoot): String = menuTitles[root] ?: when (root) {

        // Buildings Menu

        EntitySelectionRoot.Buildings -> "Buildings"
        EntitySelectionRoot.BuildingsBarns -> "Barns"
        EntitySelectionRoot.BuildingsCoops -> "Coops"
        EntitySelectionRoot.BuildingsSheds -> "Sheds"
        EntitySelectionRoot.BuildingsCabins -> "Cabins"
        EntitySelectionRoot.BuildingsMagical -> "Magical Buildings"


        // Common Equipment Menu

        EntitySelectionRoot.CommonEquipment -> "Common Equipment"


        // Furniture Menu

        EntitySelectionRoot.Furniture -> "Furniture"
        EntitySelectionRoot.FurnitureOutdoor -> "Outdoor Furniture"
        EntitySelectionRoot.FurnitureUniversal -> "Universal Furniture"
        EntitySelectionRoot.FurnitureUniversalChairs -> "Chairs"
        EntitySelectionRoot.FurnitureUniversalBenches -> "Benches"
        EntitySelectionRoot.FurnitureUniversalTables -> "Tables"
        EntitySelectionRoot.FurnitureUniversalHousePlants -> "House Plants"
        EntitySelectionRoot.FurnitureUniversalFreestandingDecorativePlants -> "Freestanding Decorative Plants"
        EntitySelectionRoot.FurnitureUniversalTorches -> "Torches"
        EntitySelectionRoot.FurnitureUniversalOtherDecorations -> "Other Decorations"
        EntitySelectionRoot.FurnitureUniversalCatalogues -> "Catalogues"
        EntitySelectionRoot.FurnitureIndoor -> "Indoor Furniture"
        EntitySelectionRoot.FurnitureIndoorBookcases -> "Bookcases"
        EntitySelectionRoot.FurnitureIndoorFireplaces -> "Fireplaces"
        EntitySelectionRoot.FurnitureIndoorLamps -> "Lamps"
        EntitySelectionRoot.FurnitureIndoorTVs -> "TVs"
        EntitySelectionRoot.FurnitureIndoorFishTanks -> "Fish Tanks"
        EntitySelectionRoot.FurnitureIndoorCouches -> "Couches"
        EntitySelectionRoot.FurnitureIndoorArmchairs -> "Armchairs"
        EntitySelectionRoot.FurnitureIndoorDressers -> "Dressers"
        EntitySelectionRoot.FurnitureHouse -> "House Furniture"
        EntitySelectionRoot.FurnitureFloor -> "Rugs"


        // Farm Elements

        EntitySelectionRoot.FarmElements -> "Farm Elements"
        EntitySelectionRoot.FarmElementsCrops -> "Crops"
        EntitySelectionRoot.FarmElementsCropsSpring -> "Spring Crops"
        EntitySelectionRoot.FarmElementsCropsSummer -> "Summer Crops"
        EntitySelectionRoot.FarmElementsCropsFall -> "Fall Crops"
        EntitySelectionRoot.FarmElementsCropsSpecial -> "Special Crops"
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

    override fun optionTitle(option: OptionsItemValue): String = when (option) {
        OptionsItemValue.Toggleable.ShowAxis -> "Show axis"
        OptionsItemValue.Toggleable.ShowGrid -> "Show grid"
        OptionsItemValue.Toggleable.ShowObjectCounter -> "Show object counter"
        OptionsItemValue.Toggleable.ShowCurrentCoordinatesAnsShapeSize -> "Show current coordinates and shape size"
        OptionsItemValue.Toggleable.ShowSpritesFully -> "Show sprites fully"

        OptionsItemValue.Toggleable.ShowScarecrowsAreaOfEffect -> "Show scarecrows area of effect"
        OptionsItemValue.Toggleable.ShowSprinklersAreaOfEffect -> "Show sprinklers area of effect"
        OptionsItemValue.Toggleable.ShowBeeHousesAreaOfEffect -> "Show bee houses area of effect"
        OptionsItemValue.Toggleable.ShowJunimoHutsAreaOfEffect -> "Show junimo huts area of effect"
    }

    override fun entity(e: Entity<*>): String = entities[e] ?: when (e) {

        // Floor

        Floor.WoodFloor -> "Wood Floor"
        Floor.RusticPlankFloor -> "Rustic Plank Floor"
        Floor.StrawFloor -> "Straw Floor"
        Floor.WeatheredFloor -> "Weathered Floor"
        Floor.CrystalFloor -> "Crystal Floor"
        Floor.StoneFloor -> "Stone Floor"
        Floor.StoneWalkwayFloor -> "Stone Walkway Floor"
        Floor.BrickFloor -> "Brick Floor"

        Floor.WoodPath -> "Wood Path"
        Floor.GravelPath -> "Gravel Path"
        Floor.CobblestonePath -> "Cobblestone Path"
        Floor.SteppingStonePath -> "Stepping Stone Path"
        Floor.CrystalPath -> "Crystal Path"

        Floor.Grass -> "Grass"


        // Floor Furniture

        FloorFurniture.SimpleRug.BurlapRug -> "Burlap Rug"
        FloorFurniture.SimpleRug.WoodcutRug -> "Woodcut Rug"
        FloorFurniture.SimpleRug.LargeRedRug -> "Large Red Rug"
        FloorFurniture.SimpleRug.MonsterRug -> "Monster Rug"
        FloorFurniture.SimpleRug.BlossomRug -> "Blossom Rug"
        FloorFurniture.SimpleRug.LargeGreenRug -> "Large Green Rug"
        FloorFurniture.SimpleRug.OldWorldRug -> "Old World Rug"
        FloorFurniture.SimpleRug.LargeCottageRug -> "Large Cottage Rug"
        FloorFurniture.SimpleRug.OceanicRug -> "Oceanic Rug"
        FloorFurniture.SimpleRug.IcyRug -> "Icy Rug"
        FloorFurniture.SimpleRug.FunkyRug -> "Funky Rug"
        FloorFurniture.SimpleRug.ModernRug -> "Modern Rug"

        is FloorFurniture.RotatableRug.BambooMat -> "Bamboo Mat"
        is FloorFurniture.RotatableRug.NauticalRug -> "Nautical Rug"
        is FloorFurniture.RotatableRug.DarkRug -> "Dark Rug"
        is FloorFurniture.RotatableRug.RedRug -> "Red Rug"
        is FloorFurniture.RotatableRug.LightGreenRug -> "Light Green Rug"
        is FloorFurniture.RotatableRug.GreenCottageRug -> "Green Cottage Rug"
        is FloorFurniture.RotatableRug.RedCottageRug -> "Red Cottage Rug"
        is FloorFurniture.RotatableRug.MysticRug -> "Mystic Rug"
        is FloorFurniture.RotatableRug.BoneRug -> "Bone Rug"
        is FloorFurniture.RotatableRug.SnowyRug -> "Snowy Rug"
        is FloorFurniture.RotatableRug.PirateRug -> "Pirate Rug"
        is FloorFurniture.RotatableRug.PatchworkRug -> "Patchwork Rug"
        is FloorFurniture.RotatableRug.FruitSaladRug -> "Fruit Salad Rug"

        is FloorFurniture.FloorDivider -> "Floor Divider"


        // Equipment

        // Common Equipment
        // (Artisan Equipment + Other Tools + Refining Equipment + Misc + Storage Equipment)

        Equipment.SimpleEquipment.MayonnaiseMachine -> "Mayonnaise Machine"
        Equipment.SimpleEquipment.BeeHouse -> "Bee House"
        Equipment.SimpleEquipment.PreservesJar -> "Preserves Jar"
        Equipment.SimpleEquipment.CheesePress -> "Cheese Press"
        Equipment.SimpleEquipment.Loom -> "Loom"
        Equipment.SimpleEquipment.Keg -> "Keg"
        Equipment.SimpleEquipment.OilMaker -> "Oil Maker"
        Equipment.SimpleEquipment.Cask -> "Cask"

        Equipment.SimpleEquipment.GardenPot -> "Garden Pot"
        Equipment.SimpleEquipment.Heater -> "Heater"
        Equipment.SimpleEquipment.AutoGrabber -> "Auto Grabber"
        Equipment.SimpleEquipment.AutoPetter -> "Auto Petter"

        Equipment.SimpleEquipment.CharcoalKiln -> "Charcoal Kiln"
        Equipment.SimpleEquipment.Crystalarium -> "Crystalarium"
        Equipment.SimpleEquipment.Furnace -> "Furnace"
        Equipment.SimpleEquipment.LightningRod -> "Lightning Rod"
        Equipment.SimpleEquipment.SolarPanel -> "Solar Panel"
        Equipment.SimpleEquipment.RecyclingMachine -> "Recycling Machine"
        Equipment.SimpleEquipment.SeedMaker -> "Seed Maker"
        Equipment.SimpleEquipment.SlimeIncubator -> "Slime Incubator"
        Equipment.SimpleEquipment.OstrichIncubator -> "Ostrich Incubator"
        Equipment.SimpleEquipment.SlimeEggPress -> "Slime Egg Press"
        Equipment.SimpleEquipment.WormBin -> "Worm Bin"
        Equipment.SimpleEquipment.BoneMill -> "Bone Mill"
        Equipment.SimpleEquipment.GeodeCrusher -> "Geode Crusher"
        Equipment.SimpleEquipment.WoodChipper -> "Wood Chipper"

        Equipment.SimpleEquipment.MiniJukebox -> "Mini-Jukebox"
        Equipment.SimpleEquipment.MiniObelisk -> "Mini-Obelisk"
        Equipment.SimpleEquipment.FarmComputer -> "Farm Computer"
        Equipment.SimpleEquipment.Hopper -> "Hopper"
        Equipment.SimpleEquipment.Deconstructor -> "Deconstructor"
        Equipment.SimpleEquipment.CoffeeMaker -> "Coffee Maker"
        Equipment.SimpleEquipment.Telephone -> "Telephone"
        Equipment.SimpleEquipment.SewingMachine -> "Sewing Machine"
        Equipment.SimpleEquipment.Workbench -> "Workbench"
        Equipment.SimpleEquipment.MiniShippingBin -> "Mini-Shipping Bin"

        // TODO (?) : CrabPot,
        // TODO (?) : Tapper,
        // TODO (?) : HeavyTapper,

        is Equipment.Chest -> "Chest"
        is Equipment.StoneChest -> "Stone Chest"
        Equipment.SimpleEquipment.JunimoChest -> "Junimo Chest"

        // Farm Elements (Scarecrows + Sprinklers)

        Equipment.SimpleEquipment.Scarecrow -> "Scarecrow"
        Equipment.SimpleEquipment.DeluxeScarecrow -> "Deluxe Scarecrow"
        Equipment.SimpleEquipment.Rarecrow1 -> "Rarecrow 1"
        Equipment.SimpleEquipment.Rarecrow2 -> "Rarecrow 2"
        Equipment.SimpleEquipment.Rarecrow3 -> "Rarecrow 3"
        Equipment.SimpleEquipment.Rarecrow4 -> "Rarecrow 4"
        Equipment.SimpleEquipment.Rarecrow5 -> "Rarecrow 5"
        Equipment.SimpleEquipment.Rarecrow6 -> "Rarecrow 6"
        Equipment.SimpleEquipment.Rarecrow7 -> "Rarecrow 7"
        Equipment.SimpleEquipment.Rarecrow8 -> "Rarecrow 8"

        Equipment.SimpleEquipment.Sprinkler -> "Sprinkler"
        Equipment.SimpleEquipment.QualitySprinkler -> "Quality Sprinkler"
        Equipment.SimpleEquipment.IridiumSprinkler -> "Iridium Sprinkler"
        // TODO : Equipment.SimpleEquipment.IridiumSprinklerWithPressureNozzle -> ""

        // Terrain Elements (Fences + Signs + Lighting)

        Equipment.SimpleEquipment.Gate -> "Gate"
        Equipment.SimpleEquipment.WoodFence -> "Wood Fence"
        Equipment.SimpleEquipment.StoneFence -> "Stone Fence"
        Equipment.SimpleEquipment.IronFence -> "Iron Fence"
        Equipment.SimpleEquipment.HardwoodFence -> "Hardwood Fence"

        Equipment.SimpleEquipment.WoodSign -> "Wood Sign"
        Equipment.SimpleEquipment.StoneSign -> "Stone Sign"
        Equipment.SimpleEquipment.DarkSign -> "Dark Sign"

        Equipment.SimpleEquipment.Torch -> "Torch"
        Equipment.SimpleEquipment.Campfire -> "Campfire"
        Equipment.SimpleEquipment.WoodenBrazier -> "Wooden Brazier"
        Equipment.SimpleEquipment.StoneBrazier -> "Stone Brazier"
        Equipment.SimpleEquipment.GoldBrazier -> "Gold Brazier"
        Equipment.SimpleEquipment.CarvedBrazier -> "Carved Brazier"
        Equipment.SimpleEquipment.StumpBrazier -> "Stump Brazier"
        Equipment.SimpleEquipment.BarrelBrazier -> "Barrel Brazier"
        Equipment.SimpleEquipment.SkullBrazier -> "Skull Brazier"
        Equipment.SimpleEquipment.MarbleBrazier -> "Marble Brazier"
        Equipment.SimpleEquipment.WoodLampPost -> "Wood Lamp-post"
        Equipment.SimpleEquipment.IronLampPost -> "Iron Lamp-post"
        Equipment.SimpleEquipment.JackOLantern -> "Jack-O-Lantern"

        // Furniture

        Equipment.SimpleEquipment.BasicLog -> "Basic Log"
        Equipment.SimpleEquipment.LogSection -> "Log Section"
        Equipment.SimpleEquipment.OrnamentalHayBale -> "Ornamental Hay Bale"
        Equipment.SimpleEquipment.SignOfTheVessel -> "Sign Of The Vessel"
        Equipment.SimpleEquipment.WickedStatue -> "Wicked Statue"
        Equipment.SimpleEquipment.BigGreenCane -> "Big Green Cane"
        Equipment.SimpleEquipment.BigRedCane -> "Big Red Cane"
        Equipment.SimpleEquipment.GreenCanes -> "Green Canes"
        Equipment.SimpleEquipment.RedCanes -> "Red Canes"
        Equipment.SimpleEquipment.MixedCane -> "Mixed Cane"
        Equipment.SimpleEquipment.LawnFlamingo -> "Lawn Flamingo"
        Equipment.SimpleEquipment.PlushBunny -> "Plush Bunny"
        Equipment.SimpleEquipment.SeasonalDecor -> "Seasonal Decor"
        Equipment.SimpleEquipment.TubOFlowers -> "Tub o' Flowers"
        Equipment.SimpleEquipment.SeasonalPlant1 -> "Seasonal Plant 1"
        Equipment.SimpleEquipment.SeasonalPlant2 -> "Seasonal Plant 2"
        Equipment.SimpleEquipment.SeasonalPlant3 -> "Seasonal Plant 3"
        Equipment.SimpleEquipment.SeasonalPlant4 -> "Seasonal Plant 4"
        Equipment.SimpleEquipment.SeasonalPlant5 -> "Seasonal Plant 5"
        Equipment.SimpleEquipment.SeasonalPlant6 -> "Seasonal Plant 6"
        Equipment.SimpleEquipment.DrumBlock -> "Drum Block"
        Equipment.SimpleEquipment.FluteBlock -> "Flute Block"
        Equipment.SimpleEquipment.GraveStone -> "Grave Stone"
        Equipment.SimpleEquipment.StoneCairn -> "Stone Cairn"
        Equipment.SimpleEquipment.StoneFrog -> "Stone Frog"
        Equipment.SimpleEquipment.StoneJunimo -> "Stone Junimo"
        Equipment.SimpleEquipment.StoneOwl -> "Stone Owl"
        Equipment.SimpleEquipment.StoneParrot -> "Stone Parrot"
        Equipment.SimpleEquipment.SuitOfArmor -> "Suit Of Armor"
        Equipment.SimpleEquipment.Foroguemon -> "??FOROGUEMON??"
        Equipment.SimpleEquipment.HMTGF -> "??HMTGF??"
        Equipment.SimpleEquipment.PinkyLemon -> "??Pinky Lemon??"
        Equipment.SimpleEquipment.SolidGoldLewis -> "Solid Gold Lewis"
        Equipment.SimpleEquipment.StatueOfEndlessFortune -> "Statue Of Endless Fortune"
        Equipment.SimpleEquipment.StatueOfPerfection -> "Statue Of Perfection"
        Equipment.SimpleEquipment.StatueOfTruePerfection -> "Statue Of True Perfection"
        Equipment.SimpleEquipment.SodaMachine -> "Soda Machine"
        Equipment.SimpleEquipment.StardewHeroTrophy -> "Stardew Hero Trophy"
        Equipment.SimpleEquipment.JunimoKartArcadeSystem -> "Junimo Kart Arcade System"
        Equipment.SimpleEquipment.PrairieKingArcadeSystem -> "Prairie King Arcade System"
        Equipment.SimpleEquipment.SingingStone -> "Singing Stone"
        Equipment.SimpleEquipment.SecretStoneOwl -> "Stone Owl"
        Equipment.SimpleEquipment.SecretStrangeCapsule -> "Strange Capsule"
        Equipment.SimpleEquipment.SecretEmptyCapsule -> "Empty Capsule"


        // House Furniture

        HouseFurniture.ChildBed -> "Child Bed"
        HouseFurniture.SingleBed -> "Single Bed"
        HouseFurniture.DoubleBed -> "Double Bed"
        HouseFurniture.BirchDoubleBed -> "Birch Double Bed"
        HouseFurniture.DeluxeRedDoubleBed -> "Deluxe Red Double Bed"
        HouseFurniture.ExoticDoubleBed -> "Exotic Double Bed"
        HouseFurniture.FisherDoubleBed -> "Fisher Double Bed"
        HouseFurniture.ModernDoubleBed -> "Modern Double Bed"
        HouseFurniture.PirateDoubleBed -> "Pirate Double Bed"
        HouseFurniture.StarryDoubleBed -> "Starry Double Bed"
        HouseFurniture.StrawberryDoubleBed -> "Strawberry Double Bed"
        HouseFurniture.TropicalBed -> "Tropical Bed"
        HouseFurniture.TropicalDoubleBed -> "Tropical Double Bed"
        HouseFurniture.WildDoubleBed -> "Wild Double Bed"

        HouseFurniture.MiniFridge -> "Mini-Fridge" // TODO : Not a furniture


        // Indoor Furniture

        IndoorFurniture.SimpleIndoorFurniture.ArtistBookcase -> "Artist Bookcase"
        IndoorFurniture.SimpleIndoorFurniture.ModernBookcase -> "Modern Bookcase"
        IndoorFurniture.SimpleIndoorFurniture.LuxuryBookcase -> "Luxury Bookcase"
        IndoorFurniture.SimpleIndoorFurniture.DarkBookcase -> "Dark Bookcase"
        IndoorFurniture.SimpleIndoorFurniture.BrickFireplace -> "Brick Fireplace"
        IndoorFurniture.SimpleIndoorFurniture.ElegantFireplace -> "Elegant Fireplace"
        IndoorFurniture.SimpleIndoorFurniture.IridiumFireplace -> "Iridium Fireplace"
        IndoorFurniture.SimpleIndoorFurniture.MonsterFireplace -> "Monster Fireplace"
        IndoorFurniture.SimpleIndoorFurniture.StoneFireplace -> "Stone Fireplace"
        IndoorFurniture.SimpleIndoorFurniture.StoveFireplace -> "Stove Fireplace"
        IndoorFurniture.SimpleIndoorFurniture.CountryLamp -> "Country Lamp"
        IndoorFurniture.SimpleIndoorFurniture.ModernLamp -> "Modern Lamp"
        IndoorFurniture.SimpleIndoorFurniture.ClassicLamp -> "Classic Lamp"
        IndoorFurniture.SimpleIndoorFurniture.BoxLamp -> "Box Lamp"
        IndoorFurniture.SimpleIndoorFurniture.CandleLamp -> "Candle Lamp"
        IndoorFurniture.SimpleIndoorFurniture.OrnateLamp -> "Ornate Lamp"
        IndoorFurniture.SimpleIndoorFurniture.FloorTV -> "Floor TV"
        IndoorFurniture.SimpleIndoorFurniture.BudgetTV -> "Budget TV"
        IndoorFurniture.SimpleIndoorFurniture.PlasmaTV -> "Plasma TV"
        IndoorFurniture.SimpleIndoorFurniture.TropicalTV -> "Tropical TV"
        IndoorFurniture.SimpleIndoorFurniture.SmallFishTank -> "Small Fish Tank"
        IndoorFurniture.SimpleIndoorFurniture.ModernFishTank -> "Modern Fish Tank"
        IndoorFurniture.SimpleIndoorFurniture.LargeFishTank -> "Large Fish Tank"
        IndoorFurniture.SimpleIndoorFurniture.DeluxeFishTank -> "Deluxe Fish Tank"
        IndoorFurniture.SimpleIndoorFurniture.AquaticSanctuary -> "Aquatic Sanctuary"
        IndoorFurniture.SimpleIndoorFurniture.ChinaCabinet -> "China Cabinet"

        is IndoorFurniture.Couch.BlueCouch -> "Blue Couch"
        is IndoorFurniture.Couch.BrownCouch -> "Brown Couch"
        is IndoorFurniture.Couch.GreenCouch -> "Green Couch"
        is IndoorFurniture.Couch.RedCouch -> "Red Couch"
        is IndoorFurniture.Couch.YellowCouch -> "Yellow Couch"
        is IndoorFurniture.Couch.DarkCouch -> "Dark Couch"
        is IndoorFurniture.Couch.WoodsyCouch -> "Woodsy Couch"
        is IndoorFurniture.Couch.WizardCouch -> "Wizard Couch"

        is IndoorFurniture.LargeBrownCouch -> "Large Brown Couch"

        is IndoorFurniture.Armchair.BlueArmchair -> "Blue Armchair"
        is IndoorFurniture.Armchair.BrownArmchair -> "Brown Armchair"
        is IndoorFurniture.Armchair.GreenArmchair -> "Green Armchair"
        is IndoorFurniture.Armchair.RedArmchair -> "Red Armchair"
        is IndoorFurniture.Armchair.YellowArmchair -> "Yellow Armchair"

        is IndoorFurniture.Dresser.BirchDresser -> "Birch Dresser"
        is IndoorFurniture.Dresser.OakDresser -> "Oak Dresser"
        is IndoorFurniture.Dresser.WalnutDresser -> "Walnut Dresser"
        is IndoorFurniture.Dresser.MahoganyDresser -> "Mahogany Dresser"


        // Universal Furniture

        UniversalFurniture.SimpleUniversalFurniture.GreenOfficeStool -> "Green Office Stool"
        UniversalFurniture.SimpleUniversalFurniture.OrangeOfficeStool -> "Orange Office Stool"
        UniversalFurniture.SimpleUniversalFurniture.GreenStool -> "Green Stool"
        UniversalFurniture.SimpleUniversalFurniture.BlueStool -> "Blue Stool"

        UniversalFurniture.SimpleUniversalFurniture.OakTable -> "Oak Table"
        UniversalFurniture.SimpleUniversalFurniture.OakTeaTable -> "Oak Tea-Table"
        UniversalFurniture.SimpleUniversalFurniture.BirchTable -> "Birch Table"
        UniversalFurniture.SimpleUniversalFurniture.BirchTeaTable -> "Birch Tea-Table"
        UniversalFurniture.SimpleUniversalFurniture.MahoganyTable -> "Mahogany Table"
        UniversalFurniture.SimpleUniversalFurniture.MahoganyTeaTable -> "Mahogany Tea-Table"
        UniversalFurniture.SimpleUniversalFurniture.WalnutTable -> "Walnut Table"
        UniversalFurniture.SimpleUniversalFurniture.WalnutTeaTable -> "Walnut Tea-Table"
        UniversalFurniture.SimpleUniversalFurniture.ModernTable -> "Modern Table"
        UniversalFurniture.SimpleUniversalFurniture.ModernTeaTable -> "Modern Tea-Table"
        UniversalFurniture.SimpleUniversalFurniture.ModernEndTable -> "Modern End Table"
        UniversalFurniture.SimpleUniversalFurniture.PuzzleTable -> "Puzzle Table"
        UniversalFurniture.SimpleUniversalFurniture.SunTable -> "Sun Table"
        UniversalFurniture.SimpleUniversalFurniture.MoonTable -> "Moon Table"
        UniversalFurniture.SimpleUniversalFurniture.LuxuryTable -> "Luxury Table"
        UniversalFurniture.SimpleUniversalFurniture.DivinerTable -> "Diviner Table"
        UniversalFurniture.SimpleUniversalFurniture.GrandmotherEndTable -> "Grandmother End Table"
        UniversalFurniture.SimpleUniversalFurniture.PubTable -> "Pub Table"
        UniversalFurniture.SimpleUniversalFurniture.LuauTable -> "Luau Table"
        UniversalFurniture.SimpleUniversalFurniture.DarkTable -> "Dark Table"
        UniversalFurniture.SimpleUniversalFurniture.CandyTable -> "Candy Table"
        UniversalFurniture.SimpleUniversalFurniture.WinterTable -> "Winter Table"
        UniversalFurniture.SimpleUniversalFurniture.WinterEndTable -> "Winter End Table"
        UniversalFurniture.SimpleUniversalFurniture.NeolithicTable -> "Neolithic Table"

        UniversalFurniture.SimpleUniversalFurniture.HousePlant1 -> "House Plant"
        UniversalFurniture.SimpleUniversalFurniture.HousePlant2 -> "House Plant"
        UniversalFurniture.SimpleUniversalFurniture.HousePlant3 -> "House Plant"
        UniversalFurniture.SimpleUniversalFurniture.HousePlant4 -> "House Plant"
        UniversalFurniture.SimpleUniversalFurniture.HousePlant5 -> "House Plant"
        UniversalFurniture.SimpleUniversalFurniture.HousePlant6 -> "House Plant"
        UniversalFurniture.SimpleUniversalFurniture.HousePlant7 -> "House Plant"
        UniversalFurniture.SimpleUniversalFurniture.HousePlant8 -> "House Plant"
        UniversalFurniture.SimpleUniversalFurniture.HousePlant9 -> "House Plant"
        UniversalFurniture.SimpleUniversalFurniture.HousePlant10 -> "House Plant"
        UniversalFurniture.SimpleUniversalFurniture.HousePlant11 -> "House Plant"
        UniversalFurniture.SimpleUniversalFurniture.HousePlant12 -> "House Plant"
        UniversalFurniture.SimpleUniversalFurniture.HousePlant13 -> "House Plant"
        UniversalFurniture.SimpleUniversalFurniture.HousePlant14 -> "House Plant"
        UniversalFurniture.SimpleUniversalFurniture.HousePlant15 -> "House Plant"

        UniversalFurniture.SimpleUniversalFurniture.DriedSunflowers -> "Dried Sunflowers"
        UniversalFurniture.SimpleUniversalFurniture.BonsaiTree -> "Bonsai Tree"
        UniversalFurniture.SimpleUniversalFurniture.SmallPine -> "Small Pine"
        UniversalFurniture.SimpleUniversalFurniture.TreeColumn -> "Tree Column"
        UniversalFurniture.SimpleUniversalFurniture.SmallPlant -> "Small Plant"
        UniversalFurniture.SimpleUniversalFurniture.TablePlant -> "Table Plant"
        UniversalFurniture.SimpleUniversalFurniture.DeluxeTree -> "Deluxe Tree"
        UniversalFurniture.SimpleUniversalFurniture.ExoticTree -> "Exotic Tree"
        UniversalFurniture.SimpleUniversalFurniture.IndoorPalm -> "Indoor Palm"
        UniversalFurniture.SimpleUniversalFurniture.TopiaryTree -> "Topiary Tree"
        UniversalFurniture.SimpleUniversalFurniture.ManicuredPine -> "Manicured Pine"
        UniversalFurniture.SimpleUniversalFurniture.TreeOfTheWinterStar -> "Tree Of The Winter Star"
        UniversalFurniture.SimpleUniversalFurniture.LongCactus -> "Long Cactus"
        UniversalFurniture.SimpleUniversalFurniture.LongPalm -> "Long Palm"

        UniversalFurniture.SimpleUniversalFurniture.JungleTorch -> "Jungle Torch"
        UniversalFurniture.SimpleUniversalFurniture.PlainTorch -> "Plain Torch"
        UniversalFurniture.SimpleUniversalFurniture.StumpTorch -> "Stump Torch"

        UniversalFurniture.SimpleUniversalFurniture.CeramicPillar -> "Ceramic Pillar"
        UniversalFurniture.SimpleUniversalFurniture.GoldPillar -> "Gold Pillar"
        UniversalFurniture.SimpleUniversalFurniture.TotemPole -> "Totem Pole"
        UniversalFurniture.SimpleUniversalFurniture.DecorativeBowl -> "Decorative Bowl"
        UniversalFurniture.SimpleUniversalFurniture.DecorativeLantern -> "Decorative Lantern"
        UniversalFurniture.SimpleUniversalFurniture.Globe -> "Globe"
        UniversalFurniture.SimpleUniversalFurniture.ModelShip -> "Model Ship"
        UniversalFurniture.SimpleUniversalFurniture.SmallCrystal -> "Small Crystal"
        UniversalFurniture.SimpleUniversalFurniture.FutanBear -> "Futan Bear"
        UniversalFurniture.SimpleUniversalFurniture.DecorativeTrashCan -> "Decorative Trash Can"

        UniversalFurniture.SimpleUniversalFurniture.BearStatue -> "Bear Statue"
        UniversalFurniture.SimpleUniversalFurniture.ChickenStatue -> "Chicken Statue"
        UniversalFurniture.SimpleUniversalFurniture.LgFutanBear -> "Lg. Futan Bear"
        UniversalFurniture.SimpleUniversalFurniture.ObsidianVase -> "Obsidian Vase"
        UniversalFurniture.SimpleUniversalFurniture.SkeletonStatue -> "Skeleton Statue"
        UniversalFurniture.SimpleUniversalFurniture.SlothSkeletonL -> "Sloth Skeleton L"
        UniversalFurniture.SimpleUniversalFurniture.SlothSkeletonM -> "Sloth Skeleton M"
        UniversalFurniture.SimpleUniversalFurniture.SlothSkeletonR -> "Sloth Skeleton R"
        UniversalFurniture.SimpleUniversalFurniture.StandingGeode -> "Standing Geode"
        UniversalFurniture.SimpleUniversalFurniture.ButterflyHutch -> "Butterfly Hutch"
        UniversalFurniture.SimpleUniversalFurniture.LeahSculpture -> "Leah's Sculpture"
        UniversalFurniture.SimpleUniversalFurniture.SamBoombox -> "Sam's Boombox"
        UniversalFurniture.SimpleUniversalFurniture.FutanRabbit -> "Futan Rabbit"
        UniversalFurniture.SimpleUniversalFurniture.SmallJunimoPlush1 -> "Small Junimo Plush"
        UniversalFurniture.SimpleUniversalFurniture.SmallJunimoPlush2 -> "Small Junimo Plush"
        UniversalFurniture.SimpleUniversalFurniture.SmallJunimoPlush3 -> "Small Junimo Plush"
        UniversalFurniture.SimpleUniversalFurniture.SmallJunimoPlush4 -> "Small Junimo Plush"
        UniversalFurniture.SimpleUniversalFurniture.GreenSerpentStatue -> "Green Serpent Statue"
        UniversalFurniture.SimpleUniversalFurniture.PurpleSerpentStatue -> "Purple Serpent Statue"
        UniversalFurniture.SimpleUniversalFurniture.BoboStatue -> "Bobo Statue"
        UniversalFurniture.SimpleUniversalFurniture.WumbusStatue -> "Wumbus Statue"
        UniversalFurniture.SimpleUniversalFurniture.JunimoPlush -> "Junimo Plush"
        UniversalFurniture.SimpleUniversalFurniture.GourmandStatue -> "Gourmand Statue"
        UniversalFurniture.SimpleUniversalFurniture.IridiumKrobus -> "Iridium Krobus"
        UniversalFurniture.SimpleUniversalFurniture.SquirrelFigurine -> "Squirrel Figurine"

        UniversalFurniture.SimpleUniversalFurniture.Catalogue -> "Catalogue"
        UniversalFurniture.SimpleUniversalFurniture.FurnitureCatalogue -> "Furniture Catalogue"

        is UniversalFurniture.Chair.OakChair -> "Oak Chair"
        is UniversalFurniture.Chair.WalnutChair -> "Walnut Chair"
        is UniversalFurniture.Chair.BirchChair -> "Birch Chair"
        is UniversalFurniture.Chair.MahoganyChair -> "Mahogany Chair"
        is UniversalFurniture.Chair.RedDinerChair -> "Red Diner Chair"
        is UniversalFurniture.Chair.BlueDinerChair -> "Blue Diner Chair"
        is UniversalFurniture.Chair.CountryChair -> "Country Chair"
        is UniversalFurniture.Chair.BreakfastChair -> "Breakfast Chair"
        is UniversalFurniture.Chair.PinkOfficeChair -> "Pink Office Chair"
        is UniversalFurniture.Chair.PurpleOfficeChair -> "Purple Office Chair"
        is UniversalFurniture.Chair.DarkThrone -> "Dark Throne"
        is UniversalFurniture.Chair.DiningChairYellow -> "Dining Chair Yellow"
        is UniversalFurniture.Chair.DiningChairRed -> "Dining Chair Red"
        is UniversalFurniture.Chair.GreenPlushSeat -> "Green Plush Seat"
        is UniversalFurniture.Chair.PinkPlushSeat -> "Pink Plush Seat"
        is UniversalFurniture.Chair.WinterChair -> "Winter Chair"
        is UniversalFurniture.Chair.GroovyChair -> "Groovy Chair"
        is UniversalFurniture.Chair.CuteChair -> "Cute Chair"
        is UniversalFurniture.Chair.StumpSeat -> "Stump Seat"
        is UniversalFurniture.Chair.MetalChair -> "Metal Chair"
        is UniversalFurniture.Chair.KingChair -> "King Chair"
        is UniversalFurniture.Chair.CrystalChair -> "Crystal Chair"
        is UniversalFurniture.Chair.TropicalChair -> "Tropical Chair"

        is UniversalFurniture.Bench.BirchBench -> "Birch Bench"
        is UniversalFurniture.Bench.OakBench -> "Oak Bench"
        is UniversalFurniture.Bench.WalnutBench -> "Walnut Bench"
        is UniversalFurniture.Bench.MahoganyBench -> "Mahogany Bench"
        is UniversalFurniture.Bench.ModernBench -> "Modern Bench"

        is UniversalFurniture.WoodenEndTable.OakEndTable -> "Oak End Table"
        is UniversalFurniture.WoodenEndTable.BirchEndTable -> "Birch End Table"
        is UniversalFurniture.WoodenEndTable.MahoganyEndTable -> "Mahogany End Table"
        is UniversalFurniture.WoodenEndTable.WalnutEndTable -> "Walnut End Table"

        is UniversalFurniture.CoffeeTable -> "Coffee Table"

        is UniversalFurniture.StoneSlab -> "Stone Slab"

        is UniversalFurniture.LongTable.ModernDiningTable -> "Modern Dining Table"
        is UniversalFurniture.LongTable.MahoganyDiningTable -> "Mahogany Dining Table"
        is UniversalFurniture.LongTable.FestiveDiningTable -> "Festive Dining Table"
        is UniversalFurniture.LongTable.WinterDiningTable -> "Winter Dining Table"


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


        // Crops

        Crop.SimpleCrop.Parsnip -> "Parsnip"
        Crop.SimpleCrop.GreenBean -> "Green Bean"
        Crop.SimpleCrop.Cauliflower -> "Cauliflower"
        Crop.SimpleCrop.Potato -> "Potato"
        Crop.SimpleCrop.Garlic -> "Garlic"
        Crop.SimpleCrop.Kale -> "Kale"
        Crop.SimpleCrop.Rhubarb -> "Rhubarb"
        Crop.SimpleCrop.Melon -> "Melon"
        Crop.SimpleCrop.Tomato -> "Tomato"
        Crop.SimpleCrop.Blueberry -> "Blueberry"
        Crop.SimpleCrop.HotPepper -> "Hot Pepper"
        Crop.SimpleCrop.Wheat -> "Wheat"
        Crop.SimpleCrop.Radish -> "Radish"
        Crop.SimpleCrop.RedCabbage -> "Red Cabbage"
        Crop.SimpleCrop.Starfruit -> "Starfruit"
        Crop.SimpleCrop.Corn -> "Corn"
        Crop.SimpleCrop.Eggplant -> "Eggplant"
        Crop.SimpleCrop.Artichoke -> "Artichoke"
        Crop.SimpleCrop.Pumpkin -> "Pumpkin"
        Crop.SimpleCrop.BokChoy -> "Bok Choy"
        Crop.SimpleCrop.Yam -> "Yam"
        Crop.SimpleCrop.Cranberries -> "Cranberries"
        Crop.SimpleCrop.Beet -> "Beet"
        Crop.SimpleCrop.AncientFruit -> "Ancient Fruit"
        Crop.SimpleCrop.Tulip -> "Tulip"
        Crop.SimpleCrop.BlueJazz -> "Blue Jazz"
        Crop.SimpleCrop.Poppy -> "Poppy"
        Crop.SimpleCrop.SummerSpangle -> "Summer Spangle"
        Crop.SimpleCrop.Sunflower -> "Sunflower"
        Crop.SimpleCrop.FairyRose -> "Fairy Rose"
        Crop.SimpleCrop.SweetGemBerry -> "Sweet Gem Berry"
        Crop.SimpleCrop.Rice -> "Rice"
        Crop.SimpleCrop.Strawberry -> "Strawberry"
        Crop.SimpleCrop.Hops -> "Hops"
        Crop.SimpleCrop.Grape -> "Grape"
        Crop.SimpleCrop.Amaranth -> "Amaranth"
        Crop.SimpleCrop.CoffeeBean -> "Coffee Bean"
        Crop.SimpleCrop.CactusFruit -> "Cactus Fruit"
        Crop.SimpleCrop.TaroRoot -> "Taro Root"
        Crop.SimpleCrop.Pineapple -> "Pineapple"
    }.also { entities[e] = it }


    override val playerNamePlaceholder: String = "Player Name"

    override val farmNamePlaceholder: String = "Farm Name"


    override val buttonSaveDesignAsImageTooltip: String = "Save design as an image"

    override val saveDesignAsImageTitle: String = "Provide path for the design save"

    override fun saveDesignAsImageNotificationMessage(path: String?): String = if (path != null) {
        "Saved to \"$path\""
    } else {
        "Saved"
    }


    override val buttonSaveDesignTooltip: String = "Save design"

    override val saveDesignNotificationMessage: String = "Saved"


    override val buttonSaveDesignAsTooltip: String = "Save design as..."

    override val saveDesignAsTitle: String = "Provide path for the design save"

    override fun saveDesignAsNotificationMessage(path: String?): String = if (path != null) {
        "Saved to \"$path\""
    } else {
        "Saved"
    }


    override fun tool(type: ToolType?): String = when (type) {
        null -> "No tool selected"
        ToolType.Drag -> "Drag entities"
        ToolType.Pen -> "Pen (put entities)"
        ToolType.Eraser -> "Eraser (remove entities)"
        ToolType.Select -> "Select entities"
        ToolType.EyeDropper -> "Eye dropper (entity picker)"
        ToolType.Hand -> "Hand (move layout)"
    }

    override fun shape(type: ShapeType?): String = when (type) {
        null -> "Point"
        ShapeType.Rect -> "Rect"
        ShapeType.RectOutline -> "Rect outline"
        ShapeType.Ellipse -> "Ellipse"
        ShapeType.EllipseOutline -> "Ellipse outline"
        ShapeType.Diamond -> "Diamond"
        ShapeType.DiamondOutline -> "Diamond outline"
        ShapeType.Line -> "Line"
    }

    override val notAvailableForThisTool: String = "not available for this tool"

    override fun layer(type: LayerType<*>): String = layers[type] ?: when (type) {
        LayerType.Floor -> "Floor layer"
        LayerType.FloorFurniture -> "Rug layer"
        LayerType.Object -> "Object layer"
        LayerType.EntityWithoutFloor -> "Buildings and crops layer"
    }.also { layers[type] = it }

    override val wallpapersTabTitle: String = "Wallpapers"

    override val flooringTabTitle: String = "Flooring"

    override val objectCounterTitle: String = "Object count"

    override val width: String = "Width"

    override val height: String = "Height"

    override val start: String = "Start"

    override val end: String = "End"


    // Private

    private val menuTitles: MutableMap<EntitySelectionRoot, String> = mutableMapOf()

    private val entities: MutableMap<Entity<*>, String> = mutableMapOf()

    private val layers: MutableMap<LayerType<*>, String> = mutableMapOf()
}
