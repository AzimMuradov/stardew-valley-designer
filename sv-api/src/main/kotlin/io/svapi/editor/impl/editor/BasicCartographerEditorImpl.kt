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

package io.svapi.editor.impl.editor

import io.svapi.editor.Coordinate
import io.svapi.editor.impl.editor.CartographerEditorBehaviour.Companion.rewriter
import io.svapi.editor.impl.editor.CartographerEditorBehaviour.OnConflict
import io.svapi.editor.impl.entity.*
import io.svapi.editor.impl.entity.FlooringAndGrassType.GrassType
import io.svapi.editor.impl.entity.ObjectLikeType.ObjectType.GrassHatingObjectType
import io.svapi.editor.impl.layer.CartographerLayer
import io.svapi.editor.impl.layer.MutableCartographerLayer
import io.svapi.editor.impl.layer.mutableLayerOf
import io.svapi.editor.impl.layout.CartographerLayout
import io.svapi.editor.set
import kotlin.properties.Delegates.observable


fun basicCartographerEditor(layout: CartographerLayout): BasicCartographerEditor = BasicCartographerEditorImpl(layout)


private class BasicCartographerEditorImpl(layout: CartographerLayout) : BasicCartographerEditor {

    override var behaviour: CartographerEditorBehaviour by observable(initialValue = rewriter) { _, old, new ->
        if (old != new) {
            updateLayersBehaviour()
        }
    }


    private val _flooringAndGrassLayer = mutableLayerOf(layout.rulesForFlooringAndGrassLayer)
    override val flooringAndGrassLayer: CartographerLayer<FlooringAndGrassType> = _flooringAndGrassLayer

    private val _objectLikeLayer = mutableLayerOf(layout.rulesForObjectLikeLayer)
    override val objectLikeLayer: CartographerLayer<ObjectLikeType> = _objectLikeLayer

    private val _cropsLayer = mutableLayerOf(layout.rulesForCropsLayer)
    override val cropsLayer: CartographerLayer<CropsType> = _cropsLayer

    private val _bigEntitiesLayer = mutableLayerOf(layout.rulesForBigEntitiesLayer)
    override val bigEntitiesLayer: CartographerLayer<BigEntityType> = _bigEntitiesLayer


    private val layers: Sequence<MutableCartographerLayer<*>> = sequenceOf(
        _flooringAndGrassLayer,
        _objectLikeLayer,
        _cropsLayer,
        _bigEntitiesLayer,
    )

    init {
        updateLayersBehaviour()
    }


    override fun get(type: CartographerEntityType, key: Coordinate): CartographerEntity<*>? =
        when (type) {
            is FlooringAndGrassType -> _flooringAndGrassLayer[key]
            is ObjectLikeType -> _objectLikeLayer[key]
            is CropsType -> _cropsLayer[key]
            is BigEntityType -> _bigEntitiesLayer[key]
        }

    override fun get(key: Coordinate): CartographerEntity<*>? =
        layers.firstNotNullOfOrNull { it[key] }

    override fun put(key: Coordinate, value: CartographerEntity<*>) = withChecks(key, value, onFail = {}) {
        when (value.id.type) {
            is FlooringAndGrassType -> {
                _flooringAndGrassLayer[key] = value as CartographerEntity<FlooringAndGrassType>
            }
            is ObjectLikeType -> {
                _objectLikeLayer[key] = value as CartographerEntity<ObjectLikeType>
            }
            is CropsType -> {
                _cropsLayer[key] = value as CartographerEntity<CropsType>
            }
            is BigEntityType -> {
                _bigEntitiesLayer[key] = value as CartographerEntity<BigEntityType>
            }
        }
    }

    override fun remove(type: CartographerEntityType, key: Coordinate) {
        when (type) {
            is FlooringAndGrassType -> _flooringAndGrassLayer.remove(key)
            is ObjectLikeType -> _objectLikeLayer.remove(key)
            is CropsType -> _cropsLayer.remove(key)
            is BigEntityType -> _bigEntitiesLayer.remove(key)
        }
    }

    override fun remove(key: Coordinate) {
        for (layer in layers) {
            layer.remove(key)
        }
    }

    override fun putAll(from: Map<Coordinate, CartographerEntity<*>>) {
        for ((k, v) in from) {
            put(k, v)
        }
    }

    override fun removeAll(type: CartographerEntityType, keys: Iterable<Coordinate>) {
        when (type) {
            is FlooringAndGrassType -> _flooringAndGrassLayer.removeAll(keys)
            is ObjectLikeType -> _objectLikeLayer.removeAll(keys)
            is CropsType -> _cropsLayer.removeAll(keys)
            is BigEntityType -> _bigEntitiesLayer.removeAll(keys)
        }
    }

    override fun removeAll(keys: Iterable<Coordinate>) {
        for (layer in layers) {
            layer.removeAll(keys)
        }
    }


    override fun clear(type: CartographerEntityType) {
        when (type) {
            is FlooringAndGrassType -> _flooringAndGrassLayer.clear()
            is ObjectLikeType -> _objectLikeLayer.clear()
            is CropsType -> _cropsLayer.clear()
            is BigEntityType -> _bigEntitiesLayer.clear()
        }
    }

    override fun clear() {
        for (layer in layers) {
            layer.clear()
        }
    }


    // Utilities

    private fun updateLayersBehaviour() {
        for (layer in layers) {
            layer.behaviour = layer.behaviour.copy(
                onInternalConflict = behaviour.onConflict.toOnInternalConflict(),
            )
        }
    }

    private inline fun <T> withChecks(
        key: Coordinate, value: CartographerEntity<*>,
        onFail: () -> T,
        onSuccess: () -> T,
    ): T {
        val type = value.id.type

        when (behaviour.onConflict) {
            OnConflict.SKIP -> {
                if (
                    if (type is ObjectLikeType) {
                        type == GrassHatingObjectType && _flooringAndGrassLayer[key]?.id?.type == GrassType
                    } else {
                        key in _flooringAndGrassLayer.keys
                    }
                ) return onFail()
                if (
                    if (type is FlooringAndGrassType) {
                        type == GrassType && _objectLikeLayer[key]?.id?.type == GrassHatingObjectType
                    } else {
                        key in _objectLikeLayer.keys
                    }
                ) return onFail()
                if (key in _cropsLayer.keys) return onFail()
                if (key in _bigEntitiesLayer.keys) return onFail()
            }
            OnConflict.OVERWRITE -> {
                if (
                    if (type is ObjectLikeType) {
                        type == GrassHatingObjectType && _flooringAndGrassLayer[key]?.id?.type == GrassType
                    } else {
                        true
                    }
                ) _flooringAndGrassLayer.remove(key)
                if (
                    if (type is FlooringAndGrassType) {
                        type == GrassType && _objectLikeLayer[key]?.id?.type == GrassHatingObjectType
                    } else {
                        true
                    }
                ) _objectLikeLayer.remove(key)
                _cropsLayer.remove(key)
                _bigEntitiesLayer.remove(key)
            }
        }

        return onSuccess()
    }
}