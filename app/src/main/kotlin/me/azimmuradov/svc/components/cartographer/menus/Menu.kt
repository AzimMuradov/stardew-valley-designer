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


data class Menu<out Title, out V>(
    val title: Title,
    val elements: List<MenuElement<Title, V>>,
)

sealed interface MenuElement<out Title, out V> {

    data class SubMenu<out Title, out V>(
        val title: Title,
        val elements: List<MenuElement<Title, V>>,
    ) : MenuElement<Title, V>

    data class Item<out Title, out V>(val value: V) : MenuElement<Title, V>
}