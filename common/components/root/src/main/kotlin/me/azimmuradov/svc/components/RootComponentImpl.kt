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

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.parcelable.Parcelable
import me.azimmuradov.svc.components.RootComponent.Child
import me.azimmuradov.svc.components.RootComponent.Child.*
import me.azimmuradov.svc.components.RootComponentImpl.Config.*
import me.azimmuradov.svc.components.screens.cartographer.CartographerComponentImpl
import me.azimmuradov.svc.components.screens.menu.MainMenuComponentImpl
import me.azimmuradov.svc.components.screens.welcome.WelcomeComponentImpl
import me.azimmuradov.svc.engine.SvcEngine


fun rootComponent(): RootComponent = RootComponentImpl(
    componentContext = DefaultComponentContext(
        lifecycle = LifecycleRegistry(),
    ),
)


private class RootComponentImpl(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val stack = childStack(
        source = navigation,
        // initialConfiguration = MainMenuConfig,
        initialConfiguration = WelcomeConfig,
        childFactory = ::child,
    )

    override val childStack: Value<ChildStack<*, Child>> = stack


    override fun onWelcomeScreenEnd() {
        navigation.replaceCurrent(configuration = MainMenuConfig)
    }

    override fun onCartographerScreenCall(engine: SvcEngine) {
        navigation.push(configuration = CartographerConfig(engine))
    }

    override fun onCartographerScreenReturn() {
        navigation.pop()
    }


    private fun child(config: Config, componentContext: ComponentContext): Child = when (config) {
        WelcomeConfig -> WelcomeChild(
            WelcomeComponentImpl(
                this::onWelcomeScreenEnd,
            )
        )

        MainMenuConfig -> MainMenuChild(
            MainMenuComponentImpl(
                this::onCartographerScreenCall,
            )
        )

        is CartographerConfig -> CartographerChild(
            CartographerComponentImpl(
                engine = config.engine,
                onCartographerScreenReturn = this::onCartographerScreenReturn,
            )
        )
    }

    private sealed class Config : Parcelable {
        data object WelcomeConfig : Config()
        data object MainMenuConfig : Config()
        data class CartographerConfig(val engine: SvcEngine) : Config()
    }
}
