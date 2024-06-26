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

package io.stardewvalleydesigner.ui.component.editor.screen.topmenu

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.cmplib.menu.DropdownMenuStyle
import io.stardewvalleydesigner.cmplib.menu.HoverableDropdownMenu
import io.stardewvalleydesigner.component.editor.EditorIntent
import io.stardewvalleydesigner.component.editor.menus.OptionsMenu
import io.stardewvalleydesigner.designformat.models.Options
import io.stardewvalleydesigner.designformat.models.OptionsItemValue
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings


@Composable
internal fun RowScope.OptionsMenu(
    options: Options,
    menu: OptionsMenu,
    intentConsumer: (EditorIntent.Options) -> Unit,
) {
    val wordList = GlobalSettings.strings

    Box(modifier = Modifier.aspectRatio(1f).fillMaxHeight()) {
        HoverableDropdownMenu(
            menu = menu,

            dropdownMenuStyle = DropdownMenuStyle.of(shape = RectangleShape),

            menuRootContent = { _, hovered ->
                val rotation by animateFloatAsState(if (hovered) 180f else 0f)

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp).rotate(rotation).fillMaxSize(),
                        tint = MaterialTheme.colors.onPrimary,
                    )
                }
            },
            menuModifierProvider = { DROPDOWN_MENU_MODIFIER },

            itemContent = { value, _ ->
                when (value) {
                    is OptionsItemValue.Toggleable -> ToggleableOption(
                        name = wordList.optionTitle(value),
                        checked = options.toggleables.getValue(value),
                        onCheckedChange = { intentConsumer(EditorIntent.Options.Toggle(value, it)) }
                    )
                }
            },
            itemModifierProvider = { _, hovered ->
                Modifier
                    .fillMaxSize()
                    .background(color = if (hovered) Color.Black.copy(alpha = 0.15f) else Color.Transparent)
            },
        )
    }
}


@Composable
private fun RowScope.ToggleableOption(name: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Checkbox(checked, onCheckedChange, Modifier.size(32.dp))
    Spacer(modifier = Modifier.width(12.dp))
    Text(
        text = name,
        modifier = Modifier.weight(1f),
        style = MaterialTheme.typography.subtitle2
    )
}


private val REQUIRED_DROPDOWN_MENU_WIDTH: Dp = 200.dp
private val REQUIRED_MAX_DROPDOWN_MENU_HEIGHT: Dp = 304.dp

private val DROPDOWN_MENU_MODIFIER: Modifier =
    Modifier
        .requiredHeightIn(max = REQUIRED_MAX_DROPDOWN_MENU_HEIGHT)
        .requiredWidth(REQUIRED_DROPDOWN_MENU_WIDTH)
