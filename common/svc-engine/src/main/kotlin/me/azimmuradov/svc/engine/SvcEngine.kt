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

package me.azimmuradov.svc.engine

import me.azimmuradov.svc.engine.entity.*
import me.azimmuradov.svc.engine.geometry.Coordinate
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layer.layerType
import me.azimmuradov.svc.engine.layers.*


/**
 * SVC engine. He is responsible for all low-level interactions with the editor map.
 */
interface SvcEngine {

    val layers: Layers


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


// Operations

fun SvcEngine.get(c: Coordinate): LayeredSingleEntitiesData = layeredSingleEntitiesData { get(it, c) }

fun SvcEngine.remove(c: Coordinate): LayeredSingleEntitiesData = layeredSingleEntitiesData { remove(it, c) }

fun <EType : EntityType> SvcEngine.remove(obj: PlacedEntity<EType>): PlacedEntity<EType>? =
    remove(obj.layerType, obj.place)


// Bulk Operations

fun SvcEngine.getAll(cs: Iterable<Coordinate>): LayeredEntitiesData = layeredEntitiesData { getAll(it, cs) }

fun SvcEngine.putAll(objs: LayeredEntities): LayeredEntitiesData =
    objs.flatten().flatMap { put(it).flatten() }.layeredData()

fun SvcEngine.removeAll(cs: Iterable<Coordinate>): LayeredEntitiesData = layeredEntitiesData { removeAll(it, cs) }

fun SvcEngine.removeAll(objs: List<PlacedEntity<*>>): LayeredEntitiesData =
    objs.mapNotNull(this::remove).layeredData()

fun SvcEngine.clear() = LayerType.all.forEach(this::clear)


// Utils

// TODO : Move
fun SvcEngine.getReplacedBy(obj: PlacedEntity<*>): LayeredEntitiesData {
    val type = obj.layerType

    val replaced = buildList<PlacedEntity<*>> {
        addAll(
            when (type) {
                LayerType.Object, LayerType.Floor, LayerType.FloorFurniture -> {
                    LayerType.withoutFloor.flatMap { layers.layerBy(it).getAll(obj.coordinates) }
                }

                LayerType.EntityWithoutFloor -> {
                    LayerType.withFloor.flatMap { layers.layerBy(it).getAll(obj.coordinates) }
                }
            }
        )

        addAll(layers.layerBy(type).getAll(obj.coordinates))
    }.layeredData()

    return replaced
}

// TODO : Move
fun <EType : EntityType> SvcEngine.getReplacedBy(objs: DisjointEntities<EType>): LayeredEntitiesData =
    objs.flatMapTo(mutableSetOf()) { getReplacedBy(it).flatten() }.layeredData()
