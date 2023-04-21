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

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.azimmuradov.svc.cartographer.res.LayoutSpritesProvider
import me.azimmuradov.svc.engine.SvcEngine
import me.azimmuradov.svc.engine.layers.entities
import me.azimmuradov.svc.utils.GlobalSettings


@Composable
fun LayoutChoosingMenu(
    modifier: Modifier,
    layouts: List<SvcEngine>?,
    placeholder: String,
    chosenLayout: SvcEngine?,
    okText: String,
    cancelText: String,
    onLayoutChosen: (SvcEngine) -> Unit,
    onOk: () -> Unit,
    onCancel: () -> Unit,
) {
    val wordList = GlobalSettings.strings

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (layouts != null) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 2),
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(layouts) { layout ->
                    Column(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .border(
                                width = if (chosenLayout == layout) 2.dp else Dp.Unspecified,
                                color = Color.Black,
                                shape = MaterialTheme.shapes.medium
                            )
                            .clickable(onClick = { onLayoutChosen(layout) })
                            .padding(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box {
                            LayoutPreview(
                                layoutSprite = LayoutSpritesProvider.layoutSpriteBy(layout.layers.layout.type),
                                layoutSize = layout.layers.layout.size,
                                entities = layout.layers.entities,
                            )
                        }
                        Text(
                            text = wordList.layout(layout.layers.layout.type),
                            style = MaterialTheme.typography.subtitle2
                        )
                    }
                }
            }
        } else {
            Box(Modifier.fillMaxWidth().weight(1f), Alignment.Center) {
                Text(placeholder)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().height(48.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onOk,
                modifier = Modifier.width(100.dp).height(36.dp),
                enabled = chosenLayout != null
            ) {
                Text(okText)
            }
            TextButton(
                onClick = onCancel,
                modifier = Modifier.width(100.dp).height(36.dp)
            ) {
                Text(cancelText)
            }
        }
    }
}
