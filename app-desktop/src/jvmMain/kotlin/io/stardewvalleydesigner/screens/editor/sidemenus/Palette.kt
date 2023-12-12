/*
 * Copyright 2021-2023 Azim Muradov
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

package io.stardewvalleydesigner.screens.editor.sidemenus

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.cmplib.group.GroupOption
import io.stardewvalleydesigner.cmplib.group.ToggleButtonsGroup
import io.stardewvalleydesigner.editor.EditorIntent
import io.stardewvalleydesigner.editor.modules.palette.PaletteState
import io.stardewvalleydesigner.editor.res.OtherImages
import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.entity.Colors
import io.stardewvalleydesigner.utils.*
import androidx.compose.ui.graphics.Color as ComposeColor


@Composable
fun Palette(
    palette: PaletteState,
    intentConsumer: (EditorIntent.Palette) -> Unit,
) {
    // TODO : hotbar feature (val hotbar = palette.hotbar)

    InUseCard(palette.inUse)

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

    val inUse = palette.inUse

    if (inUse is FlavoredEntity) {
        Spacer(modifier = Modifier.height(8.dp))

        when (val e = inUse as FlavoredEntity) {
            is Rotatable -> Unit // TODO

            is Colored.ColoredFishPond -> Unit // TODO

            is Colored.ColoredChest -> ChestColorMenu(
                color = e.color,
                onColorChosen = {
                    intentConsumer(
                        EditorIntent.Palette.AddToInUse(
                            when (e) {
                                is Equipment.Chest -> e.copy(color = it)
                                is Equipment.StoneChest -> e.copy(color = it)
                            }
                        )
                    )
                }
            )

            is TripleColoredFarmBuilding -> Unit // TODO
        }
    }
}


// TODO : Size bug on certain entities

@Composable
private fun InUseCard(inUse: Entity<*>?) {
    val wordList = GlobalSettings.strings

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
                        style = MaterialTheme.typography.subtitle2,
                    )
                }
            }
        }
    }
}


@Composable
private fun ChestColorMenu(color: Colors.ChestColors, onColorChosen: (Colors.ChestColors) -> Unit) {
    ToggleButtonsGroup(
        buttonLabels = Colors.ChestColors.entries.map { GroupOption.Some(it) },
        rowSize = 7u,
        modifier = Modifier.fillMaxWidth(),
        chosenLabel = color,
        onButtonClick = onColorChosen,
        spaceContent = {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                modifier = Modifier.fillMaxSize().padding(8.dp),
            )
        },
        buttonContent = { c ->
            val value = c.value
            Box(
                Modifier
                    .fillMaxSize()
                    .then(
                        if (value != null) {
                            Modifier.background(value.toComposeColor())
                        } else {
                            Modifier.drawBehind {
                                drawImage(
                                    image = OtherImages.defaultChestMenu,
                                    dstSize = size.toIntSize(),
                                    filterQuality = FilterQuality.None
                                )
                            }
                        }
                    )
                    .border(1.dp, ComposeColor.Black)
            )
        }
    )
}
