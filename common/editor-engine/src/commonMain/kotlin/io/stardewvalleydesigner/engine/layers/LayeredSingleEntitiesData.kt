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

@file:Suppress("UNCHECKED_CAST")

package io.stardewvalleydesigner.engine.layers

import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.layer.LayerType
import io.stardewvalleydesigner.engine.layer.PlacedEntity


data class LayeredSingleEntitiesData(
    val floorEntity: PlacedEntity<FloorType>? = null,
    val floorFurnitureEntity: PlacedEntity<FloorFurnitureType>? = null,
    val objectEntity: PlacedEntity<ObjectType>? = null,
    val entityWithoutFloorEntity: PlacedEntity<EntityWithoutFloorType>? = null,
) {

    val all: List<Pair<LayerType<*>, PlacedEntity<*>?>> = listOf(
        LayerType.Floor to floorEntity,
        LayerType.FloorFurniture to floorFurnitureEntity,
        LayerType.Object to objectEntity,
        LayerType.EntityWithoutFloor to entityWithoutFloorEntity,
    )
}

// sort held es


// Utils

fun LayeredSingleEntitiesData.flatten(): List<PlacedEntity<*>> = all.mapNotNull { (_, entity) -> entity }

fun LayeredSingleEntitiesData.topmost(): PlacedEntity<*>? = flatten().lastOrNull()


// Internal utils

internal inline fun layeredSingleEntitiesData(
    layers: Set<LayerType<*>> = LayerType.all,
    entitiesSelector: (LayerType<*>) -> PlacedEntity<*>?,
): LayeredSingleEntitiesData = layers
    .associateWith(entitiesSelector)
    .asLayeredSingleEntitiesData()

@Suppress("UNCHECKED_CAST")
private fun Map<LayerType<*>, PlacedEntity<*>?>.asLayeredSingleEntitiesData() = LayeredSingleEntitiesData(
    floorEntity = get(LayerType.Floor) as PlacedEntity<FloorType>?,
    floorFurnitureEntity = get(LayerType.FloorFurniture) as PlacedEntity<FloorFurnitureType>?,
    objectEntity = get(LayerType.Object) as PlacedEntity<ObjectType>?,
    entityWithoutFloorEntity = get(LayerType.EntityWithoutFloor) as PlacedEntity<EntityWithoutFloorType>?,
)
