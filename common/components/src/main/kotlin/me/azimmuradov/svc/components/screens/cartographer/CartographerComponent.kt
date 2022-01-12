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

import androidx.compose.runtime.*
import me.azimmuradov.svc.cartographer.svcOf
import me.azimmuradov.svc.components.screens.Cartographer
import me.azimmuradov.svc.engine.layout.LayoutsProvider.LayoutType.Shed
import me.azimmuradov.svc.engine.layout.LayoutsProvider.layoutOf
import me.azimmuradov.svc.settings.Settings


internal class CartographerComponent(
    override val onCartographerScreenReturn: () -> Unit,
    settings: Settings,
) : Cartographer {

    override var svc by mutableStateOf(svcOf(layoutOf(type = Shed)))

    override var options by mutableStateOf(Options.DEFAULT)

    override var settings by mutableStateOf(settings)
}