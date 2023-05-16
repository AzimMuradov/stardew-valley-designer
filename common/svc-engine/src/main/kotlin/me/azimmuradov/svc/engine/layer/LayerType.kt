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

package me.azimmuradov.svc.engine.layer

import me.azimmuradov.svc.engine.entity.*
import me.azimmuradov.svc.engine.impossible


sealed interface LayerType<out EType : EntityType> {

    val ordinal: Int


    data object Floor : LayerType<FloorType> {

        override val ordinal: Int = 0
    }

    data object FloorFurniture : LayerType<FloorFurnitureType> {

        override val ordinal: Int = 1
    }

    data object Object : LayerType<ObjectType> {

        override val ordinal: Int = 2
    }

    data object EntityWithoutFloor : LayerType<EntityWithoutFloorType> {

        override val ordinal: Int = 0
    }


    companion object {

        val withFloor = setOf(Floor, FloorFurniture, Object)

        val withoutFloor = setOf(EntityWithoutFloor)


        val all = withFloor + withoutFloor
    }
}


val LayerType<*>.incompatibleLayers
    get() = if (this in LayerType.withFloor) {
        LayerType.withoutFloor
    } else {
        LayerType.withFloor
    }


// TODO : Report the issue

@Suppress("UNCHECKED_CAST")
fun <EType : EntityType> EType.toLayerType(): LayerType<EType> = when (this) {
    FloorType -> LayerType.Floor
    FloorFurnitureType -> LayerType.FloorFurniture
    is ObjectType -> LayerType.Object
    is EntityWithoutFloorType -> LayerType.EntityWithoutFloor
    else -> impossible()
} as LayerType<EType>

@Suppress("UNCHECKED_CAST")
fun <EType : EntityType> LayerType<EType>.allEntityTypes(): Set<EType> = when (this) {
    LayerType.Floor -> setOf(FloorType)
    LayerType.FloorFurniture -> setOf(FloorFurnitureType)
    LayerType.Object -> ObjectType.all
    LayerType.EntityWithoutFloor -> EntityWithoutFloorType.all
} as Set<EType>

val <EType : EntityType> PlacedEntity<EType>.layerType get() = type.toLayerType()
