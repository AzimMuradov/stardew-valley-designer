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

    fun getEntities(): LayeredEntities

    var wallpaper: Wallpaper?

    var flooring: Flooring?


    // Operations

    fun <T : EntityType> get(layer: LayerType<T>, c: Coordinate): PlacedEntity<T>?

    fun put(entity: PlacedEntity<*>)

    fun <T : EntityType> remove(layer: LayerType<T>, c: Coordinate): PlacedEntity<T>?


    // Bulk Operations

    fun <T : EntityType> getAll(layer: LayerType<T>, cs: Iterable<Coordinate>): Set<PlacedEntity<T>>

    fun putAll(entities: Iterable<PlacedEntity<*>>)

    fun <T : EntityType> removeAll(layer: LayerType<T>, cs: Iterable<Coordinate>): Set<PlacedEntity<T>>

    fun clear(type: LayerType<*>)
}


// Operations

fun EditorEngine.get(
    c: Coordinate,
    layers: Set<LayerType<*>> = LayerType.all,
): LayeredSingleEntities = layeredSingleEntities(layers) { layer ->
    get(layer, c)
}

fun EditorEngine.remove(
    c: Coordinate,
    layers: Set<LayerType<*>> = LayerType.all,
): LayeredSingleEntities = layeredSingleEntities(layers) { layer ->
    remove(layer, c)
}

fun <T : EntityType> EditorEngine.remove(
    entity: PlacedEntity<T>,
): PlacedEntity<T>? = remove(entity.layerType, entity.place)


// Bulk Operations

fun EditorEngine.getAll(
    cs: Iterable<Coordinate>,
    layers: Set<LayerType<*>> = LayerType.all,
): LayeredEntities = layeredEntities(layers) { layer ->
    getAll(layer, cs)
}

fun EditorEngine.putAll(entities: LayeredEntities) {
    entities.flattenSequence().forEach(this::put)
}

fun EditorEngine.removeAll(
    cs: Iterable<Coordinate>,
    layers: Set<LayerType<*>> = LayerType.all,
): LayeredEntities = layeredEntities(layers) { layer ->
    removeAll(layer, cs)
}

fun EditorEngine.removeAll(
    entities: Iterable<PlacedEntity<*>>,
): List<PlacedEntity<*>> = entities.mapNotNull(this::remove)

fun EditorEngine.clear(layers: Set<LayerType<*>> = LayerType.all) {
    layers.forEach(this::clear)
}


// Utils

fun EditorEngine.getReplacedBy(
    entities: Iterable<PlacedEntity<*>>,
): List<PlacedEntity<*>> = entities
    .asSequence()
    .flatMap {
        val layer = it.layerType
        getAll(it.coordinates, layers = layer.incompatibleLayers + layer).flattenSequence()
    }
    .toList()
