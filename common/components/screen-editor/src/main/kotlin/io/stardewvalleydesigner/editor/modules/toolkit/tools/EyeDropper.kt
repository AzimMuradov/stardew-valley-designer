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

package io.stardewvalleydesigner.editor.modules.toolkit.tools

import io.stardewvalleydesigner.editor.modules.palette.PaletteState
import io.stardewvalleydesigner.editor.modules.toolkit.*
import io.stardewvalleydesigner.engine.EditorEngine
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.get
import io.stardewvalleydesigner.engine.layer.LayerType
import io.stardewvalleydesigner.engine.layers.LayeredEntitiesData
import io.stardewvalleydesigner.engine.layers.topmost


class EyeDropper(private val engine: EditorEngine) : Tool {

    override fun start(
        coordinate: Coordinate,
        currentEntity: Entity<*>?,
        selectedEntities: LayeredEntitiesData,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn = ActionReturn(
        toolkit = ToolkitState.EyeDropper.Point.Acting,
        palette = PaletteState.default().copy(inUse = engine.get(coordinate, visLayers).topmost()?.rectObject),
        selectedEntities = selectedEntities
    )

    override fun keep(
        coordinate: Coordinate,
        currentEntity: Entity<*>?,
        selectedEntities: LayeredEntitiesData,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn = ActionReturn(
        toolkit = ToolkitState.EyeDropper.Point.Acting,
        palette = PaletteState.default().copy(inUse = engine.get(coordinate, visLayers).topmost()?.rectObject),
        selectedEntities = selectedEntities
    )

    override fun end(
        currentEntity: Entity<*>?,
        selectedEntities: LayeredEntitiesData,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn = ActionReturn(
        toolkit = ToolkitState.EyeDropper.Point.Idle,
        palette = PaletteState.default().copy(inUse = currentEntity),
        selectedEntities = selectedEntities
    )


    override fun dispose() = Unit
}