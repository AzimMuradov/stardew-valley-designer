/*
 * Copyright 2021-2021 Azim Muradov
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

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.settings.Lang
import me.azimmuradov.svc.settings.Settings


@Composable
fun LayersVisibility(
    visibleLayers: Set<LayerType<*>>,
    onVisibilityChange: (LayerType<*>, Boolean) -> Unit,
    lang: Lang,
) {
    Column(modifier = Modifier.padding(vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        for (lType in LayerType.all) {
            LayerVisibility(
                layerType = lType,
                visible = lType in visibleLayers,
                onVisibleChange = { vis -> onVisibilityChange(lType, vis) },
                lang = lang,
            )
        }
    }
}


@Composable
private fun LayerVisibility(
    layerType: LayerType<*>,
    visible: Boolean,
    onVisibleChange: (Boolean) -> Unit,
    lang: Lang,
) {
    val wordList = Settings.wordList(lang)

    Row(
        modifier = Modifier.fillMaxWidth().height(48.dp).padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconToggleButton(
            checked = visible,
            onCheckedChange = onVisibleChange,
            modifier = Modifier.aspectRatio(1f).fillMaxSize(),
        ) {
            Box(Modifier.fillMaxSize()) {
                Icon(
                    imageVector = if (visible) IconsRoundedVisibility else IconsRoundedVisibilityOff,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp).align(Alignment.Center),
                )
            }
        }
        Spacer(Modifier.width(8.dp))
        Text(text = wordList.layer(layerType), fontSize = 14.sp)
    }
}


// Copied from "androidx.compose.material.icons.rounded" package

private val IconsRoundedVisibility: ImageVector = materialIcon(name = "Rounded.Visibility") {
    materialPath {
        moveTo(12.0f, 4.0f)
        curveTo(7.0f, 4.0f, 2.73f, 7.11f, 1.0f, 11.5f)
        curveTo(2.73f, 15.89f, 7.0f, 19.0f, 12.0f, 19.0f)
        reflectiveCurveToRelative(9.27f, -3.11f, 11.0f, -7.5f)
        curveTo(21.27f, 7.11f, 17.0f, 4.0f, 12.0f, 4.0f)
        close()
        moveTo(12.0f, 16.5f)
        curveToRelative(-2.76f, 0.0f, -5.0f, -2.24f, -5.0f, -5.0f)
        reflectiveCurveToRelative(2.24f, -5.0f, 5.0f, -5.0f)
        reflectiveCurveToRelative(5.0f, 2.24f, 5.0f, 5.0f)
        reflectiveCurveToRelative(-2.24f, 5.0f, -5.0f, 5.0f)
        close()
        moveTo(12.0f, 8.5f)
        curveToRelative(-1.66f, 0.0f, -3.0f, 1.34f, -3.0f, 3.0f)
        reflectiveCurveToRelative(1.34f, 3.0f, 3.0f, 3.0f)
        reflectiveCurveToRelative(3.0f, -1.34f, 3.0f, -3.0f)
        reflectiveCurveToRelative(-1.34f, -3.0f, -3.0f, -3.0f)
        close()
    }
}

private val IconsRoundedVisibilityOff: ImageVector = materialIcon(name = "Rounded.VisibilityOff") {
    materialPath {
        moveTo(12.0f, 6.5f)
        curveToRelative(2.76f, 0.0f, 5.0f, 2.24f, 5.0f, 5.0f)
        curveToRelative(0.0f, 0.51f, -0.1f, 1.0f, -0.24f, 1.46f)
        lineToRelative(3.06f, 3.06f)
        curveToRelative(1.39f, -1.23f, 2.49f, -2.77f, 3.18f, -4.53f)
        curveTo(21.27f, 7.11f, 17.0f, 4.0f, 12.0f, 4.0f)
        curveToRelative(-1.27f, 0.0f, -2.49f, 0.2f, -3.64f, 0.57f)
        lineToRelative(2.17f, 2.17f)
        curveToRelative(0.47f, -0.14f, 0.96f, -0.24f, 1.47f, -0.24f)
        close()
        moveTo(2.71f, 3.16f)
        curveToRelative(-0.39f, 0.39f, -0.39f, 1.02f, 0.0f, 1.41f)
        lineToRelative(1.97f, 1.97f)
        curveTo(3.06f, 7.83f, 1.77f, 9.53f, 1.0f, 11.5f)
        curveTo(2.73f, 15.89f, 7.0f, 19.0f, 12.0f, 19.0f)
        curveToRelative(1.52f, 0.0f, 2.97f, -0.3f, 4.31f, -0.82f)
        lineToRelative(2.72f, 2.72f)
        curveToRelative(0.39f, 0.39f, 1.02f, 0.39f, 1.41f, 0.0f)
        curveToRelative(0.39f, -0.39f, 0.39f, -1.02f, 0.0f, -1.41f)
        lineTo(4.13f, 3.16f)
        curveToRelative(-0.39f, -0.39f, -1.03f, -0.39f, -1.42f, 0.0f)
        close()
        moveTo(12.0f, 16.5f)
        curveToRelative(-2.76f, 0.0f, -5.0f, -2.24f, -5.0f, -5.0f)
        curveToRelative(0.0f, -0.77f, 0.18f, -1.5f, 0.49f, -2.14f)
        lineToRelative(1.57f, 1.57f)
        curveToRelative(-0.03f, 0.18f, -0.06f, 0.37f, -0.06f, 0.57f)
        curveToRelative(0.0f, 1.66f, 1.34f, 3.0f, 3.0f, 3.0f)
        curveToRelative(0.2f, 0.0f, 0.38f, -0.03f, 0.57f, -0.07f)
        lineTo(14.14f, 16.0f)
        curveToRelative(-0.65f, 0.32f, -1.37f, 0.5f, -2.14f, 0.5f)
        close()
        moveTo(14.97f, 11.17f)
        curveToRelative(-0.15f, -1.4f, -1.25f, -2.49f, -2.64f, -2.64f)
        lineToRelative(2.64f, 2.64f)
        close()
    }
}