/*
 * Copyright 2021-2022 Azim Muradov
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

package me.azimmuradov.svc.utils.menu

import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Alignment.Companion.TopStart


// TODO
data class MenuPositioning(val anchor: Alignment, val alignment: Alignment)


fun Alignment.toVertical(): Alignment.Vertical = when (this) {
    TopStart, TopCenter, TopEnd -> Alignment.Top
    CenterStart, Center, CenterEnd -> Alignment.CenterVertically
    BottomStart, BottomCenter, BottomEnd -> Alignment.Bottom
    else -> error("Not supported")
}


fun Alignment.toHorizontal(): Alignment.Horizontal = when (this) {
    TopStart, CenterStart, BottomStart -> Alignment.Start
    TopCenter, Center, BottomCenter -> Alignment.CenterHorizontally
    TopEnd, CenterEnd, BottomEnd -> Alignment.End
    else -> error("Not supported")
}