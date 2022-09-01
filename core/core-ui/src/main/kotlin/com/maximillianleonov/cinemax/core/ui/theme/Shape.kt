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

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

private val DefaultShape = RectangleShape
private val ExtraSmallShape = RoundedCornerShape(4.dp)
private val SmallShape = RoundedCornerShape(8.dp)
private val SmallMediumShape = RoundedCornerShape(12.dp)
private val MediumShape = RoundedCornerShape(16.dp)
private val ExtraMediumShape = RoundedCornerShape(24.dp)
private val LargeShape = RoundedCornerShape(32.dp)
private val ExtraLargeShape = RoundedCornerShape(40.dp)

internal val Shapes = Shapes(small = SmallShape, medium = MediumShape, large = LargeShape)

@Immutable
data class CinemaxShapes(
    val default: Shape = DefaultShape,
    val extraSmall: Shape = ExtraSmallShape,
    val small: Shape = SmallShape,
    val smallMedium: Shape = SmallMediumShape,
    val medium: Shape = MediumShape,
    val extraMedium: Shape = ExtraMediumShape,
    val large: Shape = LargeShape,
    val extraLarge: Shape = ExtraLargeShape
)

internal val LocalCinemaxShapes = staticCompositionLocalOf { CinemaxShapes() }
