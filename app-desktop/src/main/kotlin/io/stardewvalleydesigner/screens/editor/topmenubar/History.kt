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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.stardewvalleydesigner.editor.EditorIntent
import io.stardewvalleydesigner.editor.modules.history.HistoryState


@Composable
fun History(
    history: HistoryState,
    intentConsumer: (EditorIntent) -> Unit,
) {
    Row(modifier = Modifier.wrapContentWidth().fillMaxHeight()) {
        TopMenuIconButton(
            icon = Icons.Rounded.ArrowBack,
            enabled = history.canGoBack,
            onClick = { intentConsumer(EditorIntent.History.GoBack) }
        )
        TopMenuIconButton(
            icon = Icons.Rounded.ArrowForward,
            enabled = history.canGoForward,
            onClick = { intentConsumer(EditorIntent.History.GoForward) }
        )
    }
}
