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

package com.maximillianleonov.cinemax.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxImagePlaceholder
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxNetworkImage
import com.maximillianleonov.cinemax.core.designsystem.component.cinemaxPlaceholder
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme

@Composable
internal fun HorizontalFeedItem(
    title: String,
    posterPath: String?,
    genres: List<String>,
    voteAverage: Double,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = CinemaxTheme.colors.primarySoft,
    shape: Shape = CinemaxTheme.shapes.smallMedium,
    isPlaceholder: Boolean = false
) {
    Card(
        modifier = modifier
            .width(HorizontalFeedItemWidth)
            .clip(shape)
            .then(if (isPlaceholder) Modifier else Modifier.clickable(onClick = onClick)),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = shape
    ) {
        Column {
            Box(modifier = Modifier.height(HorizontalFeedItemPosterHeight)) {
                if (isPlaceholder) {
                    CinemaxImagePlaceholder()
                } else {
                    CinemaxNetworkImage(
                        modifier = Modifier.fillMaxSize(),
                        model = posterPath,
                        contentDescription = title
                    )
                }
                RatingItem(
                    rating = voteAverage,
                    modifier = Modifier
                        .padding(
                            top = CinemaxTheme.spacing.small,
                            end = CinemaxTheme.spacing.small
                        )
                        .align(Alignment.TopEnd)
                        .then(
                            if (isPlaceholder) {
                                Modifier.cinemaxPlaceholder(color = CinemaxTheme.colors.secondaryOrange)
                            } else {
                                Modifier
                            }
                        )
                )
            }
            Spacer(modifier = Modifier.height(CinemaxTheme.spacing.smallMedium))
            Text(
                modifier = Modifier
                    .padding(horizontal = CinemaxTheme.spacing.small)
                    .then(
                        if (isPlaceholder) {
                            Modifier
                                .fillMaxWidth()
                                .cinemaxPlaceholder(
                                    color = CinemaxTheme.colors.white
                                )
                        } else {
                            Modifier
                        }
                    ),
                text = title,
                style = CinemaxTheme.typography.semiBold.h5,
                color = CinemaxTheme.colors.white,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(CinemaxTheme.spacing.extraSmall))
            Text(
                modifier = Modifier
                    .padding(
                        start = CinemaxTheme.spacing.small,
                        end = CinemaxTheme.spacing.small,
                        bottom = CinemaxTheme.spacing.small
                    )
                    .then(
                        if (isPlaceholder) {
                            Modifier
                                .fillMaxWidth(
                                    HorizontalFeedItemPlaceholderSecondTextMaxWidthFraction
                                )
                                .cinemaxPlaceholder(color = CinemaxTheme.colors.grey)
                        } else {
                            Modifier
                        }
                    ),
                text = genres.joinToString(separator = FeedItemGenreSeparator)
                    .ifEmpty { stringResource(id = R.string.no_genre) },
                style = CinemaxTheme.typography.medium.h7,
                color = CinemaxTheme.colors.grey,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
internal fun HorizontalFeedItemPlaceholder(modifier: Modifier = Modifier) {
    HorizontalFeedItem(
        modifier = modifier,
        title = FeedItemPlaceholderText,
        posterPath = FeedItemPlaceholderText,
        genres = emptyList(),
        voteAverage = FeedItemPlaceholderRating,
        onClick = {},
        isPlaceholder = true
    )
}

private val HorizontalFeedItemWidth = 135.dp
private val HorizontalFeedItemPosterHeight = 178.dp
private const val HorizontalFeedItemPlaceholderSecondTextMaxWidthFraction = 0.5f
