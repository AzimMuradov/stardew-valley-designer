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

package me.azimmuradov.svc.screens.cartographer.sidemenus

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.azimmuradov.svc.cartographer.state.PaletteState
import me.azimmuradov.svc.cartographer.wishes.SvcWish
import me.azimmuradov.svc.engine.entity.Entity
import me.azimmuradov.svc.settings.wordlists.WordList
import me.azimmuradov.svc.utils.GlobalSettings
import me.azimmuradov.svc.utils.Sprite


@Composable
fun Palette(
    palette: PaletteState,
    wishConsumer: (SvcWish.Palette) -> Unit,
) {
    // TODO : hotbar feature (val hotbar = palette.hotbar)
    val wordList = GlobalSettings.strings

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        InUseCard(palette.inUse, wordList)

        // TODO : hotbar feature
        //
        // Spacer(modifier = Modifier.height(8.dp))
        //
        // ButtonsGroup(
        //     buttonLabels = hotbar,
        //     rowSize = 5u,
        //     onButtonClick = { },
        //     spaceContent = { Icon(Icons.Default.Clear, null, Modifier.fillMaxSize()) },
        //     buttonContent = { entity -> Sprite(entity = entity, modifier = Modifier.fillMaxSize()) },
        // )

        // Flavors
        // if (inUse is EntityFlavor) {
        //     val a = when (inUse) {
        //         is RotatableFlavor.RotatableFlavor2 -> TODO()
        //         is RotatableFlavor.RotatableFlavor4 -> TODO()
        //         is ColoredFlavor.ColoredFishPondFlavor -> TODO()
        //         is ColoredFlavor.ColoredChestFlavor -> TODO()
        //         is ColoredFarmBuildingFlavor -> TODO()
        //     }
        // }
    }
}


@Composable
private fun InUseCard(
    inUse: Entity<*>?,
    wordList: WordList,
) {
    Card(elevation = 0.dp) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = { /* TODO : hotbar feature */ },
                modifier = Modifier
                    .size(56.dp)
                    .border(
                        width = Dp.Hairline,
                        color = MaterialTheme.colors.secondaryVariant,
                        shape = CircleShape,
                    ),
                elevation = ButtonDefaults.elevation(pressedElevation = 4.dp),
                shape = CircleShape,
                colors = ButtonDefaults.outlinedButtonColors(),
                contentPadding = PaddingValues(12.dp),
                enabled = false, /* TODO : hotbar feature (inUse != null) */
            ) {
                if (inUse != null) {
                    Sprite(entity = inUse, modifier = Modifier.fillMaxSize())
                } else {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().padding(4.dp),
                    )
                }
            }

            if (inUse != null) {
                Column {
                    Text(
                        text = wordList.entity(e = inUse),
                        color = MaterialTheme.colors.secondaryVariant,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.subtitle1,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = with(inUse.size) { "$w x $h" },
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
        }
    }
}