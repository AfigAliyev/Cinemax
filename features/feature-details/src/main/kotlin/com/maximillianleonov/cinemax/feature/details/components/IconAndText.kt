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

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.maximillianleonov.cinemax.core.designsystem.component.cinemaxPlaceholder
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme

@Composable
internal fun IconAndText(
    @DrawableRes iconResourceId: Int,
    text: String,
    modifier: Modifier = Modifier,
    color: Color = CinemaxTheme.colors.grey,
    isPlaceholder: Boolean = false
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.extraSmall)
    ) {
        Icon(
            modifier = Modifier.size(IconAndTextIconSize),
            painter = painterResource(id = iconResourceId),
            contentDescription = text,
            tint = color
        )
        Text(
            modifier = if (isPlaceholder) {
                Modifier
                    .fillMaxWidth(PlaceholderTextMaxWidthFraction)
                    .height(PlaceholderTextHeight)
                    .cinemaxPlaceholder(color = color)
            } else {
                Modifier
            },
            text = text,
            style = CinemaxTheme.typography.medium.h5,
            color = color,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
internal fun IconAndTextPlaceholder(
    @DrawableRes iconResourceId: Int,
    modifier: Modifier = Modifier
) {
    IconAndText(
        modifier = modifier,
        iconResourceId = iconResourceId,
        text = PlaceholderText,
        isPlaceholder = true
    )
}

private val IconAndTextIconSize = 18.dp
private val PlaceholderTextHeight = IconAndTextIconSize / 1.5f
private const val PlaceholderText = ""
private const val PlaceholderTextMaxWidthFraction = 0.5f
