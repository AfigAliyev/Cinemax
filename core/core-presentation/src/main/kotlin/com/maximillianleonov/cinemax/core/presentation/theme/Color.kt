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

package com.maximillianleonov.cinemax.core.presentation.theme

import androidx.compose.material.darkColors
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val Dark = Color(0xFF1F1D2B)
private val Soft = Color(0xFF252836)
private val BlueAccent = Color(0xFF12CDD9)
private val Green = Color(0xFF22B07D)
private val Orange = Color(0xFFFF8700)
private val Red = Color(0xFFFB4141)
private val Black = Color(0xFF171725)
private val Grey = Color(0xFF92929D)
private val DarkGrey = Color(0xFF696974)
private val White = Color(0xFFFFFFFF)
private val WhiteGrey = Color(0xFFEBEBEF)
private val LineDark = Color(0xFFEAEAEA)

internal val DarkColorPalette = darkColors(
    primary = Dark,
    primaryVariant = Soft,
    secondary = Green,
    secondaryVariant = Orange,
    background = Dark,
    surface = Dark
)

@Immutable
data class CinemaxColors(
    val default: Color = Color.Unspecified,
    val primaryDark: Color = Dark,
    val primarySoft: Color = Soft,
    val primaryBlue: Color = BlueAccent,
    val secondaryGreen: Color = Green,
    val secondaryOrange: Color = Orange,
    val secondaryRed: Color = Red,
    val textWhite: Color = White,
    val textWhiteGrey: Color = WhiteGrey,
    val textBlack: Color = Black,
    val textGrey: Color = Grey,
    val textDarkGrey: Color = DarkGrey,
    val textLineDark: Color = LineDark
)

internal val LocalCinemaxColors = staticCompositionLocalOf { CinemaxColors() }
