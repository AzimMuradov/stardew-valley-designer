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

package io.stardewvalleydesigner.screens.mainmenu

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SideMenuButton(
    text: String,
    tooltip: String,
    icon: ImageVector,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
) {
    TooltipArea(
        tooltip = {
            if (enabled) {
                Surface(
                    modifier = Modifier.shadow(4.dp),
                    color = Color(red = 255, green = 255, blue = 210),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = tooltip,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        },
        tooltipPlacement = TooltipPlacement.ComponentRect(
            anchor = Alignment.TopEnd,
            alignment = Alignment.TopEnd,
            offset = DpOffset((-16).dp, 16.dp)
        )
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.height(48.dp).fillMaxWidth(),
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.primary
            ),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 8.dp,
                end = 16.dp,
                bottom = 8.dp
            )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            )
            Spacer(Modifier.weight(1f))
        }
    }
}
