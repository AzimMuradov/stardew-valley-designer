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

package me.azimmuradov.svc.engine.impl.editor

import me.azimmuradov.svc.engine.Coordinate
import me.azimmuradov.svc.engine.impl.editor.CartographerEditorBehaviour.Companion.rewriter
import me.azimmuradov.svc.engine.impl.editor.CartographerEditorBehaviour.OnConflict
import me.azimmuradov.svc.engine.impl.entity.*
import me.azimmuradov.svc.engine.impl.generateCoordinates
import me.azimmuradov.svc.engine.impl.layer.CartographerLayer
import me.azimmuradov.svc.engine.impl.layer.MutableCartographerLayer
import me.azimmuradov.svc.engine.impl.layer.mutableLayerOf
import me.azimmuradov.svc.engine.impl.layout.CartographerLayout
import me.azimmuradov.svc.engine.set
import kotlin.properties.Delegates.observable


fun basicCartographerEditor(layout: CartographerLayout): BasicCartographerEditor = BasicCartographerEditorImpl(layout)


private class BasicCartographerEditorImpl(layout: CartographerLayout) : BasicCartographerEditor {

    override var behaviour: CartographerEditorBehaviour by observable(initialValue = rewriter) { _, old, new ->
        if (old != new) {
            updateLayersBehaviour()
        }
    }


    private val _flooringLayer = mutableLayerOf(layout.rulesForFlooringLayer)
    override val flooringLayer: CartographerLayer<FloorType> = _flooringLayer

    private val _floorFurnitureLayer = mutableLayerOf(layout.rulesForFloorFurnitureLayer)
    override val floorFurnitureLayer: CartographerLayer<FloorFurnitureType> = _floorFurnitureLayer

    private val _objectsLayer = mutableLayerOf(layout.rulesForObjectsLayer)
    override val objectsLayer: CartographerLayer<ObjectType> = _objectsLayer

    private val _standaloneEntitiesLayer = mutableLayerOf(layout.rulesForBigEntitiesLayer)
    override val standaloneEntitiesLayer: CartographerLayer<EntityWithoutFloorType> = _standaloneEntitiesLayer


    private val layers: Sequence<MutableCartographerLayer<*>> = sequenceOf(
        _flooringLayer,
        _floorFurnitureLayer,
        _objectsLayer,
        _standaloneEntitiesLayer,
    )

    init {
        updateLayersBehaviour()
    }


    override fun get(type: CartographerEntityType, key: Coordinate): CartographerEntity<*>? =
        when (type) {
            FloorType -> _flooringLayer[key]
            FloorFurnitureType -> _floorFurnitureLayer[key]
            is ObjectType -> _objectsLayer[key]
            is EntityWithoutFloorType -> _standaloneEntitiesLayer[key]
        }

    override fun get(key: Coordinate): CartographerEntity<*>? =
        layers.firstNotNullOfOrNull { it[key] }

    override fun put(key: Coordinate, value: CartographerEntity<*>) = withChecks(key, value, onFail = {}) {
        when (value.id.type) {
            FloorType -> _flooringLayer[key] = value as CartographerEntity<FloorType>
            FloorFurnitureType -> _floorFurnitureLayer[key] = value as CartographerEntity<FloorFurnitureType>
            is ObjectType -> _objectsLayer[key] = value as CartographerEntity<ObjectType>
            is EntityWithoutFloorType -> _standaloneEntitiesLayer[key] =
                value as CartographerEntity<EntityWithoutFloorType>
        }
    }

    override fun remove(type: CartographerEntityType, key: Coordinate) {
        when (type) {
            FloorType -> _flooringLayer.remove(key)
            FloorFurnitureType -> _floorFurnitureLayer.remove(key)
            is ObjectType -> _objectsLayer.remove(key)
            is EntityWithoutFloorType -> _standaloneEntitiesLayer.remove(key)
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
            FloorType -> _flooringLayer.removeAll(keys)
            FloorFurnitureType -> _floorFurnitureLayer.removeAll(keys)
            is ObjectType -> _objectsLayer.removeAll(keys)
            is EntityWithoutFloorType -> _standaloneEntitiesLayer.removeAll(keys)
        }
    }

    override fun removeAll(keys: Iterable<Coordinate>) {
        for (layer in layers) {
            layer.removeAll(keys)
        }
    }


    override fun clear(type: CartographerEntityType) {
        when (type) {
            FloorType -> _flooringLayer.clear()
            FloorFurnitureType -> _floorFurnitureLayer.clear()
            is ObjectType -> _objectsLayer.clear()
            is EntityWithoutFloorType -> _standaloneEntitiesLayer.clear()
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
        val coordinates = generateCoordinates(key, value.id.size)
        val type = value.id.type

        val checks = listOf(
            _flooringLayer to (type == FloorType || type is EntityWithoutFloorType),
            _floorFurnitureLayer to (type == FloorFurnitureType || type is EntityWithoutFloorType),
            _objectsLayer to (type is ObjectType || type is EntityWithoutFloorType),
            _standaloneEntitiesLayer to true,
        ).map { (layer, mayConflict) ->
            layer to (mayConflict && coordinates.any(layer.keys::contains))
        }

        for ((layer, hasConflicts) in checks) {
            if (hasConflicts) {
                when (behaviour.onConflict) {
                    OnConflict.SKIP -> return onFail()
                    OnConflict.OVERWRITE -> layer.removeAll(coordinates)
                }
            }
        }

        return onSuccess()
    }
}