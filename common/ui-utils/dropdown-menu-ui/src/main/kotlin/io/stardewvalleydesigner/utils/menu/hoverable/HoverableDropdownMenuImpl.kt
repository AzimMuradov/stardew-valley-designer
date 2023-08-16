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

package io.stardewvalleydesigner.utils.menu.hoverable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.utils.menu.DropdownMenuStyle
import io.stardewvalleydesigner.utils.menu.MenuElement
import io.stardewvalleydesigner.utils.menu.impl.*


@Composable
internal fun <SubmenuRoot, ItemValue> HoverableMenuSubmenu(
    menuStack: MutableList<List<MenuElement<SubmenuRoot, ItemValue>>>,
    menu: List<MenuElement<SubmenuRoot, ItemValue>>,

    submenu: MenuElement.Submenu<SubmenuRoot, ItemValue>,

    dropdownMenuStyle: DropdownMenuStyle = DropdownMenuStyle.of(),

    inContainerWithScrollbar: Boolean,

    submenuParameters: HoverableMenuSubmenuParameters<SubmenuRoot>,
    itemParameters: HoverableMenuItemParameters<ItemValue>,
) {
    val (root, elements) = submenu
    val (submenuRootContent, submenuRootModifierProvider, submenuModifierProvider) = submenuParameters
    val hovered = menuStack.any { submenu in it }

    Box(modifier = Modifier.hoverProcessedFor(menuStack, menu)) {
        CustomDropdownMenuItem(
            submenuRootModifierProvider(root, hovered)
                .padding(end = if (inContainerWithScrollbar) DropdownMenuSpecs.EndPaddingWhenHasScrollbar else 0.dp)
        ) {
            submenuRootContent(root, hovered)
        }

        CustomDropdownMenu(
            expanded = hovered,
            onDismissRequest = menuStack::clear,
            modifier = Modifier
                .hoverProcessedFor(menuStack, menu)
                .then(submenuModifierProvider(hovered)),
            dropdownMenuStyle = dropdownMenuStyle,
            preferredMenuPositioning = preferredToBeHorizontalMenu,
        ) {
            items(elements) { element ->
                when (element) {
                    is MenuElement.Submenu -> HoverableMenuSubmenu(
                        menuStack = menuStack,
                        menu = menu + element,
                        submenu = element,
                        dropdownMenuStyle = dropdownMenuStyle,
                        inContainerWithScrollbar = this@CustomDropdownMenu.hasScrollbar,
                        submenuParameters = submenuParameters,
                        itemParameters = itemParameters
                    )

                    is MenuElement.Item -> HoverableMenuItem(
                        menuStack = menuStack,
                        menu = menu + element,
                        item = element,
                        inContainerWithScrollbar = this@CustomDropdownMenu.hasScrollbar,
                        itemParameters = itemParameters
                    )
                }
            }
        }
    }
}

@Composable
internal fun <SubmenuRoot, ItemValue> HoverableMenuItem(
    menuStack: MutableList<List<MenuElement<SubmenuRoot, ItemValue>>>,
    menu: List<MenuElement<SubmenuRoot, ItemValue>>,

    item: MenuElement.Item<SubmenuRoot, ItemValue>,

    inContainerWithScrollbar: Boolean,

    itemParameters: HoverableMenuItemParameters<ItemValue>,
) {
    val (itemContent, itemModifierProvider) = itemParameters
    val hovered = menuStack.any { item in it }

    CustomDropdownMenuItem(
        modifier = Modifier
            .hoverProcessedFor(menuStack, menu)
            .then(itemModifierProvider(item.value, hovered))
            .padding(end = if (inContainerWithScrollbar) DropdownMenuSpecs.EndPaddingWhenHasScrollbar else 0.dp)
    ) {
        itemContent(item.value, hovered)
    }
}


@OptIn(ExperimentalComposeUiApi::class)
internal fun <SubmenuRoot, ItemValue> Modifier.hoverProcessedFor(
    menuStack: MutableList<List<MenuElement<SubmenuRoot, ItemValue>>>,
    menu: List<MenuElement<SubmenuRoot, ItemValue>>,
) = onPointerEvent(
    eventType = PointerEventType.Enter,
    pass = PointerEventPass.Initial,
    onEvent = {
        menuStack.removeAll { it.isNotEmpty() && menu.none { e -> e in it } }
        if (menu !in menuStack) menuStack.add(menu)
    }
).onPointerEvent(
    eventType = PointerEventType.Exit,
    pass = PointerEventPass.Final,
    onEvent = { menuStack.remove(menu) }
)


internal data class HoverableMenuSubmenuParameters<SubmenuRoot>(
    val rootContent: MenuElementRootContent<SubmenuRoot>,
    val rootModifierProvider: MenuElementRootModifierProvider<SubmenuRoot>,
    val modifierProvider: ModifierProvider,
)

internal data class HoverableMenuItemParameters<ItemValue>(
    val content: MenuElementRootContent<ItemValue>,
    val modifierProvider: MenuElementRootModifierProvider<ItemValue>,
)


internal typealias ModifierProvider = @Composable (hovered: Boolean) -> Modifier
internal typealias MenuElementRootModifierProvider<Root> = @Composable (root: Root, hovered: Boolean) -> Modifier

internal typealias MenuRootContent<Root> = @Composable BoxScope.(root: Root, hovered: Boolean) -> Unit
internal typealias MenuElementRootContent<Root> = @Composable RowScope.(root: Root, hovered: Boolean) -> Unit
