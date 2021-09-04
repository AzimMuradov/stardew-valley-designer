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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.azimmuradov.svc.components.cartographer.Cartographer
import me.azimmuradov.svc.engine.aspectRatio


@Composable
fun MainPart(component: Cartographer, modifier: Modifier = Modifier) {
    val ratio = component.models.value.editor.layout.size.aspectRatio

    Box(modifier) {
        EditorLayout(
            component,
            modifier = Modifier
                .aspectRatio(ratio)
                .border(width = 1.dp, color = Color.Black)
                .fillMaxSize()
                .align(Alignment.Center)
                // .clipToBounds()
                .background(color = Color.White),
        )
    }
}