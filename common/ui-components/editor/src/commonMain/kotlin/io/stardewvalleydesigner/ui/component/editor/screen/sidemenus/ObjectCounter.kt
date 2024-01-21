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

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.engine.layers.LayeredEntitiesData
import io.stardewvalleydesigner.engine.layers.flattenSequence
import io.stardewvalleydesigner.kmplib.clipboard.Clipboard
import io.stardewvalleydesigner.metadata.*
import io.stardewvalleydesigner.ui.component.editor.utils.Sprite
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings


@Composable
internal fun ObjectCounter(entities: LayeredEntitiesData) {
    val wordList = GlobalSettings.strings
    val countedEntities = entities
        .flattenSequence()
        .map { it.rectObject }
        .groupingBy { EntityDataProvider.entityToId.getValue(it).default }
        .eachCount()
        .asSequence()
        .sortedWith(
            compareBy<Map.Entry<EntityId, Int>> { -it.value }
                .thenBy { wordList.entity(EntityDataProvider.entityById.getValue(it.key)) }
                .thenBy { it.key.page.ordinal }
                .thenBy { it.key.localId }
        )
        .toList()

    var isCopied by remember { mutableStateOf(false) }

    LaunchedEffect(countedEntities) {
        isCopied = false
    }

    val tint by animateColorAsState(
        targetValue = if (isCopied) MaterialTheme.colors.primary else Color.Black,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(Modifier.size(36.dp))
        Spacer(Modifier.width(8.dp))
        Divider(Modifier.weight(1f))
        Spacer(Modifier.width(8.dp))
        Text(
            text = wordList.objectCounterTitle,
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(Modifier.width(8.dp))
        Divider(Modifier.weight(1f))
        Spacer(Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.ContentCopy,
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .pointerHoverIcon(PointerIcon.Hand)
                .clickable(
                    interactionSource = remember(::MutableInteractionSource),
                    indication = rememberRipple(color = Color.Black),
                    onClick = {
                        Clipboard.copyToClipboard(
                            text = buildString {
                                appendLine("# ${wordList.objectCounterTitle}")
                                appendLine()
                                for ((eId, cnt) in countedEntities) {
                                    val entity = EntityDataProvider.entityById.getValue(eId)
                                    appendLine("- [ ] ${wordList.entity(entity)} - $cnt")
                                }
                            }
                        )
                        isCopied = true
                    }
                )
                .padding(8.dp)
                .size(20.dp),
            tint = tint
        )
    }

    Spacer(Modifier.size(16.dp))

    Box(modifier = Modifier.animateContentSize()) {
        val stateVertical = rememberScrollState(initial = 0)

        Column(
            modifier = Modifier
                .verticalScroll(stateVertical)
                .padding(end = if (stateVertical.canScrollBackward || stateVertical.canScrollForward) 12.dp else 0.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            for ((eId, cnt) in countedEntities) {
                val entity = EntityDataProvider.entityById.getValue(eId)

                Row(Modifier.fillMaxWidth()) {
                    Surface(
                        modifier = Modifier.wrapContentSize(),
                        shape = RoundedCornerShape(3.dp),
                        border = BorderStroke(Dp.Hairline, MaterialTheme.colors.secondaryVariant)
                    ) {
                        Sprite(entity, modifier = Modifier.size(16.dp).padding(2.dp))
                    }
                    Spacer(Modifier.width(6.dp))
                    Text(text = wordList.entity(entity))
                    Spacer(Modifier.weight(1f))
                    Text(text = "$cnt")
                }
            }
        }

        Box(modifier = Modifier.matchParentSize()) {
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd),
                adapter = rememberScrollbarAdapter(stateVertical)
            )
        }
    }
}
