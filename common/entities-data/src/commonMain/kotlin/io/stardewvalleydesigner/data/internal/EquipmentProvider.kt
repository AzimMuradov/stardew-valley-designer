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

package io.stardewvalleydesigner.data.internal

import io.stardewvalleydesigner.data.*
import io.stardewvalleydesigner.data.FenceVariant.*
import io.stardewvalleydesigner.data.SpritePage.Companion.UNIT
import io.stardewvalleydesigner.data.SpritePage.Craftables
import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.entity.Equipment.Chest
import io.stardewvalleydesigner.engine.entity.Equipment.SimpleEquipment.*
import io.stardewvalleydesigner.engine.entity.Equipment.StoneChest


internal fun equipment(qe: QualifiedEntity<Equipment>): QualifiedEntityData {
    val entity = qe.entity

    fun craftable(
        id: Int,
        flavor: EntityFlavor? = null,
    ) = craftable(entity, id, flavor)

    fun fence(
        id: Int,
        page: SpritePage,
    ) = fence(entity, qualifier = qe.qualifier as SpriteQualifier.FenceQualifier, id, page)

    fun seasonalPlant(id: Int) =
        seasonalPlant(entity, qualifier = qe.qualifier as SpriteQualifier.SeasonQualifier, id)

    fun common(id: Int) = common(entity, id)

    fun chest(
        index: Int,
        color: Colors.ChestColors,
    ) = chest(entity, index, color)

    fun tubOFlowers() = tubOFlowers(
        qualifier = qe.qualifier as SpriteQualifier.TubOFlowersQualifier,
    )

    return when (entity) {
        // (Artisan Equipment + Other Tools + Refining Equipment + Misc + Storage Equipment)

        MayonnaiseMachine -> craftable(24)
        BeeHouse -> craftable(10)
        PreservesJar -> craftable(15)
        CheesePress -> craftable(16)
        Loom -> craftable(17)
        Keg -> craftable(12)
        OilMaker -> craftable(19)
        Cask -> craftable(163)

        GardenPot -> craftable(62)
        Heater -> craftable(104)
        AutoGrabber -> craftable(165)
        AutoPetter -> craftable(272)

        CharcoalKiln -> craftable(114)
        Crystalarium -> craftable(21)
        Furnace -> craftable(13)
        LightningRod -> craftable(9)
        SolarPanel -> craftable(231)
        RecyclingMachine -> craftable(20)
        SeedMaker -> craftable(25)
        SlimeIncubator -> craftable(156)
        OstrichIncubator -> craftable(254)
        SlimeEggPress -> craftable(158)
        WormBin -> craftable(154)
        BoneMill -> craftable(90)
        GeodeCrusher -> craftable(182)
        WoodChipper -> craftable(211)

        MiniJukebox -> craftable(209)
        MiniObelisk -> craftable(238)
        FarmComputer -> craftable(239)
        Hopper -> craftable(275)
        Deconstructor -> craftable(265)
        CoffeeMaker -> craftable(246)
        Telephone -> craftable(214)
        SewingMachine -> craftable(247)
        Workbench -> craftable(208)
        MiniShippingBin -> craftable(248)

        // TODO (?) : CrabPot,
        // TODO (?) : Tapper,
        // TODO (?) : HeavyTapper,

        is Chest -> chest(130, color = entity.color)

        is StoneChest -> chest(232, color = entity.color)

        JunimoChest -> craftable(256)


        // Farm Elements (Scarecrows + Sprinklers)

        Scarecrow -> craftable(8)
        DeluxeScarecrow -> craftable(167)
        Rarecrow1 -> craftable(110)
        Rarecrow2 -> craftable(113)
        Rarecrow3 -> craftable(126)
        Rarecrow4 -> craftable(136)
        Rarecrow5 -> craftable(137)
        Rarecrow6 -> craftable(138)
        Rarecrow7 -> craftable(139)
        Rarecrow8 -> craftable(140)

        Sprinkler -> common(599)
        QualitySprinkler -> common(621)
        IridiumSprinkler -> common(645)
        // TODO : Equipment.SimpleEquipment.IridiumSprinklerWithPressureNozzle -> common(916)


        // Terrain Elements (Fences + Signs + Lighting)

        Gate -> common(325)
        WoodFence -> fence(322, SpritePage.Fence1)
        StoneFence -> fence(323, SpritePage.Fence2)
        IronFence -> fence(324, SpritePage.Fence3)
        HardwoodFence -> fence(298, SpritePage.Fence5)

        WoodSign -> craftable(37)
        StoneSign -> craftable(38)
        DarkSign -> craftable(39)

        Torch -> common(93)
        Campfire -> craftable(146)
        WoodenBrazier -> craftable(143)
        StoneBrazier -> craftable(144)
        GoldBrazier -> craftable(145)
        CarvedBrazier -> craftable(148)
        StumpBrazier -> craftable(147)
        BarrelBrazier -> craftable(150)
        SkullBrazier -> craftable(149)
        MarbleBrazier -> craftable(151)
        WoodLampPost -> craftable(152)
        IronLampPost -> craftable(153)
        JackOLantern -> common(746)


        // Furniture

        BasicLog -> craftable(35)
        LogSection -> craftable(46)
        OrnamentalHayBale -> craftable(45)
        SignOfTheVessel -> craftable(34)
        WickedStatue -> craftable(83)
        BigGreenCane -> craftable(40)
        BigRedCane -> craftable(44)
        GreenCanes -> craftable(41)
        RedCanes -> craftable(43)
        MixedCane -> craftable(42)
        LawnFlamingo -> craftable(36)
        PlushBunny -> craftable(107)
        SeasonalDecor -> seasonalPlant(48)
        TubOFlowers -> tubOFlowers()
        SeasonalPlant1 -> seasonalPlant(184)
        SeasonalPlant2 -> seasonalPlant(188)
        SeasonalPlant3 -> seasonalPlant(192)
        SeasonalPlant4 -> seasonalPlant(196)
        SeasonalPlant5 -> seasonalPlant(200)
        SeasonalPlant6 -> seasonalPlant(204)
        DrumBlock -> common(463)
        FluteBlock -> common(464)
        GraveStone -> craftable(47)
        StoneCairn -> craftable(32)
        StoneFrog -> craftable(52)
        StoneJunimo -> craftable(55)
        StoneOwl -> craftable(54)
        StoneParrot -> craftable(53)
        SuitOfArmor -> craftable(33)
        Foroguemon -> craftable(162)
        HMTGF -> craftable(155)
        PinkyLemon -> craftable(161)
        SolidGoldLewis -> craftable(164)
        StatueOfEndlessFortune -> craftable(127)
        StatueOfPerfection -> craftable(160)
        StatueOfTruePerfection -> craftable(280)
        SodaMachine -> craftable(117)
        StardewHeroTrophy -> craftable(116)
        JunimoKartArcadeSystem -> craftable(159)
        PrairieKingArcadeSystem -> craftable(141)
        SingingStone -> craftable(94)
        SecretStoneOwl -> craftable(95)
        SecretStrangeCapsule -> craftable(96)
        SecretEmptyCapsule -> craftable(98)
    }
}


private fun craftable(
    entity: Entity<*>,
    id: Int,
    flavor: EntityFlavor? = null,
) = QualifiedEntityData(
    qualifiedEntity = QualifiedEntity(entity),
    entityId = EntityId(EntityPage.Craftables, id, flavor),
    spriteId = SpriteId.RegularSprite(
        page = Craftables,
        offset = SpriteOffset(
            x = id % (Craftables.width / UNIT) * Craftables.grain.w,
            y = id / (Craftables.width / UNIT) * Craftables.grain.h,
        ) * UNIT,
        size = SpriteSize(w = 1, h = 2) * UNIT,
    )
)

private fun fence(
    entity: Entity<*>,
    qualifier: SpriteQualifier.FenceQualifier,
    id: Int,
    page: SpritePage,
) = QualifiedEntityData(
    qualifiedEntity = QualifiedEntity(entity, qualifier),
    entityId = EntityId(EntityPage.CommonObjects, id),
    spriteId = SpriteId.RegularSprite(
        page = page,
        offset = when (qualifier.variant) {
            Single -> SpriteOffset(x = 2, y = 2)
            HasLeft -> SpriteOffset(x = 0, y = 6)
            HasRight -> SpriteOffset(x = 1, y = 6)
            HasLeftAndRight -> SpriteOffset(x = 1, y = 4)
            HasTop -> SpriteOffset(x = 0, y = 2)
            HasLeftAndBottom -> SpriteOffset(x = 2, y = 0)
            HasRightAndBottom -> SpriteOffset(x = 0, y = 0)
            HasLeftRightAndBottom -> SpriteOffset(x = 1, y = 2)
            HasLeftAndTop -> SpriteOffset(x = 2, y = 4)
            HasRightAndTop -> SpriteOffset(x = 0, y = 4)
        } * UNIT,
        size = SpriteSize(w = 1, h = 2) * UNIT,
    )
)

private fun seasonalPlant(
    entity: Entity<*>,
    qualifier: SpriteQualifier.SeasonQualifier,
    id: Int,
) = QualifiedEntityData(
    qualifiedEntity = QualifiedEntity(entity, qualifier),
    entityId = EntityId(EntityPage.Craftables, id),
    spriteId = SpriteId.RegularSprite(
        page = Craftables,
        offset = SpriteOffset(
            x = (id + qualifier.season.ordinal) % (Craftables.width / UNIT) * Craftables.grain.w,
            y = (id + qualifier.season.ordinal) / (Craftables.width / UNIT) * Craftables.grain.h,
        ) * UNIT,
        size = SpriteSize(w = 1, h = 2) * UNIT,
    )
)

private fun tubOFlowers(
    qualifier: SpriteQualifier.TubOFlowersQualifier,
) = QualifiedEntityData(
    qualifiedEntity = QualifiedEntity(TubOFlowers, qualifier),
    entityId = EntityId(EntityPage.Craftables, localId = 108),
    spriteId = SpriteId.RegularSprite(
        page = Craftables,
        offset = SpriteOffset(
            x = 108 % (Craftables.width / UNIT) * Craftables.grain.w + if (!qualifier.isBlooming) 1 else 0,
            y = 108 / (Craftables.width / UNIT) * Craftables.grain.h,
        ) * UNIT,
        size = SpriteSize(w = 1, h = 2) * UNIT,
    )
)

private fun chest(
    entity: Entity<*>,
    id: Int,
    color: Colors.ChestColors,
): QualifiedEntityData {
    val colorValue = color.value

    return if (colorValue == null) {
        QualifiedEntityData(
            qualifiedEntity = QualifiedEntity(entity),
            entityId = EntityId(EntityPage.Craftables, id, color),
            spriteId = SpriteId.RegularSprite(
                page = Craftables,
                size = SpriteSize(w = 1, h = 2) * UNIT,
                offset = SpriteOffset(
                    x = id % (Craftables.width / UNIT) * Craftables.grain.w,
                    y = id / (Craftables.width / UNIT) * Craftables.grain.h,
                ) * UNIT,
            )
        )
    } else {
        val spriteIndex = if (entity is Chest) 168 else id
        QualifiedEntityData(
            qualifiedEntity = QualifiedEntity(entity),
            entityId = EntityId(EntityPage.Craftables, id, color),
            spriteId = SpriteId.ChestSprite(
                tint = colorValue,
                offset = SpriteOffset(
                    x = spriteIndex % (Craftables.width / UNIT) * Craftables.grain.w,
                    y = spriteIndex / (Craftables.width / UNIT) * Craftables.grain.h,
                ) * UNIT,
                coverOffset = SpriteOffset(
                    x = spriteIndex % (Craftables.width / UNIT) * Craftables.grain.w,
                    y = spriteIndex / (Craftables.width / UNIT) * Craftables.grain.h + 2,
                ) * UNIT,
            )
        )
    }
}
