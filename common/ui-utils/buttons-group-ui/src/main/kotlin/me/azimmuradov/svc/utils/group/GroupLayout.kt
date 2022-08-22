/*
 * Copyright 2021-2022 Azim Muradov
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

package me.azimmuradov.svc.utils.group

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout


/**
 * Group of adjacent elements.
 */
@Composable
fun VerticalGroup(
    rowSize: UInt,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
        modifier = modifier,
    ) { measurables, constraints ->
        val itemWidth = constraints.maxWidth / rowSize.toInt()

        // Keep given height constraints, but set an exact width
        val itemConstraints = constraints.copy(minWidth = itemWidth, maxWidth = itemWidth)

        // Measure each item with these constraints
        val placeables = measurables.map { it.measure(itemConstraints) }

        // Track each columns' height, so we can calculate the overall height
        val columnHeights = IntArray(rowSize.toInt())
        for ((i, placeable) in placeables.withIndex()) {
            columnHeights[i % rowSize.toInt()] += placeable.height
        }
        val height = (columnHeights.maxOrNull() ?: constraints.minHeight).coerceAtMost(constraints.maxHeight)

        layout(
            width = constraints.maxWidth,
            height = height,
        ) {
            // Track the Y co-ord per column we have placed up to
            val columnY = IntArray(rowSize.toInt())
            placeables.forEachIndexed { index, placeable ->
                val column = index % rowSize.toInt()
                placeable.placeRelative(
                    x = column * itemWidth,
                    y = columnY[column]
                )
                columnY[column] += placeable.height
            }
        }
    }
}