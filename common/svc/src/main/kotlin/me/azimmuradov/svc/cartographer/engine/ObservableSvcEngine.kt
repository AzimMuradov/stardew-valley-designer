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

package me.azimmuradov.svc.cartographer.engine

import me.azimmuradov.svc.engine.SvcEngine
import me.azimmuradov.svc.engine.entity.*
import me.azimmuradov.svc.engine.geometry.Coordinate
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layers.Layers


internal class ObservableSvcEngine(
    private val onLayersChanged: (Layers) -> Unit,
    private val engine: SvcEngine,
) : SvcEngine by engine {

    override fun put(obj: PlacedEntity<*>) =
        engine.put(obj).alsoObserve()

    override fun <EType : EntityType> remove(type: LayerType<EType>, c: Coordinate) =
        engine.remove(type, c).alsoObserve()

    override fun <EType : EntityType> putAll(objs: DisjointEntities<EType>) =
        engine.putAll(objs).alsoObserve()

    override fun <EType : EntityType> removeAll(type: LayerType<EType>, cs: Iterable<Coordinate>) =
        engine.removeAll(type, cs).alsoObserve()

    override fun clear(type: LayerType<*>) =
        engine.clear(type).alsoObserve()


    private fun <T> T.alsoObserve(): T = also { onLayersChanged(layers) }
}