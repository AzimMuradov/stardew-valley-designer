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

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.azimmuradov.svc.cartographer.palette.MutablePalette
import me.azimmuradov.svc.components.screens.cartographer.menus.EntitySelectionMenu
import me.azimmuradov.svc.components.screens.cartographer.menus.filterElements
import me.azimmuradov.svc.engine.entity.EntityType
import me.azimmuradov.svc.settings.languages.Language
import me.azimmuradov.svc.utils.drawSpriteBy
import me.azimmuradov.svc.utils.menu.HoverableCascadingDropdownMenu


// TODO


@Composable
fun RowScope.EntitySelectionMenu(
    palette: MutablePalette,
    languageCartographer: Language.Cartographer,
    menu: EntitySelectionMenu,
    disallowedTypes: List<EntityType>? = null,
) {
    val filteredMenu = disallowedTypes?.let { menu.filterElements(it) } ?: menu

    var myText by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxHeight().width(REQUIRED_DROPDOWN_MENU_WIDTH)) {
        HoverableCascadingDropdownMenu(
            menu = filteredMenu,

            shape = RectangleShape,

            menuRootModifierProvider = {
                Modifier
                    .background(
                        color = if (it) Color(0xFF629749) else Color.Transparent
                    )
            },
            menuRootContent = { root, _ ->
                Row(
                    modifier = Modifier.fillMaxSize().align(Alignment.Center),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = languageCartographer.menuTitle(root),
                        color = MaterialTheme.colors.onPrimary.copy(alpha = LocalContentAlpha.current),
                        fontSize = 16.sp,
                    )
                }
            },
            menuModifierProvider = { DROPDOWN_MENU_MODIFIER },

            submenuRootModifierProvider = {
                Modifier
                    .fillMaxSize()
                    .background(color = if (it) Color.Black.copy(alpha = 0.2f) else Color.Transparent)
            },
            submenuRootContent = { root, hovered ->
                val rotation by animateFloatAsState(if (hovered) 180f else 0f)

                Text(
                    text = languageCartographer.menuTitle(root),
                    modifier = Modifier.weight(1f),
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    Icons.Filled.ArrowDropDown,
                    null,
                    modifier = Modifier.rotate(rotation)
                )
                Spacer(modifier = Modifier.width(8.dp))
            },
            submenuModifierProvider = { DROPDOWN_MENU_MODIFIER },

            itemModifierProvider = { root, hovered ->
                Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .background(color = if (hovered) Color.Black.copy(alpha = 0.1f) else Color.Transparent)
                    .clickable { palette.putInUse(root) }
            },
            itemValueContent = { value, _ ->
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    modifier = Modifier.wrapContentSize(),
                    shape = RoundedCornerShape(5.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colors.secondaryVariant),
                    elevation = 3.dp,
                ) {
                    Canvas(modifier = Modifier.size(36.dp).padding(3.dp)) {
                        drawSpriteBy(
                            id = value,
                            layoutSize = size,
                        )
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = languageCartographer.entity(value),
                    modifier = Modifier.weight(1f),
                    fontSize = 13.sp,
                )
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