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

package io.stardewvalleydesigner.ui.component.windowsize

import androidx.compose.runtime.*


@Composable
fun WithMeasuredWindowSize(
    windowWidth: Int,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalWindowSize provides WindowSize.fromWindowWidth(windowWidth),
        content = content
    )
}

@Composable
fun WithDefaultWindowSize(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalWindowSize provides WindowSize.LARGE,
        content = content
    )
}


val LocalWindowSize = staticCompositionLocalOf<WindowSize> {
    error("No window size provided")
}
