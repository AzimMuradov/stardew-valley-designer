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

package io.stardewvalleydesigner.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.mvikotlin.extensions.coroutines.states
import io.stardewvalleydesigner.component.mainmenu.MainMenuComponent
import io.stardewvalleydesigner.screens.mainmenu.rightside.RightSideMenu
import io.stardewvalleydesigner.screens.mainmenu.sidemenu.SideMenu


@Composable
fun MainMenuScreen(component: MainMenuComponent) {
    val store = component.store
    val state by store.states.collectAsState(component.store.state)

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        SideMenu()
        RightSideMenu(state, intentConsumer = store::accept)
    }
}
