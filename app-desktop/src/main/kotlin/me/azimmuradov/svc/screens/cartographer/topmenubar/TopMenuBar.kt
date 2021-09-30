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

package me.azimmuradov.svc.screens.cartographer.topmenubar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.azimmuradov.svc.components.screens.cartographer.Options
import me.azimmuradov.svc.engine.entity.Entity
import me.azimmuradov.svc.engine.entity.EntityType
import me.azimmuradov.svc.settings.Lang


@Composable
fun TopMenuBar(
    disallowedTypes: Set<EntityType>,
    onEntitySelection: (Entity<*>) -> Unit,
    options: Options,
    lang: Lang,
) {
    Surface(elevation = 4.dp) {
        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth().height(24.dp)
                    .background(color = MaterialTheme.colors.primaryVariant),
            )
            TopMenu(
                disallowedTypes = disallowedTypes,
                onEntitySelection = onEntitySelection,
                options = options,
                lang = lang,
                modifier = Modifier
                    .fillMaxWidth().height(56.dp)
                    .background(color = MaterialTheme.colors.primary)
                    .padding(horizontal = 16.dp),
            )
        }
    }
}