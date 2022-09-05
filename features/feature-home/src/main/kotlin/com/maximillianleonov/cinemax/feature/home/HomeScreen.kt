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

package com.maximillianleonov.cinemax.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.maximillianleonov.cinemax.core.ui.R
import com.maximillianleonov.cinemax.core.ui.common.ContentType
import com.maximillianleonov.cinemax.core.ui.components.CinemaxCenteredBox
import com.maximillianleonov.cinemax.core.ui.components.CinemaxErrorDisplay
import com.maximillianleonov.cinemax.core.ui.components.CinemaxSwipeRefresh
import com.maximillianleonov.cinemax.core.ui.components.MoviesAndTvShowsContainer
import com.maximillianleonov.cinemax.core.ui.model.Movie
import com.maximillianleonov.cinemax.core.ui.model.TvShow
import com.maximillianleonov.cinemax.core.ui.theme.CinemaxTheme
import com.maximillianleonov.cinemax.feature.home.components.UpcomingMoviesContainer

@Composable
internal fun HomeRoute(
    onNavigateToListDestination: (ContentType.List) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    HomeScreen(
        uiState = uiState,
        onSeeAllClick = onNavigateToListDestination,
        onRefresh = { viewModel.onEvent(HomeEvent.Refresh) },
        onRetry = { viewModel.onEvent(HomeEvent.Retry) },
        onOfflineModeClick = { viewModel.onEvent(HomeEvent.ClearError) },
        modifier = modifier
    )
}

@Suppress("ReusedModifierInstance")
@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onSeeAllClick: (ContentType.List) -> Unit,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    onOfflineModeClick: () -> Unit,
    modifier: Modifier = Modifier,
    swipeRefreshState: SwipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = uiState.isLoading
    )
) {
    CinemaxSwipeRefresh(
        modifier = modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        swipeRefreshState = swipeRefreshState,
        onRefresh = onRefresh
    ) {
        if (uiState.isError) {
            CinemaxCenteredBox(
                modifier = Modifier
                    .padding(horizontal = CinemaxTheme.spacing.extraMedium)
                    .fillMaxSize()
            ) {
                CinemaxErrorDisplay(
                    errorMessage = uiState.requireError(),
                    onRetry = onRetry,
                    shouldShowOfflineMode = uiState.isOfflineModeAvailable,
                    onOfflineModeClick = onOfflineModeClick
                )
            }
        } else {
            ContentDisplay(
                movies = uiState.movies,
                tvShows = uiState.tvShows,
                onSeeAllClick = onSeeAllClick
            )
        }
    }
}

@Composable
private fun ContentDisplay(
    movies: Map<ContentType.Main, List<Movie>>,
    tvShows: Map<ContentType.Main, List<TvShow>>,
    onSeeAllClick: (ContentType.List) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag(tag = ContentTestTag),
        verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.extraMedium),
        contentPadding = PaddingValues(vertical = CinemaxTheme.spacing.extraMedium)
    ) {
        item {
            UpcomingMoviesContainer(
                modifier = Modifier.testTag(tag = UpcomingTestTag),
                movies = movies[ContentType.Main.UpcomingMovies].orEmpty(),
                onSeeAllClick = { onSeeAllClick(ContentType.List.Upcoming) }
            )
        }
        item {
            MoviesAndTvShowsContainer(
                titleResourceId = R.string.top_rated,
                onSeeAllClick = { onSeeAllClick(ContentType.List.TopRated) },
                movies = movies[ContentType.Main.TopRatedMovies].orEmpty(),
                tvShows = tvShows[ContentType.Main.TopRatedTvShows].orEmpty()
            )
        }
        item {
            MoviesAndTvShowsContainer(
                titleResourceId = R.string.most_popular,
                onSeeAllClick = { onSeeAllClick(ContentType.List.Popular) },
                movies = movies[ContentType.Main.PopularMovies].orEmpty(),
                tvShows = tvShows[ContentType.Main.PopularTvShows].orEmpty()
            )
        }
        item {
            MoviesAndTvShowsContainer(
                modifier = Modifier.testTag(tag = NowPlayingTestTag),
                titleResourceId = R.string.now_playing,
                onSeeAllClick = { onSeeAllClick(ContentType.List.NowPlaying) },
                movies = movies[ContentType.Main.NowPlayingMovies].orEmpty(),
                tvShows = tvShows[ContentType.Main.NowPlayingTvShows].orEmpty()
            )
        }
    }
}

private const val TestTag = "home"
private const val ContentTestTag = "$TestTag:content"
private const val UpcomingTestTag = "$TestTag:upcoming"
private const val NowPlayingTestTag = "$TestTag:nowplaying"
