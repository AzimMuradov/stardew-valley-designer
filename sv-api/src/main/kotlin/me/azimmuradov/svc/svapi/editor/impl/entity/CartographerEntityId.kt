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

package me.azimmuradov.svc.svapi.editor.impl.entity

import me.azimmuradov.svc.svapi.editor.impl.entity.FloorType.DecorType
import me.azimmuradov.svc.svapi.editor.impl.entity.FloorType.GrassType
import me.azimmuradov.svc.svapi.editor.impl.entity.ObjectType.EquipmentType


sealed interface CartographerEntityId<out EType : CartographerEntityType> {

    val type: EType
}


enum class Decor : CartographerEntityId<DecorType> {
    WoodFloor,
    RusticPlankFloor,
    StrawFloor,
    WeatheredFloor,
    CrystalFloor,
    StoneFloor,
    StoneWalkwayFloor,
    BrickFloor,

    WoodPath,
    GravelPath,
    CobblestonePath,
    SteppingStonePath,
    CrystalPath;

    override val type: DecorType = DecorType
}


object Grass : CartographerEntityId<GrassType> {

    override val type: GrassType = GrassType
}


enum class Equipment : CartographerEntityId<EquipmentType> {

    // Fences
    Gate,
    WoodFence,
    StoneFence,
    IronFence,
    HardwoodFence,

    // Sprinklers
    Sprinkler,
    QualitySprinkler,
    IridiumSprinkler,

    // Artisan Equipment
    MayonnaiseMachine,
    BeeHouse,
    PreservesJar,
    CheesePress,
    Loom,
    Keg,
    OilMaker,
    Cask,

    // CrabPot,

    // Lighting
    Torch,
    Campfire,
    WoodenBrazier,
    StoneBrazier,
    GoldBrazier,
    CarvedBrazier,
    StumpBrazier,
    BarrelBrazier,
    SkullBrazier,
    MarbleBrazier,
    WoodLampPost,
    IronLampPost,
    JackOLantern,

    // Refining Equipment
    CharcoalKiln,
    Crystalarium,
    Furnace,
    LightningRod,
    SolarPanel,
    RecyclingMachine,
    SeedMaker,
    SlimeIncubator,
    OstrichIncubator,
    SlimeEggPress,

    // Tapper,
    // HeavyTapper,

    WormBin,
    BoneMill,
    GeodeCrusher,

    // Furniture
    TubOFlowers,
    WickedStatue,
    FluteBlock,
    DrumBlock,

    // Storage Equipment
    Chest,
    StoneChest,
    JunimoChest,

    // Signs
    WoodSign,
    StoneSign,
    DarkSign,

    // Misc
    GardenPot,
    Scarecrow,
    DeluxeScarecrow,
    MiniJukebox,
    MiniObelisk,
    FarmComputer,
    Hopper,

    // Other Tools
    Heater,
    AutoGrabber,
    AutoPetter,
    ;

    override val type: EquipmentType = EquipmentType
}