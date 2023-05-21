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

package io.stardewvalleydesigner

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun AppTheme(themeVariant: ThemeVariant, content: @Composable () -> Unit) {
    when (themeVariant) {
        ThemeVariant.LIGHT -> AppLightTheme(content)
        ThemeVariant.DARK -> AppDarkTheme(content)
    }
}

enum class ThemeVariant {
    LIGHT,
    DARK,
}


@Composable
private fun AppLightTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = lightColors(
            primary = Color(0xFF33691E),
            primaryVariant = Color(0xFF003D00),
            secondary = Color(0xFF00695C),
            secondaryVariant = Color(0xFF003D33),
            // background =,
            // surface =,
            // error =,
            // onPrimary =,
            // onSecondary =,
            // onBackground =,
            // onSurface =,
            // onError =,
        ),
        typography = Typography(
            defaultFontFamily = FontFamily(
                Font(
                    family = "Roboto",
                    name = "Roboto-Regular",
                )
            ),
            // h1 = ,
            // h2 = ,
            // h3 = ,
            // h4 = ,
            // h5 = ,
            // h6 = ,
            // subtitle1 = ,
            // subtitle2 = ,
            // body1 = ,
            // body2 = ,
            // button = ,
            // caption = ,
            // overline = ,
        ),
        shapes = Shapes(
            // small = ,
            // medium = ,
            // large = ,
        ),
        content = content,
    )
}

@Composable
private fun AppDarkTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = darkColors(
            // primary = Color(0xFF),
            // primaryVariant = Color(0xFF),
            // secondary = Color(0xFF),
            // secondaryVariant = Color(0xFF),
            // background =,
            // surface =,
            // error =,
            // onPrimary =,
            // onSecondary =,
            // onBackground =,
            // onSurface =,
            // onError =,
        ),
        typography = Typography(
            // h1 = ,
            // h2 = ,
            // h3 = ,
            // h4 = ,
            // h5 = ,
            // h6 = ,
            // subtitle1 = ,
            // subtitle2 = ,
            // body1 = ,
            // body2 = ,
            // button = ,
            // caption = ,
            // overline = ,
        ),
        shapes = Shapes(
            // small = ,
            // medium = ,
            // large = ,
        ),
        content = content,
    )
}


val MENU_ELEVATION: Dp = 4.dp


// @Composable
// private fun FontFamily(
//     name: String,
//     weight: FontWeight = FontWeight.Normal,
//     style: FontStyle = FontStyle.Normal,
// ): FontFamily = FontFamily(
//     Font("fonts/Roboto/Roboto-Regular.ttf", weight, style),
// )

@Composable
private fun Font(
    family: String,
    name: String,
    weight: FontWeight = FontWeight.Normal,
    style: FontStyle = FontStyle.Normal,
): Font = Font("fonts/$family/$name.ttf", weight, style)
