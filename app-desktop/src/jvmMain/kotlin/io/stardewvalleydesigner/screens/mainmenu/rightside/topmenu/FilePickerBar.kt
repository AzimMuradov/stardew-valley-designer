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

package io.stardewvalleydesigner.screens.mainmenu.rightside.topmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.stardewvalleydesigner.cmplib.filedialogs.FilePicker


@Composable
fun FilePickerBar(
    buttonText: String,
    filePickerTitle: String,
    placeholderText: String,
    defaultPathAndFile: String?,
    onFilePicked: (String, String) -> Unit,
    fileFormat: String? = null,
) {
    var pathString by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp)
            .background(MaterialTheme.colors.secondary)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ReadOnlyTextField(
            value = pathString,
            modifier = Modifier.weight(1f).height(48.dp),
            placeholder = placeholderText,
        )

        var showFilePicker by remember { mutableStateOf(false) }

        if (showFilePicker) {
            FilePicker(
                title = filePickerTitle,
                defaultPathAndFile = pathString.takeIf(String::isNotBlank) ?: defaultPathAndFile,
                extensions = fileFormat?.let(::listOf)
            ) { result ->
                showFilePicker = false
                result?.let {
                    pathString = it.absolutePath ?: ""
                    onFilePicked(it.text, pathString)
                }
            }
        }

        Button(
            onClick = { showFilePicker = true },
            modifier = Modifier.height(48.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primaryVariant,
                contentColor = Color.White
            )
        ) {
            Icon(
                imageVector = Icons.Filled.FileUpload,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.size(8.dp))
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
