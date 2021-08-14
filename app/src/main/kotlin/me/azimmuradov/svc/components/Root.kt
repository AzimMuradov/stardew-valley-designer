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

import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.value.Value
import me.azimmuradov.svc.components.cartographer.Cartographer
import me.azimmuradov.svc.components.menu.Menu
import me.azimmuradov.svc.components.welcome.Welcome
import me.azimmuradov.svc.settings.Settings


interface Root {

    fun onWelcomeScreenEnd()

    fun onCartographerScreenCall()

    fun onCartographerScreenReturn()


    val models: Value<Model>

    data class Model(
        var settings: Settings,
        var title: String,
    )


    val routerState: Value<RouterState<*, Child>>

    sealed class Child {

        class WelcomeChild(val component: Welcome) : Child() {

            companion object {

                fun title(settings: Settings) = settings.language.appName
            }
        }

        class MenuChild(val component: Menu) : Child() {

            companion object {

                fun title(settings: Settings) = "${settings.language.menuScreen} | ${settings.language.appName}"
            }
        }

        class CartographerChild(val component: Cartographer) : Child() {

            companion object {

                fun title(settings: Settings) = "${settings.language.cartographerScreen} | ${settings.language.appName}"
            }
        }
    }
}