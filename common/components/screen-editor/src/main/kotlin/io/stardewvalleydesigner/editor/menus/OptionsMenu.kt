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

package io.stardewvalleydesigner.editor.menus

import io.stardewvalleydesigner.utils.menu.Menu
import io.stardewvalleydesigner.utils.menu.menu


typealias OptionsMenu = Menu<OptionsRoot, OptionsSubmenuRoot, OptionsItemValue>


enum class OptionsRoot {
    Options,
}

enum class OptionsSubmenuRoot {
    OptionsGeneral,
}

sealed interface OptionsItemValue {

    enum class Toggleable : OptionsItemValue {
        ShowAxis,
        ShowGrid,
        ShowSpritesFully,
    }
}


val MainOptionsMenu: OptionsMenu = menu(root = OptionsRoot.Options) {
    submenu(OptionsSubmenuRoot.OptionsGeneral) {
        items(
            OptionsItemValue.Toggleable.ShowAxis,
            OptionsItemValue.Toggleable.ShowGrid,
            OptionsItemValue.Toggleable.ShowSpritesFully,
        )
    }
}
