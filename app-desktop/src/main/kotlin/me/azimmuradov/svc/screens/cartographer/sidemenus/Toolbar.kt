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

package me.azimmuradov.svc.screens.cartographer.sidemenus

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.azimmuradov.svc.cartographer.toolkit.ToolType
import me.azimmuradov.svc.cartographer.toolkit.Toolkit
import me.azimmuradov.svc.components.screens.cartographer.res.MenuSpritesProvider.toolSpriteBy
import me.azimmuradov.svc.settings.Lang
import me.azimmuradov.svc.settings.Settings
import me.azimmuradov.svc.utils.Sprite
import me.azimmuradov.svc.utils.group.ToggleButtonsGroup


@Composable
fun Toolbar(
    toolkit: Toolkit,
    lang: Lang,
) {
    val labels = ToolType.values().take(4) + List(size = 2) { null }
    val wordList = Settings.wordList(lang)

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Divider(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = wordList.tool(toolkit.tool?.type))
            Spacer(modifier = Modifier.width(8.dp))
            Divider(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        ToggleButtonsGroup(
            buttonLabels = labels,
            rowSize = 5u,
            chosenLabel = toolkit.tool?.type,
            onButtonClick = { toolkit.chooseToolOf(type = it) },
            spaceContent = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().padding(8.dp),
                )
            },
            buttonContent = { toolType ->
                Sprite(
                    sprite = toolSpriteBy(toolType),
                    modifier = Modifier.fillMaxSize().padding(4.dp),
                )
            }
        )
    }
}