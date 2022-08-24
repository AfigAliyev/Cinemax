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

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.maximillianleonov.cinemax.core.presentation.R
import com.maximillianleonov.cinemax.core.presentation.model.Movie
import com.maximillianleonov.cinemax.core.presentation.model.TvShow
import com.maximillianleonov.cinemax.core.presentation.theme.CinemaxTheme
import kotlinx.datetime.LocalDate

@Composable
fun MoviesAndTvShowsContainer(
    @StringRes titleResourceId: Int,
    onSeeAllClick: () -> Unit,
    movies: List<Movie>,
    tvShows: List<TvShow>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(horizontal = CinemaxTheme.spacing.extraMedium)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = titleResourceId),
                style = CinemaxTheme.typography.semiBold.h4,
                color = CinemaxTheme.colors.textWhite
            )
            TextButton(onClick = onSeeAllClick) {
                Text(
                    text = stringResource(id = R.string.see_all),
                    style = CinemaxTheme.typography.medium.h5,
                    color = CinemaxTheme.colors.primaryBlue
                )
            }
        }
        Text(
            modifier = Modifier.padding(horizontal = CinemaxTheme.spacing.extraMedium),
            text = stringResource(id = R.string.movies),
            style = CinemaxTheme.typography.semiBold.h5,
            color = CinemaxTheme.colors.textWhiteGrey
        )
        Spacer(modifier = Modifier.height(CinemaxTheme.spacing.small))
        MoviesContainer(movies = movies)
        Spacer(modifier = Modifier.height(CinemaxTheme.spacing.smallMedium))
        Text(
            modifier = Modifier.padding(horizontal = CinemaxTheme.spacing.extraMedium),
            text = stringResource(id = R.string.tv_shows),
            style = CinemaxTheme.typography.semiBold.h5,
            color = CinemaxTheme.colors.textWhiteGrey
        )
        Spacer(modifier = Modifier.height(CinemaxTheme.spacing.small))
        TvShowsContainer(tvShows = tvShows)
    }
}

@Composable
fun HorizontalContentItem(
    title: String,
    posterPath: String?,
    genres: List<String>,
    voteAverage: Double,
    modifier: Modifier = Modifier
) = Card(
    modifier = modifier.width(HorizontalContentItemWidth),
    backgroundColor = CinemaxTheme.colors.primarySoft,
    shape = CinemaxTheme.shapes.smallMedium
) {
    Column {
        Box(modifier = Modifier.height(HorizontalContentItemPosterHeight)) {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = posterPath,
                contentDescription = title,
                contentScale = ContentScale.Crop
            ) { SubcomposeAsyncImageHandler() }
            RatingItem(
                rating = voteAverage,
                modifier = Modifier
                    .padding(
                        top = CinemaxTheme.spacing.small,
                        end = CinemaxTheme.spacing.small
                    )
                    .align(Alignment.TopEnd)
            )
        }
        Spacer(modifier = Modifier.height(CinemaxTheme.spacing.smallMedium))
        Text(
            modifier = Modifier.padding(horizontal = CinemaxTheme.spacing.small),
            text = title,
            style = CinemaxTheme.typography.semiBold.h5,
            color = CinemaxTheme.colors.textWhite,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(CinemaxTheme.spacing.extraSmall))
        Text(
            modifier = Modifier.padding(
                start = CinemaxTheme.spacing.small,
                end = CinemaxTheme.spacing.small,
                bottom = CinemaxTheme.spacing.small
            ),
            text = genres.joinToString(separator = ContentItemGenreSeparator)
                .ifEmpty { stringResource(id = R.string.no_genre) },
            style = CinemaxTheme.typography.medium.h7,
            color = CinemaxTheme.colors.textGrey,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun HorizontalContentItemPlaceholder(
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    shape: Shape = CinemaxTheme.shapes.medium,
    highlight: PlaceholderHighlight = PlaceholderHighlight.shimmer()
) {
    Card(
        modifier = modifier.width(HorizontalContentItemWidth),
        backgroundColor = CinemaxTheme.colors.primarySoft,
        shape = CinemaxTheme.shapes.smallMedium
    ) {
        Column {
            Box(modifier = Modifier.height(HorizontalContentItemPosterHeight)) {
                CinemaxPlaceholder()
                RatingItem(
                    rating = PlaceholderRating,
                    modifier = Modifier
                        .padding(
                            top = CinemaxTheme.spacing.small,
                            end = CinemaxTheme.spacing.small
                        )
                        .align(Alignment.TopEnd)
                        .placeholder(
                            visible = visible,
                            color = CinemaxTheme.colors.secondaryOrange,
                            shape = shape,
                            highlight = highlight
                        )
                )
            }
            Spacer(modifier = Modifier.height(CinemaxTheme.spacing.smallMedium))
            Text(
                modifier = Modifier
                    .padding(horizontal = CinemaxTheme.spacing.small)
                    .fillMaxWidth()
                    .placeholder(
                        visible = visible,
                        color = CinemaxTheme.colors.textWhite,
                        shape = shape,
                        highlight = highlight
                    ),
                text = ContentItemPlaceholderText,
                style = CinemaxTheme.typography.semiBold.h5
            )
            Spacer(modifier = Modifier.height(CinemaxTheme.spacing.extraSmall))
            Text(
                modifier = Modifier
                    .padding(
                        start = CinemaxTheme.spacing.small,
                        end = CinemaxTheme.spacing.small,
                        bottom = CinemaxTheme.spacing.small
                    )
                    .fillMaxWidth(HorizontalContentItemPlaceholderSecondTextMaxWidthFraction)
                    .placeholder(
                        visible = visible,
                        color = CinemaxTheme.colors.textGrey,
                        shape = shape,
                        highlight = highlight
                    ),
                text = ContentItemPlaceholderText,
                style = CinemaxTheme.typography.medium.h7
            )
        }
    }
}

@Composable
internal fun VerticalContentItem(
    title: String,
    overview: String,
    posterPath: String?,
    voteAverage: Double,
    releaseDate: LocalDate?,
    genres: List<String>,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier
        .fillMaxWidth()
        .height(VerticalContentItemHeight),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.medium)
) {
    Box(
        modifier = Modifier
            .width(VerticalContentItemPosterWidth)
            .fillMaxHeight()
            .clip(shape = CinemaxTheme.shapes.small)
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = posterPath,
            contentDescription = title,
            contentScale = ContentScale.Crop
        ) { SubcomposeAsyncImageHandler() }
        RatingItem(
            rating = voteAverage,
            modifier = Modifier
                .padding(
                    top = CinemaxTheme.spacing.small,
                    start = CinemaxTheme.spacing.small
                )
                .align(Alignment.TopStart)
        )
    }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = title,
            style = CinemaxTheme.typography.semiBold.h4,
            color = CinemaxTheme.colors.textWhite,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = overview.ifEmpty { stringResource(id = R.string.no_overview) },
            style = CinemaxTheme.typography.medium.h5,
            color = CinemaxTheme.colors.textGrey,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Column {
            VerticalContentItemIconAndText(
                iconResourceId = R.drawable.ic_calendar,
                text = releaseDate?.year?.toString()
                    ?: stringResource(id = R.string.no_release_date)
            )
            VerticalContentItemIconAndText(
                iconResourceId = R.drawable.ic_film,
                text = genres.joinToString(separator = ContentItemGenreSeparator)
                    .ifEmpty { stringResource(id = R.string.no_genre) }
            )
        }
    }
}

@Composable
private fun VerticalContentItemIconAndText(
    @DrawableRes iconResourceId: Int,
    text: String,
    modifier: Modifier = Modifier,
    color: Color = CinemaxTheme.colors.textGrey
) = Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.extraSmall)
) {
    Icon(
        modifier = Modifier.size(VerticalContentItemIconSize),
        painter = painterResource(id = iconResourceId),
        contentDescription = text,
        tint = color
    )
    Text(
        text = text,
        style = CinemaxTheme.typography.medium.h5,
        color = color,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
internal fun VerticalContentItemPlaceholder(
    modifier: Modifier = Modifier,
    color: Color = CinemaxTheme.colors.textGrey,
    visible: Boolean = true,
    shape: Shape = CinemaxTheme.shapes.medium,
    highlight: PlaceholderHighlight = PlaceholderHighlight.shimmer()
) = Row(
    modifier = modifier
        .fillMaxWidth()
        .height(VerticalContentItemHeight),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.medium)
) {
    Box(
        modifier = Modifier
            .width(VerticalContentItemPosterWidth)
            .fillMaxHeight()
            .clip(shape = CinemaxTheme.shapes.small)
    ) {
        CinemaxPlaceholder()
        RatingItem(
            rating = PlaceholderRating,
            modifier = Modifier
                .padding(
                    top = CinemaxTheme.spacing.small,
                    start = CinemaxTheme.spacing.small
                )
                .align(Alignment.TopStart)
                .placeholder(
                    visible = visible,
                    color = CinemaxTheme.colors.secondaryOrange,
                    shape = shape,
                    highlight = highlight
                )
        )
    }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .placeholder(
                    visible = visible,
                    color = CinemaxTheme.colors.textWhiteGrey,
                    shape = shape,
                    highlight = highlight
                ),
            text = ContentItemPlaceholderText
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .placeholder(
                    visible = visible,
                    color = color,
                    shape = shape,
                    highlight = highlight
                ),
            text = ContentItemPlaceholderText
        )
        Column {
            VerticalContentItemIconAndTextPlaceholder(iconResourceId = R.drawable.ic_calendar)
            VerticalContentItemIconAndTextPlaceholder(iconResourceId = R.drawable.ic_film)
        }
    }
}

@Composable
private fun VerticalContentItemIconAndTextPlaceholder(
    @DrawableRes iconResourceId: Int,
    modifier: Modifier = Modifier,
    color: Color = CinemaxTheme.colors.textGrey,
    visible: Boolean = true,
    shape: Shape = CinemaxTheme.shapes.medium,
    highlight: PlaceholderHighlight = PlaceholderHighlight.shimmer()
) = Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.extraSmall)
) {
    Icon(
        modifier = Modifier.size(VerticalContentItemIconSize),
        painter = painterResource(id = iconResourceId),
        contentDescription = ContentItemPlaceholderText,
        tint = color
    )
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .height(VerticalContentItemIconAndTextPlaceholderHeight)
            .placeholder(
                visible = visible,
                color = color,
                shape = shape,
                highlight = highlight
            ),
        text = ContentItemPlaceholderText
    )
}

private val HorizontalContentItemWidth = 135.dp
private val HorizontalContentItemPosterHeight = 178.dp
private const val HorizontalContentItemPlaceholderSecondTextMaxWidthFraction = 0.5f

private val VerticalContentItemHeight = 147.dp
private val VerticalContentItemPosterWidth = 112.dp
private val VerticalContentItemIconSize = 18.dp
private val VerticalContentItemIconAndTextPlaceholderHeight = VerticalContentItemIconSize / 1.5f

private const val ContentItemPlaceholderText = ""
private const val ContentItemGenreSeparator = ", "
private const val PlaceholderRating = 0.0
