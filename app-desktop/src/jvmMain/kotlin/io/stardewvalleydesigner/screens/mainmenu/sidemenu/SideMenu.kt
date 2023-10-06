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

package io.stardewvalleydesigner.screens.mainmenu.sidemenu

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.stardewvalleydesigner.ICON_RES_PATH
import io.stardewvalleydesigner.ternaryColor
import io.stardewvalleydesigner.utils.*
import java.net.URI


@Composable
fun RowScope.SideMenu() {
    val wordList = GlobalSettings.strings

    val menuWidth by animateDpAsState(
        when (LocalWindowSize.current) {
            WindowSize.SMALL -> 240.dp
            WindowSize.MEDIUM -> 300.dp
        }
    )

    val appNameTextStyle by animateValueAsState(
        targetValue = when (LocalWindowSize.current) {
            WindowSize.SMALL -> MaterialTheme.typography.h5
            WindowSize.MEDIUM -> MaterialTheme.typography.h4
        },
        typeConverter = TwoWayConverter(
            convertToVector = {
                AnimationVector2D(it.fontSize.value, it.letterSpacing.value)
            },
            convertFromVector = {
                TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = it.v1.sp,
                    letterSpacing = it.v2.sp
                )
            }
        )
    )

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(menuWidth)
            .shadow(
                elevation = 4.dp,
                shape = MaterialTheme.shapes.large
            )
            .background(MaterialTheme.colors.secondary)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 9f / 5f)
                .clip(shape = MaterialTheme.shapes.medium)
                .background(ternaryColor)
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(ICON_RES_PATH),
                contentDescription = null,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.size(12.dp))
            Text(
                text = GlobalSettings.strings.application,
                modifier = Modifier.weight(3f),
                textAlign = TextAlign.Center,
                style = appNameTextStyle
            )
        }

        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SideMenuButton(
                text = wordList.buttonStardewValleyText,
                tooltip = wordList.buttonStardewValleyTooltip,
                icon = Icons.Filled.OpenInBrowser,
                onClick = { BrowserUtils.open(URI.create("https://www.stardewvalley.net/")) }
            )
            SideMenuButton(
                text = wordList.buttonSwitchThemeText,
                tooltip = wordList.buttonSwitchThemeTooltip,
                icon = Icons.Filled.LightMode,
                enabled = false
            )
            SideMenuButton(
                text = wordList.buttonSettingsText,
                tooltip = wordList.buttonSettingsTooltip,
                icon = Icons.Filled.Settings,
                enabled = false
            )
            SideMenuButton(
                text = wordList.buttonDonateText,
                tooltip = wordList.buttonDonateTooltip,
                icon = Icons.Filled.EmojiFoodBeverage,
                enabled = false
            )
            InfoWindow()
        }
    }
}
