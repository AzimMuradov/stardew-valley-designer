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

package me.azimmuradov.svc.engine.rectmap

import me.azimmuradov.svc.engine.geometry.Coordinate
import me.azimmuradov.svc.engine.geometry.Rect


/**
 * Rectangular map that contains rectangular objects.
 */
interface RectMap<out RO : RectObject> {

    val size: Rect


    // Query Operations

    operator fun get(c: Coordinate): PlacedRectObject<RO>?


    // Bulk Query Operations

    fun getAll(cs: Iterable<Coordinate>): DisjointRectObjects<RO>


    // Views

    val objects: DisjointRectObjects<RO>
}


/**
 * Mutable rectangular map that contains rectangular objects.
 */
interface MutableRectMap<RO : RectObject> : RectMap<RO> {

    // Modification Operations

    fun put(obj: PlacedRectObject<RO>): DisjointRectObjects<RO>

    fun remove(c: Coordinate): PlacedRectObject<RO>?


    // Bulk Modification Operations

    fun putAll(objs: DisjointRectObjects<RO>): DisjointRectObjects<RO>

    fun removeAll(cs: Iterable<Coordinate>): DisjointRectObjects<RO>

    fun clear()
}