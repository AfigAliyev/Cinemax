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

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxImagePlaceholder
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxNetworkImage
import com.maximillianleonov.cinemax.core.designsystem.component.cinemaxPlaceholder
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme
import kotlinx.datetime.LocalDate

@Composable
internal fun VerticalFeedItem(
    title: String,
    overview: String,
    posterPath: String?,
    voteAverage: Double,
    releaseDate: LocalDate?,
    genres: List<String>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CinemaxTheme.shapes.small,
    isPlaceholder: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(VerticalFeedItemHeight)
            .clip(shape)
            .then(if (isPlaceholder) Modifier else Modifier.clickable(onClick = onClick)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.medium)
    ) {
        Box(
            modifier = Modifier
                .width(VerticalFeedItemPosterWidth)
                .fillMaxHeight()
                .clip(shape)
        ) {
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
                        start = CinemaxTheme.spacing.small
                    )
                    .align(Alignment.TopStart)
                    .then(
                        if (isPlaceholder) {
                            Modifier.cinemaxPlaceholder(color = CinemaxTheme.colors.secondaryVariant)
                        } else {
                            Modifier
                        }
                    )
            )
        }
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Text(
                modifier = if (isPlaceholder) {
                    Modifier
                        .fillMaxWidth()
                        .cinemaxPlaceholder(color = CinemaxTheme.colors.textPrimaryVariant)
                } else {
                    Modifier
                },
                text = title,
                style = CinemaxTheme.typography.semiBold.h4,
                color = CinemaxTheme.colors.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = if (isPlaceholder) {
                    Modifier
                        .fillMaxWidth()
                        .cinemaxPlaceholder(color = CinemaxTheme.colors.textSecondary)
                } else {
                    Modifier
                },
                text = overview.ifEmpty { stringResource(id = R.string.no_overview) },
                style = CinemaxTheme.typography.medium.h5,
                color = CinemaxTheme.colors.textSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Column {
                if (isPlaceholder) {
                    VerticalFeedItemIconAndTextPlaceholder(iconResourceId = R.drawable.ic_calendar)
                    VerticalFeedItemIconAndTextPlaceholder(iconResourceId = R.drawable.ic_film)
                } else {
                    VerticalFeedItemIconAndText(
                        iconResourceId = R.drawable.ic_calendar,
                        text = releaseDate?.year?.toString()
                            ?: stringResource(id = R.string.no_release_date)
                    )
                    VerticalFeedItemIconAndText(
                        iconResourceId = R.drawable.ic_film,
                        text = genres.joinToString(separator = FeedItemGenreSeparator)
                            .ifEmpty { stringResource(id = R.string.no_genre) }
                    )
                }
            }
        }
    }
}

@Composable
internal fun VerticalFeedItemPlaceholder(modifier: Modifier = Modifier) {
    VerticalFeedItem(
        modifier = modifier,
        title = FeedItemPlaceholderText,
        overview = FeedItemPlaceholderText,
        posterPath = FeedItemPlaceholderText,
        voteAverage = FeedItemPlaceholderRating,
        releaseDate = null,
        genres = emptyList(),
        onClick = {},
        isPlaceholder = true
    )
}

@Composable
private fun VerticalFeedItemIconAndText(
    @DrawableRes iconResourceId: Int,
    text: String,
    modifier: Modifier = Modifier,
    color: Color = CinemaxTheme.colors.textSecondary,
    isPlaceholder: Boolean = false
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.extraSmall)
    ) {
        Icon(
            modifier = Modifier.size(VerticalFeedItemIconSize),
            painter = painterResource(id = iconResourceId),
            contentDescription = text,
            tint = color
        )
        Text(
            modifier = if (isPlaceholder) {
                Modifier
                    .fillMaxWidth()
                    .height(VerticalFeedItemIconAndTextPlaceholderHeight)
                    .cinemaxPlaceholder(color = CinemaxTheme.colors.textSecondary)
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
private fun VerticalFeedItemIconAndTextPlaceholder(
    @DrawableRes iconResourceId: Int,
    modifier: Modifier = Modifier,
    color: Color = CinemaxTheme.colors.textSecondary
) {
    VerticalFeedItemIconAndText(
        iconResourceId = iconResourceId,
        text = FeedItemPlaceholderText,
        modifier = modifier,
        color = color,
        isPlaceholder = true
    )
}

private val VerticalFeedItemHeight = 147.dp
private val VerticalFeedItemPosterWidth = 112.dp
private val VerticalFeedItemIconSize = 18.dp
private val VerticalFeedItemIconAndTextPlaceholderHeight = VerticalFeedItemIconSize / 1.5f
