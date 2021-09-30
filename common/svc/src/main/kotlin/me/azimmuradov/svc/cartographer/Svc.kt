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

import me.azimmuradov.svc.cartographer.palette.MutablePalette
import me.azimmuradov.svc.cartographer.toolkit.Toolkit
import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.layer.Layer
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layout.Layout


// TODO : layers visibility
// TODO : chosen entities
// TODO : SVC behaviour
// TODO : Acts history


/**
 * Stardew Valley Cartographer.
 *
 * Cartographer is defined by its [layout].
 */
interface Svc {

    /**
     * SVC layout.
     */
    val layout: Layout


    // Views

    /**
     * SVC layers.
     */
    val layers: Map<LayerType<*>, Layer<*>>

    /**
     * Entities that are currently being held.
     */
    val heldEntities: List<PlacedEntity<*>>


    /**
     * SVC palette.
     */
    val palette: MutablePalette

    /**
     * SVC toolkit, using it, you can choose tool or use the current tool.
     */
    val toolkit: Toolkit
}