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

@file:Suppress("UNCHECKED_CAST")

package me.azimmuradov.svc.engine

import me.azimmuradov.svc.engine.SvcBehaviour.OnBetweenLayersConflict
import me.azimmuradov.svc.engine.entity.*
import me.azimmuradov.svc.engine.layer.LayerBehaviour.OnDisallowed
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layer.MutableLayer
import me.azimmuradov.svc.engine.layer.mutableLayerOf
import me.azimmuradov.svc.engine.layer.toLayerType
import me.azimmuradov.svc.engine.layout.Layout
import me.azimmuradov.svc.engine.rectmap.Coordinate
import me.azimmuradov.svc.engine.rectmap.RectMapBehaviour.OnConflict
import me.azimmuradov.svc.engine.rectmap.RectMapBehaviour.OnOutOfBounds


fun svcEngineOf(
    layout: Layout,
    behaviour: SvcBehaviour = DefaultSvcBehaviour.rewriter,
): SvcEngine = SvcEngineImpl(layout, behaviour)


object DefaultSvcBehaviour {

    val skipper: SvcBehaviour = MutableSvcBehaviourImpl(
        onOutOfBounds = OnOutOfBounds.SKIP,
        onConflict = OnConflict.SKIP,
        onDisallowed = OnDisallowed.SKIP,
        onBetweenLayersConflict = OnBetweenLayersConflict.SKIP,
    )

    val rewriter: SvcBehaviour = MutableSvcBehaviourImpl(
        onOutOfBounds = OnOutOfBounds.SKIP,
        onConflict = OnConflict.OVERWRITE,
        onDisallowed = OnDisallowed.SKIP,
        onBetweenLayersConflict = OnBetweenLayersConflict.OVERWRITE,
    )
}

fun SvcBehaviour.toMutableSvcBehaviour(): MutableSvcBehaviour = MutableSvcBehaviourImpl(
    onOutOfBounds,
    onConflict,
    onDisallowed,
    onBetweenLayersConflict,
)


// Actual private implementations

private class SvcEngineImpl(
    override val layout: Layout,
    behaviour: SvcBehaviour,
) : SvcEngine {

    override val behaviour: MutableSvcBehaviour = behaviour.toMutableSvcBehaviour()


    override fun layerOf(type: LayerType<*>) = _layerOf(type)

    private fun _layerOf(type: LayerType<*>) = _layers[LayerType.all.indexOf(type)]

    private val _layers: List<MutableLayer<*>> = listOf(
        mutableLayerOf<FloorType>(layout, behaviour),
        mutableLayerOf<FloorFurnitureType>(layout, behaviour),
        mutableLayerOf<ObjectType>(layout, behaviour),
        mutableLayerOf<EntityWithoutFloorType>(layout, behaviour),
    )

    private val Entity<*>.layer get() = _layerOf(id.type.toLayerType())


    // Operations

    override fun get(type: LayerType<*>, key: Coordinate) = layerOf(type)[key]

    override fun put(key: Coordinate, value: Entity<*>) = withChecks(key, value, onFail = { null }) {
        (value.layer as MutableLayer<in EntityType>).put(key, value)
    }

    override fun remove(type: LayerType<*>, key: Coordinate) = _layerOf(type).remove(key)


    // Bulk Operations

    override fun getAll(type: LayerType<*>, keys: Iterable<Coordinate>) = layerOf(type).getAll(keys)

    override fun putAll(from: Map<Coordinate, Entity<*>>) = from.mapNotNull { (key, value) -> put(key, value) }

    override fun removeAll(type: LayerType<*>, keys: Iterable<Coordinate>) = _layerOf(type).removeAll(keys)

    override fun clear(type: LayerType<*>) = _layerOf(type).clear()


    // Utilities

    // TODO
    private inline fun <T> withChecks(
        key: Coordinate, value: Entity<*>,
        onFail: () -> T,
        onSuccess: () -> T,
    ): T {
        val coordinates = value.size.coordinatesFrom(key)
        val type = value.id.type

        for ((i, layer) in _layers.withIndex()) {
            if (blcChecks[i](type, coordinates)) {
                when (behaviour.onBetweenLayersConflict) {
                    OnBetweenLayersConflict.SKIP -> return onFail()
                    OnBetweenLayersConflict.OVERWRITE -> layer.removeAll(coordinates)
                }
            }
        }

        return onSuccess()
    }

    private val blcChecks: List<Check> = listOf<SpecificCheck>(
        { type -> type == FloorType || type is EntityWithoutFloorType },
        { type -> type == FloorFurnitureType || type is EntityWithoutFloorType },
        { type -> type is ObjectType || type is EntityWithoutFloorType },
        { true },
    ).mapIndexed { i, specificCheck ->
        { type, cs -> specificCheck(type) && cs.any(_layers[i].keys::contains) }
    }
}


private typealias SpecificCheck = (EntityType) -> Boolean
private typealias Check = (EntityType, List<Coordinate>) -> Boolean

private data class MutableSvcBehaviourImpl(
    override var onOutOfBounds: OnOutOfBounds,
    override var onConflict: OnConflict,
    override var onDisallowed: OnDisallowed,
    override var onBetweenLayersConflict: OnBetweenLayersConflict,
) : MutableSvcBehaviour