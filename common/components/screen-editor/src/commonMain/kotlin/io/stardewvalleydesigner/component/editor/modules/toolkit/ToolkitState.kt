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

import io.stardewvalleydesigner.engine.entity.PlacedEntity
import io.stardewvalleydesigner.engine.geometry.BoundVector
import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.geometry.shapes.PlacedShape
import io.stardewvalleydesigner.engine.layers.LayeredEntitiesData


sealed class ToolkitState(val tool: ToolType) {

    abstract val isIdle: Boolean

    val isActing: Boolean get() = !isIdle


    abstract val shape: ShapeType?

    abstract val actionVector: BoundVector?

    val allowedShapes: List<ShapeType?> = tool.allowedShapes


    sealed class Drag : ToolkitState(tool = ToolType.Drag) {

        sealed class Point(final override val isIdle: Boolean) : Drag() {

            data object Idle : Point(isIdle = true)

            data class Acting(val heldEntities: LayeredEntitiesData) : Point(isIdle = false)

            final override val shape: ShapeType? = null

            final override val actionVector: BoundVector? = null
        }
    }

    sealed class Pen : ToolkitState(tool = ToolType.Pen) {

        sealed class Point(final override val isIdle: Boolean) : Pen() {

            data object Idle : Point(isIdle = true)

            data object Acting : Point(isIdle = false)

            final override val shape: ShapeType? = null

            final override val actionVector: BoundVector? = null
        }

        sealed class Shape(final override val isIdle: Boolean) : Pen() {

            data class Idle(override val shape: ShapeType) : Shape(isIdle = true) {

                override val actionVector: BoundVector? = null
            }

            data class Acting(
                val placedShape: PlacedShape,
                val entitiesToDraw: Set<PlacedEntity<*>>,
                val entitiesToDelete: Set<Coordinate>,
            ) : Shape(isIdle = false) {

                override val shape: ShapeType = placedShape.type()

                override val actionVector: BoundVector = BoundVector(placedShape.a, placedShape.b)
            }
        }
    }

    sealed class Eraser : ToolkitState(tool = ToolType.Eraser) {

        sealed class Point(final override val isIdle: Boolean) : Eraser() {

            data object Idle : Point(isIdle = true)

            data object Acting : Point(isIdle = false)

            final override val shape: ShapeType? = null

            final override val actionVector: BoundVector? = null
        }

        sealed class Shape(final override val isIdle: Boolean) : Eraser() {

            data class Idle(override val shape: ShapeType) : Shape(isIdle = true) {

                override val actionVector: BoundVector? = null
            }

            data class Acting(
                val placedShape: PlacedShape,
                val entitiesToDelete: Set<Coordinate>,
            ) : Shape(isIdle = false) {

                override val shape: ShapeType = placedShape.type()

                override val actionVector: BoundVector = BoundVector(placedShape.a, placedShape.b)
            }
        }
    }

    sealed class Select : ToolkitState(tool = ToolType.Select) {

        sealed class Shape(final override val isIdle: Boolean) : Select() {

            data class Idle(override val shape: ShapeType) : Shape(isIdle = true) {

                override val actionVector: BoundVector? = null
            }

            data class Acting(val placedShape: PlacedShape) : Shape(isIdle = false) {

                override val shape: ShapeType = placedShape.type()

                override val actionVector: BoundVector = BoundVector(placedShape.a, placedShape.b)
            }
        }
    }

    sealed class EyeDropper : ToolkitState(tool = ToolType.EyeDropper) {

        sealed class Point(final override val isIdle: Boolean) : EyeDropper() {

            data object Idle : Point(isIdle = true)

            data object Acting : Point(isIdle = false)

            final override val shape: ShapeType? = null

            final override val actionVector: BoundVector? = null
        }
    }

    sealed class Hand : ToolkitState(tool = ToolType.Hand) {

        sealed class Point(final override val isIdle: Boolean) : Hand() {

            data object Idle : Point(isIdle = true)

            data object Acting : Point(isIdle = false)

            final override val shape: ShapeType? = null

            final override val actionVector: BoundVector? = null
        }
    }

    companion object {

        fun default() = Hand.Point.Idle


        fun idle(tool: ToolType, shape: ShapeType?): ToolkitState {
            val compatibleShape = if (shape in tool.allowedShapes) shape else tool.allowedShapes.firstOrNull()

            return when (tool) {
                ToolType.Drag -> Drag.Point.Idle
                ToolType.Pen -> compatibleShape?.let(Pen.Shape::Idle) ?: Pen.Point.Idle
                ToolType.Eraser -> compatibleShape?.let(Eraser.Shape::Idle) ?: Eraser.Point.Idle
                ToolType.Select -> Select.Shape.Idle(compatibleShape!!)
                ToolType.EyeDropper -> EyeDropper.Point.Idle
                ToolType.Hand -> Hand.Point.Idle
            }
        }

        private val ToolType.allowedShapes: List<ShapeType?>
            get() = when (this) {
                ToolType.Drag -> listOf(null)
                ToolType.Pen -> listOf(null) + ShapeType.entries
                ToolType.Eraser -> listOf(null) + ShapeType.entries
                ToolType.Select -> ShapeType.entries
                ToolType.EyeDropper -> listOf(null)
                ToolType.Hand -> listOf(null)
            }
    }
}
