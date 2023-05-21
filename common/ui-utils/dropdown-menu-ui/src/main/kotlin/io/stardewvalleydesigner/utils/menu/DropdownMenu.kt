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

package io.stardewvalleydesigner.utils.menu

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import io.stardewvalleydesigner.utils.menu.impl.calculateTransformOrigin
import io.stardewvalleydesigner.utils.menu.impl.customDropdownMenuPositionProvider


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

                    BoxWithConstraints(modifier) {
                        LazyColumn(
                            state = state,
                            verticalArrangement = verticalArrangement,
                            horizontalAlignment = horizontalAlignment,
                            content = content,
                        )

                        if (state.layoutInfo.viewportSize.height.toFloat() == this.maxHeight.value) {
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
    MenuPositioning(Alignment.BottomCenter, Alignment.BottomCenter),
    MenuPositioning(Alignment.BottomStart, Alignment.BottomEnd),
    MenuPositioning(Alignment.BottomEnd, Alignment.BottomStart),
)

val preferredToBeHorizontalMenu = listOf(
    MenuPositioning(Alignment.TopEnd, Alignment.BottomEnd),
    MenuPositioning(Alignment.TopStart, Alignment.BottomStart),
)

// Material Dropdown Menu : Specs (see https://material.io/components/menus#specs)

object DropdownMenuSpecs {

    val MenuElevation = 4.dp

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
