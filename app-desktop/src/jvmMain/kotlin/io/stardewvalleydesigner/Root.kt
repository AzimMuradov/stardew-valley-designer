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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import io.stardewvalleydesigner.components.RootComponent
import io.stardewvalleydesigner.screens.*


@Composable
fun Root(component: RootComponent) {
    // val rootModel by root.model.subscribeAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Children(stack = component.childStack) { (_, child) ->
            when (child) {
                is RootComponent.Child.SplashChild -> SplashScreen(child.component)
                is RootComponent.Child.MainMenuChild -> MainMenuScreen(child.component)
                is RootComponent.Child.EditorChild -> EditorScreen(child.component)
            }
        }
    }
}
