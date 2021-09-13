/*
 * Copyright 2021-2021 Azim Muradov
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

package me.azimmuradov.svc.utils.menu

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import kotlin.math.max
import kotlin.math.min


@Composable
fun CustomDropdownMenu(
    expanded: Boolean,

    focusable: Boolean = true,
    onDismissRequest: (() -> Unit)? = null,
    onPreviewKeyEvent: ((KeyEvent) -> Boolean) = { false },
    onKeyEvent: ((KeyEvent) -> Boolean) = { false },

    cardScaleStateProvider: @Composable Transition<Boolean>.() -> State<Float> = { mutableStateOf(1f) },
    cardAlphaStateProvider: @Composable Transition<Boolean>.() -> State<Float> = { mutableStateOf(1f) },
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    border: BorderStroke? = null,
    elevation: Dp = 1.dp,
    preferredMenuPositioning: List<MenuPositioning> = preferredToBeVerticalMenu,
    offset: DpOffset = DpOffset.Zero,

    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: LazyListScope.() -> Unit,
) {
    val expandedStates = remember { MutableTransitionState(false) }
    expandedStates.targetState = expanded

    if (expandedStates.currentState || expandedStates.targetState) {
        val transformOriginState = remember { mutableStateOf(TransformOrigin.Center) }

        Popup(
            popupPositionProvider = customDropdownMenuPositionProvider(
                preferredMenuPositioning,
                offset,
            ) { parentBounds, menuBounds ->
                transformOriginState.value = calculateTransformOrigin(parentBounds, menuBounds)
            },
            onDismissRequest = onDismissRequest,
            onPreviewKeyEvent = onPreviewKeyEvent,
            onKeyEvent = onKeyEvent,
            focusable = focusable,
        ) {
            // Menu open / close animation.

            val transition = updateTransition(expandedStates, "CustomDropdownMenu")

            val cardScale by transition.cardScaleStateProvider()
            val cardAlpha by transition.cardAlphaStateProvider()


            // Content

            Card(
                modifier = Modifier.graphicsLayer {
                    scaleX = cardScale
                    scaleY = cardScale
                    alpha = cardAlpha
                    transformOrigin = transformOriginState.value
                },
                shape = shape,
                backgroundColor = backgroundColor,
                contentColor = contentColor,
                border = border,
                elevation = elevation,
            ) {
                Box {
                    val state = rememberLazyListState()

                    LazyColumn(
                        modifier = modifier,
                        state = state,
                        verticalArrangement = verticalArrangement,
                        horizontalAlignment = horizontalAlignment,
                        content = content,
                    )

                    Box(Modifier.matchParentSize().padding(all = 5.dp)) {
                        VerticalScrollbar(
                            modifier = Modifier.requiredWidth(8.dp).align(Alignment.CenterEnd),
                            adapter = rememberScrollbarAdapter(state),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomDropdownMenuItem(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        content = content,
    )
}


// Constants

private val defaultMenuPositioning = MenuPositioning(Alignment.BottomCenter, Alignment.BottomCenter)

val preferredToBeVerticalMenu = listOf(
    MenuPositioning(Alignment.BottomStart, Alignment.BottomEnd),
    MenuPositioning(Alignment.BottomEnd, Alignment.BottomStart),
)

val preferredToBeHorizontalMenu = listOf(
    MenuPositioning(Alignment.TopEnd, Alignment.BottomEnd),
    MenuPositioning(Alignment.TopStart, Alignment.BottomStart),
)

// Material Dropdown Menu : Specs (see https://material.io/components/menus#specs)

object DropdownMenuSpecs {

    val MenuElevation = 8.dp

    // Size defaults.

    private val WidthStep = 56.dp

    val MenuVerticalMargin = 48.dp
    val DropdownMenuVerticalPadding = 8.dp
    val DropdownMenuItemHorizontalPadding = 16.dp
    val DropdownMenuItemDefaultMinWidth = WidthStep * 2
    val DropdownMenuItemDefaultMaxWidth = WidthStep * 5
    val DropdownMenuItemDefaultHeight = 48.dp

    val DropdownMenuItemContentPadding = PaddingValues(
        horizontal = DropdownMenuItemHorizontalPadding,
        vertical = 0.dp
    )


    // Menu open/close animation.

    const val InTransitionDuration = 120
    const val OutTransitionDuration = 75
}


// Menu positioning

@Composable
private fun customDropdownMenuPositionProvider(
    preferredMenuPositioning: List<MenuPositioning>,
    offset: DpOffset,
    onPositionCalculated: (IntRect, IntRect) -> Unit,
): PopupPositionProvider = with(LocalDensity.current) {
    object : PopupPositionProvider {
        override fun calculatePosition(
            anchorBounds: IntRect,
            windowSize: IntSize,
            layoutDirection: LayoutDirection,
            popupContentSize: IntSize,
        ): IntOffset {
            val menuPositioning = preferredMenuPositioning.takeUnless { it.isEmpty() } ?: listOf(defaultMenuPositioning)

            fun posBy(menuPositioning: MenuPositioning): IntOffset {
                val (anchor, alignment) = menuPositioning
                val anchorPoint = anchor.align(IntSize.Zero, anchorBounds.size, layoutDirection)
                val tooltipArea = IntRect(
                    IntOffset(
                        anchorBounds.left + anchorPoint.x - popupContentSize.width,
                        anchorBounds.top + anchorPoint.y - popupContentSize.height,
                    ),
                    IntSize(
                        popupContentSize.width * 2,
                        popupContentSize.height * 2
                    )
                )
                val position = alignment.align(popupContentSize, tooltipArea.size, layoutDirection)

                return tooltipArea.topLeft + position + IntOffset(
                    offset.x.roundToPx().let { it * if (anchor.toHorizontal() == Alignment.Start) 1 else -1 },
                    offset.y.roundToPx().let { it * if (anchor.toVertical() == Alignment.Bottom) 1 else -1 },
                )
            }

            val positions = menuPositioning.map(::posBy)

            // The min margin above and below the menu, relative to the screen.
            val verticalMargin = DropdownMenuSpecs.MenuVerticalMargin.roundToPx()

            val pos = positions.firstOrNull { (x, y) ->
                x >= 0 && x + popupContentSize.width <= windowSize.width &&
                    y >= 0 + verticalMargin && y + popupContentSize.height <= windowSize.height - verticalMargin
            } ?: positions.last()


            // TODO : Took inspiration from the code below
            // // The min margin above and below the menu, relative to the screen.
            // val verticalMargin = MenuVerticalMargin.roundToPx()
            // // The content offset specified using the dropdown offset parameter.
            // val contentOffsetX = offset.x.roundToPx()
            // val contentOffsetY = offset.y.roundToPx()
            //
            // // Compute horizontal position.
            // val toRight = anchorBounds.left + contentOffsetX
            // val toLeft = anchorBounds.right - contentOffsetX - popupContentSize.width
            // val toDisplayRight = windowSize.width - popupContentSize.width
            // val toDisplayLeft = 0
            // val x = if (layoutDirection == LayoutDirection.Ltr) {
            //     sequenceOf(toRight, toLeft, toDisplayRight)
            // } else {
            //     sequenceOf(toLeft, toRight, toDisplayLeft)
            // }.firstOrNull {
            //     it >= 0 && it + popupContentSize.width <= windowSize.width
            // } ?: toLeft
            //
            // // Compute vertical position.
            // val toBottom = maxOf(anchorBounds.bottom + contentOffsetY, verticalMargin)
            // val toTop = anchorBounds.top - contentOffsetY - popupContentSize.height
            // val toCenter = anchorBounds.top - popupContentSize.height / 2
            // val toDisplayBottom = windowSize.height - popupContentSize.height - verticalMargin
            //
            // val y = sequenceOf(toBottom, toTop, toCenter, toDisplayBottom).firstOrNull {
            //     it >= verticalMargin && it + popupContentSize.height <= windowSize.height - verticalMargin
            // } ?: toTop
            //
            // val pos = IntOffset(x, y)


            onPositionCalculated(
                anchorBounds,
                IntRect(pos.x, pos.y, pos.x + popupContentSize.width, pos.y + popupContentSize.height)
            )

            return pos
        }
    }
}

private fun calculateTransformOrigin(
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