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

package com.maximillianleonov.cinemax.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import com.google.accompanist.placeholder.material.placeholder
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme

@Composable
fun CinemaxImagePlaceholder(
    modifier: Modifier = Modifier,
    color: Color = CinemaxTheme.colors.primarySoft
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .cinemaxPlaceholder(color = color)
    )
}

fun Modifier.cinemaxPlaceholder() = composed {
    cinemaxPlaceholder(color = CinemaxTheme.colors.primarySoft)
}

fun Modifier.cinemaxPlaceholder(color: Color) = composed {
    placeholder(
        visible = true,
        color = color,
        shape = CinemaxTheme.shapes.medium
    )
}
