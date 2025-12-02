/*
 * Copyright 2022 Afig Aliyev
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

import androidx.compose.foundation.LocalIndication
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun CinemaxTheme(
    colorScheme: ColorScheme = DarkColorScheme,
    shapes: Shapes = Shapes,
    typography: Typography = Typography,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = colorScheme,
        shapes = shapes,
        typography = typography
    ) { ProvideCinemaxThemeDependencies(content = content) }
}

@Composable
private fun ProvideCinemaxThemeDependencies(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalCinemaxColors provides CinemaxColors(),
        LocalCinemaxShapes provides CinemaxShapes(),
        LocalCinemaxTypography provides CinemaxTypography(),
        LocalCinemaxSpacing provides CinemaxSpacing(),
        LocalIndication provides cinemaxRipple()
    ) {
        ProvideTextStyle(value = CinemaxTheme.typography.regular.h4, content = content)
    }
}

object CinemaxTheme {
    val colors: CinemaxColors
        @Composable
        @ReadOnlyComposable
        get() = LocalCinemaxColors.current

    val shapes: CinemaxShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalCinemaxShapes.current

    val typography: CinemaxTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalCinemaxTypography.current

    val spacing: CinemaxSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalCinemaxSpacing.current
}
