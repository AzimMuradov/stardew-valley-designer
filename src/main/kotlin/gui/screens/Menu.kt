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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


object Menu : Screen {

    private val padding = 20.dp
    private val buttonColor = Color(0xFFF4D21F)

    @Composable
    operator fun invoke(sh: ScreenHandler) {
        Row(Modifier.fillMaxSize()) {
            Column(Modifier.weight(1f).fillMaxHeight().background(color = Color(0xFF6B4A37))) {
                NewPlanButton()
                PlansButton()
                Spacer(Modifier.weight(2f).fillMaxWidth().padding(padding))
                Row(Modifier.weight(1f).fillMaxWidth()) {
                    ResourcesButton()
                    SettingsButton()
                }
                Row(Modifier.weight(1f).fillMaxWidth()) {
                    AboutButton()
                    DonateButton()
                }
            }
            Column(Modifier.weight(2f).fillMaxHeight().background(color = Color(0xFFBF9999))) {
                //
            }
        }
    }


    @Composable
    fun ColumnScope.NewPlanButton() {
        Box(Modifier.weight(1f).fillMaxSize().padding(padding)) {
            IconButton(modifier = Modifier.fillMaxSize().background(buttonColor), onClick = {}) { Text("New Plan") }
        }
    }

    @Composable
    fun ColumnScope.PlansButton() {
        Box(Modifier.weight(1f).fillMaxSize().padding(padding)) {
            IconButton(modifier = Modifier.fillMaxSize().background(buttonColor), onClick = {}) { Text("Plans") }
        }
    }


    @Composable
    fun RowScope.ResourcesButton() {
        Box(Modifier.weight(1f).fillMaxSize().padding(padding)) {
            IconButton(modifier = Modifier.fillMaxSize().background(buttonColor), onClick = {}) { Text("Resources") }
        }
    }

    @Composable
    fun RowScope.SettingsButton() {
        Box(Modifier.weight(1f).fillMaxSize().padding(padding)) {
            IconButton(modifier = Modifier.fillMaxSize().background(buttonColor), onClick = {}) { Text("Settings") }
        }
    }

    @Composable
    fun RowScope.AboutButton() {
        Box(Modifier.weight(1f).fillMaxSize().padding(padding)) {
            IconButton(modifier = Modifier.fillMaxSize().background(buttonColor), onClick = {}) { Text("About") }
        }
    }

    @Composable
    fun RowScope.DonateButton() {
        Box(Modifier.weight(1f).fillMaxSize().padding(padding)) {
            IconButton(modifier = Modifier.fillMaxSize().background(buttonColor), onClick = {}) { Text("Donate") }
        }
    }
}