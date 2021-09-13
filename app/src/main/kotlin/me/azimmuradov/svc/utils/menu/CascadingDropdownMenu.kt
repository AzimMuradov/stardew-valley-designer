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

package me.azimmuradov.svc.utils.menu

import androidx.compose.foundation.layout.*
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


// @Composable
// fun <Title, V> CascadingDropdownMenu(
//     menu: Menu<Title, V>,
//     modifier: Modifier = Modifier,
//     menuAnchorContent: @Composable () -> Unit,
//     submenuAnchorContent: @Composable () -> Unit,
//     itemContent: @Composable RowScope.() -> Unit,
// ) {
//     val (title, elements) = menu
//     var expanded by remember { mutableStateOf(false) }
//
//     Box(modifier) {
//         menuAnchorContent()
//         CustomDropdownMenu(
//             expanded = expanded,
//             onDismissRequest = { expanded = false },
//             modifier = DROPDOWN_MENU_MODIFIER,
//         ) {
//             elements.forEach {
//                 MenuElement(it, Modifier, submenuAnchorContent, itemContent)
//             }
//         }
//     }
// }
//
// @Composable
// private fun <Title, V> MenuElement(
//     element: MenuElement<Title, V>,
//     modifier: Modifier = Modifier,
//     submenuAnchorContent: @Composable () -> Unit,
//     itemContent: @Composable RowScope.() -> Unit,
// ) {
//     when (element) {
//         is MenuElement.Submenu -> Submenu(element, Modifier, submenuAnchorContent, itemContent)
//         is MenuElement.Item -> Item(element, Modifier, itemContent)
//     }
// }
//
// @Composable
// private fun <Title, V> Submenu(
//     submenu: MenuElement.Submenu<Title, V>,
//     modifier: Modifier = Modifier,
//     submenuAnchorContent: @Composable () -> Unit,
//     itemContent: @Composable RowScope.() -> Unit,
// ) {
//     val (title, elements) = submenu
//     var expanded by remember { mutableStateOf(false) }
//
//     Box {
//         submenuAnchorContent()
//
//         CustomDropdownMenu(
//             expanded = expanded,
//             onDismissRequest = { expanded = false },
//             modifier = DROPDOWN_MENU_MODIFIER,
//             offset = DpOffset(x = REQUIRED_DROPDOWN_MENU_WIDTH, y = (-48).dp),
//         ) {
//             elements.forEach {
//                 MenuElement(it, Modifier, submenuAnchorContent, itemContent)
//             }
//         }
//     }
// }
//
// @Composable
// private fun <Title, V> Item(
//     item: MenuElement.Item<Title, V>,
//     modifier: Modifier = Modifier,
//     itemContent: @Composable RowScope.() -> Unit,
// ) {
//     val (value) = item
//     var expanded by remember { mutableStateOf(false) }
//
//     CustomDropdownMenuItem(
//         onClick = {},
//         modifier = modifier,
//         contentPadding = DROPDOWN_MENU_ITEM_PADDING,
//         content = itemContent,
//     )
// }


@Composable
fun <Title, V> CascadingDropdownMenu(
    menu: Menu<Title, V>,
    modifier: Modifier = Modifier,
    cellModifier: Modifier = Modifier,
    titleCellContent: @Composable RowScope.(title: Title) -> Unit,
    valueCellContent: @Composable RowScope.(item: MenuElement.Item<Title, V>) -> Unit,
) {
    val (title, elements) = menu
    var expanded by remember { mutableStateOf(false) }

    Box(modifier) {
        TextButton(modifier = Modifier.matchParentSize(), onClick = { expanded = true }) {
            titleCellContent(title)
        }
        CustomDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = DROPDOWN_MENU_MODIFIER,
        ) {
            elements.forEach {
                MenuElement(it, cellModifier, titleCellContent, valueCellContent)
            }
        }
    }
}


@Composable
private fun <Title, V> MenuElement(
    element: MenuElement<Title, V>,
    cellModifier: Modifier = Modifier,
    titleCellContent: @Composable RowScope.(title: Title) -> Unit,
    valueCellContent: @Composable RowScope.(item: MenuElement.Item<Title, V>) -> Unit,
) {
    when (element) {
        is MenuElement.Submenu -> Submenu(element, cellModifier, titleCellContent, valueCellContent)
        is MenuElement.Item -> CustomDropdownMenuItem(
            onClick = {},
            modifier = cellModifier,
            contentPadding = DROPDOWN_MENU_ITEM_PADDING,
        ) {
            valueCellContent(element)
        }
    }
}

@Composable
private fun <Title, V> Submenu(
    submenu: MenuElement.Submenu<Title, V>,
    cellModifier: Modifier = Modifier,
    titleCellContent: @Composable RowScope.(title: Title) -> Unit,
    valueCellContent: @Composable RowScope.(item: MenuElement.Item<Title, V>) -> Unit,
) {
    val (title, elements) = submenu
    var expanded by remember { mutableStateOf(false) }

    Box {
        CustomDropdownMenuItem(
            onClick = { expanded = true },
            modifier = cellModifier,
            contentPadding = DROPDOWN_MENU_ITEM_PADDING,
        ) {
            titleCellContent(title)
        }

        CustomDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            // modifier = DROPDOWN_MENU_MODIFIER,
            preferredMenuPositioning = preferredToBeHorizontalMenu,
        ) {
            elements.forEach {
                MenuElement(it, cellModifier, titleCellContent, valueCellContent)
            }
        }
    }
}


private val REQUIRED_DROPDOWN_MENU_WIDTH: Dp = 200.dp
private val REQUIRED_MAX_DROPDOWN_MENU_HEIGHT: Dp = 300.dp

private val DROPDOWN_MENU_MODIFIER: Modifier =
    Modifier
        .requiredHeightIn(max = REQUIRED_MAX_DROPDOWN_MENU_HEIGHT)
        .requiredWidth(REQUIRED_DROPDOWN_MENU_WIDTH)

private val DROPDOWN_MENU_ITEM_PADDING: PaddingValues = PaddingValues(
    start = 16.dp,
    top = 3.dp,
    end = 16.dp,
    bottom = 3.dp,
)