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

package io.stardewvalleydesigner.engine

import io.stardewvalleydesigner.engine.entity.EntityType
import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.layer.*
import io.stardewvalleydesigner.engine.layers.*
import io.stardewvalleydesigner.engine.layout.Layout


/**
 * Editor engine. It is responsible for all low-level interactions with the editor map.
 */
interface EditorEngine {

    val layout: Layout

    fun getEntities(): LayeredEntitiesData

    var wallpaper: Wallpaper?

    var flooring: Flooring?


    // Operations

    fun <T : EntityType> get(layer: LayerType<T>, c: Coordinate): PlacedEntity<T>?

    fun put(entity: PlacedEntity<*>): LayeredEntitiesData

    fun <T : EntityType> remove(layer: LayerType<T>, c: Coordinate): PlacedEntity<T>?


    // Bulk Operations

    fun <T : EntityType> getAll(layer: LayerType<T>, cs: Iterable<Coordinate>): Set<PlacedEntity<T>>

    fun <T : EntityType> putAll(entities: DisjointEntities<T>): LayeredEntitiesData

    fun <T : EntityType> removeAll(layer: LayerType<T>, cs: Iterable<Coordinate>): Set<PlacedEntity<T>>

    fun clear(type: LayerType<*>)
}


// Operations

fun EditorEngine.get(
    c: Coordinate,
    layers: Set<LayerType<*>> = LayerType.all,
): LayeredSingleEntitiesData = layeredSingleEntitiesData { layer ->
    if (layer in layers) get(layer, c) else null
}

fun EditorEngine.remove(
    c: Coordinate,
    layers: Set<LayerType<*>> = LayerType.all,
): LayeredSingleEntitiesData = layeredSingleEntitiesData { layer ->
    if (layer in layers) remove(layer, c) else null
}

fun <T : EntityType> EditorEngine.remove(
    entity: PlacedEntity<T>,
): PlacedEntity<T>? = remove(entity.layerType, entity.place)


// Bulk Operations

fun EditorEngine.getAll(
    cs: Iterable<Coordinate>,
    layers: Set<LayerType<*>> = LayerType.all,
): LayeredEntitiesData = layeredEntitiesData { layer ->
    if (layer in layers) getAll(layer, cs) else emptySet()
}

fun EditorEngine.putAll(
    entities: LayeredEntities,
): LayeredEntitiesData = entities
    .flattenSequence()
    .flatMap { put(it).flattenSequence() }
    .layeredData()

fun EditorEngine.removeAll(
    cs: Iterable<Coordinate>,
    layers: Set<LayerType<*>> = LayerType.all,
): LayeredEntitiesData = layeredEntitiesData { layer ->
    if (layer in layers) removeAll(layer, cs) else emptySet()
}

fun EditorEngine.removeAll(
    entities: Iterable<PlacedEntity<*>>,
): LayeredEntitiesData = entities
    .mapNotNull(this::remove)
    .layeredData()

fun EditorEngine.clear(
    layers: Set<LayerType<*>> = LayerType.all,
) = layers.forEach(this::clear)


// Utils

fun EditorEngine.getReplacedBy(entity: PlacedEntity<*>): LayeredEntitiesData {
    val layer = entity.layerType
    return getAll(entity.coordinates, layers = layer.incompatibleLayers + layer)
}

fun EditorEngine.getReplacedBy(
    entities: Iterable<PlacedEntity<*>>,
): LayeredEntitiesData = entities
    .asSequence()
    .flatMap { getReplacedBy(it).flattenSequence() }
    .layeredData()
