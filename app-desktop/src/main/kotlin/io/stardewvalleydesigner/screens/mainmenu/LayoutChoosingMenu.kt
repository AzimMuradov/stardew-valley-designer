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

package io.stardewvalleydesigner.screens.mainmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.stardewvalleydesigner.engine.EditorEngine
import io.stardewvalleydesigner.engine.layers.entities
import io.stardewvalleydesigner.engine.layout
import io.stardewvalleydesigner.utils.GlobalSettings


@Composable
fun LayoutChoosingMenu(
    modifier: Modifier,
    layouts: List<EditorEngine>?,
    placeholder: String,
    chosenLayout: EditorEngine?,
    okText: String,
    onLayoutChosen: (EditorEngine) -> Unit,
    onOk: () -> Unit,
    isLoading: Boolean = false,
) {
    val wordList = GlobalSettings.strings

    Column(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = MaterialTheme.shapes.large
            )
            .background(MaterialTheme.colors.secondaryVariant)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val m = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(MaterialTheme.colors.surface)
            .padding(8.dp)

        when {
            isLoading -> {
                Box(
                    modifier = m,
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(strokeCap = StrokeCap.Round)
                }
            }

            layouts == null -> {
                Box(
                    modifier = m.padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        placeholder,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h6
                    )
                }
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(count = 3),
                    modifier = m,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(layouts) { engine ->
                        val chosenColor = MaterialTheme.colors.primary.copy(alpha = 0.3f)

                        Column(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium)
                                .drawWithContent {
                                    drawContent()
                                    if (chosenLayout == engine) {
                                        drawRect(chosenColor)
                                    }
                                }
                                .clickable(onClick = { onLayoutChosen(engine) })
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box {
                                LayoutPreview(
                                    layout = engine.layout,
                                    entities = engine.layers.entities,
                                    wallpaper = engine.wallpaper,
                                    flooring = engine.flooring,
                                )
                            }
                            Text(
                                text = wordList.layout(engine.layout.type),
                                style = MaterialTheme.typography.subtitle2
                            )
                        }
                    }
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onOk,
                modifier = Modifier.height(48.dp),
                enabled = chosenLayout != null
            ) {
                Text(
                    okText,
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}
