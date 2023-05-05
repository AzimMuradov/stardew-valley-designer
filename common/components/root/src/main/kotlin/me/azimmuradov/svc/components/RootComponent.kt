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

package me.azimmuradov.svc.components

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import me.azimmuradov.svc.cartographer.CartographerComponent
import me.azimmuradov.svc.components.screens.SplashComponent
import me.azimmuradov.svc.engine.SvcEngine
import me.azimmuradov.svc.mainmenu.MainMenuComponent


interface RootComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class SplashChild(val component: SplashComponent) : Child()
        class MainMenuChild(val component: MainMenuComponent) : Child()
        class CartographerChild(val component: CartographerComponent) : Child()
    }


    fun onSplashScreenEnd()

    fun onCartographerScreenCall(engine: SvcEngine)

    fun onCartographerScreenReturn()
}
