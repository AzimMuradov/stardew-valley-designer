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

package me.azimmuradov.svc.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.router.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.parcelable.Parcelable
import me.azimmuradov.svc.components.RootComponent.Child
import me.azimmuradov.svc.components.RootComponent.Child.*
import me.azimmuradov.svc.components.RootComponentImpl.Config.*
import me.azimmuradov.svc.components.screens.cartographer.CartographerComponentImpl
import me.azimmuradov.svc.components.screens.menu.MainMenuComponentImpl
import me.azimmuradov.svc.components.screens.welcome.WelcomeComponentImpl

fun rootComponent(): RootComponent = RootComponentImpl(
    componentContext = DefaultComponentContext(
        lifecycle = LifecycleRegistry(),
    ),
)


private class RootComponentImpl(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val router = router<Config, Child>(
        initialConfiguration = CartographerConfig,
        // initialConfiguration = WelcomeConfig,
        childFactory = ::screenByScreenConfig,
    )

    override val routerState: Value<RouterState<*, Child>> = router.state


    override fun onWelcomeScreenEnd() {
        router.replaceCurrent(configuration = MainMenuConfig)
    }

    override fun onCartographerScreenCall() {
        router.push(configuration = CartographerConfig)
    }

    override fun onCartographerScreenReturn() {
        router.pop()
    }


    private fun screenByScreenConfig(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            WelcomeConfig -> WelcomeChild(WelcomeComponentImpl(
                this::onWelcomeScreenEnd,
            ))
            MainMenuConfig -> MainMenuChild(MainMenuComponentImpl(
                this::onCartographerScreenCall,
            ))
            CartographerConfig -> CartographerChild(CartographerComponentImpl(
                this::onCartographerScreenReturn,
            ))
        }

    private sealed class Config : Parcelable {
        object WelcomeConfig : Config()
        object MainMenuConfig : Config()
        object CartographerConfig : Config()
    }
}