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

package io.stardewvalleydesigner.screens.mainmenu.rightside.topmenu

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun ReadOnlyTextField(
    value: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        modifier = modifier,
        readOnly = true,
        placeholder = { Text(placeholder) },
        trailingIcon = null,
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onPrimary,
            backgroundColor = MaterialTheme.colors.secondaryVariant,
            cursorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            trailingIconColor = MaterialTheme.colors.onPrimary.copy(TextFieldDefaults.IconOpacity),
            placeholderColor = MaterialTheme.colors.onPrimary.copy(ContentAlpha.medium)
        )
    )
}
