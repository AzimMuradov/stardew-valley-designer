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

package me.azimmuradov.svc.engine.llsvc.layer

import me.azimmuradov.svc.engine.Coordinate
import me.azimmuradov.svc.engine.Rect
import me.azimmuradov.svc.engine.llsvc.coordinatesFrom
import me.azimmuradov.svc.engine.llsvc.entity.SvcEntity
import me.azimmuradov.svc.engine.llsvc.entity.SvcEntityType
import me.azimmuradov.svc.engine.llsvc.layer.SvcLayerBehaviour.OnDisallowed
import me.azimmuradov.svc.engine.rectmap.MutableRectMap
import me.azimmuradov.svc.engine.rectmap.RectMapBehaviour.OnConflict
import me.azimmuradov.svc.engine.rectmap.RectMapBehaviour.OnOutOfBounds


fun <EType : SvcEntityType> mutableSvcLayerOf(
    size: Rect,
    layoutRules: SvcLayoutRules,
    behaviour: SvcLayerBehaviour = DefaultSvcLayerBehaviour.rewriter,
): MutableSvcLayer<EType> = MutableSvcLayerImpl(size, layoutRules, behaviour)


object DefaultSvcLayerBehaviour {

    val skipper: SvcLayerBehaviour = MutableSvcLayerBehaviourImpl(
        onOutOfBounds = OnOutOfBounds.SKIP,
        onConflict = OnConflict.SKIP,
        onDisallowed = OnDisallowed.SKIP,
    )

    val rewriter: SvcLayerBehaviour = MutableSvcLayerBehaviourImpl(
        onOutOfBounds = OnOutOfBounds.SKIP,
        onConflict = OnConflict.OVERWRITE,
        onDisallowed = OnDisallowed.SKIP,
    )
}

fun SvcLayerBehaviour.toMutableSvcLayerBehaviour(): MutableSvcLayerBehaviour = MutableSvcLayerBehaviourImpl(
    onOutOfBounds,
    onConflict,
    onDisallowed,
)


// Actual private implementations

private class SvcLayerImpl<EType : SvcEntityType>(svcLayer: SvcLayer<EType>) : SvcLayer<EType> by svcLayer

private class MutableSvcLayerImpl<EType : SvcEntityType> private constructor(
    override val rect: Rect,
    override val layoutRules: SvcLayoutRules,
    behaviour: SvcLayerBehaviour,
    private val rectMap: MutableRectMap<Any?, SvcEntity<EType>>,
) : MutableSvcLayer<EType>, MutableRectMap<Any?, SvcEntity<EType>> by rectMap {

    override val behaviour: MutableSvcLayerBehaviour = behaviour.toMutableSvcLayerBehaviour()

    constructor(rect: Rect, layoutRules: SvcLayoutRules, behaviour: SvcLayerBehaviour) :
        this(rect, layoutRules, behaviour, mutableRectMapOf(rect))


    // Modification operations with additional `SvcLayer`-specific checks

    override fun put(key: Coordinate, value: SvcEntity<EType>) = withChecks(key, value, onFail = { null }) {
        rectMap.put(key, value)
    }

    override fun putAll(from: Map<out Coordinate, SvcEntity<EType>>) {
        for ((key, value) in from) {
            put(key, value)
        }
    }


    // Utilities

    private inline fun <T> withChecks(
        key: Coordinate, value: SvcEntity<EType>,
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


private data class MutableSvcLayerBehaviourImpl(
    override var onOutOfBounds: OnOutOfBounds,
    override var onConflict: OnConflict,
    override var onDisallowed: OnDisallowed,
) : MutableSvcLayerBehaviour