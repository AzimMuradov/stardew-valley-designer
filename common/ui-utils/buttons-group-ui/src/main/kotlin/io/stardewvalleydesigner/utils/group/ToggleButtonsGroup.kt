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

package io.stardewvalleydesigner.utils.group

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun <L> ToggleButtonsGroup(
    buttonLabels: List<GroupOption<L>>,
    rowSize: UInt,
    modifier: Modifier = Modifier,
    chosenLabel: GroupOption<L>,
    onButtonClick: (L) -> Unit,
    spaceContent: @Composable RowScope.() -> Unit = {},
    buttonContent: @Composable RowScope.(label: L) -> Unit,
) {
    val items = buttonLabels
        .chunked(size = rowSize.toInt()) { it.resizedTo(size = rowSize.toInt(), spacer = GroupOption.None) }
        .flatten()

    Surface(
        modifier = Modifier.wrapContentSize().then(modifier),
        shape = MaterialTheme.shapes.medium,
        elevation = 2.dp,
    ) {
        VerticalGroup(
            rowSize = rowSize,
            modifier = Modifier.clip(MaterialTheme.shapes.medium),
        ) {
            for (item in items) {
                when (item) {
                    is GroupOption.Some -> ToggleButtonsGroupItem(
                        onClick = { onButtonClick(item.value) },
                        background = if (item == chosenLabel) Color.LightGray else MaterialTheme.colors.surface,
                        content = { buttonContent(item.value) },
                    )

                    is GroupOption.Disabled -> ToggleButtonsGroupItem(
                        onClick = { onButtonClick(item.value) },
                        enabled = false,
                        background = if (item == chosenLabel) Color.LightGray else MaterialTheme.colors.surface,
                        content = { buttonContent(item.value) },
                    )

                    GroupOption.None -> ToggleButtonsGroupItem(
                        onClick = {},
                        enabled = false,
                        content = spaceContent,
                    )
                }
            }
        }
    }
}

private fun <R, T : R> List<T>.resizedTo(size: Int, spacer: R): List<R> =
    if (this.size >= size) slice(0 until size)
    else this + List(size = size - this.size) { spacer }

private fun <T> List<T>.resizedTo(size: Int): List<T?> =
    if (this.size >= size) slice(0 until size)
    else this + List(size = size - this.size) { null }


@Composable
private fun ToggleButtonsGroupItem(
    onClick: () -> Unit,
    enabled: Boolean = true,
    background: Color = MaterialTheme.colors.surface,
    content: @Composable RowScope.() -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.aspectRatio(1f).fillMaxSize(),
        enabled = enabled,
        shape = RectangleShape,
        border = null,
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = background),
        contentPadding = PaddingValues(4.dp),
        content = content,
    )
}
