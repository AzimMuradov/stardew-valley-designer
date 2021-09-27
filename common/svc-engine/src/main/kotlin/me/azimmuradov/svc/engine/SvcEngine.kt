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

import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.layer.Layer
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layout.Layout
import me.azimmuradov.svc.engine.rectmap.Coordinate


interface SvcEngine {

    val layout: Layout

    fun layerOf(type: LayerType<*>): Layer<*>


    // Operations

    fun get(type: LayerType<*>, c: Coordinate): PlacedEntity<*>?

    fun put(obj: PlacedEntity<*>): Map<LayerType<*>, List<PlacedEntity<*>>>

    fun remove(type: LayerType<*>, c: Coordinate): PlacedEntity<*>?


    // Bulk Operations

    fun getAll(type: LayerType<*>, cs: Iterable<Coordinate>): List<PlacedEntity<*>>

    fun putAll(objs: Iterable<PlacedEntity<*>>): Map<LayerType<*>, List<PlacedEntity<*>>>

    fun removeAll(type: LayerType<*>, cs: Iterable<Coordinate>): List<PlacedEntity<*>>

    fun clear(type: LayerType<*>)
}


fun SvcEngine.layers() = LayerType.all.associateWith(this::layerOf)


fun SvcEngine.get(c: Coordinate): Map<LayerType<*>, PlacedEntity<*>?> =
    LayerType.all.associateWith { get(it, c) }

fun SvcEngine.remove(c: Coordinate): Map<LayerType<*>, PlacedEntity<*>?> =
    LayerType.all.associateWith { remove(it, c) }


fun SvcEngine.getAll(cs: Iterable<Coordinate>): Map<LayerType<*>, List<PlacedEntity<*>>> =
    LayerType.all.associateWith { getAll(it, cs) }

fun SvcEngine.removeAll(cs: Iterable<Coordinate>): Map<LayerType<*>, List<PlacedEntity<*>>> =
    LayerType.all.associateWith { removeAll(it, cs) }

fun SvcEngine.clear() = LayerType.all.forEach(this::clear)