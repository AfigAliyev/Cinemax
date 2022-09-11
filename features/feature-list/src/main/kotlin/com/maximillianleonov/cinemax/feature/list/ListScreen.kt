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

package com.maximillianleonov.cinemax.feature.list

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.maximillianleonov.cinemax.core.ui.common.ContentType.List.Upcoming
import com.maximillianleonov.cinemax.core.ui.components.CinemaxTopAppBar
import com.maximillianleonov.cinemax.core.ui.components.MoviesDisplay
import com.maximillianleonov.cinemax.core.ui.components.TvShowsDisplay
import com.maximillianleonov.cinemax.core.ui.model.Movie
import com.maximillianleonov.cinemax.core.ui.model.TvShow
import com.maximillianleonov.cinemax.core.ui.theme.CinemaxTheme
import com.maximillianleonov.cinemax.feature.list.common.ListTab
import com.maximillianleonov.cinemax.feature.list.util.toTitleResourceId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun ListRoute(
    onBackButtonClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val movies = uiState.movies.collectAsLazyPagingItems()
    val tvShows = uiState.tvShows.collectAsLazyPagingItems()
    ListScreen(
        uiState = uiState,
        movies = movies,
        tvShows = tvShows,
        onBackButtonClick = onBackButtonClick,
        onMovieClick = onMovieClick,
        onTvShowClick = onTvShowClick,
        modifier = modifier
    )
}

@Composable
private fun ListScreen(
    uiState: ListUiState,
    movies: LazyPagingItems<Movie>,
    tvShows: LazyPagingItems<TvShow>,
    onBackButtonClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .fillMaxSize(),
        topBar = {
            CinemaxTopAppBar(
                titleResourceId = uiState.contentType.toTitleResourceId(),
                onBackButtonClick = onBackButtonClick
            )
        }
    ) { innerPadding ->
        when (uiState.contentType) {
            Upcoming -> MoviesDisplay(
                movies = movies,
                onClick = onMovieClick,
                modifier = Modifier
                    .padding(innerPadding)
                    .testTag(tag = ContentTestTag)
            )
            else -> MoviesAndTvShowsDisplay(
                movies = movies,
                tvShows = tvShows,
                onMovieClick = onMovieClick,
                onTvShowClick = onTvShowClick,
                modifier = Modifier
                    .padding(innerPadding)
                    .testTag(tag = ContentTestTag)
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun MoviesAndTvShowsDisplay(
    movies: LazyPagingItems<Movie>,
    tvShows: LazyPagingItems<TvShow>,
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
                    movies = movies,
                    onClick = onMovieClick
                )
                ListTab.TvShows.ordinal -> TvShowsDisplay(
                    tvShows = tvShows,
                    onClick = onTvShowClick
                )
            }
        }
    }
}

private const val TestTag = "list"
private const val ContentTestTag = "$TestTag:content"
