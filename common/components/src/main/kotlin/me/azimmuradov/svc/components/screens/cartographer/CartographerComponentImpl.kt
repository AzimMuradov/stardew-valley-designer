/*
 * Copyright 2021-2022 Azim Muradov
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

package me.azimmuradov.svc.components.screens.cartographer

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import me.azimmuradov.svc.cartographer.svcOf
import me.azimmuradov.svc.components.screens.CartographerComponent
import me.azimmuradov.svc.components.screens.CartographerComponent.Model
import me.azimmuradov.svc.engine.layout.LayoutsProvider.LayoutType.Shed
import me.azimmuradov.svc.engine.layout.LayoutsProvider.layoutOf

internal class CartographerComponentImpl(
    override val onCartographerScreenReturn: () -> Unit,
) : CartographerComponent {

    private val _model = MutableValue(
        Model(
            svc = svcOf(layoutOf(type = Shed)),
            options = Options.DEFAULT
        )
    )

    override val model: Value<Model> get() = _model
}