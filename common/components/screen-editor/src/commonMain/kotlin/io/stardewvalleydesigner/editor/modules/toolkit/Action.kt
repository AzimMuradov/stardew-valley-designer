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

package io.stardewvalleydesigner.editor.modules.toolkit

import io.stardewvalleydesigner.editor.EditorIntent
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.engine.layer.LayerType
import io.stardewvalleydesigner.engine.layers.LayeredEntitiesData


typealias Action = EditorIntent.Engine

data class ActionReturn(
    val toolkit: ToolkitState,
    val currentEntity: Entity<*>?,
    val selectedEntities: LayeredEntitiesData,
)

fun Toolkit.runAction(
    action: Action,
    currentEntity: Entity<*>?,
    selectedEntities: LayeredEntitiesData,
    visLayers: Set<LayerType<*>>,
): ActionReturn? = when (action) {
    is EditorIntent.Engine.Start -> start(
        action.coordinate,
        currentEntity,
        selectedEntities,
        visLayers,
    )

    is EditorIntent.Engine.Keep -> keep(
        action.coordinate,
        currentEntity,
        selectedEntities,
        visLayers,
    )

    is EditorIntent.Engine.End -> end(
        currentEntity,
        selectedEntities,
        visLayers,
    )
}
