/*
 * Copyright 2021-2023 Azim Muradov
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

package io.stardewvalleydesigner.editor.modules.map

import io.stardewvalleydesigner.editor.utils.toState
import io.stardewvalleydesigner.engine.*
import io.stardewvalleydesigner.engine.layers.LayeredEntitiesData
import io.stardewvalleydesigner.engine.layers.entities
import io.stardewvalleydesigner.engine.layout.Layout


data class MapState(
    val entities: LayeredEntitiesData,
    val selectedEntities: LayeredEntitiesData,
    val wallpaper: Wallpaper?,
    val flooring: Flooring?,
    val layout: LayoutState,
) {

    companion object {

        fun default(layout: Layout) = MapState(
            entities = LayeredEntitiesData(),
            selectedEntities = LayeredEntitiesData(),
            wallpaper = null,
            flooring = null,
            layout = layout.toState(),
        )

        fun from(engine: EditorEngine) = MapState(
            entities = engine.layers.entities,
            selectedEntities = LayeredEntitiesData(),
            wallpaper = engine.wallpaper,
            flooring = engine.flooring,
            layout = engine.layers.layout.toState(),
        )
    }
}
