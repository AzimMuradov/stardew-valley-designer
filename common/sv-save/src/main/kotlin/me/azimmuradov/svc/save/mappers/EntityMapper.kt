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

package me.azimmuradov.svc.save.mappers

import me.azimmuradov.svc.engine.entity.*
import me.azimmuradov.svc.engine.geometry.xy
import me.azimmuradov.svc.engine.layer.placeIt
import me.azimmuradov.svc.metadata.EntityDataProvider.entityById
import me.azimmuradov.svc.metadata.EntityDataProvider.entityToId
import me.azimmuradov.svc.metadata.EntityId
import me.azimmuradov.svc.metadata.EntityPage
import me.azimmuradov.svc.save.models.*
import me.azimmuradov.svc.save.models.Building
import me.azimmuradov.svc.engine.entity.Building as BuildingEntity


fun Object.toPlacedEntityOrNull(): PlacedEntity<*>? {
    val entityId = EntityId(
        page = when {
            type == "Crafting" && typeAttr != "Fence" -> EntityPage.Craftables
            else -> EntityPage.CommonObjects
        },
        localId = when (typeAttr) {
            "Fence" -> when (name) {
                "Wood Fence" -> entityToId[Equipment.SimpleEquipment.WoodFence]?.localId
                "Stone Fence" -> entityToId[Equipment.SimpleEquipment.StoneFence]?.localId
                "Iron Fence" -> entityToId[Equipment.SimpleEquipment.IronFence]?.localId
                "Gate" -> entityToId[Equipment.SimpleEquipment.Gate]?.localId
                "Hardwood Fence" -> entityToId[Equipment.SimpleEquipment.HardwoodFence]?.localId
                else -> null
            } ?: return null

            else -> parentSheetIndex
        }
    )
    return entityById[entityId]?.placeIt(there = tileLocation.toCoordinate())
}

fun Furniture.toPlacedEntityOrNull(): PlacedEntity<*>? {
    val rotation = when (rotations) {
        2 -> when (currentRotation) {
            1 -> Rotations.Rotations2.R1
            2 -> Rotations.Rotations2.R2
            else -> Rotations.Rotations2.R1
        }

        4 -> when (currentRotation) {
            1 -> Rotations.Rotations4.R1
            2 -> Rotations.Rotations4.R2
            3 -> Rotations.Rotations4.R3
            4 -> Rotations.Rotations4.R4
            else -> Rotations.Rotations4.R1
        }

        else -> null
    }

    val entityId = EntityId(
        page = EntityPage.Furniture,
        localId = parentSheetIndex,
        flavor = rotation
    )

    return entityById[entityId]?.placeIt(there = tileLocation.toCoordinate())
}

fun Item<Vector2Wrapper, TerrainFeatureWrapper>.toPlacedEntityOrNull(): PlacedEntity<*>? {
    val (v2, tfw) = this
    val entity = when (tfw.tf.typeAttr) {
        "Flooring" -> entityById[
            EntityId(
                page = EntityPage.Flooring,
                localId = tfw.tf.whichFloor!!
            )
        ]

        "Grass" -> Floor.Grass

        "Tree" -> null

        "HoeDirt" -> null

        else -> null
    }
    return entity?.placeIt(there = v2.pos.toCoordinate())
}

fun Building.toPlacedEntityOrNull(): PlacedEntity<*>? {
    val entity = when (buildingType) {
        "Barn" -> BuildingEntity.SimpleBuilding.Barn1
        "Big Barn" -> BuildingEntity.SimpleBuilding.Barn2
        "Deluxe Barn" -> BuildingEntity.Barn3()

        "Coop" -> BuildingEntity.SimpleBuilding.Coop1
        "Big Coop" -> BuildingEntity.SimpleBuilding.Coop2
        "Deluxe Coop" -> BuildingEntity.Coop3()

        "Shed" -> BuildingEntity.SimpleBuilding.Shed1
        "Big Shed" -> BuildingEntity.Shed2()

        "Stone Cabin" -> BuildingEntity.StoneCabin3()
        "Plank Cabin" -> BuildingEntity.PlankCabin3()
        "Log Cabin" -> BuildingEntity.LogCabin3()

        "Earth Obelisk" -> BuildingEntity.SimpleBuilding.EarthObelisk
        "Water Obelisk" -> BuildingEntity.SimpleBuilding.WaterObelisk
        "Desert Obelisk" -> BuildingEntity.SimpleBuilding.DesertObelisk
        "Island Obelisk" -> BuildingEntity.SimpleBuilding.IslandObelisk
        "Junimo Hut" -> BuildingEntity.SimpleBuilding.JunimoHut
        "Gold Clock" -> BuildingEntity.SimpleBuilding.GoldClock

        "Mill" -> BuildingEntity.SimpleBuilding.Mill
        "Silo" -> BuildingEntity.SimpleBuilding.Silo
        "Well" -> BuildingEntity.SimpleBuilding.Well
        "Stable" -> BuildingEntity.Stable()
        "Fish Pond" -> BuildingEntity.FishPond()
        "Slime Hutch" -> BuildingEntity.SimpleBuilding.SlimeHutch
        "Shipping Bin" -> BuildingEntity.SimpleBuilding.ShippingBin

        else -> null
    }
    return entity?.placeIt(there = xy(tileX, tileY))
}


fun Position.toCoordinate() = xy(x = x.toInt(), y = y.toInt())
