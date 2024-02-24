/*
 * Copyright 2021-2024 Azim Muradov
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

package io.stardewvalleydesigner.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.stardewvalleydesigner.component.mainmenu.MainMenuComponent
import io.stardewvalleydesigner.screens.mainmenu.rightside.RightSideMenu
import io.stardewvalleydesigner.screens.mainmenu.sidemenu.SideMenu


@Composable
fun MainMenuScreen(component: MainMenuComponent) {
    Row(modifier = Modifier.fillMaxSize()) {
        SideMenu()
        RightSideMenu(
            intentConsumer = component.store::accept,
            newDesignComponent = component.newDesignComponent,
            openDesignComponent = component.openDesignComponent,
            openSvSaveComponent = component.openSvSaveComponent,
        )
    }
}
