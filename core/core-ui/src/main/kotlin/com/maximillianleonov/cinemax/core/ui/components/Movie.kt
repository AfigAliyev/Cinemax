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

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.maximillianleonov.cinemax.core.ui.R
import com.maximillianleonov.cinemax.core.ui.mapper.toNames
import com.maximillianleonov.cinemax.core.ui.model.Movie
import com.maximillianleonov.cinemax.core.ui.theme.CinemaxTheme
import com.maximillianleonov.cinemax.core.ui.util.isEmpty
import com.maximillianleonov.cinemax.core.ui.util.isLoading

@Composable
fun MoviesContainer(
    movies: List<Movie>,
    modifier: Modifier = Modifier
) {
    val shouldShowPlaceholder = movies.isEmpty()
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.smallMedium),
        contentPadding = PaddingValues(horizontal = CinemaxTheme.spacing.smallMedium)
    ) {
        if (shouldShowPlaceholder) {
            items(PlaceholderCount) { HorizontalMovieItemPlaceholder() }
        } else {
            items(movies) { movie ->
                HorizontalMovieItem(movie = movie)
            }
        }
    }
}

@Composable
fun MoviesContainer(
    @StringRes titleResourceId: Int,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
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
            TextButton(
                modifier = Modifier.testTag(tag = SeeAllTag),
                onClick = onSeeAllClick
            ) {
                Text(
                    text = stringResource(id = R.string.see_all),
                    style = CinemaxTheme.typography.medium.h5,
                    color = CinemaxTheme.colors.primaryBlue
                )
            }
        }
        Spacer(modifier = Modifier.height(CinemaxTheme.spacing.extraSmall))
        content()
    }
}

@Suppress("ReusedModifierInstance")
@Composable
fun MoviesDisplay(
    movies: LazyPagingItems<Movie>,
    modifier: Modifier = Modifier,
    swipeRefreshState: SwipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = movies.loadState.refresh.isLoading
    )
) {
    CinemaxSwipeRefresh(
        swipeRefreshState = swipeRefreshState,
        onRefresh = movies::refresh
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.medium),
            contentPadding = PaddingValues(CinemaxTheme.spacing.extraMedium)
        ) {
            if (movies.loadState.refresh is LoadState.NotLoading) {
                items(movies) { movie ->
                    if (movie == null) {
                        VerticalMovieItemPlaceholder()
                    } else {
                        VerticalMovieItem(movie = movie)
                    }
                }
                if (movies.isEmpty) {
                    item {
                        NoResultsDisplay(
                            modifier = Modifier.fillParentMaxSize(),
                            messageResourceId = R.string.no_movie_results
                        )
                    }
                }
            } else {
                items(PlaceholderCount) { VerticalMovieItemPlaceholder() }
            }
            if (movies.loadState.append is LoadState.Loading) {
                item {
                    CinemaxCenteredBox(modifier = Modifier.fillMaxWidth()) {
                        CinemaxCircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun HorizontalMovieItem(
    movie: Movie,
    modifier: Modifier = Modifier
) = with(movie) {
    HorizontalContentItem(
        title = title,
        posterPath = posterPath,
        voteAverage = voteAverage,
        genres = genres.toNames(),
        modifier = modifier
    )
}

@Composable
fun HorizontalMovieItemPlaceholder(
    modifier: Modifier = Modifier
) = HorizontalContentItemPlaceholder(modifier = modifier)

@Composable
fun VerticalMovieItem(
    movie: Movie,
    modifier: Modifier = Modifier
) = with(movie) {
    VerticalContentItem(
        title = title,
        overview = overview,
        posterPath = posterPath,
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        genres = genres.toNames(),
        modifier = modifier
    )
}

@Composable
fun VerticalMovieItemPlaceholder(
    modifier: Modifier = Modifier
) = VerticalContentItemPlaceholder(modifier = modifier)

private const val SeeAllTag = "seeall"
