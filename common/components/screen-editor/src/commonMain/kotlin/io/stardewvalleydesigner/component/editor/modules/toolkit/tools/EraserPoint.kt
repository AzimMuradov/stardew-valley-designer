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

package io.stardewvalleydesigner.component.editor.modules.toolkit.tools

import io.stardewvalleydesigner.component.editor.modules.toolkit.*
import io.stardewvalleydesigner.engine.EditorEngine
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.layer.LayerType
import io.stardewvalleydesigner.engine.layers.LayeredEntitiesData
import io.stardewvalleydesigner.engine.remove


class EraserPoint(private val engine: EditorEngine) : Tool {

    override fun start(
        coordinate: Coordinate,
        currentEntity: Entity<*>?,
        selectedEntities: LayeredEntitiesData,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn {
        engine.remove(coordinate, visLayers)
        return ActionReturn(
            toolkit = ToolkitState.Eraser.Point.Acting,
            currentEntity = currentEntity,
            selectedEntities = selectedEntities
        )
    }

    override fun keep(
        coordinate: Coordinate,
        currentEntity: Entity<*>?,
        selectedEntities: LayeredEntitiesData,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn {
        engine.remove(coordinate, visLayers)
        return ActionReturn(
            toolkit = ToolkitState.Eraser.Point.Acting,
            currentEntity = currentEntity,
            selectedEntities = selectedEntities
        )
    }

    override fun end(
        currentEntity: Entity<*>?,
        selectedEntities: LayeredEntitiesData,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn {
        return ActionReturn(
            toolkit = ToolkitState.Eraser.Point.Idle,
            currentEntity = currentEntity,
            selectedEntities = selectedEntities
        )
    }


    override fun dispose() = Unit
}
