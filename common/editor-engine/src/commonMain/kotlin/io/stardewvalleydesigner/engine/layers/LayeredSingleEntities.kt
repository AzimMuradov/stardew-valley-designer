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

package io.stardewvalleydesigner.engine.layers

import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.layer.LayerType
import io.stardewvalleydesigner.engine.layer.PlacedEntity


data class LayeredSingleEntities(
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

fun LayeredSingleEntities.flatten(): List<PlacedEntity<*>> = all.mapNotNull { (_, entity) -> entity }

fun LayeredSingleEntities.topmost(): PlacedEntity<*>? = flatten().lastOrNull()


// Internal utils

internal inline fun layeredSingleEntities(
    layers: Set<LayerType<*>> = LayerType.all,
    entitiesSelector: (LayerType<*>) -> PlacedEntity<*>?,
): LayeredSingleEntities = layers
    .associateWith(entitiesSelector)
    .asLayeredSingleEntities()

@Suppress("UNCHECKED_CAST")
private fun Map<LayerType<*>, PlacedEntity<*>?>.asLayeredSingleEntities() = LayeredSingleEntities(
    floorEntity = get(LayerType.Floor) as PlacedEntity<FloorType>?,
    floorFurnitureEntity = get(LayerType.FloorFurniture) as PlacedEntity<FloorFurnitureType>?,
    objectEntity = get(LayerType.Object) as PlacedEntity<ObjectType>?,
    entityWithoutFloorEntity = get(LayerType.EntityWithoutFloor) as PlacedEntity<EntityWithoutFloorType>?,
)
