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

// @OptIn(ExperimentalComposeUiApi::class)
// @Composable
// fun <MenuRoot, SubmenuRoot, ItemValue> HoverableCascadingDropdownMenu(
//     menu: Menu<HoverableData<MenuRoot>, HoverableData<SubmenuRoot>, HoverableData<ItemValue>>,
//
//     shape: Shape = MaterialTheme.shapes.medium,
//     backgroundColor: Color = MaterialTheme.colors.surface,
//     contentColor: Color = contentColorFor(backgroundColor),
//
//     menuRootModifierProvider: ModifierProvider = { Modifier },
//     menuRootContent: MenuRootContent<MenuRoot>,
//     menuModifierProvider: ModifierProvider = { Modifier },
//
//     submenuRootModifierProvider: ModifierProvider = { Modifier },
//     submenuRootContent: MenuElementRootContent<SubmenuRoot>,
//     submenuModifierProvider: ModifierProvider = { Modifier },
//
//     itemModifierProvider: MenuElementRootModifierProvider<ItemValue> = { _, _ -> Modifier },
//     itemValueContent: MenuElementRootContent<ItemValue>,
// ) {
//     val (root, elements) = menu
//     val (isHovered, value) = root
//     val enabled = elements.isNotEmpty()
//
//     Box(modifier = if (enabled) {
//         Modifier.clickable {}
//     } else {
//         Modifier
//     }.then(menuRootModifierProvider(isHovered))) {
//         val contentAlpha = if (enabled) ContentAlpha.high else ContentAlpha.disabled
//         CompositionLocalProvider(LocalContentAlpha provides contentAlpha) {
//             menuRootContent(value, isHovered)
//         }
//
//         MaterialDropdownMenu(
//             expanded = isHovered,
//             onDismissRequest = {},
//             shape = shape,
//             backgroundColor = backgroundColor,
//             contentColor = contentColor,
//             preferredMenuPositioning = preferredToBeVerticalMenu,
//             modifier = menuModifierProvider(isHovered),
//         ) {
//             items(elements) { element ->
//                 when (element) {
//                     is MenuElement.Submenu -> Submenu(
//                         submenu = element,
//                         isParentHovered = isHovered,
//                         shape = shape,
//                         backgroundColor = backgroundColor,
//                         contentColor = contentColor,
//                         submenuRootModifierProvider = submenuRootModifierProvider,
//                         submenuRootContent = submenuRootContent,
//                         submenuModifierProvider = submenuModifierProvider,
//                         itemModifierProvider = itemModifierProvider,
//                         itemValueContent = itemValueContent,
//                     )
//                     is MenuElement.Item -> Item(
//                         item = element,
//                         isParentHovered = isHovered,
//                         itemModifierProvider = itemModifierProvider,
//                         itemValueContent = itemValueContent,
//                     )
//                 }
//             }
//         }
//     }
// }
//
//
// // Actual private implementations
//
// @OptIn(ExperimentalComposeUiApi::class)
// @Composable
// private fun <SubmenuRoot, ItemValue> Submenu(
//     submenu: MenuElement.Submenu<HoverableData<SubmenuRoot>, HoverableData<ItemValue>>,
//
//     isParentHovered: Boolean,
//
//     shape: Shape,
//     backgroundColor: Color,
//     contentColor: Color,
//
//     submenuRootModifierProvider: ModifierProvider,
//     submenuRootContent: MenuElementRootContent<SubmenuRoot>,
//     submenuModifierProvider: ModifierProvider,
//
//     itemModifierProvider: MenuElementRootModifierProvider<ItemValue>,
//     itemValueContent: MenuElementRootContent<ItemValue>,
// ) {
//     val (root, elements) = submenu
//     val (isHovered, value) = root
//
//     Box(Modifier.onPointerEvent(
//         eventType = PointerEventType.Enter,
//         pass = PointerEventPass.Final,
//         onEvent = {}
//     ).onPointerEvent(
//         eventType = PointerEventType.Move,
//         pass = PointerEventPass.Final,
//         onEvent = {}
//     ).onPointerEvent(
//         eventType = PointerEventType.Exit,
//         pass = PointerEventPass.Main,
//         onEvent = {}
//     )) {
//         MaterialDropdownMenuItem(submenuRootModifierProvider(isParentHovered || isHovered)) {
//             submenuRootContent(value, isParentHovered || isHovered)
//         }
//
//         MaterialDropdownMenu(
//             expanded = isParentHovered || isHovered,
//             onDismissRequest = {},
//             shape = shape,
//             backgroundColor = backgroundColor,
//             contentColor = contentColor,
//             preferredMenuPositioning = preferredToBeHorizontalMenu,
//             modifier = submenuModifierProvider(isParentHovered || isHovered),
//         ) {
//             items(elements) { element ->
//                 when (element) {
//                     is MenuElement.Submenu -> Submenu(
//                         submenu = element,
//                         isParentHovered = isParentHovered || isHovered,
//                         shape = shape,
//                         backgroundColor = backgroundColor,
//                         contentColor = contentColor,
//                         submenuRootModifierProvider = submenuRootModifierProvider,
//                         submenuRootContent = submenuRootContent,
//                         submenuModifierProvider = submenuModifierProvider,
//                         itemModifierProvider = itemModifierProvider,
//                         itemValueContent = itemValueContent,
//                     )
//                     is MenuElement.Item -> Item(
//                         item = element,
//                         isParentHovered = isParentHovered || isHovered,
//                         itemModifierProvider = itemModifierProvider,
//                         itemValueContent = itemValueContent,
//                     )
//                 }
//             }
//         }
//     }
// }
//
// @OptIn(ExperimentalComposeUiApi::class)
// @Composable
// private fun <SubmenuRoot, ItemValue> Item(
//     item: MenuElement.Item<HoverableData<SubmenuRoot>, HoverableData<ItemValue>>,
//
//     isParentHovered: Boolean,
//
//     itemModifierProvider: MenuElementRootModifierProvider<ItemValue>,
//     itemValueContent: MenuElementRootContent<ItemValue>,
// ) {
//     val (isHovered, value) = item.value
//
//     MaterialDropdownMenuItem(Modifier.onPointerEvent(
//         eventType = PointerEventType.Enter,
//         pass = PointerEventPass.Final,
//         onEvent = {}
//     ).onPointerEvent(
//         eventType = PointerEventType.Move,
//         pass = PointerEventPass.Final,
//         onEvent = {}
//     ).onPointerEvent(
//         eventType = PointerEventType.Exit,
//         pass = PointerEventPass.Main,
//         onEvent = {}
//     ).then(itemModifierProvider(value, isParentHovered || isHovered))) {
//         itemValueContent(value, isParentHovered || isHovered)
//     }
// }
//
//
// private typealias ModifierProvider = @Composable (hovered: Boolean) -> Modifier
// private typealias MenuElementRootModifierProvider<Root> = @Composable (root: Root, hovered: Boolean) -> Modifier
// private typealias MenuRootContent<Root> = @Composable BoxScope.(root: Root, hovered: Boolean) -> Unit
// private typealias MenuElementRootContent<Root> = @Composable RowScope.(root: Root, hovered: Boolean) -> Unit