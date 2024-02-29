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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.stardewvalleydesigner.cmplib.group.GroupOption
import io.stardewvalleydesigner.cmplib.group.ToggleButtonsGroup
import io.stardewvalleydesigner.metadata.Season


@Composable
internal fun SeasonMenu(
    chosenSeason: Season,
    onSeasonChosen: (Season) -> Unit,
) {
    ToggleButtonsGroup(
        buttonLabels = Season.entries.map { GroupOption.Some(it) },
        rowSize = 4u,
        modifier = Modifier.fillMaxWidth(fraction = 0.7f),
        chosenLabel = chosenSeason,
        onButtonClick = onSeasonChosen,
        buttonContent = { season ->
            Text(season.name)
        }
    )
}
