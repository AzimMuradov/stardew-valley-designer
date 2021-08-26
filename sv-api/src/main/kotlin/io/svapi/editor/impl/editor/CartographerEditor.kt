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

package io.svapi.editor.impl.editor

import io.svapi.editor.impl.layout.CartographerLayout


fun cartographerEditor(layout: CartographerLayout): CartographerEditor = CartographerEditor(layout)


class CartographerEditor internal constructor(
    layout: CartographerLayout,
    private var basicEditor: BasicCartographerEditor = basicCartographerEditor(layout),
) : BasicCartographerEditor by basicEditor {

    var layout: CartographerLayout = layout
        private set


    fun changeLayout(newLayout: CartographerLayout) {
        layout = newLayout
        basicEditor = basicCartographerEditor(newLayout)
    }
}