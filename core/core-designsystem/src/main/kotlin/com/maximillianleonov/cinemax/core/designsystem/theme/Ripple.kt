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

@file:Suppress("MatchingDeclarationName")

package com.maximillianleonov.cinemax.core.designsystem.theme

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Immutable
internal object CinemaxRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = RippleColor

    @Composable
    override fun rippleAlpha() = RippleAlpha
}

@Composable
internal fun rememberCinemaxRipple(
    bounded: Boolean = true,
    radius: Dp = Dp.Unspecified,
    color: Color = RippleColor
) = rememberRipple(bounded = bounded, radius = radius, color = color)

private val RippleColor: Color
    @Composable get() = CinemaxTheme.colors.primaryBlue

private val RippleAlpha = RippleAlpha(
    draggedAlpha = 0.16f,
    focusedAlpha = 0.12f,
    hoveredAlpha = 0.08f,
    pressedAlpha = 0.12f
)
