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


/**
 * Mutable rectangular map that contains rectangular objects.
 */
interface MutableRectMap<T> : RectMap<T> {

    // Modification Operations

    fun put(obj: PlacedRectObject<T>): List<PlacedRectObject<T>>

    fun remove(c: Coordinate): PlacedRectObject<T>?


    // Bulk Modification Operations

    fun putAll(objs: Iterable<PlacedRectObject<T>>): List<PlacedRectObject<T>>

    fun removeAll(cs: Iterable<Coordinate>): List<PlacedRectObject<T>>

    fun clear()
}

operator fun <T> MutableRectMap<T>.set(key: Coordinate, value: RectObject<T>) {
    put(PlacedRectObject(rectObj = value, place = key))
}