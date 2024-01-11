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

package io.stardewvalleydesigner.cmplib.menu

import io.stardewvalleydesigner.cmplib.menu.MenuElement.Item
import io.stardewvalleydesigner.cmplib.menu.MenuElement.Submenu


@DslMarker
private annotation class MenuDsl


fun <MenuRoot, SubmenuRoot, ItemValue> menu(
    root: MenuRoot,
    b: MenuBuilder<MenuRoot, SubmenuRoot, ItemValue>.() -> Unit,
): Menu<MenuRoot, SubmenuRoot, ItemValue> = MenuBuilder<MenuRoot, SubmenuRoot, ItemValue>(root).apply(b).build()


@MenuDsl
class MenuBuilder<MenuRoot, SubmenuRoot, ItemValue> internal constructor(private val root: MenuRoot) {

    fun submenu(root: SubmenuRoot, b: SubmenuBuilder<SubmenuRoot, ItemValue>.() -> Unit) {
        elements += SubmenuBuilder<SubmenuRoot, ItemValue>(root).apply(b).build()
    }

    fun items(vararg values: ItemValue) {
        elements += values.map(::Item)
    }


    internal fun build(): Menu<MenuRoot, SubmenuRoot, ItemValue> = Menu(root, elements)

    private val elements: MutableList<MenuElement<SubmenuRoot, ItemValue>> = mutableListOf()
}

@MenuDsl
class SubmenuBuilder<SubmenuRoot, ItemValue> internal constructor(private val root: SubmenuRoot) {

    fun submenu(root: SubmenuRoot, b: SubmenuBuilder<SubmenuRoot, ItemValue>.() -> Unit) {
        elements += SubmenuBuilder<SubmenuRoot, ItemValue>(root).apply(b).build()
    }

    fun items(vararg values: ItemValue) {
        elements += values.map(::Item)
    }


    internal fun build(): Submenu<SubmenuRoot, ItemValue> = Submenu(root, elements)

    private val elements: MutableList<MenuElement<SubmenuRoot, ItemValue>> = mutableListOf()
}
