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

package com.maximillianleonov.cinemax.feature.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.maximillianleonov.cinemax.core.presentation.common.ContentType
import com.maximillianleonov.cinemax.core.presentation.components.CinemaxSwipeRefresh
import com.maximillianleonov.cinemax.core.presentation.components.MoviesAndTvShowsContainer
import com.maximillianleonov.cinemax.core.presentation.components.SnackbarErrorHandler
import com.maximillianleonov.cinemax.core.presentation.theme.CinemaxTheme
import com.maximillianleonov.cinemax.feature.home.presentation.common.ContentLoadType
import com.maximillianleonov.cinemax.feature.home.presentation.components.UpcomingMoviesContainer

@Composable
fun HomeRoute(
    onNavigateToListDestination: (ContentType) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    HomeScreen(
        uiState = uiState,
        modifier = modifier,
        onNavigateToListDestination = onNavigateToListDestination,
        onRefresh = { viewModel.onEvent(HomeEvent.Refresh) },
        onDismiss = { viewModel.onEvent(HomeEvent.ClearError) }
    )
}

@Suppress("ReusedModifierInstance")
@Composable
internal fun HomeScreen(
    uiState: HomeUiState,
    onNavigateToListDestination: (ContentType) -> Unit,
    onRefresh: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    swipeRefreshState: SwipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = uiState.isLoading
    )
) {
    SnackbarErrorHandler(
        errorMessage = uiState.error,
        onRetry = onRefresh,
        onDismiss = onDismiss
    )
    CinemaxSwipeRefresh(
        swipeRefreshState = swipeRefreshState,
        onRefresh = onRefresh
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.extraMedium),
            contentPadding = PaddingValues(vertical = CinemaxTheme.spacing.extraMedium)
        ) {
            item {
                UpcomingMoviesContainer(
                    movies = uiState.movies[ContentLoadType.UpcomingMovies].orEmpty(),
                    onSeeAllClick = { onNavigateToListDestination(ContentType.Upcoming) }
                )
            }
            item {
                @Suppress("ForbiddenComment")
                MoviesAndTvShowsContainer(
                    titleResourceId = R.string.top_rated,
                    onSeeAllClick = { /*TODO*/ },
                    movies = uiState.movies[ContentLoadType.TopRatedMovies].orEmpty(),
                    tvShows = uiState.tvShows[ContentLoadType.TopRatedTvShows].orEmpty()
                )
            }
            item {
                @Suppress("ForbiddenComment")
                MoviesAndTvShowsContainer(
                    titleResourceId = R.string.most_popular,
                    onSeeAllClick = { /*TODO*/ },
                    movies = uiState.movies[ContentLoadType.PopularMovies].orEmpty(),
                    tvShows = uiState.tvShows[ContentLoadType.PopularTvShows].orEmpty()
                )
            }
            item {
                @Suppress("ForbiddenComment")
                MoviesAndTvShowsContainer(
                    titleResourceId = R.string.now_playing,
                    onSeeAllClick = { /*TODO*/ },
                    movies = uiState.movies[ContentLoadType.NowPlayingMovies].orEmpty(),
                    tvShows = uiState.tvShows[ContentLoadType.NowPlayingTvShows].orEmpty()
                )
            }
        }
    }
}
