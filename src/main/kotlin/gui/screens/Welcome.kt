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
import gui.settings.languages.Language


object Welcome : Screen {

    override fun title(language: Language): String = language.appName


    private const val ICON_WEIGHT = 7f
    private const val SPACE_WEIGHT = 10f

    @Composable
    operator fun invoke(changeScreen: () -> Unit) {
        Row(Modifier.fillMaxSize()) {
            Spacer(Modifier.weight(SPACE_WEIGHT).fillMaxHeight())
            Column(Modifier.weight(ICON_WEIGHT).fillMaxHeight()) {
                Spacer(Modifier.weight(SPACE_WEIGHT).fillMaxWidth())
                Box(Modifier.weight(ICON_WEIGHT).fillMaxWidth()) {
                    AnimatedIcon(changeScreen)
                }
                Spacer(Modifier.weight(SPACE_WEIGHT).fillMaxWidth())
            }
            Spacer(Modifier.weight(SPACE_WEIGHT).fillMaxHeight())
        }
    }


    private enum class IconAnimationState {
        None,
        Appearing,
        Disappearing,
    }

    private const val ICON_RES_PATH = "icon.png"

    private object Transparency {
        const val MAX = 1.0f
        const val MIN = 0.0f
    }

    @Composable
    fun AnimatedIcon(changeScreen: () -> Unit) {
        var ias by remember { mutableStateOf(IconAnimationState.None) }

        AppManager.windows.first().events.onOpen = { ias = IconAnimationState.Appearing }

        val alpha: Float by animateFloatAsState(
            targetValue = when (ias) {
                IconAnimationState.None -> Transparency.MIN
                IconAnimationState.Appearing -> Transparency.MAX
                IconAnimationState.Disappearing -> Transparency.MIN
            },
            animationSpec = spring(stiffness = 7f)
        ) {
            when (ias) {
                IconAnimationState.Appearing -> if (it == Transparency.MAX) {
                    ias = IconAnimationState.Disappearing
                }
                IconAnimationState.Disappearing -> if (it == Transparency.MIN) {
                    changeScreen()
                }
                else -> Unit
            }
        }

        Image(
            imageResource(ICON_RES_PATH), contentDescription = "Icon",
            modifier = Modifier.fillMaxSize().alpha(alpha)
        )
    }
}