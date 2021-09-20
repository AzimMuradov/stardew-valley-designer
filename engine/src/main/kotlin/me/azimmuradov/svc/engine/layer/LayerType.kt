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

package me.azimmuradov.svc.engine.layer

import me.azimmuradov.svc.engine.entity.*
import me.azimmuradov.svc.engine.layer.LayerType.*


sealed interface LayerType<out EType : EntityType> {

    object Floor : LayerType<FloorType>

    object FloorFurniture : LayerType<FloorFurnitureType>

    object Object : LayerType<ObjectType>

    object EntityWithoutFloor : LayerType<EntityWithoutFloorType>


    companion object {

        val all = listOf(Floor, FloorFurniture, Object, EntityWithoutFloor)
    }
}

fun EntityType.toLayerType() = when (this) {
    FloorType -> Floor
    FloorFurnitureType -> FloorFurniture
    is ObjectType -> Object
    is EntityWithoutFloorType -> EntityWithoutFloor
}