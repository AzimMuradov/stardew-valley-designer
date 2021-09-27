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

import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import me.azimmuradov.svc.components.RootComponent


fun main() {
    val root = RootComponent(
        componentContext = DefaultComponentContext(lifecycle = LifecycleRegistry())
    )

    application {
        val rootModel by root.model.subscribeAsState()

        Window(
            onCloseRequest = ::exitApplication,
            state = rememberWindowState(
                position = WindowPosition(alignment = Alignment.Center),
                size = WindowSize(width = 1280.dp, height = 720.dp),
            ),
            title = rootModel.title,
            icon = painterResource(ICON_RES_PATH),
            resizable = true,
        ) {
            AppTheme(themeVariant = ThemeVariant.LIGHT) { RootUi(root) }
        }
    }
}


private const val ICON_RES_PATH: String = "icon.png"