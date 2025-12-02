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

package com.maximillianleonov.cinemax.feature.list

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxTopAppBar
import com.maximillianleonov.cinemax.core.model.MediaType.Common.Upcoming
import com.maximillianleonov.cinemax.core.model.Movie
import com.maximillianleonov.cinemax.core.model.TvShow
import com.maximillianleonov.cinemax.core.ui.CinemaxBackButton
import com.maximillianleonov.cinemax.core.ui.MediaTabPager
import com.maximillianleonov.cinemax.core.ui.MoviesContainer
import com.maximillianleonov.cinemax.core.ui.TvShowsContainer
import com.maximillianleonov.cinemax.feature.list.util.asTitleResourceId

@Composable
internal fun ListRoute(
    onBackButtonClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val movies = uiState.movies.collectAsLazyPagingItems()
    val tvShows = uiState.tvShows.collectAsLazyPagingItems()
    ListScreen(
        modifier = modifier,
        uiState = uiState,
        movies = movies,
        tvShows = tvShows,
        onBackButtonClick = onBackButtonClick,
        onMovieClick = onMovieClick,
        onTvShowClick = onTvShowClick
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
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
        modifier = modifier.fillMaxSize(),
        topBar = {
            CinemaxTopAppBar(
                titleResourceId = uiState.mediaType.asTitleResourceId(),
                navigationIcon = { CinemaxBackButton(onClick = onBackButtonClick) }
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing.only(
            WindowInsetsSides.Horizontal + WindowInsetsSides.Top
        )
    ) { innerPadding ->
        when (uiState.mediaType) {
            Upcoming -> {
                MoviesContainer(
                    modifier = Modifier
                        .padding(innerPadding)
                        .consumeWindowInsets(innerPadding),
                    movies = movies,
                    onClick = onMovieClick
                )
            }

            else -> {
                MediaTabPager(
                    modifier = Modifier
                        .padding(innerPadding)
                        .consumeWindowInsets(innerPadding),
                    moviesTabContent = {
                        MoviesContainer(movies = movies, onClick = onMovieClick)
                    },
                    tvShowsTabContent = {
                        TvShowsContainer(tvShows = tvShows, onClick = onTvShowClick)
                    }
                )
            }
        }
    }
}
