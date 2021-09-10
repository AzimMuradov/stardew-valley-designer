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

package me.azimmuradov.svc.components.cartographer.menus

import me.azimmuradov.svc.components.cartographer.menus.MenuElement.Item
import me.azimmuradov.svc.components.cartographer.menus.MenuElement.SubMenu


@DslMarker
private annotation class MenuDsl


fun <Title, V> menu(title: Title, b: MenuBuilder<Title, V>.() -> Unit): Menu<Title, V> =
    MenuBuilder<Title, V>(title).apply(b).build()


@MenuDsl
class MenuBuilder<Title, V> internal constructor(private val title: Title) {

    fun submenu(title: Title, b: SubMenuBuilder<Title, V>.() -> Unit) {
        elements += SubMenuBuilder<Title, V>(title).apply(b).build()
    }

    fun items(vararg values: V) {
        elements += values.map(::Item)
    }


    internal fun build(): Menu<Title, V> = Menu(title, elements)

    private val elements: MutableList<MenuElement<Title, V>> = mutableListOf()
}

@MenuDsl
class SubMenuBuilder<Title, V> internal constructor(private val title: Title) {

    fun submenu(title: Title, b: SubMenuBuilder<Title, V>.() -> Unit) {
        elements += SubMenuBuilder<Title, V>(title).apply(b).build()
    }

    fun items(vararg values: V) {
        elements += values.map(::Item)
    }


    internal fun build(): SubMenu<Title, V> = SubMenu(title, elements)

    private val elements: MutableList<MenuElement<Title, V>> = mutableListOf()
}