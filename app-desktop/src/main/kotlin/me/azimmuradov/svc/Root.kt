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

package me.azimmuradov.svc

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import me.azimmuradov.svc.components.RootComponent
import me.azimmuradov.svc.screens.*
import me.azimmuradov.svc.settings.Lang
import me.azimmuradov.svc.utils.WithSettings


@Composable
fun Root(component: RootComponent) {
    // val rootModel by root.model.subscribeAsState()

    AppTheme(themeVariant = ThemeVariant.LIGHT) {
        WithSettings(lang = Lang.EN) {
            Box(modifier = Modifier.fillMaxSize()) {
                Children(stack = component.childStack) { (_, child) ->
                    when (child) {
                        is RootComponent.Child.SplashChild -> SplashScreen(child.component)
                        is RootComponent.Child.MainMenuChild -> MainMenuScreen(child.component)
                        is RootComponent.Child.CartographerChild -> CartographerScreen(child.component)
                    }
                }
            }
        }
    }
}
