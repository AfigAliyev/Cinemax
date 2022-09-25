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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.maximillianleonov.cinemax.core.model.TvShow
import com.maximillianleonov.cinemax.core.model.TvShowDetails
import com.maximillianleonov.cinemax.core.ui.mapper.asNames

@Suppress("ReusedModifierInstance")
@Composable
fun HorizontalTvShowItem(
    tvShow: TvShow,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    with(tvShow) {
        HorizontalFeedItem(
            title = name,
            posterPath = posterPath,
            voteAverage = voteAverage,
            genres = genres.asNames(),
            onClick = { onClick(id) },
            modifier = modifier
        )
    }
}

@Composable
fun HorizontalTvShowItemPlaceholder(
    modifier: Modifier = Modifier
) {
    HorizontalFeedItemPlaceholder(modifier = modifier)
}

@Suppress("ReusedModifierInstance")
@Composable
fun VerticalTvShowItem(
    tvShow: TvShow,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    with(tvShow) {
        VerticalFeedItem(
            title = name,
            overview = overview,
            posterPath = posterPath,
            voteAverage = voteAverage,
            releaseDate = firstAirDate,
            genres = genres.asNames(),
            onClick = { onClick(id) },
            modifier = modifier
        )
    }
}

@Suppress("ReusedModifierInstance")
@Composable
fun VerticalTvShowItem(
    tvShow: TvShowDetails,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    with(tvShow) {
        VerticalFeedItem(
            title = name,
            overview = overview,
            posterPath = posterPath,
            voteAverage = voteAverage,
            releaseDate = firstAirDate,
            genres = genres.asNames(),
            onClick = { onClick(id) },
            modifier = modifier
        )
    }
}

@Composable
fun VerticalTvShowItemPlaceholder(
    modifier: Modifier = Modifier
) {
    VerticalFeedItemPlaceholder(modifier = modifier)
}
