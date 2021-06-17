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

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.desktop.AppManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.imageResource


object Welcome : Screen {

    private const val ICON_WEIGHT = 7f
    private const val SPACE_WEIGHT = 10f

    private const val ICON_RES_PATH = "icon.png"

    @Composable
    operator fun invoke() {
        Row(Modifier.fillMaxSize()) {
            Spacer(Modifier.weight(SPACE_WEIGHT).fillMaxHeight())
            Column(Modifier.weight(ICON_WEIGHT).fillMaxHeight()) {
                Spacer(Modifier.weight(SPACE_WEIGHT).fillMaxWidth())
                Box(modifier = Modifier.weight(ICON_WEIGHT)) { LogoAnimation() }
                Spacer(Modifier.weight(SPACE_WEIGHT).fillMaxWidth())
            }
            Spacer(Modifier.weight(SPACE_WEIGHT).fillMaxHeight())
        }
    }

    @Composable
    fun LogoAnimation() {
        var visible by remember { mutableStateOf(false) }
        var isGone by remember { mutableStateOf(false) }
        val alpha: Float by animateFloatAsState(
            targetValue = if (visible) Transparency.MAX else Transparency.MIN,
            animationSpec = spring(stiffness = 7f)
        ) {
            if (visible && it == Transparency.MAX) {
                visible = false
                isGone = true
            } else if (!isGone && !visible && it == Transparency.MIN) {
                visible = true
            }
        }

        AppManager.windows.first().events.onOpen = { visible = !visible }

        Image(imageResource(ICON_RES_PATH), contentDescription = "Icon", modifier = Modifier.alpha(alpha).fillMaxSize())
    }

    object Transparency {
        const val MAX = 1.0f
        const val MIN = 0.0f
    }
}