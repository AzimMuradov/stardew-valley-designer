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

package io.stardewvalleydesigner.ui.component.editor.utils

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.stardewvalleydesigner.data.Season
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.ui.component.editor.res.*
import io.stardewvalleydesigner.ui.component.editor.utils.DrawerUtils.drawEntityContained


@Composable
internal fun Sprite(
    sprite: Sprite,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier) {
        drawEntityContained(sprite, layoutSize = size)
    }
}

@Composable
internal fun Sprite(
    entity: Entity<*>,
    season: Season,
    modifier: Modifier = Modifier,
) {
    Sprite(
        sprite = SpriteUtils.calculateSprite(
            spriteMaps = ImageResources.entities,
            entity, season,
        ),
        modifier,
    )
}
