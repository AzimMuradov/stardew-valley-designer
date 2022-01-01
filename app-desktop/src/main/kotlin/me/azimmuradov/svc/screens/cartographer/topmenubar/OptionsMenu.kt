/*
 * Copyright 2021-2021 Azim Muradov
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

package me.azimmuradov.svc.screens.cartographer.topmenubar

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
import androidx.compose.ui.unit.sp
import me.azimmuradov.svc.components.screens.cartographer.Options
import me.azimmuradov.svc.components.screens.cartographer.menus.OptionsItemValue
import me.azimmuradov.svc.components.screens.cartographer.menus.OptionsMenu
import me.azimmuradov.svc.settings.Lang
import me.azimmuradov.svc.utils.menu.ClickableCascadingDropdownMenu


// TODO


@Composable
fun RowScope.OptionsMenu(
    options: Options,
    lang: Lang,
    menu: OptionsMenu,
) {
    Box(modifier = Modifier.aspectRatio(1f).fillMaxHeight()) {
        ClickableCascadingDropdownMenu(
            menu = menu,

            shape = RectangleShape,

            menuRootModifierProvider = {
                Modifier
                    .background(
                        color = if (it) Color(0xFF629749) else Color.Transparent
                    )
            },
            menuRootContent = { _, hovered ->
                val rotation by animateFloatAsState(if (hovered) 180f else 0f)

                Row(
                    modifier = Modifier.fillMaxSize().align(Alignment.Center),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp).rotate(rotation).fillMaxSize().weight(1f),
                        tint = MaterialTheme.colors.onPrimary,
                    )
                }
            },
            menuModifierProvider = { DROPDOWN_MENU_MODIFIER },

            submenuRootModifierProvider = { Modifier },
            submenuRootContent = { _, _ -> },
            submenuModifierProvider = { DROPDOWN_MENU_MODIFIER },

            itemModifierProvider = { _, hoverable ->
                Modifier
                    .fillMaxSize()
                    .background(color = if (hoverable) Color.Black.copy(alpha = 0.1f) else Color.Transparent)
            },
            itemValueContent = { value, _ ->
                when (value) {
                    OptionsItemValue.ShowAxis -> {
                        Spacer(modifier = Modifier.width(8.dp))
                        Checkbox(
                            checked = options.showAxis,
                            onCheckedChange = { options.showAxis = it }
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "Show axis",
                            modifier = Modifier.weight(1f),
                            fontSize = 13.sp,
                        )
                    }
                    OptionsItemValue.ShowGrid -> {
                        Spacer(modifier = Modifier.width(8.dp))
                        Checkbox(
                            checked = options.showGrid,
                            onCheckedChange = { options.showGrid = it }
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "Show grid",
                            modifier = Modifier.weight(1f),
                            fontSize = 13.sp,
                        )
                    }
                }
            },
        )
    }
}


private val REQUIRED_DROPDOWN_MENU_WIDTH: Dp = 224.dp
private val REQUIRED_MAX_DROPDOWN_MENU_HEIGHT: Dp = 304.dp

private val DROPDOWN_MENU_MODIFIER: Modifier =
    Modifier
        .requiredHeightIn(max = REQUIRED_MAX_DROPDOWN_MENU_HEIGHT)
        .requiredWidth(REQUIRED_DROPDOWN_MENU_WIDTH)