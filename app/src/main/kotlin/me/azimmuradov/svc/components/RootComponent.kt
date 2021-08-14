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
import me.azimmuradov.svc.components.cartographer.CartographerComponent
import me.azimmuradov.svc.components.menu.MenuComponent
import me.azimmuradov.svc.components.welcome.WelcomeComponent
import me.azimmuradov.svc.settings.Settings


class RootComponent(componentContext: ComponentContext) : Root, ComponentContext by componentContext {

    override fun onWelcomeScreenEnd() {
        _models.value = models.value.copy(title = MenuChild.title(models.value.settings))
        router.replaceCurrent(configuration = MenuConfig)
    }

    override fun onCartographerScreenCall() {
        _models.value = models.value.copy(title = CartographerChild.title(models.value.settings))
        router.push(configuration = CartographerConfig)
    }

    override fun onCartographerScreenReturn() {
        _models.value = models.value.copy(title = MenuChild.title(models.value.settings))
        router.pop()
    }


    override val models: Value<Model> by lazy { _models }

    private val _models: MutableValue<Model> = MutableValue(Model(
        settings = Settings.DefaultSettings,
        title = WelcomeChild.title(Settings.DefaultSettings),
    ))


    override val routerState: Value<RouterState<*, Child>> by lazy { router.state }

    private val router = router<Config, Child>(
        initialConfiguration = WelcomeConfig,
        childFactory = ::screenByScreenConfig,
    )

    private fun screenByScreenConfig(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            WelcomeConfig -> WelcomeChild(WelcomeComponent(
                this::onWelcomeScreenEnd,
                models.value.settings,
            ))
            MenuConfig -> MenuChild(MenuComponent(
                this::onCartographerScreenCall,
                models.value.settings,
            ))
            CartographerConfig -> CartographerChild(CartographerComponent(
                this::onCartographerScreenReturn,
                models.value.settings,
            ))
        }

    private sealed class Config : Parcelable {

        object WelcomeConfig : Config()

        object MenuConfig : Config()

        object CartographerConfig : Config()
    }
}