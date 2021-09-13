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

package me.azimmuradov.svc.ui.screens.cartographer

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import me.azimmuradov.svc.components.cartographer.Cartographer
import me.azimmuradov.svc.components.cartographer.menus.EntitySelectionMenu
import me.azimmuradov.svc.components.cartographer.menus.entityselection.*
import me.azimmuradov.svc.components.cartographer.menus.filterElements
import me.azimmuradov.svc.engine.llsvc.entity.SvcEntityType
import me.azimmuradov.svc.settings.languages.Language
import me.azimmuradov.svc.utils.drawSpriteBy
import me.azimmuradov.svc.utils.menu.HoverableCascadingDropdownMenu


@Composable
fun TopMenu(
    component: Cartographer,
    modifier: Modifier = Modifier,
) {
    val language = component.models.value.settings.language

    Row(modifier) {
        // TODO : File Menu (it is working title)
        EntitySelectionMenu(languageCartographer = language.cartographer, menu = BuildingsMenu)
        EntitySelectionMenu(languageCartographer = language.cartographer, menu = CommonEquipmentMenu)
        EntitySelectionMenu(languageCartographer = language.cartographer, menu = FurnitureMenu)
        EntitySelectionMenu(languageCartographer = language.cartographer, menu = FarmElementsMenu)
        EntitySelectionMenu(languageCartographer = language.cartographer, menu = TerrainElementsMenu)
        // TODO : Entity Search
        // TODO : Layout Selection Menu
        // TODO : Options Menu
    }
}


// TODO
@Composable
private fun RowScope.EntitySelectionMenu(
    languageCartographer: Language.Cartographer,
    menu: EntitySelectionMenu,
    disallowedTypes: List<SvcEntityType>? = null,
) {
    val filteredMenu = disallowedTypes?.let { menu.filterElements(it) } ?: menu

    Box(modifier = Modifier.fillMaxHeight().weight(1f)) {
        HoverableCascadingDropdownMenu(
            menu = filteredMenu,

            shape = RectangleShape,

            menuRootModifierProvider = {
                Modifier.background(color = if (it) Color.Green.copy(alpha = 0.3f) else Color.Transparent)
            },
            menuRootContent = { root, hovered ->
                val rotation by animateFloatAsState(if (hovered) 180f else 0f)

                Row(
                    modifier = Modifier.fillMaxSize().align(Alignment.Center),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = languageCartographer.menuTitle(root),
                        fontSize = 17.sp,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        null,
                        modifier = Modifier.rotate(rotation)
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

            itemModifierProvider = {
                Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .background(color = if (it) Color.Black.copy(alpha = 0.1f) else Color.Transparent)
            },
            itemValueContent = { value, _ ->
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    modifier = Modifier.wrapContentSize(),
                    shape = RoundedCornerShape(5.dp),
                    border = BorderStroke(1.dp, Color.DarkGray),
                    elevation = 3.dp,
                ) {
                    Canvas(modifier = Modifier.size(36.dp).align(Alignment.CenterVertically).padding(3.dp)) {
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


private val REQUIRED_DROPDOWN_MENU_WIDTH: Dp = 280.dp
private val REQUIRED_MAX_DROPDOWN_MENU_HEIGHT: Dp = 300.dp

private val DROPDOWN_MENU_MODIFIER: Modifier =
    Modifier
        .requiredHeightIn(max = REQUIRED_MAX_DROPDOWN_MENU_HEIGHT)
        .requiredWidth(REQUIRED_DROPDOWN_MENU_WIDTH)