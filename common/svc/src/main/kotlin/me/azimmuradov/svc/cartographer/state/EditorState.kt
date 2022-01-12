/*
 * Copyright 2021-2022 Azim Muradov
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

package me.azimmuradov.svc.cartographer.state

import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layers.LayeredEntitiesData

data class EditorState(
    val entities: LayeredEntitiesData,
    val layout: LayoutState,
    val visibleLayers: Set<LayerType<*>>,
) {

    companion object {

        fun default(layout: LayoutState) = EditorState(
            entities = LayeredEntitiesData(),
            layout = layout,
            visibleLayers = LayerType.all.toSet()
        )
    }
}