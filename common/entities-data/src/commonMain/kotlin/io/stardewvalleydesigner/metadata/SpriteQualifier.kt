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

package io.stardewvalleydesigner.metadata


sealed interface SpriteQualifier {

    data class SeasonQualifier(
        val season: Season,
    ) : SpriteQualifier

    data class FenceQualifier(
        val variant: FenceVariant,
    ) : SpriteQualifier

    data class FloorQualifier(
        val variant: FloorVariant,
        val isNotWinter: Boolean,
    ) : SpriteQualifier

    data class TubOFlowersQualifier(
        val isBlooming: Boolean,
    ) : SpriteQualifier

    data class SteppingStonePathQualifier(
        val isNotWinter: Boolean,
    ) : SpriteQualifier

    data object None : SpriteQualifier
}
