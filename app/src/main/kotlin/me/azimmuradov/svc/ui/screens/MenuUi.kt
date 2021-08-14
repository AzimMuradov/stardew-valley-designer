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

package me.azimmuradov.svc.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import me.azimmuradov.svc.components.menu.Menu
import me.azimmuradov.svc.settings.languages.Language

val padding = 20.dp
val buttonColor = Color(0xFFF4D21F)


@Composable
fun MenuUi(component: Menu) {

    val models by component.models.subscribeAsState()

    val (settings) = models


    Row(Modifier.fillMaxSize()) {
        Column(Modifier.weight(1f).fillMaxHeight().background(color = Color(0xFF6B4A37))) {
            NewPlanButton(settings.language, component.onCartographerScreenCall)
            PlansButton(settings.language)
            Spacer(Modifier.weight(2f).fillMaxWidth().padding(padding))
            Row(Modifier.weight(1f).fillMaxWidth()) {
                ResourcesButton(settings.language)
                SettingsButton(settings.language)
            }
            Row(Modifier.weight(1f).fillMaxWidth()) {
                AboutButton(settings.language)
                DonateButton(settings.language)
            }
        }
        Column(Modifier.weight(2f).fillMaxHeight().background(color = Color(0xFFBF9999))) {
            //
        }
    }
}


@Composable
fun ColumnScope.NewPlanButton(language: Language, onClick: () -> Unit) {
    Box(Modifier.weight(1f).fillMaxSize().padding(padding)) {
        IconButton(modifier = Modifier.fillMaxSize().background(buttonColor), onClick = onClick) {
            Text(language.menuButtons.newPlan)
        }
    }
}

@Composable
fun ColumnScope.PlansButton(language: Language) {
    Box(Modifier.weight(1f).fillMaxSize().padding(padding)) {
        IconButton(modifier = Modifier.fillMaxSize().background(buttonColor), onClick = {}) {
            Text(language.menuButtons.plans)
        }
    }
}


@Composable
fun RowScope.ResourcesButton(language: Language) {
    Box(Modifier.weight(1f).fillMaxSize().padding(padding)) {
        IconButton(modifier = Modifier.fillMaxSize().background(buttonColor), onClick = {}) {
            Text(language.menuButtons.resources)
        }
    }
}

@Composable
fun RowScope.SettingsButton(language: Language) {
    Box(Modifier.weight(1f).fillMaxSize().padding(padding)) {
        IconButton(modifier = Modifier.fillMaxSize().background(buttonColor), onClick = {}) {
            Text(language.menuButtons.settings)
        }
    }
}

@Composable
fun RowScope.AboutButton(language: Language) {
    Box(Modifier.weight(1f).fillMaxSize().padding(padding)) {
        IconButton(modifier = Modifier.fillMaxSize().background(buttonColor), onClick = {}) {
            Text(language.menuButtons.about)
        }
    }
}

@Composable
fun RowScope.DonateButton(language: Language) {
    Box(Modifier.weight(1f).fillMaxSize().padding(padding)) {
        IconButton(modifier = Modifier.fillMaxSize().background(buttonColor), onClick = {}) {
            Text(language.menuButtons.donate)
        }
    }
}