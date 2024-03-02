/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * The source was changed by Azim Muradov for the Stardew Valley Designer project.
 */

package io.stardewvalleydesigner.cmplib.tooltip

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.PopupPositionProvider


@Composable
internal fun rememberComponentRectPositionProvider(
    componentRect: TooltipPlacement.ComponentRect,
): PopupPositionProvider {
    val (anchor, alignment, offset) = componentRect
    val offsetPx = with(LocalDensity.current) {
        IntOffset(offset.x.roundToPx(), offset.y.roundToPx())
    }
    return remember(anchor, alignment, offsetPx) {
        ComponentRectPositionProvider(anchor, alignment, offsetPx)
    }
}


internal data class ComponentRectPositionProvider(
    val anchor: Alignment,
    val alignment: Alignment,
    val offsetPx: IntOffset,
) : PopupPositionProvider {

    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize,
    ): IntOffset {
        val anchorPoint = anchor.align(IntSize.Zero, anchorBounds.size, layoutDirection)
        val tooltipArea = IntRect(
            IntOffset(
                anchorBounds.left + anchorPoint.x - popupContentSize.width,
                anchorBounds.top + anchorPoint.y - popupContentSize.height,
            ),
            IntSize(
                popupContentSize.width * 2,
                popupContentSize.height * 2
            )
        )
        val position = alignment.align(popupContentSize, tooltipArea.size, layoutDirection)
        return tooltipArea.topLeft + position + offsetPx
    }
}
