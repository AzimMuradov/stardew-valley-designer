/*
 * Copyright 2021-2024 Azim Muradov
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

package io.stardewvalleydesigner.engine

import io.stardewvalleydesigner.LoggerUtils.logger
import io.stardewvalleydesigner.engine.entity.EntityType
import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.layer.*
import io.stardewvalleydesigner.engine.layers.*
import io.stardewvalleydesigner.engine.layout.Layout
import io.stardewvalleydesigner.engine.layout.respects


fun editorEngineOf(layout: Layout): EditorEngine = EditorEngineImpl(layout)


// Actual private implementation

private class EditorEngineImpl(override val layout: Layout) : EditorEngine {

    private val layers = Layers()


    override fun getEntities(): LayeredEntitiesData = layeredEntitiesData {
        layers.layerBy(it).placedEntities
    }

    override var wallpaper: Wallpaper? = null

    override var flooring: Flooring? = null


    // Operations

    override fun <T : EntityType> get(layer: LayerType<T>, c: Coordinate) = layers.layerBy(layer)[c]

    override fun put(entity: PlacedEntity<*>): LayeredEntitiesData {
        if (!(entity respects layout)) {
            logger.warn { "The object ($entity) failed to satisfy layout rules." }
            return LayeredEntitiesData()
        }

        val objLayer = entity.layerType

        val replaced = buildList {
            addAll(objLayer.incompatibleLayers.flatMap { removeAll(it, entity.coordinates) })
            addAll(layers.layerBy(objLayer).put(entity))
        }.layeredData()

        return replaced
    }

    override fun <T : EntityType> remove(layer: LayerType<T>, c: Coordinate) = layers.layerBy(layer).remove(c)


    // Bulk Operations

    override fun <T : EntityType> getAll(layer: LayerType<T>, cs: Iterable<Coordinate>) =
        layers.layerBy(layer).getAll(cs)

    override fun <T : EntityType> putAll(entities: DisjointEntities<T>) =
        entities.asSequence().flatMap { put(it).flattenSequence() }.layeredData()

    override fun <T : EntityType> removeAll(layer: LayerType<T>, cs: Iterable<Coordinate>) =
        layers.layerBy(layer).removeAll(cs)

    override fun clear(type: LayerType<*>) = layers.layerBy(type).clear()
}
