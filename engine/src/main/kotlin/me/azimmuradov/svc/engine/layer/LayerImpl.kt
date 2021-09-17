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

package me.azimmuradov.svc.engine.layer

import me.azimmuradov.svc.engine.coordinatesFrom
import me.azimmuradov.svc.engine.entity.Entity
import me.azimmuradov.svc.engine.entity.EntityType
import me.azimmuradov.svc.engine.entity.ids.EntityId
import me.azimmuradov.svc.engine.layer.LayerBehaviour.OnDisallowed
import me.azimmuradov.svc.engine.layout.Layout
import me.azimmuradov.svc.engine.rectmap.Coordinate
import me.azimmuradov.svc.engine.rectmap.MutableRectMap
import me.azimmuradov.svc.engine.rectmap.RectMapBehaviour.OnConflict
import me.azimmuradov.svc.engine.rectmap.RectMapBehaviour.OnOutOfBounds
import me.azimmuradov.svc.engine.rectmap.mutableRectMapOf


fun <EType : EntityType> mutableLayerOf(
    layout: Layout,
    behaviour: LayerBehaviour = DefaultLayerBehaviour.rewriter,
): MutableLayer<EType> = MutableLayerImpl(layout, behaviour)


object DefaultLayerBehaviour {

    val skipper: LayerBehaviour = MutableLayerBehaviourImpl(
        onOutOfBounds = OnOutOfBounds.SKIP,
        onConflict = OnConflict.SKIP,
        onDisallowed = OnDisallowed.SKIP,
    )

    val rewriter: LayerBehaviour = MutableLayerBehaviourImpl(
        onOutOfBounds = OnOutOfBounds.SKIP,
        onConflict = OnConflict.OVERWRITE,
        onDisallowed = OnDisallowed.SKIP,
    )
}

fun LayerBehaviour.toMutableLayerBehaviour(): MutableLayerBehaviour = MutableLayerBehaviourImpl(
    onOutOfBounds,
    onConflict,
    onDisallowed,
)


// Actual private implementations

private class LayerImpl<EType : EntityType>(layer: Layer<EType>) : Layer<EType> by layer

private class MutableLayerImpl<EType : EntityType> private constructor(
    layout: Layout,
    behaviour: LayerBehaviour,
    private val rectMap: MutableRectMap<EntityId<EType>, Entity<EType>>,
) : MutableLayer<EType>, MutableRectMap<EntityId<EType>, Entity<EType>> by rectMap {

    override val rect = layout.size

    override val layoutRules = layout

    override val behaviour = behaviour.toMutableLayerBehaviour()

    constructor(layout: Layout, behaviour: LayerBehaviour) :
        this(layout, behaviour, mutableRectMapOf(layout.size))


    // Modification operations with additional `Layer`-specific checks

    override fun put(key: Coordinate, value: Entity<EType>) = withChecks(key, value, onFail = { null }) {
        rectMap.put(key, value)
    }

    override fun putAll(from: Map<out Coordinate, Entity<EType>>) {
        for ((key, value) in from) {
            put(key, value)
        }
    }


    // Utilities

    private inline fun <T> withChecks(
        key: Coordinate, value: Entity<EType>,
        onFail: () -> T,
        onSuccess: () -> T,
    ): T {
        val coordinates = value.size.coordinatesFrom(key)

        return if (
            value.id.type in layoutRules.disallowedTypes ||
            coordinates.mapNotNull(layoutRules.disallowedTypesMap::get).any { value.id.type in it } ||
            coordinates.any(layoutRules.disallowedCoordinates::contains)
        ) {
            when (behaviour.onDisallowed) {
                OnDisallowed.SKIP -> onFail()
            }
        } else {
            onSuccess()
        }
    }
}


private data class MutableLayerBehaviourImpl(
    override var onOutOfBounds: OnOutOfBounds,
    override var onConflict: OnConflict,
    override var onDisallowed: OnDisallowed,
) : MutableLayerBehaviour