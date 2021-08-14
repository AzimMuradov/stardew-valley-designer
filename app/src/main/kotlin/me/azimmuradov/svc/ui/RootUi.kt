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

package me.azimmuradov.svc.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import me.azimmuradov.svc.components.Root.Child.*
import me.azimmuradov.svc.components.RootComponent
import me.azimmuradov.svc.ui.screens.CartographerUi
import me.azimmuradov.svc.ui.screens.MenuUi
import me.azimmuradov.svc.ui.screens.WelcomeUi


@Composable
fun RootUi(component: RootComponent) {

    Box(modifier = Modifier.fillMaxSize()) {
        Children(routerState = component.routerState) { (_, child) ->
            when (child) {
                is WelcomeChild -> WelcomeUi(child.component)
                is MenuChild -> MenuUi(child.component)
                is CartographerChild -> CartographerUi(child.component)
            }
        }
    }
}