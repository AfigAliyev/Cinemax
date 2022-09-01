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

package com.maximillianleonov.cinemax.core.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun CinemaxTheme(
    colors: Colors = DarkColorPalette,
    typography: Typography = Typography,
    shapes: Shapes = Shapes,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalCinemaxColors provides CinemaxColors(),
        LocalCinemaxTypography provides CinemaxTypography(),
        LocalCinemaxShapes provides CinemaxShapes(),
        LocalCinemaxSpacing provides CinemaxSpacing()
    ) {
        MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}

object CinemaxTheme {
    val colors: CinemaxColors
        @Composable
        @ReadOnlyComposable
        get() = LocalCinemaxColors.current

    val typography: CinemaxTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalCinemaxTypography.current

    val shapes: CinemaxShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalCinemaxShapes.current

    val spacing: CinemaxSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalCinemaxSpacing.current
}
