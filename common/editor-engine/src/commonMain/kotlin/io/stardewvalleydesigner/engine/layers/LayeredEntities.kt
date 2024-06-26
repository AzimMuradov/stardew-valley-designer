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

import io.stardewvalleydesigner.engine.customGroupByTo
import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.layer.*


data class LayeredEntities(
    val floorEntities: Set<PlacedEntity<FloorType>> = emptySet(),
    val floorFurnitureEntities: Set<PlacedEntity<FloorFurnitureType>> = emptySet(),
    val objectEntities: Set<PlacedEntity<ObjectType>> = emptySet(),
    val entityWithoutFloorEntities: Set<PlacedEntity<EntityWithoutFloorType>> = emptySet(),
) {

    val all: List<Pair<LayerType<*>, Set<PlacedEntity<*>>>> = listOf(
        LayerType.Floor to floorEntities,
        LayerType.FloorFurniture to floorFurnitureEntities,
        LayerType.Object to objectEntities,
        LayerType.EntityWithoutFloor to entityWithoutFloorEntities,
    )
}


// Utils

fun LayeredEntities.isEmpty(): Boolean = all.sumOf { it.second.size } == 0

fun LayeredEntities.filter(visibleLayers: Set<LayerType<*>>): LayeredEntities = LayeredEntities(
    if (LayerType.Floor in visibleLayers) floorEntities else emptySet(),
    if (LayerType.FloorFurniture in visibleLayers) floorFurnitureEntities else emptySet(),
    if (LayerType.Object in visibleLayers) objectEntities else emptySet(),
    if (LayerType.EntityWithoutFloor in visibleLayers) entityWithoutFloorEntities else emptySet(),
)

fun LayeredEntities.flatten(): List<PlacedEntity<*>> = all.flatMap { (_, es) -> es }

fun LayeredEntities.flattenSequence(): Sequence<PlacedEntity<*>> = all.asSequence().flatMap { (_, es) -> es }

fun Iterable<PlacedEntity<*>>.layered(): LayeredEntities = customGroupByTo(
    destination = mutableMapOf(),
    keySelector = PlacedEntity<*>::layerType,
    valuesCollectionGenerator = ::mutableSetOf
).asLayeredEntities()


// Internal utils

internal inline fun layeredEntities(
    layers: Set<LayerType<*>> = LayerType.all,
    entitiesSelector: (LayerType<*>) -> Set<PlacedEntity<*>>,
): LayeredEntities = layers
    .associateWith(entitiesSelector)
    .asLayeredEntities()

@Suppress("UNCHECKED_CAST")
private fun Map<LayerType<*>, Set<PlacedEntity<*>>>.asLayeredEntities() = LayeredEntities(
    floorEntities = get(LayerType.Floor) as Set<PlacedEntity<FloorType>>? ?: emptySet(),
    floorFurnitureEntities = get(LayerType.FloorFurniture) as Set<PlacedEntity<FloorFurnitureType>>? ?: emptySet(),
    objectEntities = get(LayerType.Object) as Set<PlacedEntity<ObjectType>>? ?: emptySet(),
    entityWithoutFloorEntities = get(LayerType.EntityWithoutFloor) as Set<PlacedEntity<EntityWithoutFloorType>>?
        ?: emptySet(),
)
