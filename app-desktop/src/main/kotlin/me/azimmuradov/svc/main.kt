/*
 * Copyright 2021-2022 Azim Muradov
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import me.azimmuradov.svc.components.Root
import me.azimmuradov.svc.components.rootComponent
import me.azimmuradov.svc.screens.*


fun main() {
    val root = rootComponent()

    application {
        // val rootModel by root.model.subscribeAsState()

        Window(
            onCloseRequest = ::exitApplication,
            state = rememberWindowState(
                position = WindowPosition(alignment = Alignment.Center),
                size = DpSize(width = 1366.dp, height = 768.dp),
            ),
            title = "",
            icon = painterResource(ICON_RES_PATH),
            resizable = false,
        ) {
            AppTheme(themeVariant = ThemeVariant.LIGHT) { RootUi(root) }
        }
    }
}


@Composable
fun RootUi(component: Root) {
    Box(modifier = Modifier.fillMaxSize()) {
        Children(routerState = component.routerState) { (_, child) ->
            when (child) {
                is Root.Child.WelcomeChild -> WelcomeUi(child.component)
                is Root.Child.MainMenuChild -> MainMenuUi(child.component)
                is Root.Child.CartographerChild -> CartographerUi(child.component)
            }
        }
    }
}

private const val ICON_RES_PATH: String = "icon.png"