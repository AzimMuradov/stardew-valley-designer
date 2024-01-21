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

package io.stardewvalleydesigner.cmplib.group

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.dp


@Composable
internal fun ToggleButtonsGroupItem(
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    background: Color = MaterialTheme.colors.surface,
    content: @Composable BoxScope.() -> Unit,
) {
    val colors = ButtonDefaults.outlinedButtonColors(backgroundColor = background)
    val contentColor by colors.contentColor(enabled)
    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .semantics { role = Role.Button }
                // .minimumInteractiveComponentSize() // May cause visual issues
                .background(
                    color = colors.backgroundColor(enabled).value,
                    shape = RectangleShape
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    enabled = enabled,
                    onClick = onClick
                ),
            propagateMinConstraints = true
        ) {
            CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
                ProvideTextStyle(value = MaterialTheme.typography.button) {
                    Box(Modifier.fillMaxSize().padding(4.dp)) {
                        content()
                    }
                }
            }
        }
    }
}
