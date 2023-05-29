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

package io.stardewvalleydesigner

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.stardewvalleydesigner.components.RootComponent
import io.stardewvalleydesigner.components.rootComponent
import io.stardewvalleydesigner.settings.Lang
import io.stardewvalleydesigner.utils.WithSettings
import java.awt.Dimension


fun main() {
    val root = rootComponent()

    application {
        // val rootModel by root.model.subscribeAsState()

        val state = rememberWindowState(
            position = WindowPosition(Alignment.Center),
            size = DpSize(width = 1200.dp, height = 700.dp),
        )

        Window(
            onCloseRequest = ::exitApplication,
            state = state,
            title = "",
            icon = painterResource(ICON_RES_PATH),
            resizable = true,
        ) {
            AppTheme(themeVariant = ThemeVariant.LIGHT) {
                WithSettings(lang = Lang.EN) {
                    Root(root)
                }
            }

            val childStack by root.childStack.subscribeAsState()
            val currentChild = childStack.active.instance

            LaunchedEffect(currentChild) {
                window.minimumSize = when (currentChild) {
                    is RootComponent.Child.SplashChild -> Dimension(1200, 700)
                    is RootComponent.Child.MainMenuChild -> Dimension(1200, 700)
                    is RootComponent.Child.EditorChild -> Dimension(1366, 768)
                }

                state.size = when (currentChild) {
                    is RootComponent.Child.SplashChild -> DpSize(width = 1200.dp, height = 700.dp)
                    is RootComponent.Child.MainMenuChild -> DpSize(width = 1200.dp, height = 700.dp)
                    is RootComponent.Child.EditorChild -> DpSize(width = 1366.dp, height = 768.dp)
                }
            }
        }
    }
}


const val ICON_RES_PATH: String = "icons/icon-1024.png"
