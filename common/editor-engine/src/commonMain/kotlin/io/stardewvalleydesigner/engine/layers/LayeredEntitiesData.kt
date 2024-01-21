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

import io.stardewvalleydesigner.engine.customGroupByTo
import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.layer.*


data class LayeredEntitiesData(
    val floorEntities: Set<PlacedEntity<FloorType>> = emptySet(),
    val floorFurnitureEntities: Set<PlacedEntity<FloorFurnitureType>> = emptySet(),
    val objectEntities: Set<PlacedEntity<ObjectType>> = emptySet(),
    val entityWithoutFloorEntities: Set<PlacedEntity<EntityWithoutFloorType>> = emptySet(),
) {

    private val entitiesMap = mapOf(
        LayerType.Floor to floorEntities,
        LayerType.FloorFurniture to floorFurnitureEntities,
        LayerType.Object to objectEntities,
        LayerType.EntityWithoutFloor to entityWithoutFloorEntities,
    )


    val all: List<Pair<LayerType<*>, Set<PlacedEntity<*>>>> = entitiesMap.toList()

    fun <EType : EntityType> entitiesBy(layerType: LayerType<EType>): Set<PlacedEntity<EType>> =
        entitiesMap.getValue(layerType) as Set<PlacedEntity<EType>>
}

fun layeredEntitiesData(entitiesSelector: (LayerType<*>) -> Set<PlacedEntity<*>>): LayeredEntitiesData =
    LayerType.all.associateWith { entitiesSelector(it) }.asLayeredEntitiesData()


// Conversions

fun LayeredEntitiesData.flatten(): List<PlacedEntity<*>> = all.flatMap { (_, es) -> es }

fun LayeredEntitiesData.flattenSequence(): Sequence<PlacedEntity<*>> = all.asSequence().flatMap { (_, es) -> es }

fun Iterable<PlacedEntity<*>>.layeredData(): LayeredEntitiesData = customGroupByTo(
    destination = mutableMapOf(),
    keySelector = PlacedEntity<*>::layerType,
    valuesCollectionGenerator = ::mutableSetOf
).asLayeredEntitiesData()

fun Sequence<PlacedEntity<*>>.layeredData(): LayeredEntitiesData = customGroupByTo(
    destination = mutableMapOf(),
    keySelector = PlacedEntity<*>::layerType,
    valuesCollectionGenerator = ::mutableSetOf
).asLayeredEntitiesData()

fun LayeredEntitiesData.toLayeredEntities(): LayeredEntities = LayeredEntities(
    floorEntities.toList().asDisjointUnsafe(),
    floorFurnitureEntities.toList().asDisjointUnsafe(),
    objectEntities.toList().asDisjointUnsafe(),
    entityWithoutFloorEntities.toList().asDisjointUnsafe(),
)


// Utils

fun LayeredEntitiesData.isEmpty() = all.sumOf { it.second.size } == 0


// Private utils

private fun Map<LayerType<*>, Set<PlacedEntity<*>>>.asLayeredEntitiesData() = LayeredEntitiesData(
    floorEntities = get(LayerType.Floor) as Set<PlacedEntity<FloorType>>? ?: emptySet(),
    floorFurnitureEntities = get(LayerType.FloorFurniture) as Set<PlacedEntity<FloorFurnitureType>>? ?: emptySet(),
    objectEntities = get(LayerType.Object) as Set<PlacedEntity<ObjectType>>? ?: emptySet(),
    entityWithoutFloorEntities = get(LayerType.EntityWithoutFloor) as Set<PlacedEntity<EntityWithoutFloorType>>?
        ?: emptySet(),
)
