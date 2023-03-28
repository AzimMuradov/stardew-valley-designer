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

package me.azimmuradov.svc.utils.menu

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.*


@OptIn(ExperimentalComposeUiApi::class)
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

    val menuStacks: MutableList<List<MenuElement<SubmenuRoot, ItemValue>>> = remember { mutableStateListOf() }

    val hovered = menuStacks.isNotEmpty()

    Box(
        modifier = Modifier
            .onPointerEvent(
                eventType = PointerEventType.Enter,
                pass = PointerEventPass.Initial,
                onEvent = { if (emptyList() !in menuStacks) menuStacks.add(emptyList()) }
            )
            .onPointerEvent(
                eventType = PointerEventType.Exit,
                pass = PointerEventPass.Final,
                onEvent = { menuStacks.remove(emptyList()) }
            )
            .then(menuRootModifierProvider(enabled && hovered))
    ) {
        val contentAlpha = if (enabled) ContentAlpha.high else ContentAlpha.disabled
        CompositionLocalProvider(LocalContentAlpha provides contentAlpha) {
            menuRootContent(root, enabled && hovered)
        }

        MaterialDropdownMenu(
            expanded = enabled && hovered,
            onDismissRequest = menuStacks::clear,
            shape = shape,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            preferredMenuPositioning = preferredToBeVerticalMenu,
            modifier = Modifier
                .onPointerEvent(
                    eventType = PointerEventType.Enter,
                    pass = PointerEventPass.Initial,
                    onEvent = { if (emptyList() !in menuStacks) menuStacks.add(emptyList()) }
                )
                .onPointerEvent(
                    eventType = PointerEventType.Exit,
                    pass = PointerEventPass.Final,
                    onEvent = { menuStacks.remove(emptyList()) }
                )
                .then(menuModifierProvider(enabled && hovered)),
        ) {
            items(elements) { element ->
                when (element) {
                    is MenuElement.Submenu -> Submenu(
                        menuStacks,
                        menuStack = listOf(element),
                        onHoverEnter = { s ->
                            menuStacks.removeAll { it.isNotEmpty() && s.none { e -> e in it } }
                            if (s !in menuStacks) menuStacks.add(s)
                        },
                        onHoverExit = { menuStacks.remove(it) },
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
                        menuStacks,
                        menuStack = listOf(element),
                        onHoverEnter = { s ->
                            menuStacks.removeAll { it.isNotEmpty() && s.none { e -> e in it } }
                            if (s !in menuStacks) menuStacks.add(s)
                        },
                        onHoverExit = { menuStacks.remove(it) },
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
    menuStacks: MutableList<List<MenuElement<SubmenuRoot, ItemValue>>>,
    menuStack: List<MenuElement<SubmenuRoot, ItemValue>>,

    onHoverEnter: (List<MenuElement<SubmenuRoot, ItemValue>>) -> Unit,
    onHoverExit: (List<MenuElement<SubmenuRoot, ItemValue>>) -> Unit,

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

    val hovered = menuStacks.any { submenu in it }

    Box(
        modifier = Modifier
            .onPointerEvent(
                eventType = PointerEventType.Enter,
                pass = PointerEventPass.Initial,
                onEvent = { onHoverEnter(menuStack) }
            )
            .onPointerEvent(
                eventType = PointerEventType.Exit,
                pass = PointerEventPass.Final,
                onEvent = { onHoverExit(menuStack) }
            )
    ) {
        MaterialDropdownMenuItem(submenuRootModifierProvider(hovered)) {
            submenuRootContent(root, hovered)
        }

        MaterialDropdownMenu(
            expanded = hovered,
            onDismissRequest = menuStacks::clear,
            shape = shape,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            preferredMenuPositioning = preferredToBeHorizontalMenu,
            modifier = Modifier
                .onPointerEvent(
                    eventType = PointerEventType.Enter,
                    pass = PointerEventPass.Initial,
                    onEvent = { onHoverEnter(menuStack) }
                )
                .onPointerEvent(
                    eventType = PointerEventType.Exit,
                    pass = PointerEventPass.Final,
                    onEvent = { onHoverExit(menuStack) }
                )
                .then(submenuModifierProvider(hovered)),
        ) {
            items(elements) { element ->
                when (element) {
                    is MenuElement.Submenu -> Submenu(
                        menuStacks,
                        menuStack = menuStack + element,
                        onHoverEnter = onHoverEnter,
                        onHoverExit = onHoverExit,
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
                        menuStacks,
                        menuStack = menuStack + element,
                        onHoverEnter = onHoverEnter,
                        onHoverExit = onHoverExit,
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
    menuStacks: List<List<MenuElement<SubmenuRoot, ItemValue>>>,
    menuStack: List<MenuElement<SubmenuRoot, ItemValue>>,

    onHoverEnter: (List<MenuElement<SubmenuRoot, ItemValue>>) -> Unit,
    onHoverExit: (List<MenuElement<SubmenuRoot, ItemValue>>) -> Unit,

    item: MenuElement.Item<SubmenuRoot, ItemValue>,

    itemModifierProvider: MenuElementRootModifierProvider<ItemValue>,
    itemValueContent: MenuElementRootContent<ItemValue>,
) {
    val hovered = menuStacks.any { item in it }

    MaterialDropdownMenuItem(
        modifier = Modifier
            .pointerInput(menuStack) {
                awaitPointerEventScope {
                    when (currentEvent.type) {
                        PointerEventType.Enter -> onHoverEnter(menuStack)
                        PointerEventType.Exit -> onHoverExit(menuStack)
                    }
                }
            }
            .then(itemModifierProvider(item.value, hovered))
    ) {
        itemValueContent(item.value, hovered)
    }
}


private typealias ModifierProvider = @Composable (hovered: Boolean) -> Modifier
private typealias MenuElementRootModifierProvider<Root> = @Composable (root: Root, hovered: Boolean) -> Modifier
private typealias MenuRootContent<Root> = @Composable BoxScope.(root: Root, hovered: Boolean) -> Unit
private typealias MenuElementRootContent<Root> = @Composable RowScope.(root: Root, hovered: Boolean) -> Unit
