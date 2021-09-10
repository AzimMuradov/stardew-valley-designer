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

package me.azimmuradov.svc.engine.llsvc

import me.azimmuradov.svc.engine.Coordinate
import me.azimmuradov.svc.engine.llsvc.entity.SvcEntity
import me.azimmuradov.svc.engine.llsvc.layer.SvcLayer
import me.azimmuradov.svc.engine.llsvc.layer.SvcLayerType
import me.azimmuradov.svc.engine.llsvc.layer.SvcLayout


interface LowLevelSvc {

    val layout: SvcLayout

    val behaviour: MutableSvcBehaviour

    fun layerOf(type: SvcLayerType<*>): SvcLayer<*>


    // Operations

    fun get(type: SvcLayerType<*>, key: Coordinate): SvcEntity<*>?

    fun put(key: Coordinate, value: SvcEntity<*>): SvcEntity<*>?

    fun remove(type: SvcLayerType<*>, key: Coordinate): SvcEntity<*>?


    // Bulk Operations

    fun getAll(type: SvcLayerType<*>, keys: Iterable<Coordinate>): List<SvcEntity<*>>

    fun putAll(from: Map<Coordinate, SvcEntity<*>>): List<SvcEntity<*>>

    fun removeAll(type: SvcLayerType<*>, keys: Iterable<Coordinate>)

    fun clear(type: SvcLayerType<*>)
}