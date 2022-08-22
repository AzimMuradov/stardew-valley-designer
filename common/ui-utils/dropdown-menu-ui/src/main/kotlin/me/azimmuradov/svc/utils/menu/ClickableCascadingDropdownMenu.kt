/*
 * Copyright 2021-2022 Azim Muradov
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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun <MenuRoot, SubmenuRoot, ItemValue> ClickableCascadingDropdownMenu(
    menu: Menu<MenuRoot, SubmenuRoot, ItemValue>,

    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),

    menuRootModifierProvider: ClickableModifierProvider = { Modifier },
    menuRootContent: ClickableMenuRootContent<MenuRoot>,
    menuModifierProvider: ClickableModifierProvider = { Modifier },

    submenuRootModifierProvider: ClickableModifierProvider = { Modifier },
    submenuRootContent: ClickableMenuElementRootContent<SubmenuRoot>,
    submenuModifierProvider: ClickableModifierProvider = { Modifier },

    itemModifierProvider: ClickableMenuElementRootModifierProvider<ItemValue> = { _, _ -> Modifier },
    itemValueContent: ClickableMenuElementRootContent<ItemValue>,
) {
    val (root, elements) = menu
    val enabled = elements.isNotEmpty()
    var hovered by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = if (enabled) {
        Modifier
            .onPointerEvent(PointerEventType.Move) { hovered = true }
            .onPointerEvent(PointerEventType.Enter) { hovered = true }
            .onPointerEvent(PointerEventType.Exit) { hovered = false }
            .clickable { expanded = true }
    } else {
        Modifier
    }.then(menuRootModifierProvider(hovered))) {
        val contentAlpha = if (enabled) ContentAlpha.high else ContentAlpha.disabled
        CompositionLocalProvider(LocalContentAlpha provides contentAlpha) {
            menuRootContent(root, hovered)
        }

        MaterialDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = !expanded },
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun <SubmenuRoot, ItemValue> Submenu(
    submenu: MenuElement.Submenu<SubmenuRoot, ItemValue>,

    shape: Shape,
    backgroundColor: Color,
    contentColor: Color,

    submenuRootModifierProvider: ClickableModifierProvider,
    submenuRootContent: ClickableMenuElementRootContent<SubmenuRoot>,
    submenuModifierProvider: ClickableModifierProvider,

    itemModifierProvider: ClickableMenuElementRootModifierProvider<ItemValue>,
    itemValueContent: ClickableMenuElementRootContent<ItemValue>,
) {
    val (root, elements) = submenu
    var hovered by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    Box(
        Modifier
            .onPointerEvent(PointerEventType.Move) { hovered = true }
            .onPointerEvent(PointerEventType.Enter) { hovered = true }
            .onPointerEvent(PointerEventType.Exit) { hovered = false }
            .clickable { expanded = true }
    ) {
        MaterialDropdownMenuItem(submenuRootModifierProvider(hovered)) {
            submenuRootContent(root, hovered)
        }

        MaterialDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = !expanded },
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun <SubmenuRoot, ItemValue> Item(
    item: MenuElement.Item<SubmenuRoot, ItemValue>,

    itemModifierProvider: ClickableMenuElementRootModifierProvider<ItemValue>,
    itemValueContent: ClickableMenuElementRootContent<ItemValue>,
) {
    val (value) = item
    var hovered by remember { mutableStateOf(false) }

    MaterialDropdownMenuItem(
        Modifier
            .onPointerEvent(PointerEventType.Move) { hovered = true }
            .onPointerEvent(PointerEventType.Enter) { hovered = true }
            .onPointerEvent(PointerEventType.Exit) { hovered = false }
            .then(itemModifierProvider(value, hovered))
    ) {
        itemValueContent(value, hovered)
    }
}


private typealias ClickableModifierProvider = @Composable (hovered: Boolean) -> Modifier
private typealias ClickableMenuElementRootModifierProvider<Root> = @Composable (root: Root, hovered: Boolean) -> Modifier
private typealias ClickableMenuRootContent<Root> = @Composable BoxScope.(root: Root, hovered: Boolean) -> Unit
private typealias ClickableMenuElementRootContent<Root> = @Composable RowScope.(root: Root, hovered: Boolean) -> Unit