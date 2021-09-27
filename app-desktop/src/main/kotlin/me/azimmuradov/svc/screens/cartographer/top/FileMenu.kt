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

package me.azimmuradov.svc.screens.cartographer.top

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.azimmuradov.svc.components.screens.Cartographer
import me.azimmuradov.svc.components.screens.cartographer.SessionSettings
import me.azimmuradov.svc.components.screens.cartographer.menus.OptionsItemValue
import me.azimmuradov.svc.components.screens.cartographer.menus.OptionsMenu
import me.azimmuradov.svc.engine.entity.ids.Equipment
import me.azimmuradov.svc.utils.drawSpriteBy
import me.azimmuradov.svc.utils.menu.HoverableCascadingDropdownMenu


// TODO


@Composable
fun RowScope.FileMenu(
    models: Cartographer.Model,
    updateSessionSettings: (SessionSettings) -> Unit,
    menu: OptionsMenu,
) {
    Box(modifier = Modifier.aspectRatio(1f).fillMaxHeight()) {
        HoverableCascadingDropdownMenu(
            menu = menu,

            shape = RectangleShape,

            menuRootModifierProvider = {
                Modifier
                    .background(
                        color = if (it) Color(0xFF629749) else Color.Transparent
                    )
            },
            menuRootContent = { _, _ ->
                Row(
                    modifier = Modifier.fillMaxSize().align(Alignment.Center),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Canvas(modifier = Modifier.size(32.dp)) {
                        drawSpriteBy(
                            id = Equipment.SimpleEquipment.Furnace,
                            layoutSize = size,
                        )
                    }
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
                            checked = models.sessionSettings.showAxis,
                            onCheckedChange = { updateSessionSettings(models.sessionSettings.copy(showAxis = !models.sessionSettings.showAxis)) }
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
                            checked = models.sessionSettings.showGrid,
                            onCheckedChange = { updateSessionSettings(models.sessionSettings.copy(showGrid = !models.sessionSettings.showGrid)) }
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