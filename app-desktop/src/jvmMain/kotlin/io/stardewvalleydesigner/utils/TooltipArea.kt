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

package io.stardewvalleydesigner.utils

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.ternaryColor


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TooltipArea(
    tooltip: String,
    enabled: Boolean = true,
    delayMillis: Int = 500,
    tooltipPlacement: TooltipPlacement = TooltipPlacement.CursorPoint(
        offset = DpOffset(0.dp, 8.dp)
    ),
    content: @Composable () -> Unit,
) {
    TooltipArea(
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
        tooltipPlacement = tooltipPlacement,
        content = content
    )
}