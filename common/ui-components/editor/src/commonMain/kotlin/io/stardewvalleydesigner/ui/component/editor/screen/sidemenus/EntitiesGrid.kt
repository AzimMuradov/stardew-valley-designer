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

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
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


@OptIn(ExperimentalFoundationApi::class)
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

    var text by remember { mutableStateOf("") }

    val filteredEntities = remember(entities, text) {
        entities.filter { e ->
            wordList.entity(e).contains(text, ignoreCase = true)
        }
    }

    val rows: List<List<Pair<Entity<*>, Sprite>?>> = remember(filteredEntities, season) {
        buildList {
            var row = mutableListOf<Pair<Entity<*>, Sprite>?>()
            var rowSize = 0
            for ((e, sprite) in filteredEntities.map { e -> e to SpriteUtils.calculateSprite(spriteMaps, e, season) }) {
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
            if (rowSize != 0) {
                row.add(null)
                add(row)
            }
        }
    }

    LazyColumn(
        modifier,
        state = state,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        stickyHeader(key = Unit, contentType = Unit) {
            Row(modifier = Modifier.fillMaxWidth()) {
                SearchField(
                    initName = text,
                    placeholder = "search field",
                    onNameChanged = { text = it },
                )
            }
        }

        items(rows, key = { it }) { row ->
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RowScope.SearchField(
    initName: String,
    placeholder: String,
    onNameChanged: (String) -> Unit,
) {
    val textFieldColors = TextFieldDefaults.textFieldColors(
        textColor = MaterialTheme.colors.onPrimary,
        backgroundColor = MaterialTheme.colors.primaryVariant.copy(ContentAlpha.high),
        cursorColor = MaterialTheme.colors.onPrimary,
        focusedIndicatorColor = MaterialTheme.colors.onPrimary.copy(ContentAlpha.high),
        unfocusedIndicatorColor = MaterialTheme.colors.onPrimary.copy(TextFieldDefaults.UnfocusedIndicatorLineOpacity),
        trailingIconColor = MaterialTheme.colors.onPrimary.copy(TextFieldDefaults.IconOpacity),
        placeholderColor = MaterialTheme.colors.onPrimary.copy(ContentAlpha.medium),
    )
    val textFieldInteractionSource = remember { MutableInteractionSource() }

    var name by remember(initName) { mutableStateOf(initName) }

    BasicTextField(
        value = name,
        onValueChange = {
            if (it.length <= name.length || it.length <= 15) {
                name = it
                onNameChanged(it)
            }
        },
        modifier = Modifier
            .fillMaxHeight()
            .weight(1f)
            .background(
                color = textFieldColors.backgroundColor(enabled = true).value,
                shape = MaterialTheme.shapes.medium
            ),
        readOnly = false,
        textStyle = MaterialTheme.typography.body1.merge(
            TextStyle(color = textFieldColors.textColor(enabled = true).value)
        ),
        singleLine = true,
        cursorBrush = SolidColor(textFieldColors.cursorColor(isError = false).value),
        interactionSource = textFieldInteractionSource,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = initName,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                placeholder = {
                    Text(
                        text = placeholder,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.body1,
                    )
                },
                leadingIcon = null,
                label = null,
                trailingIcon = {
                    val trailingIconInteractionSource = remember(::MutableInteractionSource)

                    Icon(
                        imageVector = Icons.Filled.Cancel,
                        contentDescription = null,
                        modifier = Modifier
                            .pointerHoverIcon(PointerIcon.Hand)
                            .size(20.dp)
                            .clickable(
                                interactionSource = trailingIconInteractionSource,
                                indication = rememberRipple(
                                    bounded = false,
                                    radius = 14.dp,
                                    color = textFieldColors.trailingIconColor(
                                        enabled = true,
                                        isError = false,
                                        trailingIconInteractionSource,
                                    ).value
                                )
                            ) {
                                name = ""
                                onNameChanged("")
                            }
                    )
                },
                singleLine = true,
                enabled = true,
                isError = false,
                interactionSource = textFieldInteractionSource,
                colors = textFieldColors,
                contentPadding = PaddingValues(start = 12.dp),
                border = {
                    TextFieldDefaults.BorderBox(
                        enabled = true,
                        isError = false,
                        interactionSource = textFieldInteractionSource,
                        colors = textFieldColors,
                        shape = MaterialTheme.shapes.medium,
                    )
                }
            )
        }
    )
}
