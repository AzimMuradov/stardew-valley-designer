package me.azimmuradov.svc.cartographer.state

import me.azimmuradov.svc.cartographer.modules.toolkit.*
import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.geometry.Coordinate
import me.azimmuradov.svc.engine.geometry.shapes.PlacedShape
import me.azimmuradov.svc.engine.layers.LayeredEntitiesData

sealed class ToolkitState(val tool: ToolType?) {

    object None : ToolkitState(tool = null) {

        override val shape: ShapeType? = null

        override val isIdle: Boolean = true
    }

    sealed class Hand : ToolkitState(tool = ToolType.Hand) {

        abstract val selectedEntities: LayeredEntitiesData

        sealed class Point(final override val isIdle: Boolean) : Hand() {

            data class Idle(
                override val selectedEntities: LayeredEntitiesData,
            ) : Point(isIdle = true)

            data class Acting(
                override val selectedEntities: LayeredEntitiesData,
                val heldEntities: LayeredEntitiesData,
            ) : Point(isIdle = false)

            override val shape: ShapeType? = null
        }
    }

    sealed class Pen : ToolkitState(tool = ToolType.Pen) {

        sealed class Point(final override val isIdle: Boolean) : Pen() {

            object Idle : Point(isIdle = true)

            object Acting : Point(isIdle = false)

            final override val shape: ShapeType? = null
        }

        sealed class Shape(final override val isIdle: Boolean) : Pen() {

            data class Idle(
                override val shape: ShapeType,
            ) : Shape(isIdle = true)

            data class Acting(
                val start: Coordinate,
                val placedShape: PlacedShape,
                val entitiesToDraw: Set<PlacedEntity<*>>,
                val entitiesToDelete: Set<Coordinate>,
            ) : Shape(isIdle = false) {

                override val shape: ShapeType? = placedShape.type()
            }
        }
    }

    sealed class Eraser : ToolkitState(tool = ToolType.Eraser) {

        sealed class Point(final override val isIdle: Boolean) : Eraser() {

            object Idle : Point(isIdle = true)

            object Acting : Point(isIdle = false)

            final override val shape: ShapeType? = null
        }

        sealed class Shape(final override val isIdle: Boolean) : Eraser() {

            data class Idle(
                override val shape: ShapeType,
            ) : Shape(isIdle = true)

            data class Acting(
                val start: Coordinate,
                val placedShape: PlacedShape,
                val entitiesToDelete: Set<Coordinate>,
            ) : Shape(isIdle = false) {

                override val shape: ShapeType? = placedShape.type()
            }
        }
    }

    sealed class EyeDropper : ToolkitState(tool = ToolType.EyeDropper) {

        sealed class Point(final override val isIdle: Boolean) : EyeDropper() {

            object Idle : Point(isIdle = true)

            object Acting : Point(isIdle = false)

            final override val shape: ShapeType? = null
        }
    }

    sealed class Select : ToolkitState(tool = ToolType.Select) {

        abstract val selectedEntities: LayeredEntitiesData

        sealed class Shape(final override val isIdle: Boolean) : Select() {

            data class Idle(
                override val shape: ShapeType,
                override val selectedEntities: LayeredEntitiesData,
            ) : Shape(isIdle = true)

            data class Acting(
                val start: Coordinate,
                val placedShape: PlacedShape,
                override val selectedEntities: LayeredEntitiesData,
            ) : Shape(isIdle = false) {

                override val shape: ShapeType? = placedShape.type()
            }
        }
    }


    val allowedShapes: List<ShapeType?> = tool.allowedShapes

    abstract val shape: ShapeType?


    abstract val isIdle: Boolean

    val isActing: Boolean get() = !isIdle


    companion object {

        fun idle(
            tool: ToolType? = null,
            shape: ShapeType? = tool.allowedShapes.firstOrNull(),
            selectedEntities: LayeredEntitiesData = LayeredEntitiesData(),
        ): ToolkitState {
            val compatibleShape = if (shape in tool.allowedShapes) shape else tool.allowedShapes.firstOrNull()

            return when (tool) {
                ToolType.Hand -> Hand.Point.Idle(selectedEntities)
                ToolType.Pen -> compatibleShape?.let(Pen.Shape::Idle) ?: Pen.Point.Idle
                ToolType.Eraser -> compatibleShape?.let(Eraser.Shape::Idle) ?: Eraser.Point.Idle
                ToolType.EyeDropper -> EyeDropper.Point.Idle
                ToolType.Select -> Select.Shape.Idle(compatibleShape!!, selectedEntities)
                null -> None
            }
        }

        private val ToolType?.allowedShapes: List<ShapeType?>
            get() = when (this) {
                ToolType.Hand -> listOf(null)
                ToolType.Pen -> listOf(null) + ShapeType.values()
                ToolType.Eraser -> listOf(null) + ShapeType.values()
                ToolType.EyeDropper -> listOf(null)
                ToolType.Select -> ShapeType.values().toList()
                null -> listOf(null)
            }
    }
}