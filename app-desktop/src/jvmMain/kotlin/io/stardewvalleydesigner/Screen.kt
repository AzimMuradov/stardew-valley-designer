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

package io.stardewvalleydesigner

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*
import io.stardewvalleydesigner.ui.component.windowsize.WithMeasuredWindowSize
import kotlinx.coroutines.delay
import java.awt.Dimension
import kotlin.math.roundToInt


@Composable
fun Screen(
    title: String,
    initialSize: DpSize,
    onCloseRequest: () -> Unit,
    onKeyEvent: (KeyEvent) -> Boolean = { false },
    ui: @Composable () -> Unit,
) {
    val state = rememberWindowState(
        position = WindowPosition(Alignment.Center),
        size = initialSize
    )
    Window(
        onCloseRequest = onCloseRequest,
        state = state,
        title = title,
        icon = painterResource(ICON_RES_PATH),
        resizable = true,
        onKeyEvent = onKeyEvent,
    ) {
        WithMeasuredWindowSize(windowWidth = state.size.width.value.roundToInt()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ui()
            }
        }

        LaunchedEffect(Unit) {
            window.minimumSize = dimension(initialSize)
            delay(timeMillis = 200)
            window.toFront()
        }
    }
}


private fun dimension(dpSize: DpSize) = Dimension(
    dpSize.width.value.roundToInt(),
    dpSize.height.value.roundToInt(),
)
