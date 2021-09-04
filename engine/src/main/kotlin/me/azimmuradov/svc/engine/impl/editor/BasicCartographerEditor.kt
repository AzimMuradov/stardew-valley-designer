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

package me.azimmuradov.svc.engine.impl.editor

import me.azimmuradov.svc.engine.Coordinate
import me.azimmuradov.svc.engine.impl.entity.*
import me.azimmuradov.svc.engine.impl.layer.CartographerLayer


interface BasicCartographerEditor {

    var behaviour: CartographerEditorBehaviour

    val flooringLayer: CartographerLayer<FloorType>
    val floorFurnitureLayer: CartographerLayer<FloorFurnitureType>
    val objectsLayer: CartographerLayer<ObjectType>
    val standaloneEntitiesLayer: CartographerLayer<EntityWithoutFloorType>


    fun get(type: CartographerEntityType, key: Coordinate): CartographerEntity<*>?
    fun get(key: Coordinate): CartographerEntity<*>?

    fun put(key: Coordinate, value: CartographerEntity<*>)

    fun remove(type: CartographerEntityType, key: Coordinate)
    fun remove(key: Coordinate)

    fun putAll(from: Map<Coordinate, CartographerEntity<*>>)

    fun removeAll(type: CartographerEntityType, keys: Iterable<Coordinate>)
    fun removeAll(keys: Iterable<Coordinate>)

    fun clear(type: CartographerEntityType)
    fun clear()
}