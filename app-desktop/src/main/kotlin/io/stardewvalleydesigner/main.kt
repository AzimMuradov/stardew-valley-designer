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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.stardewvalleydesigner.components.RootComponent
import io.stardewvalleydesigner.components.rootComponent
import io.stardewvalleydesigner.settings.Lang
import io.stardewvalleydesigner.utils.WithMeasuredWindow
import io.stardewvalleydesigner.utils.WithSettings
import java.awt.Dimension


fun main() {
    val root = rootComponent()

    application {
        val state = rememberWindowState(
            position = WindowPosition(Alignment.Center),
            size = DpSize(width = 800.dp, height = 600.dp)
        )

        val childStack by root.childStack.subscribeAsState()

        Window(
            onCloseRequest = ::exitApplication,
            state = state,
            title = "",
            icon = painterResource(ICON_RES_PATH),
            resizable = true,
        ) {
            WithMeasuredWindow(windowWidth = with(LocalDensity.current) { state.size.width.roundToPx() }) {
                AppTheme(themeVariant = ThemeVariant.LIGHT) {
                    WithSettings(lang = Lang.EN) {
                        Root(root)
                    }
                }
            }

            val density = LocalDensity.current

            LaunchedEffect(childStack.active.instance, density) {
                window.minimumSize = when (childStack.active.instance) {
                    is RootComponent.Child.SplashChild -> dimension(800.dp, 600.dp, density)
                    is RootComponent.Child.MainMenuChild -> dimension(1000.dp, 700.dp, density)
                    is RootComponent.Child.EditorChild -> dimension(1280.dp, 720.dp, density)
                }
                state.position = WindowPosition(Alignment.Center)
            }
        }
    }
}

private fun dimension(w: Dp, h: Dp, density: Density) = with(density) {
    Dimension(w.roundToPx(), h.roundToPx())
}


const val ICON_RES_PATH: String = "icons/icon-1024.png"
