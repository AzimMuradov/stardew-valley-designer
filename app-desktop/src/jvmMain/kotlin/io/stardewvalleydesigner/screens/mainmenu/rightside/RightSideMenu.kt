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

package io.stardewvalleydesigner.screens.mainmenu.rightside

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.component.mainmenu.MainMenuIntent
import io.stardewvalleydesigner.component.mainmenu.MainMenuState
import io.stardewvalleydesigner.screens.mainmenu.rightside.topmenu.*


@Composable
fun RowScope.RightSideMenu(
    state: MainMenuState,
    intentConsumer: (MainMenuIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .weight(1f)
            .shadow(
                elevation = 4.dp,
                shape = MaterialTheme.shapes.large
            )
            .background(MaterialTheme.colors.secondaryVariant)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(56.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NewDesignMenu(state, intentConsumer)
            OpenDesignMenu(state, intentConsumer)
            SaveImportMenu(state, intentConsumer)
        }
        LayoutCards()
    }
}
