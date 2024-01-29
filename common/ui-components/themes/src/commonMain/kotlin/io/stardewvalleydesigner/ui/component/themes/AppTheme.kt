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

package io.stardewvalleydesigner.ui.component.themes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import stardew_valley_designer.common.ui_components.themes.generated.resources.Res


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


// Temporary solution

val ternaryColor = Color(0xFFFFFFD2)


@Composable
private fun AppLightTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = lightColors(
            primary = Color(0xFF33691E),
            primaryVariant = Color(0xFF003D00),
            secondary = Color(0xFF00695C),
            secondaryVariant = Color(0xFF003D33),
            background = Color.White,
            surface = Color.White,
            error = Color(0xFFB00020),
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.Black,
            onSurface = Color.Black,
            onError = Color.White,
        ),
        typography = getTypography(),
        shapes = shapes,
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
        typography = getTypography(),
        shapes = shapes,
        content = content,
    )
}

@Composable
private fun getTypography(): Typography = Typography(
    defaultFontFamily = fontFamily(),
    h1 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 60.sp,
        letterSpacing = (-1.5).sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 48.sp,
        letterSpacing = (-0.5).sp
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        letterSpacing = 0.sp
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        letterSpacing = 0.25.sp
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 0.1.sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.5.sp
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.25.sp
    ),
    button = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = 0.5.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        letterSpacing = 0.4.sp
    ),
    overline = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 8.sp,
        letterSpacing = 1.5.sp
    )
)

private val shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp),
)


@Composable
private fun fontFamily(): FontFamily {
    var ff: FontFamily by remember { mutableStateOf(FontFamily.Default) }

    LaunchedEffect(Unit) {
        ff = FontFamily(
            buildList {
                for ((weight, weightName) in listOf(
                    FontWeight.Thin to "Thin",
                    FontWeight.Light to "Light",
                    FontWeight.Normal to "Regular",
                    FontWeight.Medium to "Medium",
                    FontWeight.Bold to "Bold",
                    FontWeight.Black to "Black",
                )) {
                    for ((style, styleName) in listOf(
                        FontStyle.Normal to "",
                        FontStyle.Italic to "Italic"
                    )) {
                        this += Font(
                            family = "Roboto",
                            name = "Roboto-$weightName$styleName",
                            weight = weight,
                            style = style,
                        )
                    }
                }
            }
        )
    }

    return ff
}


@OptIn(ExperimentalResourceApi::class)
private suspend fun Font(
    family: String,
    name: String,
    weight: FontWeight = FontWeight.Normal,
    style: FontStyle = FontStyle.Normal,
) = Font(
    identity = "$name-$family",
    data = Res.readBytes("files/fonts/$family/$name.ttf"),
    weight,
    style
)
