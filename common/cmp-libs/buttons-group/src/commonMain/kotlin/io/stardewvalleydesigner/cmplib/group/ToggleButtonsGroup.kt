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

package io.stardewvalleydesigner.cmplib.group

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import kotlin.math.roundToInt


@Composable
fun <L> ToggleButtonsGroup(
    buttonLabels: List<GroupOption<L>>,
    rowSize: UInt,
    modifier: Modifier = Modifier,
    chosenLabel: L,
    onButtonClick: (L) -> Unit,
    spaceContent: @Composable BoxScope.() -> Unit = {},
    buttonContent: @Composable BoxScope.(label: L) -> Unit,
) {
    val items = buttonLabels.chunked(size = rowSize.toInt()) {
        it.resizedTo(
            size = rowSize.toInt(),
            spacer = GroupOption.None
        )
    }

    Layout(
        content = {
            for (item in items.flatten()) {
                when (item) {
                    is GroupOption.Some -> ToggleButtonsGroupItem(
                        onClick = { onButtonClick(item.value) },
                        background = if (item.value == chosenLabel) {
                            Color.Black.copy(alpha = 0.15f)
                        } else {
                            MaterialTheme.colors.surface
                        },
                        content = { buttonContent(item.value) },
                    )

                    is GroupOption.Disabled -> ToggleButtonsGroupItem(
                        enabled = false,
                        content = { buttonContent(item.value) },
                    )

                    GroupOption.None -> ToggleButtonsGroupItem(
                        enabled = false,
                        content = spaceContent,
                    )
                }
            }
        },
        modifier = modifier,
    ) { measurables, constraints ->
        val itemSize = constraints.maxWidth / rowSize.toFloat()

        val xs = (0..<rowSize.toInt()).map { it * itemSize }.map { it.roundToInt() } + constraints.maxWidth
        val ys = (0..items.size).map { it * itemSize }.map { it.roundToInt() }

        val placeables = measurables.chunked(size = rowSize.toInt()).mapIndexed { yI, row ->
            row.mapIndexed { xI, m ->
                m.measure(Constraints.fixed(xs[xI + 1] - xs[xI], ys[yI + 1] - ys[yI]))
            }
        }

        layout(width = xs.last(), height = ys.last()) {
            placeables.forEachIndexed { yI, row ->
                row.forEachIndexed { xI, p ->
                    p.place(x = xs[xI], y = ys[yI])
                }
            }
        }
    }
}


// Utils

private fun <T : S, S> List<T>.resizedTo(size: Int, spacer: S): List<S> =
    if (this.size >= size) slice(0 until size)
    else this + List(size = size - this.size) { spacer }
