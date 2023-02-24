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

package me.azimmuradov.svc.utils.menu

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset


@Composable
fun MaterialDropdownMenu(
    expanded: Boolean,

    focusable: Boolean = true,
    onDismissRequest: (() -> Unit)? = null,
    onPreviewKeyEvent: ((KeyEvent) -> Boolean) = { false },
    onKeyEvent: ((KeyEvent) -> Boolean) = { false },

    cardScaleStateProvider: @Composable Transition<Boolean>.() -> State<Float> = {
        animateFloat(
            transitionSpec = {
                if (false isTransitioningTo true) {
                    tween(
                        durationMillis = DropdownMenuSpecs.InTransitionDuration,
                        easing = LinearOutSlowInEasing,
                    )
                } else {
                    tween(
                        durationMillis = 1,
                        delayMillis = DropdownMenuSpecs.OutTransitionDuration - 1,
                    )
                }
            }
        ) {
            if (it) 1f else 0.8f
        }
    },
    cardAlphaStateProvider: @Composable Transition<Boolean>.() -> State<Float> = {
        animateFloat(
            transitionSpec = {
                if (false isTransitioningTo true) {
                    tween(durationMillis = 30)
                } else {
                    tween(durationMillis = DropdownMenuSpecs.OutTransitionDuration)
                }
            }
        ) {
            if (it) 1f else 0f
        }
    },
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    border: BorderStroke? = null,
    elevation: Dp = DropdownMenuSpecs.MenuElevation,
    preferredMenuPositioning: List<MenuPositioning> = preferredToBeVerticalMenu,
    offset: DpOffset = DpOffset.Zero,

    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: LazyListScope.() -> Unit,
) {
    CustomDropdownMenu(
        expanded = expanded,

        focusable = focusable,
        onDismissRequest = onDismissRequest,
        onPreviewKeyEvent = onPreviewKeyEvent,
        onKeyEvent = onKeyEvent,

        cardScaleStateProvider = cardScaleStateProvider,
        cardAlphaStateProvider = cardAlphaStateProvider,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        border = border,
        elevation = elevation,
        preferredMenuPositioning = preferredMenuPositioning,
        offset = offset,

        modifier = modifier.padding(vertical = DropdownMenuSpecs.DropdownMenuVerticalPadding),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        content = content
    )
}


@Composable
fun MaterialDropdownMenuItem(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    CustomDropdownMenuItem(
        modifier = modifier
            .fillMaxWidth()
            // Preferred min and max width used during the intrinsic measurement.
            .sizeIn(
                minWidth = DropdownMenuSpecs.DropdownMenuItemDefaultMinWidth,
                maxWidth = DropdownMenuSpecs.DropdownMenuItemDefaultMaxWidth,
                minHeight = DropdownMenuSpecs.DropdownMenuItemDefaultHeight,
                // maxHeight = DropdownMenuSpecs.DropdownMenuItemDefaultHeight,
            )
            .padding(paddingValues = MenuDefaults.DropdownMenuItemContentPadding),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}
