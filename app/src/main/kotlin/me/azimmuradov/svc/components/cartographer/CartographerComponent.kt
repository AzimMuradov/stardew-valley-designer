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

package me.azimmuradov.svc.components.cartographer

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import me.azimmuradov.svc.components.cartographer.Cartographer.Model
import me.azimmuradov.svc.engine.impl.editor.cartographerEditor
import me.azimmuradov.svc.engine.impl.layout.CartographerLayoutType.Shed
import me.azimmuradov.svc.engine.impl.layout.layout
import me.azimmuradov.svc.settings.Settings


class CartographerComponent(
    override val onCartographerScreenReturn: () -> Unit,
    settings: Settings,
) : Cartographer {

    override val models: Value<Model> by lazy { _models }

    private val _models: MutableValue<Model> = MutableValue(Model(
        settings,
        cartographerEditor(defaultLayout()),
    ))
}


// TODO : Change
fun defaultLayout() = layout(type = Shed)