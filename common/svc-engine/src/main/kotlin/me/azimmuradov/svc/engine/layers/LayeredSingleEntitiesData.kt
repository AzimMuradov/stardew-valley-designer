/*
 * Copyright 2021-2022 Azim Muradov
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

@file:Suppress("UNCHECKED_CAST")

package me.azimmuradov.svc.engine.layers

import me.azimmuradov.svc.engine.entity.*
import me.azimmuradov.svc.engine.layer.LayerType

data class LayeredSingleEntitiesData(
    val floorEntity: PlacedEntity<FloorType>? = null,
    val floorFurnitureEntity: PlacedEntity<FloorFurnitureType>? = null,
    val objectEntity: PlacedEntity<ObjectType>? = null,
    val entityWithoutFloorEntity: PlacedEntity<EntityWithoutFloorType>? = null,
) {

    private val entitiesMap = mapOf(
        LayerType.Floor to floorEntity,
        LayerType.FloorFurniture to floorFurnitureEntity,
        LayerType.Object to objectEntity,
        LayerType.EntityWithoutFloor to entityWithoutFloorEntity,
    )


    val all: List<Pair<LayerType<*>, PlacedEntity<*>?>> = entitiesMap.toList()

    fun <EType : EntityType> entityOrNullBy(layerType: LayerType<EType>): PlacedEntity<EType>? =
        entitiesMap.getValue(layerType) as PlacedEntity<EType>?
}

fun layeredSingleEntitiesData(entitiesSelector: (LayerType<*>) -> PlacedEntity<*>?): LayeredSingleEntitiesData =
    LayerType.all.associateWith { entitiesSelector(it) }.asLayeredSingleEntitiesData()


// Conversions

fun LayeredSingleEntitiesData.flatten(): List<PlacedEntity<*>> = all.mapNotNull { (_, entity) -> entity }


// Utils

fun LayeredSingleEntitiesData.topmost(): PlacedEntity<*>? = flatten().lastOrNull()


// Private utils

private fun Map<LayerType<*>, PlacedEntity<*>?>.asLayeredSingleEntitiesData() = LayeredSingleEntitiesData(
    floorEntity = get(LayerType.Floor) as PlacedEntity<FloorType>?,
    floorFurnitureEntity = get(LayerType.FloorFurniture) as PlacedEntity<FloorFurnitureType>?,
    objectEntity = get(LayerType.Object) as PlacedEntity<ObjectType>?,
    entityWithoutFloorEntity = get(LayerType.EntityWithoutFloor) as PlacedEntity<EntityWithoutFloorType>?,
)