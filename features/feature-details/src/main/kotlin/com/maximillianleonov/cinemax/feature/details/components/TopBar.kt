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

package com.maximillianleonov.cinemax.feature.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.maximillianleonov.cinemax.core.ui.components.CinemaxBackButton
import com.maximillianleonov.cinemax.core.ui.components.CinemaxWishlistButton
import com.maximillianleonov.cinemax.core.ui.theme.CinemaxTheme

@Composable
internal fun TopBar(
    title: String,
    isWishlisted: Boolean,
    onBackButtonClick: () -> Unit,
    onWishlistButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = CinemaxTheme.spacing.extraMedium)
            .fillMaxWidth()
            .heightIn(min = TopBarHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CinemaxBackButton(onClick = onBackButtonClick)
        Text(
            modifier = Modifier
                .padding(horizontal = CinemaxTheme.spacing.large)
                .weight(1f),
            text = title,
            style = CinemaxTheme.typography.semiBold.h4,
            color = CinemaxTheme.colors.textWhite,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        CinemaxWishlistButton(isWishlisted = isWishlisted, onClick = onWishlistButtonClick)
    }
}

@Composable
internal fun TopBarPlaceholder(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    color: Color = CinemaxTheme.colors.textWhite,
    shape: Shape = CinemaxTheme.shapes.medium,
    highlight: PlaceholderHighlight = PlaceholderHighlight.shimmer()
) {
    Row(
        modifier = modifier
            .padding(horizontal = CinemaxTheme.spacing.extraMedium)
            .fillMaxWidth()
            .heightIn(min = TopBarHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CinemaxBackButton(onClick = onBackButtonClick)
        Text(
            modifier = Modifier
                .padding(horizontal = CinemaxTheme.spacing.large)
                .weight(1f)
                .placeholder(
                    visible = visible,
                    color = color,
                    shape = shape,
                    highlight = highlight
                ),
            text = PlaceholderText,
            style = CinemaxTheme.typography.semiBold.h4
        )
        CinemaxWishlistButton(isWishlisted = false, onClick = {})
    }
}

internal val TopBarHeight = 32.dp
private const val PlaceholderText = ""
