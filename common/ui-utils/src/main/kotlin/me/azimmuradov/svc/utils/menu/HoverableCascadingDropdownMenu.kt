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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerMoveFilter


@Composable
fun <MenuRoot, SubmenuRoot, ItemValue> HoverableCascadingDropdownMenu(
    menu: Menu<MenuRoot, SubmenuRoot, ItemValue>,

    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),

    menuRootModifierProvider: ModifierProvider = { Modifier },
    menuRootContent: MenuRootContent<MenuRoot>,
    menuModifierProvider: ModifierProvider = { Modifier },

    submenuRootModifierProvider: ModifierProvider = { Modifier },
    submenuRootContent: MenuElementRootContent<SubmenuRoot>,
    submenuModifierProvider: ModifierProvider = { Modifier },

    itemModifierProvider: MenuElementRootModifierProvider<ItemValue> = { _, _ -> Modifier },
    itemValueContent: MenuElementRootContent<ItemValue>,
) {
    val (root, elements) = menu
    val enabled = elements.isNotEmpty()
    var hovered by remember { mutableStateOf(false) }

    Box(modifier = if (enabled) {
        Modifier.pointerMoveFilter(
            onMove = { hovered = true; false },
            onExit = { hovered = false; false },
            onEnter = { hovered = true; false },
        )
    } else {
        Modifier
    }.then(menuRootModifierProvider(hovered))) {
        val contentAlpha = if (enabled) ContentAlpha.high else ContentAlpha.disabled
        CompositionLocalProvider(LocalContentAlpha provides contentAlpha) {
            menuRootContent(root, hovered)
        }

        MaterialDropdownMenu(
            expanded = hovered,
            shape = shape,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            preferredMenuPositioning = preferredToBeVerticalMenu,
            modifier = menuModifierProvider(hovered),
        ) {
            items(elements) { element ->
                when (element) {
                    is MenuElement.Submenu -> Submenu(
                        submenu = element,
                        shape = shape,
                        backgroundColor = backgroundColor,
                        contentColor = contentColor,
                        submenuRootModifierProvider = submenuRootModifierProvider,
                        submenuRootContent = submenuRootContent,
                        submenuModifierProvider = submenuModifierProvider,
                        itemModifierProvider = itemModifierProvider,
                        itemValueContent = itemValueContent,
                    )
                    is MenuElement.Item -> Item(
                        item = element,
                        itemModifierProvider = itemModifierProvider,
                        itemValueContent = itemValueContent,
                    )
                }
            }
        }
    }
}


// Actual private implementations

@Composable
private fun <SubmenuRoot, ItemValue> Submenu(
    submenu: MenuElement.Submenu<SubmenuRoot, ItemValue>,

    shape: Shape,
    backgroundColor: Color,
    contentColor: Color,

    submenuRootModifierProvider: ModifierProvider,
    submenuRootContent: MenuElementRootContent<SubmenuRoot>,
    submenuModifierProvider: ModifierProvider,

    itemModifierProvider: MenuElementRootModifierProvider<ItemValue>,
    itemValueContent: MenuElementRootContent<ItemValue>,
) {
    val (root, elements) = submenu
    var hovered by remember { mutableStateOf(false) }

    Box(Modifier.pointerMoveFilter(
        onMove = { hovered = true; false },
        onExit = { hovered = false; false },
        onEnter = { hovered = true; false },
    )) {
        MaterialDropdownMenuItem(submenuRootModifierProvider(hovered)) {
            submenuRootContent(root, hovered)
        }

        MaterialDropdownMenu(
            expanded = hovered,
            shape = shape,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            preferredMenuPositioning = preferredToBeHorizontalMenu,
            modifier = submenuModifierProvider(hovered),
        ) {
            items(elements) { element ->
                when (element) {
                    is MenuElement.Submenu -> Submenu(
                        submenu = element,
                        shape = shape,
                        backgroundColor = backgroundColor,
                        contentColor = contentColor,
                        submenuRootModifierProvider = submenuRootModifierProvider,
                        submenuRootContent = submenuRootContent,
                        submenuModifierProvider = submenuModifierProvider,
                        itemModifierProvider = itemModifierProvider,
                        itemValueContent = itemValueContent,
                    )
                    is MenuElement.Item -> Item(
                        item = element,
                        itemModifierProvider = itemModifierProvider,
                        itemValueContent = itemValueContent,
                    )
                }
            }
        }
    }
}

@Composable
private fun <SubmenuRoot, ItemValue> Item(
    item: MenuElement.Item<SubmenuRoot, ItemValue>,

    itemModifierProvider: MenuElementRootModifierProvider<ItemValue>,
    itemValueContent: MenuElementRootContent<ItemValue>,
) {
    val (value) = item
    var hovered by remember { mutableStateOf(false) }

    MaterialDropdownMenuItem(Modifier.pointerMoveFilter(
        onMove = { hovered = true; false },
        onExit = { hovered = false; false },
        onEnter = { hovered = true; false },
    ).then(itemModifierProvider(value, hovered))) {
        itemValueContent(value, hovered)
    }
}


private typealias ModifierProvider = @Composable (hovered: Boolean) -> Modifier
private typealias MenuElementRootModifierProvider<Root> = @Composable (root: Root, hovered: Boolean) -> Modifier
private typealias MenuRootContent<Root> = @Composable BoxScope.(root: Root, hovered: Boolean) -> Unit
private typealias MenuElementRootContent<Root> = @Composable RowScope.(root: Root, hovered: Boolean) -> Unit