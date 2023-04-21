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

package me.azimmuradov.svc.screens.mainmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import me.azimmuradov.svc.mainmenu.MainMenuIntent
import me.azimmuradov.svc.mainmenu.MainMenuState
import me.azimmuradov.svc.utils.GlobalSettings


@Composable
fun RowScope.RightSideMenu(
    state: MainMenuState,
    intentConsumer: (MainMenuIntent) -> Unit,
    onCartographerScreenCall: () -> Unit,
) {
    val wordList = GlobalSettings.strings

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
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            NewPlanMenu(state, intentConsumer)

            PlanMenuButton(
                text = "Open plan",
                icon = painterResource(resourcePath = "main-menu/file_open_FILL1_wght400_GRAD0_opsz48.svg"),
                enabled = false
            )

            SaveImportMenu()

            PlanMenuButton(
                text = "Search for a plan",
                icon = Icons.Filled.Search,
                enabled = false
            )
        }


        // TODO : Persistent Plans

        LazyColumn(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.surface),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // items(count = 5) {
            //     Card(modifier = Modifier.fillMaxWidth().height(200.dp)) {
            //         Row(
            //             modifier = Modifier.fillMaxSize().padding(8.dp),
            //             horizontalArrangement = Arrangement.spacedBy(8.dp)
            //         ) {
            //             Card(Modifier.fillMaxHeight().width(120.dp)) {
            //                 Text(text = "Plan screenshot")
            //             }
            //             Card(Modifier.fillMaxHeight().weight(1f)) {
            //                 Column {
            //                     Text(text = "Plan #$it")
            //                     Text(text = "Plan info")
            //                 }
            //             }
            //         }
            //     }
            // }
        }
    }
}
