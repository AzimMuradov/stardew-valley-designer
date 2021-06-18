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

package gui

import androidx.compose.desktop.AppManager
import androidx.compose.desktop.Window
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import gui.screens.Cartographer
import gui.screens.Menu
import gui.screens.Screen
import gui.screens.Welcome
import gui.settings.Settings
import gui.settings.Settings.DefaultSettings
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


fun SvcApp() {
    Window(
        title = "Stardew Valley Cartographer",
        icon = getIcon(),
    ) {
        val settings: Settings by remember { mutableStateOf(DefaultSettings) }

        var currentScreen: Screen by remember { mutableStateOf(Welcome) }

        AppManager.windows.first().setTitle(currentScreen.title(settings.language))

        when (currentScreen) {
            Welcome -> Welcome { currentScreen = Menu }
            Menu -> Menu(settings) { currentScreen = Cartographer }
            Cartographer -> Cartographer()
        }
    }
}

fun getIcon(): BufferedImage = try {
    ImageIO.read(File("src/main/resources/icon.png"))
} catch (e: Exception) {
    // Image file does not exist
    BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)
}