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

package com.maximillianleonov.cinemax.core.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maximillianleonov.cinemax.core.ui.R
import com.maximillianleonov.cinemax.core.ui.theme.CinemaxTheme

@Composable
fun RatingItem(
    rating: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = CinemaxTheme.shapes.small,
        backgroundColor = CinemaxTheme.colors.primarySoft.copy(
            alpha = RatingItemBackgroundColorAlpha
        ),
        contentColor = CinemaxTheme.colors.secondaryOrange
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = CinemaxTheme.spacing.small,
                vertical = CinemaxTheme.spacing.extraSmall
            )
        ) {
            Icon(
                modifier = Modifier.size(RatingIconSize),
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = stringResource(id = R.string.rating)
            )
            Spacer(modifier = Modifier.width(CinemaxTheme.spacing.extraSmall))
            Text(
                text = if (rating == RatingNotRatedValue) {
                    stringResource(id = R.string.not_rated)
                } else {
                    rating.toString()
                },
                style = CinemaxTheme.typography.semiBold.h6
            )
        }
    }
}

private val RatingIconSize = 16.dp
private const val RatingItemBackgroundColorAlpha = 0.72f
private const val RatingNotRatedValue = 0.0
