/*
 * Copyright 2021-2021 Azim Muradov
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

    fun put(obj: PlacedEntity<*>): LayeredEntities

    fun <EType : EntityType> remove(type: LayerType<EType>, c: Coordinate): PlacedEntity<EType>?


    // Bulk Operations

    fun <EType : EntityType> getAll(type: LayerType<EType>, cs: Iterable<Coordinate>): DisjointEntities<EType>

    fun <EType : EntityType> putAll(objs: DisjointEntities<EType>): LayeredEntities

    fun <EType : EntityType> removeAll(type: LayerType<EType>, cs: Iterable<Coordinate>): DisjointEntities<EType>

    fun clear(type: LayerType<*>)
}


// Operations

fun SvcEngine.get(c: Coordinate): LayeredSingleEntities = layeredSingleEntities { get(it, c) }

fun SvcEngine.remove(c: Coordinate): LayeredSingleEntities = layeredSingleEntities { remove(it, c) }

fun <EType : EntityType> SvcEngine.remove(obj: PlacedEntity<EType>): PlacedEntity<EType>? =
    remove(obj.layerType, obj.place)


// Bulk Operations

fun SvcEngine.getAll(cs: Iterable<Coordinate>): LayeredEntities = layeredEntities { getAll(it, cs) }

fun SvcEngine.putAll(objs: LayeredEntities): LayeredEntities = objs.flatten().flatMap { put(it).flatten() }.layered()

fun SvcEngine.removeAll(cs: Iterable<Coordinate>): LayeredEntities = layeredEntities { removeAll(it, cs) }

fun SvcEngine.removeAll(objs: List<PlacedEntity<*>>): LayeredEntities = objs.mapNotNull(this::remove).layered()

fun SvcEngine.clear() = LayerType.all.forEach(this::clear)