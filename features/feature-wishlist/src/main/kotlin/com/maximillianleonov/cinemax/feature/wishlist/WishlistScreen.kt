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

package com.maximillianleonov.cinemax.feature.wishlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxMessage
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxSwipeRefresh
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme
import com.maximillianleonov.cinemax.core.model.MovieDetails
import com.maximillianleonov.cinemax.core.model.TvShowDetails
import com.maximillianleonov.cinemax.core.ui.CinemaxCenteredError
import com.maximillianleonov.cinemax.core.ui.MediaTabPager
import com.maximillianleonov.cinemax.core.ui.R
import com.maximillianleonov.cinemax.core.ui.VerticalMovieItem
import com.maximillianleonov.cinemax.core.ui.VerticalMovieItemPlaceholder
import com.maximillianleonov.cinemax.core.ui.VerticalTvShowItem
import com.maximillianleonov.cinemax.core.ui.VerticalTvShowItemPlaceholder
import com.maximillianleonov.cinemax.core.ui.mapper.asUserMessage

@Composable
internal fun WishlistRoute(
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WishlistViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    WishlistScreen(
        uiState = uiState,
        onRefreshMovies = { viewModel.onEvent(WishlistEvent.RefreshMovies) },
        onRefreshTvShows = { viewModel.onEvent(WishlistEvent.RefreshTvShows) },
        onMovieClick = onMovieClick,
        onTvShowClick = onTvShowClick,
        onRetry = { viewModel.onEvent(WishlistEvent.Retry) },
        onOfflineModeClick = { viewModel.onEvent(WishlistEvent.ClearError) },
        modifier = modifier
    )
}

@Composable
private fun WishlistScreen(
    uiState: WishlistUiState,
    onRefreshMovies: () -> Unit,
    onRefreshTvShows: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    onRetry: () -> Unit,
    onOfflineModeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.windowInsetsPadding(WindowInsets.safeDrawing)) {
        if (uiState.error != null) {
            CinemaxCenteredError(
                errorMessage = uiState.error.asUserMessage(),
                onRetry = onRetry,
                shouldShowOfflineMode = uiState.isOfflineModeAvailable,
                onOfflineModeClick = onOfflineModeClick
            )
        } else {
            MediaTabPager(
                moviesTabContent = {
                    MoviesContainer(
                        movies = uiState.movies,
                        isLoading = uiState.isMoviesLoading,
                        onRefresh = onRefreshMovies,
                        onClick = onMovieClick
                    )
                },
                tvShowsTabContent = {
                    TvShowsContainer(
                        tvShows = uiState.tvShows,
                        isLoading = uiState.isTvShowsLoading,
                        onRefresh = onRefreshTvShows,
                        onClick = onTvShowClick
                    )
                }
            )
        }
    }
}

@Composable
private fun MoviesContainer(
    movies: List<MovieDetails>,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    emptyContent: @Composable LazyItemScope.() -> Unit = {
        CinemaxMessage(
            modifier = Modifier.fillParentMaxSize(),
            messageResourceId = R.string.no_movie_wishlist,
            imageResourceId = R.drawable.no_wishlist_results
        )
    }
) {
    CinemaxSwipeRefresh(
        modifier = modifier,
        isRefreshing = isLoading,
        onRefresh = onRefresh
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.medium),
            contentPadding = PaddingValues(CinemaxTheme.spacing.extraMedium)
        ) {
            when {
                movies.isNotEmpty() -> {
                    items(movies) { movie ->
                        VerticalMovieItem(movie = movie, onClick = onClick)
                    }
                }
                isLoading -> {
                    items(PlaceholderCount) { VerticalMovieItemPlaceholder() }
                }
                else -> item(content = emptyContent)
            }
        }
    }
}

@Composable
private fun TvShowsContainer(
    tvShows: List<TvShowDetails>,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    emptyContent: @Composable LazyItemScope.() -> Unit = {
        CinemaxMessage(
            modifier = Modifier.fillParentMaxSize(),
            messageResourceId = R.string.no_tv_show_wishlist,
            imageResourceId = R.drawable.no_wishlist_results
        )
    }
) {
    CinemaxSwipeRefresh(
        modifier = modifier,
        isRefreshing = isLoading,
        onRefresh = onRefresh
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.medium),
            contentPadding = PaddingValues(CinemaxTheme.spacing.extraMedium)
        ) {
            when {
                tvShows.isNotEmpty() -> {
                    items(tvShows) { tvShow ->
                        VerticalTvShowItem(tvShow = tvShow, onClick = onClick)
                    }
                }
                isLoading -> {
                    items(PlaceholderCount) { VerticalTvShowItemPlaceholder() }
                }
                else -> item(content = emptyContent)
            }
        }
    }
}

private const val PlaceholderCount = 20
