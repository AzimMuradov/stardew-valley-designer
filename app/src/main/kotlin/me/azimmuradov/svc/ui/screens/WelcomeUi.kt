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

package me.azimmuradov.svc.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import me.azimmuradov.svc.components.welcome.Welcome
import me.azimmuradov.svc.components.welcome.Welcome.IconAnimationState.APPEARING
import me.azimmuradov.svc.components.welcome.Welcome.IconAnimationState.DISAPPEARING


@Composable
fun WelcomeUi(component: Welcome) {

    Row(Modifier.fillMaxSize()) {
        Spacer(Modifier.weight(SPACE_WEIGHT).fillMaxHeight())
        Column(Modifier.weight(ICON_WEIGHT).fillMaxHeight()) {
            Spacer(Modifier.weight(SPACE_WEIGHT).fillMaxWidth())
            Box(Modifier.weight(ICON_WEIGHT).fillMaxWidth()) {
                AnimatedIcon(component)
            }
            Spacer(Modifier.weight(SPACE_WEIGHT).fillMaxWidth())
        }
        Spacer(Modifier.weight(SPACE_WEIGHT).fillMaxHeight())
    }
}

@Composable
private fun AnimatedIcon(component: Welcome) {

    val models by component.models.subscribeAsState()

    val alpha = remember { Animatable(Transparency.MIN) }

    LaunchedEffect(models.iconAnimationState) {
        alpha.animateTo(
            targetValue = when (models.iconAnimationState) {
                APPEARING -> Transparency.MAX
                DISAPPEARING -> Transparency.MIN
            },
            animationSpec = spring(stiffness = 7f)
        ) {
            when (models.iconAnimationState) {
                APPEARING -> if (alpha.value == Transparency.MAX) {
                    models.iconAnimationState = DISAPPEARING
                }
                DISAPPEARING -> if (alpha.value == Transparency.MIN) {
                    component.onWelcomeScreenEnd()
                }
            }
        }
    }

    Image(
        painter = painterResource(ICON_RES_PATH),
        contentDescription = "Icon",
        modifier = Modifier.fillMaxSize().alpha(alpha.value)
    )
}


private const val ICON_WEIGHT = 7f
private const val SPACE_WEIGHT = 10f

private object Transparency {
    const val MIN = 0.0f
    const val MAX = 1.0f
}

private const val ICON_RES_PATH = "icon.png"