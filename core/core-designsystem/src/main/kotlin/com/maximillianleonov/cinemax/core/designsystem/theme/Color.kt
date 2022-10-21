/*
 * Copyright 2022 Maximillian Leonov
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

package com.maximillianleonov.cinemax.core.designsystem.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val LightPrimary = Color(0xFFDFD8FF)
private val LightPrimaryVariant = Color(0xFFD1C4E9)
private val LightAccent = Color(0xFF441EEC)
private val LightSecondary = Color(0xFF00993F)
private val LightSecondaryVariant = Color(0xFFE06000)
private val LightError = Color(0xFFB80000)
private val LightTextPrimary = Color(0xFF0E0D0D)
private val LightTextPrimaryVariant = Color(0xFF272727)
private val LightTextSecondary = Color(0xFF4F4F53)

private val DarkPrimary = Color(0xFF1F1D2B)
private val DarkPrimaryVariant = Color(0xFF252836)
private val DarkAccent = Color(0xFF12CDD9)
private val DarkSecondary = Color(0xFF22B07D)
private val DarkSecondaryVariant = Color(0xFFFF8700)
private val DarkError = Color(0xFFFB4141)
private val DarkTextPrimary = Color(0xFFFFFBFF)
private val DarkTextPrimaryVariant = Color(0xFFEBEBEF)
private val DarkTextSecondary = Color(0xFF92929D)

private val TextOnMedia = Color(0xFFFFFBFF)
private val TextOnMediaVariant = Color(0xFFEBEBEF)

internal val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    secondary = LightPrimaryVariant,
    background = LightPrimary,
    surface = LightPrimary
)

internal val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    secondary = DarkPrimaryVariant,
    background = DarkPrimary,
    surface = DarkPrimary
)

@Immutable
data class CinemaxColors(
    val default: Color = Color.Unspecified,
    val primary: Color,
    val primaryVariant: Color,
    val accent: Color,
    val secondary: Color,
    val secondaryVariant: Color,
    val error: Color,
    val textPrimary: Color,
    val textPrimaryVariant: Color,
    val textSecondary: Color,
    val textOnMedia: Color,
    val textOnMediaVariant: Color
)

internal val LightCinemaxColors = CinemaxColors(
    primary = LightPrimary,
    primaryVariant = LightPrimaryVariant,
    accent = LightAccent,
    secondary = LightSecondary,
    secondaryVariant = LightSecondaryVariant,
    error = LightError,
    textPrimary = LightTextPrimary,
    textPrimaryVariant = LightTextPrimaryVariant,
    textSecondary = LightTextSecondary,
    textOnMedia = TextOnMedia,
    textOnMediaVariant = TextOnMediaVariant
)

internal val DarkCinemaxColors = CinemaxColors(
    primary = DarkPrimary,
    primaryVariant = DarkPrimaryVariant,
    accent = DarkAccent,
    secondary = DarkSecondary,
    secondaryVariant = DarkSecondaryVariant,
    error = DarkError,
    textPrimary = DarkTextPrimary,
    textPrimaryVariant = DarkTextPrimaryVariant,
    textSecondary = DarkTextSecondary,
    textOnMedia = TextOnMedia,
    textOnMediaVariant = TextOnMediaVariant
)

internal val LocalCinemaxColors = staticCompositionLocalOf<CinemaxColors> {
    error(LocalCinemaxColorsErrorMessage)
}

private const val LocalCinemaxColorsErrorMessage = "No CinemaxColors provided."
