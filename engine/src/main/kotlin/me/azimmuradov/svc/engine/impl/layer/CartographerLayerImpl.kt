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

package me.azimmuradov.svc.engine.impl.layer

import me.azimmuradov.svc.engine.Coordinate
import me.azimmuradov.svc.engine.Rect
import me.azimmuradov.svc.engine.RectMap
import me.azimmuradov.svc.engine.impl.contains
import me.azimmuradov.svc.engine.impl.entity.CartographerEntity
import me.azimmuradov.svc.engine.impl.entity.CartographerEntityHolder
import me.azimmuradov.svc.engine.impl.entity.CartographerEntityType
import me.azimmuradov.svc.engine.impl.generateCoordinates
import me.azimmuradov.svc.engine.impl.layer.CartographerLayerBehaviour.*
import me.azimmuradov.svc.engine.impl.removeAll
import me.azimmuradov.svc.engine.xy


fun <EType : CartographerEntityType> mutableLayerOf(
    disallowedEntityTypes: RectMap<Set<EType>?>,
): MutableCartographerLayer<EType> = MutableCartographerLayerImpl(disallowedEntityTypes)


private class MutableCartographerLayerImpl<EType : CartographerEntityType>(
    override val disallowedEntityTypes: RectMap<Set<EType>?>,
) : MutableCartographerLayer<EType> {

    override val rect: Rect = disallowedEntityTypes.rect

    override var behaviour: CartographerLayerBehaviour = CartographerLayerBehaviour.rewriter


    private val map: MutableMap<Coordinate, CartographerEntity<EType>> = mutableMapOf()
    private val generatedMap: MutableMap<Coordinate, CartographerEntityHolder<EType>> = mutableMapOf()


    override fun get(key: Coordinate): CartographerEntity<EType>? = generatedMap[key]?.entity

    override val keys: Set<Coordinate> get() = map.keys
    override val values: Collection<CartographerEntity<EType>> get() = map.values
    override val entries: Set<Map.Entry<Coordinate, CartographerEntity<EType>>> get() = map.entries

    override fun put(key: Coordinate, value: CartographerEntity<EType>): CartographerEntity<EType>? =
        withChecks(key, value, onFail = { null }) {
            putUnsafe(key, value)
        }

    override fun remove(key: Coordinate): CartographerEntity<EType>? =
        generatedMap[key]?.apply {
            map.remove(source)
            generatedMap.removeAll(coordinates)
        }?.entity

    override fun putAll(from: Map<out Coordinate, CartographerEntity<EType>>) {
        for ((key, value) in from) {
            withChecks(key, value, onFail = {}) {
                putUnsafe(key, value)
            }
        }
    }

    override fun removeAll(keys: Iterable<Coordinate>) {
        val setOfEh = keys.mapNotNull(generatedMap::get).toSet()
        for ((_, source, coordinates) in setOfEh) {
            map.remove(source)
            generatedMap.removeAll(coordinates)
        }
    }

    override fun clear() {
        map.clear()
        generatedMap.clear()
    }


    // Utilities

    private fun putUnsafe(key: Coordinate, value: CartographerEntity<EType>): CartographerEntity<EType>? {
        val retValue = map.put(key, value)

        val eh = CartographerEntityHolder(entity = value, source = key)
        generatedMap.putAll(eh.coordinates.associateWith { eh })

        return retValue
    }

    private inline fun <T> withChecks(
        key: Coordinate, value: CartographerEntity<EType>,
        onFail: () -> T,
        onSuccess: () -> T,
    ): T {
        val (min, max) = run {
            val (x, y) = key
            val (w, h) = value.id.size

            key to xy(x + w, y + h)
        }
        val coordinates = generateCoordinates(key, value.id.size)


        // Check out of bounds

        if (!(min in rect && max in rect)) {
            return when (behaviour.onOutOfBounds) {
                OnOutOfBounds.SKIP -> onFail()
            }
        }

        // Check disallowed

        if (coordinates.none { c -> disallowedEntityTypes[c]?.let { value.id.type in it } != false }) {
            return when (behaviour.onDisallowed) {
                OnDisallowed.SKIP -> onFail()
            }
        }

        // Check internal conflicts

        if (coordinates.any(generatedMap::contains)) {
            return when (behaviour.onInternalConflict) {
                OnInternalConflict.SKIP -> onFail()
                OnInternalConflict.OVERWRITE -> {
                    removeAll(coordinates)
                    onSuccess()
                }
            }
        }


        return onSuccess()
    }
}