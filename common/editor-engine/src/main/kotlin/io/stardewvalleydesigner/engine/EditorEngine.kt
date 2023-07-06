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

package io.stardewvalleydesigner.engine

import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.layer.*
import io.stardewvalleydesigner.engine.layers.*
import io.stardewvalleydesigner.engine.layout.Layout


/**
 * Editor engine. It is responsible for all low-level interactions with the editor map.
 */
interface EditorEngine {

    val layers: Layers

    var wallpaper: Wallpaper?

    var flooring: Flooring?


    // Operations

    fun <EType : EntityType> get(type: LayerType<EType>, c: Coordinate): PlacedEntity<EType>?

    fun put(obj: PlacedEntity<*>): LayeredEntitiesData

    fun <EType : EntityType> remove(type: LayerType<EType>, c: Coordinate): PlacedEntity<EType>?


    // Bulk Operations

    fun <EType : EntityType> getAll(type: LayerType<EType>, cs: Iterable<Coordinate>): Set<PlacedEntity<EType>>

    fun <EType : EntityType> putAll(objs: DisjointEntities<EType>): LayeredEntitiesData

    fun <EType : EntityType> removeAll(type: LayerType<EType>, cs: Iterable<Coordinate>): Set<PlacedEntity<EType>>

    fun clear(type: LayerType<*>)
}


// Properties

val EditorEngine.layout: Layout get() = layers.layout


// Operations

fun EditorEngine.get(c: Coordinate, layers: Set<LayerType<*>> = LayerType.all): LayeredSingleEntitiesData =
    layeredSingleEntitiesData { if (it in layers) get(it, c) else null }

fun EditorEngine.remove(c: Coordinate, layers: Set<LayerType<*>> = LayerType.all): LayeredSingleEntitiesData =
    layeredSingleEntitiesData { if (it in layers) remove(it, c) else null }

fun <EType : EntityType> EditorEngine.remove(obj: PlacedEntity<EType>): PlacedEntity<EType>? =
    remove(obj.layerType, obj.place)


// Bulk Operations

fun EditorEngine.getAll(cs: Iterable<Coordinate>, layers: Set<LayerType<*>> = LayerType.all): LayeredEntitiesData =
    layeredEntitiesData { if (it in layers) getAll(it, cs) else emptySet() }

fun EditorEngine.putAll(objs: LayeredEntities): LayeredEntitiesData =
    objs.flatten().flatMap { put(it).flatten() }.layeredData()

fun EditorEngine.removeAll(cs: Iterable<Coordinate>, layers: Set<LayerType<*>> = LayerType.all): LayeredEntitiesData =
    layeredEntitiesData { if (it in layers) removeAll(it, cs) else emptySet() }

fun EditorEngine.removeAll(objs: List<PlacedEntity<*>>): LayeredEntitiesData =
    objs.mapNotNull(this::remove).layeredData()

fun EditorEngine.clear(layers: Set<LayerType<*>> = LayerType.all) = layers.forEach(this::clear)


// Utils

fun EditorEngine.getReplacedBy(obj: PlacedEntity<*>): LayeredEntitiesData {
    val objLayer = obj.layerType
    return getAll(obj.coordinates, layers = objLayer.incompatibleLayers + objLayer)
}

fun <EType : EntityType> EditorEngine.getReplacedBy(objs: DisjointEntities<EType>): LayeredEntitiesData =
    objs.flatMapTo(mutableSetOf()) { getReplacedBy(it).flatten() }.layeredData()
