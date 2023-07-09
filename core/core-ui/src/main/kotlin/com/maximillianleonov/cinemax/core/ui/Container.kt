/*
 * Copyright 2022 Afig Aliyev
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxCenteredBox
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxCircularProgressIndicator
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxMessage
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxSwipeRefresh
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme
import com.maximillianleonov.cinemax.core.model.Movie
import com.maximillianleonov.cinemax.core.model.TvShow
import com.maximillianleonov.cinemax.core.model.UserMessage
import com.maximillianleonov.cinemax.core.ui.mapper.asUserMessage
import com.maximillianleonov.cinemax.core.ui.util.error
import com.maximillianleonov.cinemax.core.ui.util.isEmpty
import com.maximillianleonov.cinemax.core.ui.util.isError
import com.maximillianleonov.cinemax.core.ui.util.isFinished
import com.maximillianleonov.cinemax.core.ui.util.isLoading
import com.maximillianleonov.cinemax.core.ui.util.isNotEmpty

@Composable
fun MoviesAndTvShowsContainer(
    @StringRes titleResourceId: Int,
    onSeeAllClick: () -> Unit,
    movies: List<Movie>,
    tvShows: List<TvShow>,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ContainerTitleWithButton(titleResourceId = titleResourceId, onSeeAllClick = onSeeAllClick)
        Text(
            modifier = Modifier.padding(horizontal = CinemaxTheme.spacing.extraMedium),
            text = stringResource(id = R.string.movies),
            style = CinemaxTheme.typography.semiBold.h5,
            color = CinemaxTheme.colors.whiteGrey
        )
        Spacer(modifier = Modifier.height(CinemaxTheme.spacing.small))
        MoviesContainer(movies = movies, onClick = onMovieClick)
        Spacer(modifier = Modifier.height(CinemaxTheme.spacing.smallMedium))
        Text(
            modifier = Modifier.padding(horizontal = CinemaxTheme.spacing.extraMedium),
            text = stringResource(id = R.string.tv_shows),
            style = CinemaxTheme.typography.semiBold.h5,
            color = CinemaxTheme.colors.whiteGrey
        )
        Spacer(modifier = Modifier.height(CinemaxTheme.spacing.small))
        TvShowsContainer(tvShows = tvShows, onClick = onTvShowClick)
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
        ContainerTitleWithButton(titleResourceId = titleResourceId, onSeeAllClick = onSeeAllClick)
        Spacer(modifier = Modifier.height(CinemaxTheme.spacing.extraSmall))
        content()
    }
}

@Composable
fun MoviesContainer(
    movies: LazyPagingItems<Movie>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = movies.loadState.refresh.isLoading,
    emptyContent: @Composable LazyItemScope.() -> Unit = {
        CinemaxMessage(
            modifier = Modifier.fillParentMaxSize(),
            messageResourceId = R.string.no_movie_results,
            imageResourceId = R.drawable.no_search_results
        )
    },
    loadingContent: @Composable LazyItemScope.() -> Unit = { CinemaxCircularProgressIndicator() },
    errorContent: @Composable LazyItemScope.(errorMessage: UserMessage) -> Unit = { errorMessage ->
        CinemaxError(
            modifier = Modifier.fillMaxWidth(),
            errorMessage = errorMessage,
            onRetry = movies::retry
        )
    }
) {
    CinemaxSwipeRefresh(
        modifier = modifier,
        isRefreshing = isLoading,
        onRefresh = movies::refresh
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.medium),
            contentPadding = PaddingValues(CinemaxTheme.spacing.extraMedium)
        ) {
            when {
                movies.isNotEmpty() -> {
                    items(count = movies.itemCount) { index ->
                        val movie = movies[index]
                        if (movie == null) {
                            VerticalMovieItemPlaceholder()
                        } else {
                            VerticalMovieItem(movie = movie, onClick = onClick)
                        }
                    }
                    if (movies.loadState.refresh.isError) {
                        item { errorContent(errorMessage = movies.loadState.refresh.error.asUserMessage()) }
                    }
                }
                movies.loadState.refresh.isLoading -> {
                    items(PlaceholderCount) { VerticalMovieItemPlaceholder() }
                }
                movies.loadState.refresh.isFinished -> {
                    if (movies.isEmpty()) {
                        item(content = emptyContent)
                    }
                }
                movies.loadState.refresh.isError -> {
                    item {
                        CinemaxCenteredBox(modifier = Modifier.fillParentMaxSize()) {
                            errorContent(
                                errorMessage = movies.loadState.refresh.error.asUserMessage()
                            )
                        }
                    }
                }
            }
            if (movies.loadState.append.isLoading) {
                item { CinemaxCenteredBox(modifier = Modifier.fillMaxWidth()) { loadingContent() } }
            }
            if (movies.loadState.append.isError) {
                item { errorContent(errorMessage = movies.loadState.append.error.asUserMessage()) }
            }
        }
    }
}

@Composable
fun TvShowsContainer(
    tvShows: LazyPagingItems<TvShow>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = tvShows.loadState.refresh.isLoading,
    emptyContent: @Composable LazyItemScope.() -> Unit = {
        CinemaxMessage(
            modifier = Modifier.fillParentMaxSize(),
            messageResourceId = R.string.no_tv_show_results,
            imageResourceId = R.drawable.no_search_results
        )
    },
    loadingContent: @Composable LazyItemScope.() -> Unit = { CinemaxCircularProgressIndicator() },
    errorContent: @Composable LazyItemScope.(errorMessage: UserMessage) -> Unit = { errorMessage ->
        CinemaxError(
            modifier = Modifier.fillMaxWidth(),
            errorMessage = errorMessage,
            onRetry = tvShows::retry
        )
    }
) {
    CinemaxSwipeRefresh(
        modifier = modifier,
        isRefreshing = isLoading,
        onRefresh = tvShows::refresh
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.medium),
            contentPadding = PaddingValues(CinemaxTheme.spacing.extraMedium)
        ) {
            when {
                tvShows.isNotEmpty() -> {
                    items(count = tvShows.itemCount) { index ->
                        val tvShow = tvShows[index]
                        if (tvShow == null) {
                            VerticalTvShowItemPlaceholder()
                        } else {
                            VerticalTvShowItem(tvShow = tvShow, onClick = onClick)
                        }
                    }
                    if (tvShows.loadState.refresh.isError) {
                        item { errorContent(errorMessage = tvShows.loadState.refresh.error.asUserMessage()) }
                    }
                }
                tvShows.loadState.refresh.isLoading -> {
                    items(PlaceholderCount) { VerticalTvShowItemPlaceholder() }
                }
                tvShows.loadState.refresh.isFinished -> {
                    if (tvShows.isEmpty()) {
                        item(content = emptyContent)
                    }
                }
                tvShows.loadState.refresh.isError -> {
                    item {
                        CinemaxCenteredBox(modifier = Modifier.fillParentMaxSize()) {
                            errorContent(
                                errorMessage = tvShows.loadState.refresh.error.asUserMessage()
                            )
                        }
                    }
                }
            }
            if (tvShows.loadState.append.isLoading) {
                item { CinemaxCenteredBox(modifier = Modifier.fillMaxWidth()) { loadingContent() } }
            }
            if (tvShows.loadState.append.isError) {
                item { errorContent(errorMessage = tvShows.loadState.append.error.asUserMessage()) }
            }
        }
    }
}

@Composable
private fun MoviesContainer(
    movies: List<Movie>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    ContainerContent(
        modifier = modifier,
        items = movies,
        itemContent = { movie -> HorizontalMovieItem(movie = movie, onClick = onClick) },
        placeholderContent = { HorizontalMovieItemPlaceholder() }
    )
}

@Composable
private fun TvShowsContainer(
    tvShows: List<TvShow>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    ContainerContent(
        modifier = modifier,
        items = tvShows,
        itemContent = { tvShow -> HorizontalTvShowItem(tvShow = tvShow, onClick = onClick) },
        placeholderContent = { HorizontalTvShowItemPlaceholder() }
    )
}

@Composable
private fun <T> ContainerContent(
    items: List<T>,
    itemContent: @Composable LazyItemScope.(T) -> Unit,
    placeholderContent: @Composable LazyItemScope.(Int) -> Unit,
    modifier: Modifier = Modifier,
    shouldShowPlaceholder: Boolean = items.isEmpty()
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.smallMedium),
        contentPadding = PaddingValues(horizontal = CinemaxTheme.spacing.smallMedium)
    ) {
        if (shouldShowPlaceholder) {
            items(count = PlaceholderCount, itemContent = placeholderContent)
        } else {
            items(items = items, itemContent = itemContent)
        }
    }
}

@Composable
private fun ContainerTitleWithButton(
    @StringRes titleResourceId: Int,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = CinemaxTheme.spacing.extraMedium)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = titleResourceId),
            style = CinemaxTheme.typography.semiBold.h4,
            color = CinemaxTheme.colors.white
        )
        TextButton(onClick = onSeeAllClick) {
            Text(
                text = stringResource(id = R.string.see_all),
                style = CinemaxTheme.typography.medium.h5,
                color = CinemaxTheme.colors.primaryBlue
            )
        }
    }
}

private const val PlaceholderCount = 20
