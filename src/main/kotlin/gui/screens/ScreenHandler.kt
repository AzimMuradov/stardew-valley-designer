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

package gui.screens

import androidx.compose.desktop.AppManager
import androidx.compose.runtime.*
import gui.screens.Welcome.Transparency
import gui.screens.Welcome.IconAnimationState as IAS


@Composable
fun AppScreen() {
    var current: Screen by remember { mutableStateOf(Welcome) }


    AppManager.windows.first().setTitle(
        when (current) {
            Welcome -> "Stardew Valley Cartographer"
            Menu -> "Menu | Stardew Valley Cartographer"
            Cartographer -> "Cartographer | Stardew Valley Cartographer"
        }
    )


    var ias by remember { mutableStateOf(IAS.None) }

    AppManager.windows.first().events.onOpen = { ias = IAS.Appearing }

    when (current) {
        Welcome -> Welcome(ias) {
            when (ias) {
                IAS.Appearing -> if (it == Transparency.MAX) {
                    ias = IAS.Disappearing
                }
                IAS.Disappearing -> if (it == Transparency.MIN) {
                    current = Menu
                }
                else -> Unit
            }
        }
        Menu -> Menu(onClick = {
            current = Cartographer
        })
        Cartographer -> Cartographer()
    }
}