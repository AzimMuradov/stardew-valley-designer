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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.azimmuradov.svc.components.cartographer.Cartographer
import me.azimmuradov.svc.ui.screens.cartographer.BottomPart
import me.azimmuradov.svc.ui.screens.cartographer.TopMenu


@Composable
fun CartographerUi(component: Cartographer) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopMenu(
            component,
            modifier = Modifier
                .fillMaxWidth().weight(1f)
                .background(color = MaterialTheme.colors.background),
        )
        BottomPart(
            component,
            modifier = Modifier
                .fillMaxWidth().weight(12f),
        )
    }
}