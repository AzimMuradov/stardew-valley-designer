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

package io.stardewvalleydesigner.uilib.menu

import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import io.stardewvalleydesigner.uilib.menu.impl.DropdownMenuSpecs


data class DropdownMenuStyle internal constructor(
    val shape: Shape,
    val backgroundColor: Color,
    val contentColor: Color,
    val elevation: Dp,
) {

    companion object {

        @Composable
        fun of(
            shape: Shape = MaterialTheme.shapes.medium,
            backgroundColor: Color = MaterialTheme.colors.surface,
            contentColor: Color = contentColorFor(backgroundColor),
            elevation: Dp = DropdownMenuSpecs.MenuElevation,
        ) = DropdownMenuStyle(shape, backgroundColor, contentColor, elevation)
    }
}
