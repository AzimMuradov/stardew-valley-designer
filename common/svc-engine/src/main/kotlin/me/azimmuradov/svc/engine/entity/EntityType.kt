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

package me.azimmuradov.svc.engine.entity


sealed interface EntityType {

    companion object {

        val all = setOf(FloorType) + setOf(FloorFurnitureType) + ObjectType.all + EntityWithoutFloorType.all
    }
}


data object FloorType : EntityType

data object FloorFurnitureType : EntityType

sealed interface ObjectType : EntityType {

    data object EquipmentType : ObjectType

    sealed interface FurnitureType : ObjectType {

        data object HouseFurnitureType : FurnitureType

        data object IndoorFurnitureType : FurnitureType

        data object UniversalFurnitureType : FurnitureType


        companion object {

            val all = setOf(HouseFurnitureType, IndoorFurnitureType, UniversalFurnitureType)
        }
    }


    companion object {

        val all = setOf(EquipmentType) + FurnitureType.all
    }
}


sealed interface EntityWithoutFloorType : EntityType {

    data object BuildingType : EntityWithoutFloorType

    data object CropType : EntityWithoutFloorType

    // TODO
    //
    // object TreeType : EntityWithoutFloorType
    //
    // object FruitTreeType : EntityWithoutFloorType
    //
    // object TerrainEntityType : EntityWithoutFloorType // Bushes, Boulders, Rocks, Meteorites


    companion object {

        val all = setOf(BuildingType)
    }
}


// TODO : Farmhouse

// TODO : Greenhouse

// TODO : WallFurniture
