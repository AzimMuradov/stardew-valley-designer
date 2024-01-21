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
import io.stardewvalleydesigner.engine.layer.*
import io.stardewvalleydesigner.engine.notOverlapsWith


class LayeredEntities(
    floorEntities: DisjointEntities<FloorType>? = null,
    floorFurnitureEntities: DisjointEntities<FloorFurnitureType>? = null,
    objectEntities: DisjointEntities<ObjectType>? = null,
    entityWithoutFloorEntities: DisjointEntities<EntityWithoutFloorType>? = null,
) {

    private val entitiesMap = mapOf(
        LayerType.Floor to (floorEntities ?: emptyDisjointRectObjects()),
        LayerType.FloorFurniture to (floorFurnitureEntities ?: emptyDisjointRectObjects()),
        LayerType.Object to (objectEntities ?: emptyDisjointRectObjects()),
        LayerType.EntityWithoutFloor to (entityWithoutFloorEntities ?: emptyDisjointRectObjects()),
    )

    init {
        val withFloorCs = entitiesMap
            .filter { (layerType) -> layerType in LayerType.withFloor }
            .flatMapTo(mutableSetOf()) { (_, es) -> es.coordinates }

        val withoutFloorCs = entitiesMap.asSequence()
            .filter { (layerType) -> layerType in LayerType.withoutFloor }
            .flatMapTo(mutableSetOf()) { (_, es) -> es.coordinates }

        require(withoutFloorCs notOverlapsWith withFloorCs) {
            "Wrong `LayeredEntities` definition."
        }
    }


    val all: List<Pair<LayerType<*>, DisjointEntities<*>>> = entitiesMap.toList()

    fun <EType : EntityType> entitiesBy(layerType: LayerType<EType>): DisjointEntities<EType> =
        entitiesMap.getValue(layerType) as DisjointEntities<EType>
}

fun layeredEntities(entitiesSelector: (LayerType<*>) -> List<PlacedEntity<*>>): LayeredEntities =
    LayerType.all.associateWith { entitiesSelector(it).asDisjointUnsafe() }.asLayeredEntities()


// Conversions

fun LayeredEntities.flatten(): List<PlacedEntity<*>> = all.flatMap { (_, es) -> es }

fun LayeredEntities.flattenSequence(): Sequence<PlacedEntity<*>> = all.asSequence().flatMap { (_, es) -> es }

fun <C : MutableCollection<in PlacedEntity<*>>> LayeredEntities.flattenTo(destination: C): C =
    all.flatMapTo(destination) { (_, es) -> es }

fun Iterable<PlacedEntity<*>>.layered(): LayeredEntities =
    groupBy(PlacedEntity<*>::layerType).mapValues { (_, es) -> es.asDisjointUnsafe() }.asLayeredEntities()

fun Sequence<PlacedEntity<*>>.layered(): LayeredEntities =
    groupBy(PlacedEntity<*>::layerType).mapValues { (_, es) -> es.asDisjointUnsafe() }.asLayeredEntities()


// Private utils

private fun Map<LayerType<*>, DisjointEntities<*>>.asLayeredEntities() = LayeredEntities(
    floorEntities = get(LayerType.Floor) as DisjointEntities<FloorType>?,
    floorFurnitureEntities = get(LayerType.FloorFurniture) as DisjointEntities<FloorFurnitureType>?,
    objectEntities = get(LayerType.Object) as DisjointEntities<ObjectType>?,
    entityWithoutFloorEntities = get(LayerType.EntityWithoutFloor) as DisjointEntities<EntityWithoutFloorType>?,
)
