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

package io.stardewvalleydesigner.ui.component.designdialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AcceptDesignBar(
    textFieldText: String,
    buttonText: String,
    placeholderText: String,
    onClick: () -> Unit,
    buttonEnabled: Boolean = true,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ReadOnlyTextField(
            value = textFieldText,
            modifier = Modifier.weight(1f).height(48.dp),
            placeholder = placeholderText,
        )

        Button(
            onClick = onClick,
            modifier = Modifier.height(48.dp),
            enabled = buttonEnabled,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primaryVariant,
                contentColor = Color.White
            )
        ) {
            Text(
                text = buttonText,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            )
        }
    }
}
