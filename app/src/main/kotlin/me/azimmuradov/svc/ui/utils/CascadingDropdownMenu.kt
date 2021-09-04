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

package me.azimmuradov.svc.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import me.azimmuradov.svc.components.cartographer.menus.MenuElement
import me.azimmuradov.svc.components.cartographer.menus.MenuRoot
import me.azimmuradov.svc.components.cartographer.menus.MenuTitle


@Composable
fun CascadingDropdownMenu(
    root: MenuRoot,
    modifier: Modifier = Modifier,
    cellModifier: Modifier = Modifier,
    titleCellContent: @Composable RowScope.(title: MenuTitle) -> Unit,
    valueCellContent: @Composable RowScope.(value: MenuElement.Value) -> Unit,
) {
    val (title, elements) = root
    var expanded by remember { mutableStateOf(false) }

    Box(modifier) {
        TextButton(modifier = Modifier.matchParentSize(), onClick = { expanded = true }) {
            titleCellContent(title)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = DROPDOWN_MENU_MODIFIER,
        ) {
            MenuElements(elements, cellModifier, titleCellContent, valueCellContent)
        }
    }
}

@Composable
private fun Menu(
    menu: MenuElement.Menu,
    cellModifier: Modifier = Modifier,
    titleCellContent: @Composable RowScope.(title: MenuTitle) -> Unit,
    valueCellContent: @Composable RowScope.(value: MenuElement.Value) -> Unit,
) {
    val (title, elements) = menu
    var expanded by remember { mutableStateOf(false) }

    Box {
        DropdownMenuItem(onClick = { expanded = true }, modifier = cellModifier) {
            titleCellContent(title)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = DROPDOWN_MENU_MODIFIER,
            offset = DpOffset(x = REQUIRED_DROPDOWN_MENU_WIDTH, y = (-50).dp),
        ) {
            MenuElements(elements, cellModifier, titleCellContent, valueCellContent)
        }
    }
}


@Composable
private fun MenuElement(
    element: MenuElement,
    cellModifier: Modifier = Modifier,
    titleCellContent: @Composable RowScope.(title: MenuTitle) -> Unit,
    valueCellContent: @Composable RowScope.(value: MenuElement.Value) -> Unit,
) {
    when (element) {
        is MenuElement.Menu -> Menu(element, cellModifier, titleCellContent, valueCellContent)
        is MenuElement.Value -> DropdownMenuItem(onClick = {}, modifier = cellModifier) {
            valueCellContent(element)
        }
    }
}

@Composable
private fun MenuElements(
    elements: List<MenuElement>,
    cellModifier: Modifier = Modifier,
    titleCellContent: @Composable RowScope.(title: MenuTitle) -> Unit,
    valueCellContent: @Composable RowScope.(value: MenuElement.Value) -> Unit,
) {
    elements.forEach {
        MenuElement(it, cellModifier, titleCellContent, valueCellContent)
    }
}


private val REQUIRED_DROPDOWN_MENU_WIDTH: Dp = 250.dp
private val REQUIRED_MAX_DROPDOWN_MENU_HEIGHT: Dp = 300.dp

private val DROPDOWN_MENU_MODIFIER: Modifier =
    Modifier
        .requiredHeightIn(max = REQUIRED_MAX_DROPDOWN_MENU_HEIGHT)
        .requiredWidth(REQUIRED_DROPDOWN_MENU_WIDTH)