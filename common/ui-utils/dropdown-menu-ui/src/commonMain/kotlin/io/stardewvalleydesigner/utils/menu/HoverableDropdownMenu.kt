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

package io.stardewvalleydesigner.utils.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import io.stardewvalleydesigner.utils.menu.hoverable.*
import io.stardewvalleydesigner.utils.menu.impl.CustomDropdownMenu


@Composable
fun <MenuRoot, SubmenuRoot, ItemValue> HoverableDropdownMenu(
    menu: Menu<MenuRoot, SubmenuRoot, ItemValue>,

    dropdownMenuStyle: DropdownMenuStyle,

    menuRootModifierProvider: ModifierProvider = { Modifier },
    menuRootContent: MenuRootContent<MenuRoot>,
    menuModifierProvider: ModifierProvider = { Modifier },

    submenuRootContent: MenuElementRootContent<SubmenuRoot> = { _, _ -> },
    submenuRootModifierProvider: MenuElementRootModifierProvider<SubmenuRoot> = { _, _ -> Modifier },
    submenuModifierProvider: ModifierProvider = { Modifier },

    itemContent: MenuElementRootContent<ItemValue>,
    itemModifierProvider: MenuElementRootModifierProvider<ItemValue> = { _, _ -> Modifier },
) {
    val (root, elements) = menu
    val enabled = elements.isNotEmpty()
    val menuStack: MutableList<List<MenuElement<SubmenuRoot, ItemValue>>> = remember { mutableStateListOf() }
    val hovered = menuStack.isNotEmpty()

    Box(
        modifier = Modifier
            .hoverProcessedFor(menuStack, menu = emptyList())
            .then(menuRootModifierProvider(enabled && hovered))
    ) {
        val contentAlpha = if (enabled) ContentAlpha.high else ContentAlpha.disabled
        CompositionLocalProvider(LocalContentAlpha provides contentAlpha) {
            menuRootContent(root, enabled && hovered)
        }

        CustomDropdownMenu(
            expanded = enabled && hovered,
            onDismissRequest = menuStack::clear,
            modifier = Modifier
                .hoverProcessedFor(menuStack, menu = emptyList())
                .then(menuModifierProvider(enabled && hovered)),
            dropdownMenuStyle = dropdownMenuStyle,
        ) {
            items(elements) { element ->
                when (element) {
                    is MenuElement.Submenu -> HoverableMenuSubmenu(
                        menuStack = menuStack,
                        menu = listOf(element),
                        submenu = element,
                        dropdownMenuStyle = dropdownMenuStyle,
                        inContainerWithScrollbar = this@CustomDropdownMenu.hasScrollbar,
                        submenuParameters = HoverableMenuSubmenuParameters(
                            submenuRootContent,
                            submenuRootModifierProvider,
                            submenuModifierProvider,
                        ),
                        itemParameters = HoverableMenuItemParameters(
                            itemContent,
                            itemModifierProvider,
                        )
                    )

                    is MenuElement.Item -> HoverableMenuItem(
                        menuStack = menuStack,
                        menu = listOf(element),
                        item = element,
                        inContainerWithScrollbar = this@CustomDropdownMenu.hasScrollbar,
                        itemParameters = HoverableMenuItemParameters(
                            itemContent,
                            itemModifierProvider,
                        )
                    )
                }
            }
        }
    }
}
