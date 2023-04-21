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

package me.azimmuradov.svc.screens.mainmenu

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun RowScope.PlanMenuButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
) {
    PlanMenuButton(
        text,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        onClick,
        enabled
    )
}

@Composable
fun RowScope.PlanMenuButton(
    text: String,
    icon: Painter,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
) {
    PlanMenuButton(
        text,
        icon = {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        onClick,
        enabled
    )
}


@Composable
private fun RowScope.PlanMenuButton(
    text: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    enabled: Boolean,
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxHeight().weight(1f),
        enabled = enabled
    ) {
        icon()
        Spacer(Modifier.width(12.dp))
        Text(
            text,
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        )
    }
}
