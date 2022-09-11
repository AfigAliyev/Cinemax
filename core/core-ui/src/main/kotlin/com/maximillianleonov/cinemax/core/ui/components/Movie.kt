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
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.maximillianleonov.cinemax.core.ui.R
import com.maximillianleonov.cinemax.core.ui.mapper.toNames
import com.maximillianleonov.cinemax.core.ui.model.ErrorMessage
import com.maximillianleonov.cinemax.core.ui.model.Movie
import com.maximillianleonov.cinemax.core.ui.theme.CinemaxTheme
import com.maximillianleonov.cinemax.core.ui.util.error
import com.maximillianleonov.cinemax.core.ui.util.isEmpty
import com.maximillianleonov.cinemax.core.ui.util.isError
import com.maximillianleonov.cinemax.core.ui.util.isFinished
import com.maximillianleonov.cinemax.core.ui.util.isLoading
import com.maximillianleonov.cinemax.core.ui.util.isNotEmpty
import com.maximillianleonov.cinemax.core.ui.util.toErrorMessage

@Composable
fun MoviesContainer(
    movies: List<Movie>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    shouldShowPlaceholder: Boolean = movies.isEmpty()
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .testTag(tag = MoviesContainerTestTag),
        horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.smallMedium),
        contentPadding = PaddingValues(horizontal = CinemaxTheme.spacing.smallMedium)
    ) {
        if (shouldShowPlaceholder) {
            items(PlaceholderCount) { HorizontalMovieItemPlaceholder() }
        } else {
            items(movies) { movie ->
                HorizontalMovieItem(movie = movie, onClick = onClick)
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
    Column(modifier = modifier.testTag(tag = MoviesContainerTestTag)) {
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
                modifier = Modifier.testTag(tag = SeeAllTestTag),
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

@Composable
fun MoviesDisplay(
    movies: LazyPagingItems<Movie>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    swipeRefreshState: SwipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = movies.loadState.refresh.isLoading()
    ),
    emptyDisplay: @Composable LazyItemScope.() -> Unit = {
        NoResultsDisplay(
            modifier = Modifier.fillParentMaxSize(),
            messageResourceId = R.string.no_movie_results
        )
    },
    loadingDisplay: @Composable LazyItemScope.() -> Unit = { CinemaxCircularProgressIndicator() },
    errorDisplay: @Composable LazyItemScope.(errorMessage: ErrorMessage) -> Unit = { errorMessage ->
        CinemaxErrorDisplay(
            errorMessage = errorMessage,
            onRetry = movies::retry,
            modifier = Modifier.fillMaxWidth()
        )
    }
) {
    CinemaxSwipeRefresh(
        swipeRefreshState = swipeRefreshState,
        onRefresh = movies::refresh,
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.medium),
            contentPadding = PaddingValues(CinemaxTheme.spacing.extraMedium)
        ) {
            when {
                movies.isNotEmpty() -> {
                    items(movies) { movie ->
                        if (movie == null) {
                            VerticalMovieItemPlaceholder()
                        } else {
                            VerticalMovieItem(movie = movie, onClick = onClick)
                        }
                    }
                    if (movies.loadState.refresh.isError()) {
                        item { errorDisplay(errorMessage = movies.loadState.refresh.error.toErrorMessage()) }
                    }
                }
                movies.loadState.refresh.isLoading() -> {
                    items(PlaceholderCount) { VerticalMovieItemPlaceholder() }
                }
                movies.loadState.refresh.isFinished() -> {
                    if (movies.isEmpty()) {
                        item(content = emptyDisplay)
                    }
                }
                movies.loadState.refresh.isError() -> {
                    item {
                        CinemaxCenteredBox(modifier = Modifier.fillParentMaxSize()) {
                            errorDisplay(
                                errorMessage = movies.loadState.refresh.error.toErrorMessage()
                            )
                        }
                    }
                }
            }
            if (movies.loadState.append.isLoading()) {
                item { CinemaxCenteredBox(modifier = Modifier.fillMaxWidth()) { loadingDisplay() } }
            }
            if (movies.loadState.append.isError()) {
                item { errorDisplay(errorMessage = movies.loadState.append.error.toErrorMessage()) }
            }
        }
    }
}

@Suppress("ReusedModifierInstance")
@Composable
fun HorizontalMovieItem(
    movie: Movie,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    with(movie) {
        HorizontalContentItem(
            title = title,
            posterPath = posterPath,
            voteAverage = voteAverage,
            genres = genres.toNames(),
            onClick = { onClick(id) },
            modifier = modifier
        )
    }
}

@Composable
fun HorizontalMovieItemPlaceholder(
    modifier: Modifier = Modifier
) {
    HorizontalContentItemPlaceholder(modifier = modifier)
}

@Suppress("ReusedModifierInstance")
@Composable
fun VerticalMovieItem(
    movie: Movie,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    with(movie) {
        VerticalContentItem(
            title = title,
            overview = overview,
            posterPath = posterPath,
            voteAverage = voteAverage,
            releaseDate = releaseDate,
            genres = genres.toNames(),
            onClick = { onClick(id) },
            modifier = modifier
        )
    }
}

@Composable
fun VerticalMovieItemPlaceholder(
    modifier: Modifier = Modifier
) {
    VerticalContentItemPlaceholder(modifier = modifier)
}

private const val MoviesContainerTestTag = "moviescontainer"
private const val SeeAllTestTag = "seeall"
