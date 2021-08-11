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

package me.azimmuradov.svc

import com.arkivanov.decompose.*
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import me.azimmuradov.svc.Root.Screen.*
import me.azimmuradov.svc.screens.CartographerComponent
import me.azimmuradov.svc.screens.MenuComponent
import me.azimmuradov.svc.screens.WelcomeComponent


class RootComponent(componentContext: ComponentContext) : Root, ComponentContext by componentContext {

    private val _models: MutableValue<Root.Model> = MutableValue(Root.Model())
    override val model: Value<Root.Model> = _models


    private val router = router<ScreenConfig, Root.Screen>(
        initialConfiguration = ScreenConfig.Welcome,
        childFactory = ::screenByScreenConfig,
    )

    override val routerState: Value<RouterState<*, Root.Screen>> = router.state

    private fun screenByScreenConfig(config: ScreenConfig, componentContext: ComponentContext): Root.Screen =
        when (config) {
            ScreenConfig.Welcome -> WelcomeScreen(WelcomeComponent(this::onWelcomeScreenEnd))
            ScreenConfig.Menu -> MenuScreen(MenuComponent(model.value.settings, this::onCartographerScreenCall))
            ScreenConfig.Cartographer -> CartographerScreen(CartographerComponent(model.value.settings,
                this::onCartographerScreenReturn))
        }


    override fun onWelcomeScreenEnd() {
        _models.value = Root.Model(title = MenuScreen.title(model.value.settings))
        router.replaceCurrent(configuration = ScreenConfig.Menu)
    }

    override fun onCartographerScreenCall() {
        _models.value = Root.Model(title = CartographerScreen.title(model.value.settings))
        router.push(configuration = ScreenConfig.Cartographer)
    }

    override fun onCartographerScreenReturn() {
        _models.value = Root.Model(title = MenuScreen.title(model.value.settings))
        router.pop()
    }


    private sealed class ScreenConfig : Parcelable {

        object Welcome : ScreenConfig()

        object Menu : ScreenConfig()

        object Cartographer : ScreenConfig()
    }
}