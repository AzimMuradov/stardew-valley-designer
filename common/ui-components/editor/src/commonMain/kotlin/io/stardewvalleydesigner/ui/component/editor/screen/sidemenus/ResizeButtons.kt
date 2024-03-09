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

package io.stardewvalleydesigner.ui.component.editor.screen.sidemenus

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.ui.component.editor.res.ImageResourcesProvider


@Composable
internal fun ResizeButtons(
    canIncrease: Boolean,
    canDecrease: Boolean,
    onIncreased: () -> Unit,
    onDecreased: () -> Unit,
) {
    val zoomIn = ImageResourcesProvider.rememberImageResource(path = "other/zoom-in.png")
    val zoomOut = ImageResourcesProvider.rememberImageResource(path = "other/zoom-out.png")
    Row(verticalAlignment = Alignment.CenterVertically) {
        ResizeButton(
            bitmap = zoomOut,
            enabled = canDecrease,
            onClick = onDecreased,
        )
        Spacer(Modifier.size(4.dp))
        ResizeButton(
            bitmap = zoomIn,
            enabled = canIncrease,
            onClick = onIncreased,
        )
    }
}

@Composable
internal fun ResizeButton(
    bitmap: ImageBitmap,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Box(contentAlignment = Alignment.Center) {
        val contentAlpha = if (enabled) ContentAlpha.high else ContentAlpha.disabled
        Icon(
            bitmap = bitmap,
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .then(if (enabled) Modifier.pointerHoverIcon(PointerIcon.Hand) else Modifier)
                .clickable(enabled, onClick = onClick)
                .padding(10.dp)
                .size(24.dp),
            tint = Color.Black.copy(contentAlpha),
        )
    }
}
