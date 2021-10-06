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

package me.azimmuradov.svc.cartographer

import me.azimmuradov.svc.cartographer.history.HistoryTraveler
import me.azimmuradov.svc.cartographer.palette.Palette
import me.azimmuradov.svc.cartographer.toolkit.Tool
import me.azimmuradov.svc.cartographer.toolkit.ToolType
import me.azimmuradov.svc.engine.entity.Entity
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layers.LayeredEntities
import me.azimmuradov.svc.engine.layout.Layout


// TODO : chosen entities
// TODO : SVC behaviour


/**
 * Stardew Valley Cartographer.
 *
 * Cartographer is defined by its [layout].
 */
interface Svc {

    // Views

    /**
     * SVC layout.
     */
    val layout: Layout

    /**
     * SVC entities.
     */
    val entities: LayeredEntities

    /**
     * Entities that are currently being held.
     */
    val heldEntities: LayeredEntities

    /**
     * SVC palette.
     */
    val palette: Palette

    val visibleLayers: Set<LayerType<*>>


    // Functions

    // History

    val history: HistoryTraveler

    // Toolkit

    fun chooseToolOf(type: ToolType?)

    val tool: Tool?

    // Palette & Flavors

    fun useInPalette(entity: Entity<*>)

    fun clearUsedInPalette()

    // Visibility

    fun changeVisibilityBy(layerType: LayerType<*>, value: Boolean)
}