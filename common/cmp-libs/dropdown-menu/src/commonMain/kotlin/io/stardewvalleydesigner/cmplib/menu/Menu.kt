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


data class Menu<out MenuRoot, out SubmenuRoot, out ItemValue>(
    val root: MenuRoot,
    val elements: List<MenuElement<SubmenuRoot, ItemValue>>,
)

sealed interface MenuElement<out SubmenuRoot, out ItemValue> {

    data class Submenu<out SubmenuRoot, out ItemValue>(
        val root: SubmenuRoot,
        val elements: List<MenuElement<SubmenuRoot, ItemValue>>,
    ) : MenuElement<SubmenuRoot, ItemValue>

    data class Item<out SubmenuRoot, out ItemValue>(
        val value: ItemValue,
    ) : MenuElement<SubmenuRoot, ItemValue>
}
