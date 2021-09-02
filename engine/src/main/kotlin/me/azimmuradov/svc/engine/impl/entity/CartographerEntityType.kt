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

package me.azimmuradov.svc.engine.impl.entity


sealed interface CartographerEntityType


object FloorType : CartographerEntityType

object FloorFurnitureType : CartographerEntityType

sealed interface ObjectType : CartographerEntityType {

    object EquipmentType : ObjectType

    sealed interface FurnitureType : ObjectType {

        object HouseFurnitureType : FurnitureType

        object IndoorFurnitureType : FurnitureType

        object UniversalFurnitureType : FurnitureType
    }
}


sealed interface EntityWithoutFloorType : CartographerEntityType {

    object BuildingType : EntityWithoutFloorType

    // TODO
    //
    // object CropType : EntityWithoutFloorType
    //
    // object TreeType : EntityWithoutFloorType
    //
    // object FruitTreeType : EntityWithoutFloorType
    //
    // object TerrainEntityType : EntityWithoutFloorType // Bushes, Boulders, Rocks, Meteorites
}


// TODO : WallFurniture

// TODO : Flooring

// TODO : Wallpaper