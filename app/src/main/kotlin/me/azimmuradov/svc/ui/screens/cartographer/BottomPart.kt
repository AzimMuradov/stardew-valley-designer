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

package me.azimmuradov.svc.ui.screens.cartographer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.azimmuradov.svc.components.cartographer.Cartographer


@Composable
fun BottomPart(component: Cartographer, modifier: Modifier = Modifier) {
    Row(modifier) {
        LeftMenu(
            component,
            modifier = Modifier
                .fillMaxHeight().weight(1f)
                .background(color = Color.Blue),
        )
        MainPart(
            component,
            modifier = Modifier
                .fillMaxHeight().weight(5f)
                .background(color = Color.Cyan)
                .padding(20.dp),
        )
        RightMenu(
            component,
            modifier = Modifier
                .fillMaxHeight().weight(1f)
                .background(color = Color.Blue),
        )
    }
}