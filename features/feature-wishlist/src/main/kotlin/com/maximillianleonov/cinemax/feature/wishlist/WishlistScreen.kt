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

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.maximillianleonov.cinemax.core.ui.R
import com.maximillianleonov.cinemax.core.ui.components.CinemaxCenteredBox
import com.maximillianleonov.cinemax.core.ui.components.CinemaxErrorDisplay
import com.maximillianleonov.cinemax.core.ui.components.CinemaxSwipeRefresh
import com.maximillianleonov.cinemax.core.ui.components.NoResultsDisplay
import com.maximillianleonov.cinemax.core.ui.components.PlaceholderCount
import com.maximillianleonov.cinemax.core.ui.components.VerticalMovieItem
import com.maximillianleonov.cinemax.core.ui.components.VerticalMovieItemPlaceholder
import com.maximillianleonov.cinemax.core.ui.components.VerticalTvShowItem
import com.maximillianleonov.cinemax.core.ui.components.VerticalTvShowItemPlaceholder
import com.maximillianleonov.cinemax.core.ui.model.MovieDetails
import com.maximillianleonov.cinemax.core.ui.model.TvShowDetails
import com.maximillianleonov.cinemax.core.ui.theme.CinemaxTheme
import com.maximillianleonov.cinemax.feature.wishlist.common.ListTab
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun WishlistRoute(
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WishlistViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
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

    LaunchedEffect(Unit) { viewModel.onEvent(WishlistEvent.EnterScreen) }
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
                uiState = uiState,
                onRefreshMovies = onRefreshMovies,
                onRefreshTvShows = onRefreshTvShows,
                onMovieClick = onMovieClick,
                onTvShowClick = onTvShowClick
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ContentDisplay(
    uiState: WishlistUiState,
    onRefreshMovies: () -> Unit,
    onRefreshTvShows: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val tabs = remember { ListTab.values() }
    val pagerState = rememberPagerState()
    val selectedTabIndex = pagerState.currentPage
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    color = CinemaxTheme.colors.primaryBlue
                )
            }
        ) {
            tabs.forEach { tab ->
                val index = tab.ordinal
                val selected = selectedTabIndex == index
                val color by animateColorAsState(
                    targetValue = if (selected) {
                        CinemaxTheme.colors.primaryBlue
                    } else {
                        CinemaxTheme.colors.textWhite
                    }
                )
                Tab(
                    selected = selected,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = {
                        Text(
                            text = stringResource(id = tab.titleResourceId),
                            style = CinemaxTheme.typography.medium.h4,
                            color = color
                        )
                    }
                )
            }
        }
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            count = tabs.size
        ) { page ->
            when (page) {
                ListTab.Movies.ordinal -> MoviesDisplay(
                    movies = uiState.movies,
                    isLoading = uiState.isMoviesLoading,
                    onRefresh = onRefreshMovies,
                    onClick = onMovieClick
                )
                ListTab.TvShows.ordinal -> TvShowsDisplay(
                    tvShows = uiState.tvShows,
                    isLoading = uiState.isTvShowsLoading,
                    onRefresh = onRefreshTvShows,
                    onClick = onTvShowClick
                )
            }
        }
    }
}

@Composable
private fun MoviesDisplay(
    movies: List<MovieDetails>,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    swipeRefreshState: SwipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading),
    emptyDisplay: @Composable LazyItemScope.() -> Unit = {
        NoResultsDisplay(
            modifier = Modifier.fillParentMaxSize(),
            messageResourceId = R.string.no_movie_wishlist,
            imageResourceId = R.drawable.no_wishlist_results
        )
    }
) {
    CinemaxSwipeRefresh(
        modifier = modifier,
        swipeRefreshState = swipeRefreshState,
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
                else -> item(content = emptyDisplay)
            }
        }
    }
}

@Composable
private fun TvShowsDisplay(
    tvShows: List<TvShowDetails>,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    swipeRefreshState: SwipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading),
    emptyDisplay: @Composable LazyItemScope.() -> Unit = {
        NoResultsDisplay(
            modifier = Modifier.fillParentMaxSize(),
            messageResourceId = R.string.no_tv_show_wishlist,
            imageResourceId = R.drawable.no_wishlist_results
        )
    }
) {
    CinemaxSwipeRefresh(
        modifier = modifier,
        swipeRefreshState = swipeRefreshState,
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
                else -> item(content = emptyDisplay)
            }
        }
    }
}
