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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.maximillianleonov.cinemax.core.presentation.mapper.toNames
import com.maximillianleonov.cinemax.core.presentation.model.TvShow
import com.maximillianleonov.cinemax.core.presentation.theme.CinemaxTheme

@Composable
fun TvShowsContainer(
    tvShows: List<TvShow>,
    modifier: Modifier = Modifier
) {
    val shouldShowPlaceholder = tvShows.isEmpty()
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.smallMedium),
        contentPadding = PaddingValues(horizontal = CinemaxTheme.spacing.smallMedium)
    ) {
        if (shouldShowPlaceholder) {
            items(TvShowsContainerPlaceholderCount) { HorizontalTvShowItemPlaceholder() }
        } else {
            items(tvShows) { tvShow ->
                HorizontalTvShowItem(tvShow = tvShow)
            }
        }
    }
}

@Composable
fun HorizontalTvShowItem(
    tvShow: TvShow,
    modifier: Modifier = Modifier
) = with(tvShow) {
    HorizontalContentItem(
        title = name,
        posterPath = posterPath,
        voteAverage = voteAverage,
        genres = genres.toNames(),
        modifier = modifier
    )
}

@Composable
fun HorizontalTvShowItemPlaceholder(
    modifier: Modifier = Modifier
) = HorizontalContentItemPlaceholder(modifier = modifier)

@Composable
fun VerticalTvShowItem(
    tvShow: TvShow,
    modifier: Modifier = Modifier
) = with(tvShow) {
    VerticalContentItem(
        title = name,
        overview = overview,
        posterPath = posterPath,
        voteAverage = voteAverage,
        releaseDate = firstAirDate,
        genres = genres.toNames(),
        modifier = modifier
    )
}

@Composable
fun VerticalTvShowItemPlaceholder(
    modifier: Modifier = Modifier
) = VerticalContentItemPlaceholder(modifier = modifier)

private const val TvShowsContainerPlaceholderCount = 20
