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

package me.azimmuradov.svc.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import me.azimmuradov.svc.components.screens.WelcomeComponent
import me.azimmuradov.svc.screens.IconAnimationState.APPEARING
import me.azimmuradov.svc.screens.IconAnimationState.DISAPPEARING


@Composable
fun WelcomeUi(component: WelcomeComponent) {
    var iconAnimationState by remember { mutableStateOf(APPEARING) }
    val alpha = remember { Animatable(ICON_MIN_TRANSPARENCY) }

    LaunchedEffect(iconAnimationState) {
        alpha.animateTo(
            targetValue = when (iconAnimationState) {
                APPEARING -> ICON_MAX_TRANSPARENCY
                DISAPPEARING -> ICON_MIN_TRANSPARENCY
            },
            animationSpec = spring(ICON_ANIMATION_STIFFNESS)
        ) {
            when (iconAnimationState) {
                APPEARING -> if (alpha.value == ICON_MAX_TRANSPARENCY) {
                    iconAnimationState = DISAPPEARING
                }

                DISAPPEARING -> if (alpha.value == ICON_MIN_TRANSPARENCY) {
                    component.onWelcomeScreenEnd()
                }
            }
        }
    }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(ICON_RES_PATH),
            contentDescription = null,
            modifier = Modifier.size(200.dp).alpha(alpha.value)
        )
    }
}


private enum class IconAnimationState { APPEARING, DISAPPEARING }

private const val ICON_MIN_TRANSPARENCY = 0.0f
private const val ICON_MAX_TRANSPARENCY = 1.0f
private const val ICON_ANIMATION_STIFFNESS = 7f

private const val ICON_RES_PATH = "icon.png"