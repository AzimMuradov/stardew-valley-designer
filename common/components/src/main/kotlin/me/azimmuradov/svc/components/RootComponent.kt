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

package me.azimmuradov.svc.components

import com.arkivanov.decompose.*
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import me.azimmuradov.svc.components.Root.Child
import me.azimmuradov.svc.components.Root.Child.*
import me.azimmuradov.svc.components.Root.Model
import me.azimmuradov.svc.components.RootComponent.Config.*
import me.azimmuradov.svc.components.screens.cartographer.CartographerComponent
import me.azimmuradov.svc.components.screens.menu.MenuComponent
import me.azimmuradov.svc.components.screens.welcome.WelcomeComponent
import me.azimmuradov.svc.settings.Settings


class RootComponent(componentContext: ComponentContext) : Root, ComponentContext by componentContext {

    private val _model: MutableValue<Model> = MutableValue(Model(
        settings = Settings.DefaultSettings,
        title = WelcomeChild.title(Settings.DefaultSettings),
    ))

    override val model: Value<Model> = _model


    private val router = router<Config, Child>(
        initialConfiguration = CartographerConfig,
        // initialConfiguration = WelcomeConfig,
        childFactory = ::screenByScreenConfig,
    )

    override val routerState: Value<RouterState<*, Child>> = router.state


    override fun onWelcomeScreenEnd() {
        _model.value = model.value.copy(title = MenuChild.title(model.value.settings))
        router.replaceCurrent(configuration = MenuConfig)
    }

    override fun onCartographerScreenCall() {
        _model.value = model.value.copy(title = CartographerChild.title(model.value.settings))
        router.push(configuration = CartographerConfig)
    }

    override fun onCartographerScreenReturn() {
        _model.value = model.value.copy(title = MenuChild.title(model.value.settings))
        router.pop()
    }


    private fun screenByScreenConfig(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            WelcomeConfig -> WelcomeChild(WelcomeComponent(
                this::onWelcomeScreenEnd,
                model.value.settings,
            ))
            MenuConfig -> MenuChild(MenuComponent(
                this::onCartographerScreenCall,
                model.value.settings,
            ))
            CartographerConfig -> CartographerChild(CartographerComponent(
                this::onCartographerScreenReturn,
                model.value.settings,
            ))
        }

    private sealed class Config : Parcelable {

        object WelcomeConfig : Config()

        object MenuConfig : Config()

        object CartographerConfig : Config()
    }
}