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
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import com.maximillianleonov.cinemax.core.presentation.theme.CinemaxTheme
import kotlinx.datetime.LocalDate

@Composable
internal fun VerticalContentItem(
    title: String,
    overview: String,
    posterPath: String?,
    voteAverage: Double,
    releaseDate: LocalDate,
    genres: List<String>,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier
        .fillMaxWidth()
        .height(ContentItemHeight),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.medium)
) {
    Box(
        modifier = Modifier
            .width(ContentItemPosterWidth)
            .fillMaxHeight()
            .clip(shape = CinemaxTheme.shapes.small)
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = posterPath,
            contentDescription = stringResource(id = R.string.poster),
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
            ContentItemIconAndText(
                iconResourceId = R.drawable.ic_calendar,
                text = releaseDate.year.toString()
            )
            ContentItemIconAndText(
                iconResourceId = R.drawable.ic_film,
                text = genres.joinToString(separator = ContentItemGenreSeparator)
                    .ifEmpty { stringResource(id = R.string.no_genre) }
            )
        }
    }
}

@Composable
private fun ContentItemIconAndText(
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
        modifier = Modifier.size(ContentItemIconSize),
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
        .height(ContentItemHeight),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.medium)
) {
    Box(
        modifier = Modifier
            .width(ContentItemPosterWidth)
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
            ContentItemIconAndTextPlaceholder(iconResourceId = R.drawable.ic_calendar)
            ContentItemIconAndTextPlaceholder(iconResourceId = R.drawable.ic_film)
        }
    }
}

@Composable
private fun ContentItemIconAndTextPlaceholder(
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
        modifier = Modifier.size(ContentItemIconSize),
        painter = painterResource(id = iconResourceId),
        contentDescription = ContentItemPlaceholderText,
        tint = color
    )
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .height(ContentItemIconAndTextPlaceholderHeight)
            .placeholder(
                visible = visible,
                color = color,
                shape = shape,
                highlight = highlight
            ),
        text = ContentItemPlaceholderText
    )
}

private val ContentItemHeight = 147.dp
private val ContentItemPosterWidth = 112.dp
private val ContentItemIconSize = 18.dp
private const val ContentItemGenreSeparator = ", "

private const val PlaceholderRating = 0.0
private const val ContentItemPlaceholderText = ""
private val ContentItemIconAndTextPlaceholderHeight = ContentItemIconSize / 1.5f
