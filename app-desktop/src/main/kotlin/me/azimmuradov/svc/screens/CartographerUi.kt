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

package me.azimmuradov.svc.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import me.azimmuradov.svc.components.screens.Cartographer
import me.azimmuradov.svc.screens.cartographer.*


@Composable
fun CartographerUi(component: Cartographer) {
    val model by component.model.subscribeAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopMenuBar(
            model,
            component.updateSessionSettings,
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        )

        Row(Modifier.fillMaxWidth().weight(1f)) {
            LeftSideMenus(
                model,
                modifier = Modifier.fillMaxHeight().width(300.dp),
            )
            SvcLayout(
                model,
                modifier = Modifier.fillMaxHeight().weight(1f).padding(30.dp),
            )
            RightSideMenus(
                model,
                modifier = Modifier.fillMaxHeight().width(300.dp),
            )
        }
    }
}