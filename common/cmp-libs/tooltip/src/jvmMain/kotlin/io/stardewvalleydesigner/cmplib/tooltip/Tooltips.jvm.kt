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

package io.stardewvalleydesigner.cmplib.tooltip

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.ui.component.themes.ternaryColor


@OptIn(ExperimentalFoundationApi::class)
@Composable
actual fun TooltipArea(
    tooltip: String,
    enabled: Boolean,
    delayMillis: Int,
    tooltipPlacement: TooltipPlacement,
    content: @Composable () -> Unit,
) {
    androidx.compose.foundation.TooltipArea(
        tooltip = {
            if (enabled) {
                Surface(
                    modifier = Modifier.shadow(4.dp),
                    color = ternaryColor,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = tooltip,
                        modifier = Modifier.padding(10.dp),
                        color = Color.Black,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        },
        delayMillis = delayMillis,
        tooltipPlacement = when (tooltipPlacement) {
            is TooltipPlacement.CursorPoint -> androidx.compose.foundation.TooltipPlacement.CursorPoint(
                tooltipPlacement.offset,
                tooltipPlacement.alignment,
                tooltipPlacement.windowMargin,
            )

            is TooltipPlacement.ComponentRect -> androidx.compose.foundation.TooltipPlacement.ComponentRect(
                tooltipPlacement.anchor,
                tooltipPlacement.alignment,
                tooltipPlacement.offset,
            )
        },
        content = content
    )
}
