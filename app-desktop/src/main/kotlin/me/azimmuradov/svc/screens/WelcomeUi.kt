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

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import me.azimmuradov.svc.components.screens.Welcome
import me.azimmuradov.svc.screens.IconAnimationState.APPEARING
import me.azimmuradov.svc.screens.IconAnimationState.DISAPPEARING


@Composable
fun WelcomeUi(component: Welcome) {
    val iconWeight = 7f
    val spaceWeight = 10f

    Row(Modifier.fillMaxSize()) {
        Spacer(Modifier.weight(spaceWeight).fillMaxHeight())
        Column(Modifier.weight(iconWeight).fillMaxHeight()) {
            Spacer(Modifier.weight(spaceWeight).fillMaxWidth())
            AnimatedIcon(component, modifier = Modifier.weight(iconWeight).fillMaxWidth())
            Spacer(Modifier.weight(spaceWeight).fillMaxWidth())
        }
        Spacer(Modifier.weight(spaceWeight).fillMaxHeight())
    }
}


@Composable
private fun AnimatedIcon(component: Welcome, modifier: Modifier = Modifier) {
    val minTransparency = 0.0f
    val maxTransparency = 1.0f
    val stiffness = 7f

    var iconAnimationState by remember { mutableStateOf(APPEARING) }
    val alpha = remember { Animatable(minTransparency) }

    LaunchedEffect(iconAnimationState) {
        alpha.animateTo(
            targetValue = when (iconAnimationState) {
                APPEARING -> maxTransparency
                DISAPPEARING -> minTransparency
            },
            animationSpec = spring(stiffness)
        ) {
            when (iconAnimationState) {
                APPEARING -> if (alpha.value == maxTransparency) {
                    iconAnimationState = DISAPPEARING
                }
                DISAPPEARING -> if (alpha.value == minTransparency) {
                    component.onWelcomeScreenEnd()
                }
            }
        }
    }

    Image(
        painter = painterResource(ICON_RES_PATH),
        contentDescription = contentDescription,
        modifier = modifier.alpha(alpha.value)
    )
}

enum class IconAnimationState {
    APPEARING,
    DISAPPEARING,
}

private const val ICON_RES_PATH = "icon.png"

private const val contentDescription = "Icon"