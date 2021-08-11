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

import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.value.Value
import me.azimmuradov.svc.screens.Cartographer
import me.azimmuradov.svc.screens.Menu
import me.azimmuradov.svc.screens.Welcome
import me.azimmuradov.svc.settings.Settings


interface Root {

    val model: Value<Model>

    data class Model(
        val settings: Settings = Settings.DefaultSettings,
        val title: String = Screen.WelcomeScreen.title(settings),
    )


    val routerState: Value<RouterState<*, Screen>>


    fun onWelcomeScreenEnd()

    fun onCartographerScreenCall()

    fun onCartographerScreenReturn()


    sealed class Screen {

        class WelcomeScreen(val component: Welcome) : Screen() {

            companion object {

                fun title(settings: Settings) = settings.language.appName
            }
        }

        class MenuScreen(val component: Menu) : Screen() {

            companion object {

                fun title(settings: Settings) = "${settings.language.menuScreen} | ${settings.language.appName}"
            }
        }

        class CartographerScreen(val component: Cartographer) : Screen() {

            companion object {

                fun title(settings: Settings) = "${settings.language.cartographerScreen} | ${settings.language.appName}"
            }
        }
    }
}