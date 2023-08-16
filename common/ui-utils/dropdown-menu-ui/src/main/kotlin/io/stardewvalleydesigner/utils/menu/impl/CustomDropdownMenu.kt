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

import androidx.compose.animation.core.*
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import io.stardewvalleydesigner.utils.menu.DropdownMenuStyle


@Composable
internal fun CustomDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,

    modifier: Modifier = Modifier,

    dropdownMenuStyle: DropdownMenuStyle,

    preferredMenuPositioning: List<CustomDropdownMenuPositioning> = preferredToBeVerticalMenu,
    offset: DpOffset = DpOffset.Zero,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,

    content: CustomDropdownMenuScope.() -> Unit,
) {
    val expandedStates = remember { MutableTransitionState(false) }
    expandedStates.targetState = expanded

    if (expandedStates.currentState || expandedStates.targetState) {
        val transformOriginState = remember { mutableStateOf(TransformOrigin.Center) }

        Popup(
            popupPositionProvider = customDropdownMenuPositionProvider(
                preferredMenuPositioning = preferredMenuPositioning,
                offset = offset,
                onPositionCalculated = { parentBounds, menuBounds ->
                    transformOriginState.value = calculateTransformOrigin(parentBounds, menuBounds)
                },
            ),
            onDismissRequest = onDismissRequest,
        ) {
            // Menu open / close animation.

            val transition = updateTransition(expandedStates, "CustomDropdownMenu")

            val cardScale by transition.animateFloat(
                transitionSpec = {
                    if (false isTransitioningTo true) {
                        tween(
                            durationMillis = 120,
                            easing = LinearOutSlowInEasing,
                        )
                    } else {
                        tween(
                            durationMillis = 1,
                            delayMillis = 75 - 1,
                        )
                    }
                },
                targetValueByState = { if (it) 1f else 0.8f }
            )

            val cardAlpha by transition.animateFloat(
                transitionSpec = {
                    tween(durationMillis = if (false isTransitioningTo true) 30 else 75)
                },
                targetValueByState = { if (it) 1f else 0f }
            )


            // Content

            Card(
                modifier = Modifier.graphicsLayer {
                    scaleX = cardScale
                    scaleY = cardScale
                    alpha = cardAlpha
                    transformOrigin = transformOriginState.value
                },
                shape = dropdownMenuStyle.shape,
                backgroundColor = dropdownMenuStyle.backgroundColor,
                contentColor = dropdownMenuStyle.contentColor,
                elevation = dropdownMenuStyle.elevation,
            ) {
                Box {
                    val state = rememberLazyListState()

                    BoxWithConstraints(modifier = modifier.padding(DropdownMenuSpecs.MenuContentPadding)) {
                        LazyColumn(
                            state = state,
                            verticalArrangement = verticalArrangement,
                            horizontalAlignment = horizontalAlignment,
                            content = {
                                CustomDropdownMenuScopeImpl(
                                    lazyListScope = this,
                                    hasScrollbarProvider = { state.canScrollBackward || state.canScrollForward }
                                ).apply(content)
                            },
                        )

                        if (state.canScrollBackward || state.canScrollForward) {
                            Box(Modifier.matchParentSize().padding(DropdownMenuSpecs.ScrollbarPadding)) {
                                VerticalScrollbar(
                                    modifier = Modifier
                                        .requiredWidth(DropdownMenuSpecs.ScrollbarWidth)
                                        .align(Alignment.CenterEnd),
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
internal fun CustomDropdownMenuItem(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            // Preferred min and max width used during the intrinsic measurement.
            .sizeIn(
                minWidth = DropdownMenuSpecs.ElementDefaultMinWidth,
                maxWidth = DropdownMenuSpecs.ElementDefaultMaxWidth,
                minHeight = DropdownMenuSpecs.ElementDefaultHeight,
                // maxHeight = DropdownMenuSpecs.ElementDefaultHeight,
            )
            .padding(DropdownMenuSpecs.ElementContentPadding),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}


// Constants

internal val preferredToBeVerticalMenu = listOf(
    CustomDropdownMenuPositioning(Alignment.BottomCenter, Alignment.BottomCenter),
    CustomDropdownMenuPositioning(Alignment.BottomStart, Alignment.BottomEnd),
    CustomDropdownMenuPositioning(Alignment.BottomEnd, Alignment.BottomStart),
)

internal val preferredToBeHorizontalMenu = listOf(
    CustomDropdownMenuPositioning(Alignment.TopEnd, Alignment.BottomEnd),
    CustomDropdownMenuPositioning(Alignment.TopStart, Alignment.BottomStart),
)

internal object DropdownMenuSpecs {

    val MenuElevation = 4.dp

    // Size defaults.

    val MenuVerticalMargin = 48.dp

    private val WidthStep = 56.dp

    val ElementDefaultMinWidth = WidthStep * 2
    val ElementDefaultMaxWidth = WidthStep * 5
    val ElementDefaultHeight = 48.dp

    val MenuContentPadding = PaddingValues(
        horizontal = 0.dp,
        vertical = 8.dp
    )
    val ElementContentPadding = PaddingValues(
        horizontal = 16.dp,
        vertical = 0.dp
    )
    val EndPaddingWhenHasScrollbar = 8.dp

    val ScrollbarWidth = 8.dp
    val ScrollbarPadding = 5.dp
}
