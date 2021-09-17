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

import me.azimmuradov.svc.engine.contains
import me.azimmuradov.svc.engine.coordinatesFrom
import me.azimmuradov.svc.engine.rectmap.*
import me.azimmuradov.svc.engine.rectmap.RectMapBehaviour.OnConflict
import me.azimmuradov.svc.engine.rectmap.RectMapBehaviour.OnOutOfBounds


// TODO : Use a more efficient data structure


fun <T, RO : RectObject<T>> mutableRectMapOf(
    rect: Rect,
    behaviour: RectMapBehaviour = DefaultRectMapBehaviour.rewriter,
): MutableRectMap<T, RO> = MutableRectMapImpl(rect, behaviour)


object DefaultRectMapBehaviour {

    val skipper: RectMapBehaviour = MutableRectMapBehaviourImpl(
        onOutOfBounds = OnOutOfBounds.SKIP,
        onConflict = OnConflict.SKIP,
    )

    val rewriter: RectMapBehaviour = MutableRectMapBehaviourImpl(
        onOutOfBounds = OnOutOfBounds.SKIP,
        onConflict = OnConflict.OVERWRITE,
    )
}

fun RectMapBehaviour.toMutableRectMapBehaviour(): MutableRectMapBehaviour = MutableRectMapBehaviourImpl(
    onOutOfBounds,
    onConflict,
)


// Actual private implementations

private class RectMapImpl<out T, out RO : RectObject<T>>(rectMap: RectMap<T, RO>) : RectMap<T, RO> by rectMap

private class MutableRectMapImpl<T, RO : RectObject<T>>(
    override val rect: Rect,
    behaviour: RectMapBehaviour,
) : MutableRectMap<T, RO> {

    override val behaviour = behaviour.toMutableRectMapBehaviour()

    private val map = mutableMapOf<Coordinate, RO>()
    private val generatedMap = mutableMapOf<Coordinate, RectObjectHolder<T, RO>>()


    // Query Operations

    override operator fun get(key: Coordinate) = generatedMap[key]?.ro


    // Bulk Query Operations

    override fun getAll(keys: Iterable<Coordinate>) = keys
        .mapNotNullTo(mutableSetOf(), generatedMap::get)
        .map(RectObjectHolder<T, RO>::ro)


    // Views

    override val keys get() = map.keys

    override val values get() = map.values

    override val entries get() = map.entries


    // Modification Operations

    override fun put(key: Coordinate, value: RO) = withChecks(key, value, onFail = { null }) {
        putUnsafe(key, value)
    }

    override fun remove(key: Coordinate) = generatedMap[key]?.apply {
        map.remove(source)
        generatedMap.removeAll(coordinates)
    }?.ro


    // Bulk Modification Operations

    override fun putAll(from: Map<out Coordinate, RO>) {
        for ((key, value) in from) {
            put(key, value)
        }
    }

    override fun removeAll(keys: Iterable<Coordinate>) {
        val setOfRoh = keys.mapNotNull(generatedMap::get).toSet()
        for ((_, source, coordinates) in setOfRoh) {
            map.remove(source)
            generatedMap.removeAll(coordinates)
        }
    }

    override fun clear() {
        map.clear()
        generatedMap.clear()
    }


    // Utilities

    private fun putUnsafe(key: Coordinate, value: RO): RO? {
        val retValue = map.put(key, value)

        val roh = RectObjectHolder(ro = value, source = key)
        generatedMap.putAll(roh.coordinates.associateWith { roh })

        return retValue
    }

    private inline fun <E> withChecks(
        key: Coordinate, value: RO,
        onFail: () -> E,
        onSuccess: () -> E,
    ): E {
        val (min, max) = run {
            val (x, y) = key
            val (w, h) = value.size

            key to xy(x + w - 1, y + h - 1)
        }
        val coordinates = value.size.coordinatesFrom(key)


        // Check out of bounds

        if (!(min in rect && max in rect)) {
            return when (behaviour.onOutOfBounds) {
                OnOutOfBounds.SKIP -> onFail()
            }
        }

        // Check conflicts

        if (coordinates.any(generatedMap::contains)) {
            return when (behaviour.onConflict) {
                OnConflict.SKIP -> onFail()
                OnConflict.OVERWRITE -> {
                    removeAll(coordinates)
                    onSuccess()
                }
            }
        }

        return onSuccess()
    }
}


private data class MutableRectMapBehaviourImpl(
    override var onOutOfBounds: OnOutOfBounds,
    override var onConflict: OnConflict,
) : MutableRectMapBehaviour

private data class RectObjectHolder<out T, out RO : RectObject<T>>(
    val ro: RO,
    val source: Coordinate,
) {

    val coordinates: List<Coordinate> by lazy { ro.size.coordinatesFrom(source) }

    operator fun component3() = coordinates
}

private fun <K> MutableMap<K, *>.removeAll(keys: Iterable<K>) {
    for (k in keys) {
        remove(k)
    }
}