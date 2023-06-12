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

package io.stardewvalleydesigner.screens.editor.topmenubar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import io.stardewvalleydesigner.editor.menus.EntitySelectionMenu
import io.stardewvalleydesigner.editor.menus.filterElements
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.engine.entity.EntityType
import io.stardewvalleydesigner.utils.GlobalSettings
import io.stardewvalleydesigner.utils.Sprite
import io.stardewvalleydesigner.utils.menu.HoverableCascadingDropdownMenu


@Composable
fun EntitySelectionMenu(
    menu: EntitySelectionMenu,
    disallowedTypes: Set<EntityType>,
    onEntitySelection: (Entity<*>) -> Unit,
) {
    val filteredMenu = menu.filterElements(disallowedTypes)
    val wordList = GlobalSettings.strings

    Box(modifier = Modifier.fillMaxHeight().width(REQUIRED_DROPDOWN_MENU_WIDTH)) {
        HoverableCascadingDropdownMenu(
            menu = filteredMenu,

            shape = RectangleShape,

            menuRootModifierProvider = { hovered ->
                Modifier.background(
                    color = if (hovered) Color.White.copy(alpha = 0.15f) else Color.Transparent
                )
            },
            menuRootContent = { root, _ ->
                Row(
                    modifier = Modifier.fillMaxSize().align(Alignment.Center),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = wordList.menuTitle(root),
                        color = MaterialTheme.colors.onPrimary.copy(alpha = LocalContentAlpha.current),
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            },
            menuModifierProvider = { DROPDOWN_MENU_MODIFIER },

            submenuRootModifierProvider = { hovered ->
                Modifier
                    .fillMaxSize()
                    .background(color = if (hovered) Color.Black.copy(alpha = 0.2f) else Color.Transparent)
            },
            submenuRootContent = { root, hovered ->
                val rotation by animateFloatAsState(if (hovered) 180f else 0f)

                Text(
                    text = wordList.menuTitle(root),
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.subtitle1
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.rotate(rotation)
                )
                Spacer(modifier = Modifier.width(8.dp))
            },
            submenuModifierProvider = { DROPDOWN_MENU_MODIFIER },

            itemModifierProvider = { root, hovered ->
                Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .background(color = if (hovered) Color.Black.copy(alpha = 0.15f) else Color.Transparent)
                    .clickable { onEntitySelection(root) }
            },
            itemValueContent = { value, _ ->
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    modifier = Modifier.wrapContentSize(),
                    shape = RoundedCornerShape(5.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colors.secondaryVariant),
                    elevation = 3.dp,
                ) {
                    Sprite(entity = value, modifier = Modifier.size(36.dp).padding(3.dp))
                }
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = wordList.entity(value),
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.subtitle2
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
