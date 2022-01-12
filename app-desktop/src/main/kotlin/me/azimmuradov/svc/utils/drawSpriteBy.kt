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

package me.azimmuradov.svc.utils

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntOffset
import me.azimmuradov.svc.components.screens.cartographer.res.EntitySpritesProvider
import me.azimmuradov.svc.engine.entity.Entity


fun DrawScope.drawSpriteBy(
    entity: Entity<*>,
    offset: IntOffset = IntOffset.Zero,
    layoutSize: Size,
    alpha: Float = 1.0f,
) {
    drawSprite(
        sprite = EntitySpritesProvider.spriteBy(entity),
        offset = offset,
        layoutSize = layoutSize,
        alpha = alpha,
    )
}