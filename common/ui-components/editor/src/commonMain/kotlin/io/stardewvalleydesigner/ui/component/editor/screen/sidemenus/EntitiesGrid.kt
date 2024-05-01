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

package io.stardewvalleydesigner.ui.component.editor.screen.sidemenus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.cmplib.tooltip.TooltipArea
import io.stardewvalleydesigner.cmplib.tooltip.TooltipPlacement
import io.stardewvalleydesigner.data.Season
import io.stardewvalleydesigner.data.SpritePage
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.ui.component.editor.res.*
import io.stardewvalleydesigner.ui.component.editor.utils.DrawerUtils.drawEntityContained
import io.stardewvalleydesigner.ui.component.editor.utils.bounceClickable
import io.stardewvalleydesigner.ui.component.editor.utils.ratio
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings


@Composable
fun EntitiesGrid(
    modifier: Modifier,
    state: LazyListState,
    rowCapacity: UInt,
    entities: List<Entity<*>>,
    season: Season,
    onEntitySelection: (Entity<*>) -> Unit,
) {
    val wordList = GlobalSettings.strings

    val spriteMaps = ImageResources.entities

    val rowCap = rowCapacity.toInt()

    val rows: List<List<Pair<Entity<*>, Sprite>?>> = remember(entities, season) {
        buildList {
            var row = mutableListOf<Pair<Entity<*>, Sprite>?>()
            var rowSize = 0
            for ((e, sprite) in entities.map { e -> e to SpriteUtils.calculateSprite(spriteMaps, e, season) }) {
                val w = sprite.size.width / SpritePage.UNIT
                if (rowSize + w > rowCap) {
                    row.add(null)
                    add(row)
                    row = mutableListOf()
                    rowSize = 0
                }
                row.add(e to sprite)
                rowSize += w
                if (rowSize == rowCap) {
                    add(row)
                    row = mutableListOf()
                    rowSize = 0
                }
            }
        }
    }

    LazyColumn(
        modifier,
        state = state,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(rows) { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.Bottom,
            ) {
                row.forEach { pair ->
                    if (pair != null) {
                        val (e, sprite) = pair
                        Box(
                            modifier = Modifier
                                .pointerHoverIcon(PointerIcon.Hand)
                                .bounceClickable { onEntitySelection(e) }
                                .aspectRatio(ratio = sprite.size.ratio)
                                .weight(sprite.size.width.toFloat())
                                .drawBehind {
                                    drawEntityContained(
                                        sprite = sprite,
                                        layoutSize = size
                                    )
                                }
                        ) {
                            TooltipArea(
                                tooltip = wordList.entity(e),
                                tooltipPlacement = TooltipPlacement.CursorPoint(
                                    offset = DpOffset(4.dp, (-4).dp),
                                ),
                            ) {
                                Box(Modifier.fillMaxSize())
                            }
                        }
                    } else {
                        Spacer(
                            Modifier
                                .height(IntrinsicSize.Max)
                                .weight(
                                    (rowCap * SpritePage.UNIT - row
                                        .filterNotNull()
                                        .sumOf { (_, s) -> s.size.width }).toFloat()
                                )
                        )
                    }
                }
            }
        }
    }
}
