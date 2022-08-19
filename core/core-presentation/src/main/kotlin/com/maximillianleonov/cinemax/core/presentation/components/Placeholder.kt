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

package com.maximillianleonov.cinemax.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.maximillianleonov.cinemax.core.presentation.theme.CinemaxTheme

@Composable
fun CinemaxPlaceholder(
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    color: Color = CinemaxTheme.colors.primarySoft,
    shape: Shape = CinemaxTheme.shapes.medium,
    highlight: PlaceholderHighlight = PlaceholderHighlight.shimmer()
) = Box(
    modifier = modifier
        .fillMaxSize()
        .placeholder(
            visible = visible,
            color = color,
            shape = shape,
            highlight = highlight
        )
)
