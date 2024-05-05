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

package io.stardewvalleydesigner.component.editor.modules.map

import io.stardewvalleydesigner.component.editor.utils.toState
import io.stardewvalleydesigner.designformat.models.Design
import io.stardewvalleydesigner.engine.*
import io.stardewvalleydesigner.engine.layer.PlacedEntity
import io.stardewvalleydesigner.engine.layers.LayeredEntities
import io.stardewvalleydesigner.engine.layout.Layout
import io.stardewvalleydesigner.engine.layout.LayoutsProvider


data class MapState(
    val entities: LayeredEntities,
    val selectedEntities: List<PlacedEntity<*>>,
    val wallpaper: Wallpaper?,
    val flooring: Flooring?,
    val layout: LayoutState,
) {

    companion object {

        fun default(layout: Layout) = MapState(
            entities = LayeredEntities(),
            selectedEntities = emptyList(),
            wallpaper = null,
            flooring = null,
            layout = layout.toState(),
        )

        fun from(design: Design) = MapState(
            entities = design.entities,
            selectedEntities = emptyList(),
            wallpaper = design.wallpaper,
            flooring = design.flooring,
            layout = LayoutsProvider.layoutOf(design.layout).toState(),
        )
    }
}


fun MapState.generateEngine(): EditorEngine = editorEngineOf(
    layout = LayoutsProvider.layoutOf(layout.type),
).apply {
    putAll(this@generateEngine.entities)
    wallpaper = this@generateEngine.wallpaper
    flooring = this@generateEngine.flooring
}
