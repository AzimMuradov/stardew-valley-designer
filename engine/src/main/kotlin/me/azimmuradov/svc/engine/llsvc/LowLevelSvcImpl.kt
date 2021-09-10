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

package me.azimmuradov.svc.engine.llsvc

import me.azimmuradov.svc.engine.Coordinate
import me.azimmuradov.svc.engine.llsvc.SvcBehaviour.OnBetweenLayersConflict
import me.azimmuradov.svc.engine.llsvc.entity.*
import me.azimmuradov.svc.engine.llsvc.layer.*
import me.azimmuradov.svc.engine.llsvc.layer.SvcLayerBehaviour.OnDisallowed
import me.azimmuradov.svc.engine.rectmap.RectMapBehaviour.OnConflict
import me.azimmuradov.svc.engine.rectmap.RectMapBehaviour.OnOutOfBounds


fun lowLevelSvcOf(
    layout: SvcLayout,
    behaviour: SvcBehaviour = DefaultSvcBehaviour.rewriter,
): LowLevelSvc = LowLevelSvcImpl(layout, behaviour)


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

private class LowLevelSvcImpl(
    override val layout: SvcLayout,
    behaviour: SvcBehaviour,
) : LowLevelSvc {

    override val behaviour: MutableSvcBehaviour = behaviour.toMutableSvcBehaviour()


    override fun layerOf(type: SvcLayerType<*>) = _layerOf(type)

    private fun _layerOf(type: SvcLayerType<*>) = _layers[SvcLayerType.all.indexOf(type)]

    private val _layers: List<MutableSvcLayer<*>> = listOf(
        mutableSvcLayerOf<FloorType>(layout, behaviour),
        mutableSvcLayerOf<FloorFurnitureType>(layout, behaviour),
        mutableSvcLayerOf<ObjectType>(layout, behaviour),
        mutableSvcLayerOf<EntityWithoutFloorType>(layout, behaviour),
    )

    private val SvcEntity<*>.layer get() = _layerOf(id.type.toSvcLayerType())


    // Operations

    override fun get(type: SvcLayerType<*>, key: Coordinate) = layerOf(type)[key]

    override fun put(key: Coordinate, value: SvcEntity<*>) = withChecks(key, value, onFail = { null }) {
        (value.layer as MutableSvcLayer<in SvcEntityType>).put(key, value)
    }

    override fun remove(type: SvcLayerType<*>, key: Coordinate) = _layerOf(type).remove(key)


    // Bulk Operations

    override fun getAll(type: SvcLayerType<*>, keys: Iterable<Coordinate>) = layerOf(type).getAll(keys)

    override fun putAll(from: Map<Coordinate, SvcEntity<*>>) = from.mapNotNull { (key, value) -> put(key, value) }

    override fun removeAll(type: SvcLayerType<*>, keys: Iterable<Coordinate>) = _layerOf(type).removeAll(keys)

    override fun clear(type: SvcLayerType<*>) = _layerOf(type).clear()


    // Utilities

    // TODO
    private inline fun <T> withChecks(
        key: Coordinate, value: SvcEntity<*>,
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


private typealias SpecificCheck = (SvcEntityType) -> Boolean
private typealias Check = (SvcEntityType, List<Coordinate>) -> Boolean

private data class MutableSvcBehaviourImpl(
    override var onOutOfBounds: OnOutOfBounds,
    override var onConflict: OnConflict,
    override var onDisallowed: OnDisallowed,
    override var onBetweenLayersConflict: OnBetweenLayersConflict,
) : MutableSvcBehaviour