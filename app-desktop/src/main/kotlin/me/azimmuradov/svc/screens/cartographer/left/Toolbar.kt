/*
 * Copyright 2021-2021 Azim Muradov
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

package me.azimmuradov.svc.screens.cartographer.left

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.azimmuradov.svc.cartographer.toolkit.Toolkit
import me.azimmuradov.svc.engine.entity.ids.Equipment
import me.azimmuradov.svc.settings.languages.Language
import me.azimmuradov.svc.utils.drawSpriteBy
import me.azimmuradov.svc.utils.group.ToggleButtonsGroup


@Composable
fun Toolbar(
    toolkit: Toolkit,
    language: Language,
    modifier: Modifier = Modifier,
) {
    val es = List(size = 5) { it }

    Box(modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Divider(Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                Text(toolkit.tool?.type.toString())
                Spacer(modifier = Modifier.width(8.dp))
                Divider(Modifier.weight(1f))
            }

            Spacer(Modifier.height(16.dp))

            var chosenLabel by remember { mutableStateOf<Int?>(null) }

            ToggleButtonsGroup(
                buttonLabels = es,
                rowSize = 5u,
                chosenLabel = chosenLabel,
                onButtonClick = { chosenLabel = it },
                spaceContent = { Icon(Icons.Default.Clear, null, Modifier.fillMaxSize().padding(8.dp)) }
            ) {
                Canvas(Modifier.fillMaxSize().padding(8.dp)) {
                    drawSpriteBy(
                        id = Equipment.SimpleEquipment.Furnace,
                        layoutSize = size,
                    )
                }
            }
        }
    }
}