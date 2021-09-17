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

import me.azimmuradov.svc.engine.entity.Entity
import me.azimmuradov.svc.engine.layer.Layer
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layout.Layout
import me.azimmuradov.svc.engine.rectmap.Coordinate


interface SvcEngine {

    val layout: Layout

    val behaviour: MutableSvcBehaviour

    fun layerOf(type: LayerType<*>): Layer<*>


    // Operations

    fun get(type: LayerType<*>, key: Coordinate): Entity<*>?

    fun put(key: Coordinate, value: Entity<*>): Entity<*>?

    fun remove(type: LayerType<*>, key: Coordinate): Entity<*>?


    // Bulk Operations

    fun getAll(type: LayerType<*>, keys: Iterable<Coordinate>): List<Entity<*>>

    fun putAll(from: Map<Coordinate, Entity<*>>): List<Entity<*>>

    fun removeAll(type: LayerType<*>, keys: Iterable<Coordinate>)

    fun clear(type: LayerType<*>)
}