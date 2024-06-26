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

package io.stardewvalleydesigner.component.editor.modules.toolkit

import io.stardewvalleydesigner.component.editor.modules.toolkit.tools.*
import io.stardewvalleydesigner.engine.EditorEngine
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.layer.LayerType
import io.stardewvalleydesigner.engine.layer.PlacedEntity


class ToolkitImpl(
    private val engine: EditorEngine,
    initialState: ToolkitState,
) : Toolkit {

    private var tool: Tool? = null

    override var toolType: ToolType = initialState.tool

    override var shapeType: ShapeType? = initialState.shape


    override fun setTool(toolType: ToolType, shapeType: ShapeType?) {
        this.toolType = toolType
        this.shapeType = shapeType
    }


    override fun start(
        coordinate: Coordinate,
        currentEntity: Entity<*>?,
        selectedEntities: List<PlacedEntity<*>>,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn? {
        val shapeType = shapeType
        val tool = when (toolType) {
            ToolType.Drag -> if (shapeType == null) Drag(engine) else undefinedTool()
            ToolType.Pen -> if (shapeType == null) PenPoint(engine) else PenShape(engine, shapeType)
            ToolType.Eraser -> if (shapeType == null) EraserPoint(engine) else EraserShape(engine, shapeType)
            ToolType.Select -> if (shapeType == null) undefinedTool() else Select(engine, shapeType)
            ToolType.EyeDropper -> if (shapeType == null) EyeDropper(engine) else undefinedTool()
            ToolType.Hand -> if (shapeType == null) Hand() else undefinedTool()
        }
        val result = tool.start(coordinate, currentEntity, selectedEntities, visLayers)

        if (result != null) {
            this.tool = tool
        } else {
            tool.dispose()
        }

        return result
    }

    override fun keep(
        coordinate: Coordinate,
        currentEntity: Entity<*>?,
        selectedEntities: List<PlacedEntity<*>>,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn? {
        val tool = this.tool
        val result = tool?.keep(coordinate, currentEntity, selectedEntities, visLayers)

        if (tool != null && result == null) {
            tool.dispose()
            this.tool = null
        }

        return result
    }

    override fun end(
        currentEntity: Entity<*>?,
        selectedEntities: List<PlacedEntity<*>>,
        visLayers: Set<LayerType<*>>,
    ): ActionReturn? {
        val result = tool?.end(currentEntity, selectedEntities, visLayers)

        tool?.run {
            dispose()
            tool = null
        }

        return result
    }


    private companion object {

        fun undefinedTool(): Nothing = error("Undefined tool")
    }
}
