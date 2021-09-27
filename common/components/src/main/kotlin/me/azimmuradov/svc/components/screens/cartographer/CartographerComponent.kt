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

package me.azimmuradov.svc.components.screens.cartographer

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import me.azimmuradov.svc.cartographer.svcOf
import me.azimmuradov.svc.cartographer.toolkit.ToolType
import me.azimmuradov.svc.components.screens.Cartographer
import me.azimmuradov.svc.components.screens.Cartographer.Model
import me.azimmuradov.svc.engine.layout.LayoutsProvider.LayoutType.Shed
import me.azimmuradov.svc.engine.layout.LayoutsProvider.layoutOf
import me.azimmuradov.svc.settings.Settings


class CartographerComponent(
    override val onCartographerScreenReturn: () -> Unit,
    settings: Settings,
) : Cartographer {

    private val _models: MutableValue<Model> = MutableValue(Model(
        SessionSettings.default,
        settings,
        svcOf(defaultLayout),
    ))

    override val model: Value<Model> = _models

    override val updateSessionSettings: (SessionSettings) -> Unit = {
        _models.value = _models.value.copy(sessionSettings = it)
    }


    init {
        model.value.svc.toolkit.chooseToolOf(ToolType.Hand)
    }
}


// TODO : Change
val defaultLayout = layoutOf(type = Shed)