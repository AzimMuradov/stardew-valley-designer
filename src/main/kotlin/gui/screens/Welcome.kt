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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.imageResource


object Welcome : Screen {

    private const val ICON_WEIGHT = 7f
    private const val SPACE_WEIGHT = 10f

    @Composable
    operator fun invoke(ias: IconAnimationState, animationListener: ((Float) -> Unit)) {
        Row(Modifier.fillMaxSize()) {
            Spacer(Modifier.weight(SPACE_WEIGHT).fillMaxHeight())
            Column(Modifier.weight(ICON_WEIGHT).fillMaxHeight()) {
                Spacer(Modifier.weight(SPACE_WEIGHT).fillMaxWidth())
                Box(Modifier.weight(ICON_WEIGHT).fillMaxWidth()) {
                    AnimatedIcon(ias, animationListener)
                }
                Spacer(Modifier.weight(SPACE_WEIGHT).fillMaxWidth())
            }
            Spacer(Modifier.weight(SPACE_WEIGHT).fillMaxHeight())
        }
    }


    enum class IconAnimationState {
        None,
        Appearing,
        Disappearing,
    }

    private const val ICON_RES_PATH = "icon.png"

    object Transparency {
        const val MAX = 1.0f
        const val MIN = 0.0f
    }

    @Composable
    fun AnimatedIcon(ias: IconAnimationState, animationListener: ((Float) -> Unit)) {
        val alpha: Float by animateFloatAsState(
            targetValue = when (ias) {
                IconAnimationState.None -> Transparency.MIN
                IconAnimationState.Appearing -> Transparency.MAX
                IconAnimationState.Disappearing -> Transparency.MIN
            },
            animationSpec = spring(stiffness = 7f),
            finishedListener = animationListener,
        )

        Image(
            imageResource(ICON_RES_PATH), contentDescription = "Icon",
            modifier = Modifier.fillMaxSize().alpha(alpha)
        )
    }
}