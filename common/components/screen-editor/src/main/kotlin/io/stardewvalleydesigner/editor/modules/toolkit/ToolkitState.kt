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

import io.stardewvalleydesigner.engine.entity.PlacedEntity
import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.geometry.shapes.PlacedShape
import io.stardewvalleydesigner.engine.layers.LayeredEntitiesData


sealed class ToolkitState(val tool: ToolType) {

    abstract val isIdle: Boolean

    val isActing: Boolean get() = !isIdle


    abstract val shape: ShapeType?

    val allowedShapes: List<ShapeType?> = tool.allowedShapes


    sealed class Hand : ToolkitState(tool = ToolType.Hand) {

        sealed class Point(final override val isIdle: Boolean) : Hand() {

            data object Idle : Point(isIdle = true)

            data class Acting(val heldEntities: LayeredEntitiesData) : Point(isIdle = false)

            final override val shape: ShapeType? = null
        }
    }

    sealed class Pen : ToolkitState(tool = ToolType.Pen) {

        sealed class Point(final override val isIdle: Boolean) : Pen() {

            data object Idle : Point(isIdle = true)

            data object Acting : Point(isIdle = false)

            final override val shape: ShapeType? = null
        }

        sealed class Shape(final override val isIdle: Boolean) : Pen() {

            data class Idle(override val shape: ShapeType) : Shape(isIdle = true)

            data class Acting(
                val placedShape: PlacedShape,
                val entitiesToDraw: Set<PlacedEntity<*>>,
                val entitiesToDelete: Set<Coordinate>,
            ) : Shape(isIdle = false) {

                override val shape: ShapeType = placedShape.type()
            }
        }
    }

    sealed class Eraser : ToolkitState(tool = ToolType.Eraser) {

        sealed class Point(final override val isIdle: Boolean) : Eraser() {

            data object Idle : Point(isIdle = true)

            data object Acting : Point(isIdle = false)

            final override val shape: ShapeType? = null
        }

        sealed class Shape(final override val isIdle: Boolean) : Eraser() {

            data class Idle(override val shape: ShapeType) : Shape(isIdle = true)

            data class Acting(
                val placedShape: PlacedShape,
                val entitiesToDelete: Set<Coordinate>,
            ) : Shape(isIdle = false) {

                override val shape: ShapeType = placedShape.type()
            }
        }
    }

    sealed class Select : ToolkitState(tool = ToolType.Select) {

        sealed class Shape(final override val isIdle: Boolean) : Select() {

            data class Idle(override val shape: ShapeType) : Shape(isIdle = true)

            data class Acting(val placedShape: PlacedShape) : Shape(isIdle = false) {

                override val shape: ShapeType = placedShape.type()
            }
        }
    }


    companion object {

        fun default() = Pen.Point.Idle


        fun idle(tool: ToolType, shape: ShapeType?): ToolkitState {
            val compatibleShape = if (shape in tool.allowedShapes) shape else tool.allowedShapes.firstOrNull()

            return when (tool) {
                ToolType.Hand -> Hand.Point.Idle
                ToolType.Pen -> compatibleShape?.let(Pen.Shape::Idle) ?: Pen.Point.Idle
                ToolType.Eraser -> compatibleShape?.let(Eraser.Shape::Idle) ?: Eraser.Point.Idle
                ToolType.Select -> Select.Shape.Idle(compatibleShape!!)
            }
        }

        private val ToolType.allowedShapes: List<ShapeType?>
            get() = when (this) {
                ToolType.Hand -> listOf(null)
                ToolType.Pen -> listOf(null) + ShapeType.values()
                ToolType.Eraser -> listOf(null) + ShapeType.values()
                ToolType.Select -> ShapeType.values().toList()
            }
    }
}
