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

package io.stardewvalleydesigner.utils.menu.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.PopupPositionProvider
import kotlin.math.max
import kotlin.math.min


internal data class CustomDropdownMenuPositioning(val anchor: Alignment, val alignment: Alignment)


@Composable
internal fun customDropdownMenuPositionProvider(
    preferredMenuPositioning: List<CustomDropdownMenuPositioning>,
    offset: DpOffset,
    onPositionCalculated: (parentBounds: IntRect, menuBounds: IntRect) -> Unit,
): PopupPositionProvider = with(LocalDensity.current) {
    object : PopupPositionProvider {
        override fun calculatePosition(
            anchorBounds: IntRect,
            windowSize: IntSize,
            layoutDirection: LayoutDirection,
            popupContentSize: IntSize,
        ): IntOffset {
            require(preferredMenuPositioning.isNotEmpty())

            fun posBy(positioning: CustomDropdownMenuPositioning): IntOffset {
                val (anchor, alignment) = positioning
                val anchorPoint = anchor.align(IntSize.Zero, anchorBounds.size, layoutDirection)
                val tooltipArea = IntRect(
                    offset = anchorBounds.topLeft + anchorPoint
                            - IntOffset(popupContentSize.width, popupContentSize.height),
                    size = popupContentSize * 2
                )
                val position = alignment.align(popupContentSize, tooltipArea.size, layoutDirection)

                return tooltipArea.topLeft + position + IntOffset(
                    offset.x.roundToPx().let { it * if (anchor.toHorizontal() == Alignment.Start) 1 else -1 },
                    offset.y.roundToPx().let { it * if (anchor.toVertical() == Alignment.Bottom) 1 else -1 },
                )
            }

            val positions = preferredMenuPositioning.map(::posBy)

            // The min margin above and below the menu, relative to the screen.
            val verticalMargin = DropdownMenuSpecs.MenuVerticalMargin.roundToPx()

            val pos = positions.firstOrNull { (x, y) ->
                x >= 0 && x + popupContentSize.width <= windowSize.width &&
                        y >= 0 + verticalMargin && y + popupContentSize.height <= windowSize.height - verticalMargin
            } ?: positions.last()

            onPositionCalculated(
                anchorBounds,
                IntRect(offset = pos, size = popupContentSize)
            )

            return pos
        }
    }
}


internal fun calculateTransformOrigin(
    parentBounds: IntRect,
    menuBounds: IntRect,
): TransformOrigin = TransformOrigin(
    pivotFractionX = when {
        menuBounds.left >= parentBounds.right -> 0f
        menuBounds.right <= parentBounds.left -> 1f
        menuBounds.width == 0 -> 0f
        else -> {
            val max = max(parentBounds.left, menuBounds.left)
            val min = min(parentBounds.right, menuBounds.right)
            val intersectionCenter = (max + min) / 2
            (intersectionCenter - menuBounds.left).toFloat() / menuBounds.width
        }
    },
    pivotFractionY = when {
        menuBounds.top >= parentBounds.bottom -> 0f
        menuBounds.bottom <= parentBounds.top -> 1f
        menuBounds.height == 0 -> 0f
        else -> {
            val max = max(parentBounds.top, menuBounds.top)
            val min = min(parentBounds.bottom, menuBounds.bottom)
            val intersectionCenter = (max + min) / 2
            (intersectionCenter - menuBounds.top).toFloat() / menuBounds.height
        }
    },
)


private fun Alignment.toVertical(): Alignment.Vertical = when (this) {
    TopStart, TopCenter, TopEnd -> Alignment.Top
    CenterStart, Center, CenterEnd -> Alignment.CenterVertically
    BottomStart, BottomCenter, BottomEnd -> Alignment.Bottom
    else -> error("Not supported")
}

private fun Alignment.toHorizontal(): Alignment.Horizontal = when (this) {
    TopStart, CenterStart, BottomStart -> Alignment.Start
    TopCenter, Center, BottomCenter -> Alignment.CenterHorizontally
    TopEnd, CenterEnd, BottomEnd -> Alignment.End
    else -> error("Not supported")
}
