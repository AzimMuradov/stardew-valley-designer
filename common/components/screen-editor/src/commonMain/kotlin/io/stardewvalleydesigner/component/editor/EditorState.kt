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

package io.stardewvalleydesigner.component.editor

import io.stardewvalleydesigner.component.editor.modules.history.HistoryState
import io.stardewvalleydesigner.component.editor.modules.map.MapState
import io.stardewvalleydesigner.component.editor.modules.palette.PaletteState
import io.stardewvalleydesigner.component.editor.modules.toolkit.ToolkitState
import io.stardewvalleydesigner.component.editor.modules.vislayers.VisLayersState
import io.stardewvalleydesigner.designformat.models.Design
import io.stardewvalleydesigner.designformat.models.Options
import io.stardewvalleydesigner.engine.layout.Layout


data class EditorState(
    val history: HistoryState,
    val map: MapState,
    val playerName: String,
    val farmName: String,
    val toolkit: ToolkitState,
    val palette: PaletteState,
    val visLayers: VisLayersState,
    val options: Options,
    val designPath: String?,
) {

    companion object {

        fun default(layout: Layout) = EditorState(
            history = HistoryState.default(),
            map = MapState.default(layout),
            playerName = "",
            farmName = "",
            toolkit = ToolkitState.default(),
            palette = PaletteState.default(),
            visLayers = VisLayersState.default(),
            options = Options.default(),
            designPath = null,
        )

        fun from(design: Design, designPath: String?) = EditorState(
            history = HistoryState.default(),
            map = MapState.from(design),
            playerName = design.playerName,
            farmName = design.farmName,
            toolkit = ToolkitState.default(),
            palette = PaletteState.default(),
            visLayers = VisLayersState.default(),
            options = design.options,
            designPath = designPath,
        )
    }
}
