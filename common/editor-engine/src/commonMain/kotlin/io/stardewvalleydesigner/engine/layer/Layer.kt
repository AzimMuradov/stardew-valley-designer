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

package io.stardewvalleydesigner.engine.layer

import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.geometry.Rect
import io.stardewvalleydesigner.engine.layout.LayoutRules


interface Layer<out EType : EntityType> {

    val size: Rect


    // Query Operations

    operator fun get(c: Coordinate): PlacedEntity<EType>?


    // Bulk Query Operations

    fun getAll(cs: Iterable<Coordinate>): Set<PlacedEntity<EType>>


    // Views

    val objects: Set<PlacedEntity<EType>>


    val layoutRules: LayoutRules
}


interface MutableLayer<EType : EntityType> : Layer<EType> {

    // Modification Operations

    fun put(obj: PlacedEntity<EType>): Set<PlacedEntity<EType>>

    fun remove(c: Coordinate): PlacedEntity<EType>?


    // Bulk Modification Operations

    fun putAll(objs: DisjointRectObjects<Entity<EType>>): Set<PlacedEntity<EType>>

    fun removeAll(cs: Iterable<Coordinate>): Set<PlacedEntity<EType>>

    fun clear()
}


// Extensions

fun Layer<*>.isEmpty(): Boolean = objects.isEmpty()

fun Layer<*>.isNotEmpty(): Boolean = objects.isNotEmpty()
