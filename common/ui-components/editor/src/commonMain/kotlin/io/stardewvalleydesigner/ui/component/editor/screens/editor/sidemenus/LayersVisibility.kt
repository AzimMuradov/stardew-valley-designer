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

package io.stardewvalleydesigner.ui.component.editor.screens.editor.sidemenus

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.engine.layer.LayerType
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings


@Composable
internal fun LayersVisibility(
    allowedLayers: Set<LayerType<*>>,
    visibleLayers: Set<LayerType<*>>,
    onVisibilityChange: (LayerType<*>, Boolean) -> Unit,
) {
    for (lType in allowedLayers) {
        LayerVisibility(
            layerType = lType,
            visible = lType in visibleLayers,
            onVisibleChange = { vis -> onVisibilityChange(lType, vis) },
        )
    }
}


@Composable
private fun LayerVisibility(
    layerType: LayerType<*>,
    visible: Boolean,
    onVisibleChange: (Boolean) -> Unit,
) {
    val wordList = GlobalSettings.strings

    Row(
        modifier = Modifier.fillMaxWidth().height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconToggleButton(
            checked = visible,
            onCheckedChange = onVisibleChange,
            modifier = Modifier.aspectRatio(1f).fillMaxSize(),
        ) {
            Box(Modifier.fillMaxSize()) {
                Icon(
                    imageVector = if (visible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp).align(Alignment.Center),
                )
            }
        }
        Spacer(Modifier.width(8.dp))
        Text(
            text = wordList.layer(layerType),
            style = MaterialTheme.typography.subtitle1
        )
    }
}
