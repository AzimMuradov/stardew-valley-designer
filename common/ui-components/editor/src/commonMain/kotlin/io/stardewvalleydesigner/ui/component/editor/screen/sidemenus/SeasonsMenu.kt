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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import io.stardewvalleydesigner.cmplib.group.GroupOption
import io.stardewvalleydesigner.cmplib.group.ToggleButtonsGroup
import io.stardewvalleydesigner.data.Season
import io.stardewvalleydesigner.ui.component.editor.res.ImageResourcesProvider.rememberImageResource


@Composable
internal fun SeasonsMenu(
    chosenSeason: Season,
    onSeasonChosen: (Season) -> Unit,
) {
    val spring = rememberImageResource(path = "other/spring.png")
    val summer = rememberImageResource(path = "other/summer.png")
    val fall = rememberImageResource(path = "other/fall.png")
    val winter = rememberImageResource(path = "other/winter.png")

    ToggleButtonsGroup(
        buttonLabels = Season.entries.map { GroupOption.Some(it) },
        rowSize = 4u,
        modifier = Modifier.fillMaxWidth(fraction = 0.8f),
        chosenLabel = chosenSeason,
        onButtonClick = onSeasonChosen,
        buttonContent = { season ->
            Image(
                bitmap = when (season) {
                    Season.Spring -> spring
                    Season.Summer -> summer
                    Season.Fall -> fall
                    Season.Winter -> winter
                },
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth,
                filterQuality = FilterQuality.None,
            )
        }
    )
}
