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

package io.stardewvalleydesigner.screens.editor.topmenubar

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.editor.EditorIntent
import io.stardewvalleydesigner.editor.modules.history.HistoryState


@Composable
fun History(
    history: HistoryState,
    intentConsumer: (EditorIntent) -> Unit,
) {
    Row(modifier = Modifier.wrapContentWidth().fillMaxHeight()) {
        IconButton(
            onClick = { intentConsumer(EditorIntent.History.GoBack) },
            enabled = history.canGoBack,
            modifier = Modifier.fillMaxHeight(),
        ) {
            val contentAlpha = if (history.canGoBack) ContentAlpha.high else ContentAlpha.disabled
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null,
                modifier = Modifier.padding(8.dp).align(Alignment.CenterVertically),
                tint = Color.White.copy(alpha = contentAlpha),
            )
        }
        IconButton(
            onClick = { intentConsumer(EditorIntent.History.GoForward) },
            enabled = history.canGoForward,
            modifier = Modifier.fillMaxHeight(),
        ) {
            val contentAlpha = if (history.canGoForward) ContentAlpha.high else ContentAlpha.disabled
            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = null,
                modifier = Modifier.padding(8.dp).align(Alignment.CenterVertically),
                tint = Color.White.copy(alpha = contentAlpha),
            )
        }
    }
}
