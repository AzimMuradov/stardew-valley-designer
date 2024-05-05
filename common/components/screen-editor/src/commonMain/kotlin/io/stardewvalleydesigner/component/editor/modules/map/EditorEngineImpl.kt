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
import io.stardewvalleydesigner.engine.*
import io.stardewvalleydesigner.engine.layer.PlacedEntity


internal class EditorEngineImpl(engine: EditorEngine) : EditorEngine by engine {

    fun pushState(state: MapState) {
        clear()
        putAll(state.entities)
        wallpaper = state.wallpaper
        flooring = state.flooring
    }

    fun pullState(selectedEntities: List<PlacedEntity<*>>? = null) = MapState(
        entities = getEntities(),
        selectedEntities = selectedEntities ?: emptyList(),
        wallpaper = wallpaper,
        flooring = flooring,
        layout = layout.toState()
    )
}
