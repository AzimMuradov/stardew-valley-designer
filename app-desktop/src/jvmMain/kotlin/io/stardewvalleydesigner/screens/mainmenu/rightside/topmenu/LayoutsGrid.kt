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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.component.mainmenu.Wrapper
import io.stardewvalleydesigner.designformat.models.Design
import io.stardewvalleydesigner.engine.layout.LayoutsProvider.layoutOf
import io.stardewvalleydesigner.screens.mainmenu.rightside.LayoutPreview
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings


@Composable
fun LayoutsGrid(
    layouts: List<Wrapper<Design>>,
    chosenLayout: Wrapper<Design>?,
    onChoose: (Wrapper<Design>) -> Unit,
) {
    val wordList = GlobalSettings.strings

    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 3),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(items = layouts) { design ->
            val chosenColor = MaterialTheme.colors.primary.copy(alpha = 0.3f)

            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .drawWithContent {
                        drawContent()
                        if (chosenLayout == design) {
                            drawRect(chosenColor)
                        }
                    }
                    .clickable(onClick = { onChoose(design) })
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val (_, playerName, farmName, layoutType, entities, wallpaper, flooring) = design.value
                Box {
                    LayoutPreview(
                        layout = layoutOf(layoutType),
                        entities = entities,
                        wallpaper = wallpaper,
                        flooring = flooring,
                    )
                }
                Text(
                    text = wordList.layout(layoutType),
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }
}
