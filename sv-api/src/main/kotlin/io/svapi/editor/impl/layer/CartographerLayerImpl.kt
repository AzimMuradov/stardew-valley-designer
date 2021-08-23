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

package io.svapi.editor.impl.layer

import io.svapi.editor.Coordinate
import io.svapi.editor.Rect
import io.svapi.editor.RectMap
import io.svapi.editor.impl.contains
import io.svapi.editor.impl.entity.CartographerEntity
import io.svapi.editor.impl.entity.CartographerEntityHolder
import io.svapi.editor.impl.entity.CartographerEntityType
import io.svapi.editor.impl.generateCoordinates
import io.svapi.editor.impl.layer.CartographerLayerBehaviour.*
import io.svapi.editor.impl.removeAll
import io.svapi.editor.xy


fun <EType : CartographerEntityType> mutableLayerOf(
    disallowedEntityTypes: RectMap<Set<EType>?>,
): MutableCartographerLayer<EType> = MutableCartographerLayerImpl(disallowedEntityTypes)


private class MutableCartographerLayerImpl<EType : CartographerEntityType>(
    override val disallowedEntityTypes: RectMap<Set<EType>?>,
) : MutableCartographerLayer<EType> {

    override val rect: Rect = disallowedEntityTypes.rect


    private val _map: MutableMap<Coordinate, CartographerEntity<EType>> = mutableMapOf()
    override val map: Map<Coordinate, CartographerEntity<EType>> = _map

    private val _renderedMap: MutableMap<Coordinate, CartographerEntityHolder<EType>> = mutableMapOf()
    override val renderedMap: Map<Coordinate, CartographerEntityHolder<EType>> = _renderedMap


    override var behaviour: CartographerLayerBehaviour = CartographerLayerBehaviour.skipper


    override fun get(key: Coordinate): CartographerEntity<EType>? = renderedMap[key]?.entity

    override fun set(key: Coordinate, value: CartographerEntity<EType>) {
        checkOutOfBounds(key, value)
        checkDisallowed(key, value)
        checkInternallyConflict(key, value)

        putUnsafe(key, value)
    }

    override fun remove(key: Coordinate) {
        renderedMap[key]?.let { eh ->
            _map.remove(eh.source)
            _renderedMap.removeAll(eh.coordinates)
        }
    }

    override fun setAll(from: Map<Coordinate, CartographerEntity<EType>>) {
        for ((k, v) in from) {
            checkOutOfBounds(k, v)
            checkDisallowed(k, v)
        }
        for ((k, v) in from) {
            checkInternallyConflict(k, v)
        }

        for ((k, v) in from) {
            putUnsafe(k, v)
        }
    }

    override fun removeAll(keys: Iterable<Coordinate>) {
        val setOfEh = keys.mapNotNull { renderedMap[it] }.toSet()
        for (eh in setOfEh) {
            _map.remove(eh.source)
            _renderedMap.removeAll(eh.coordinates)
        }
    }

    override fun clear() {
        _map.clear()
        _renderedMap.clear()
    }


    private fun putUnsafe(key: Coordinate, value: CartographerEntity<EType>) {
        _map[key] = value

        val eh = CartographerEntityHolder(entity = value, source = key)
        _renderedMap.putAll(eh.coordinates.associateWith { eh })
    }


    // Utilities for checking the invariant

    private fun checkOutOfBounds(key: Coordinate, value: CartographerEntity<EType>) {
        val (min, max) = run {
            val (x, y) = key
            val (w, h) = value.size

            key to xy(x + w, y + h)
        }

        if (!(min in rect && max in rect)) {
            if (behaviour.onOutOfBounds == OnOutOfBounds.THROW_EXCEPTION) {
                throw IllegalStateException()
            }
        }
    }

    private fun checkDisallowed(key: Coordinate, value: CartographerEntity<EType>) {
        fun isDisallowed(c: Coordinate): Boolean =
            disallowedEntityTypes[c]?.let { value.id.type in it } ?: true

        if (generateCoordinates(key, value.size).none(::isDisallowed)) {
            if (behaviour.onDisallowed == OnDisallowed.THROW_EXCEPTION) {
                throw IllegalStateException()
            }
        }
    }

    private fun checkInternallyConflict(key: Coordinate, value: CartographerEntity<EType>) {
        val conflictingEh = generateCoordinates(key, value.size).mapNotNull { renderedMap[it] }.toSet()

        if (conflictingEh.isNotEmpty()) {
            when (behaviour.onInternalConflict) {
                OnInternalConflict.THROW_EXCEPTION -> throw IllegalStateException()
                OnInternalConflict.OVERWRITE -> for (eh in conflictingEh) {
                    _map.remove(eh.source)
                    _renderedMap.removeAll(eh.coordinates)
                }
                OnInternalConflict.SKIP -> Unit
            }
        }
    }
}