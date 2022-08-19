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

package com.maximillianleonov.cinemax.feature.list.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.maximillianleonov.cinemax.core.presentation.common.ContentType
import com.maximillianleonov.cinemax.core.presentation.components.CinemaxCenteredBox
import com.maximillianleonov.cinemax.core.presentation.components.CinemaxCircularProgressIndicator
import com.maximillianleonov.cinemax.core.presentation.components.CinemaxSwipeRefresh
import com.maximillianleonov.cinemax.core.presentation.components.CinemaxTopAppBar
import com.maximillianleonov.cinemax.core.presentation.components.SnackbarPagingErrorHandler
import com.maximillianleonov.cinemax.core.presentation.components.VerticalMovieItem
import com.maximillianleonov.cinemax.core.presentation.components.VerticalMovieItemPlaceholder
import com.maximillianleonov.cinemax.core.presentation.model.Movie
import com.maximillianleonov.cinemax.core.presentation.theme.CinemaxTheme
import com.maximillianleonov.cinemax.core.presentation.util.isLoading
import com.maximillianleonov.cinemax.feature.list.presentation.util.toTitleResourceId

@Composable
fun ListRoute(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val movies = uiState.movies.collectAsLazyPagingItems()
    ListScreen(
        uiState = uiState,
        movies = movies,
        onBackButtonClick = onBackButtonClick,
        modifier = modifier
    )
}

@Composable
internal fun ListScreen(
    uiState: ListUiState,
    movies: LazyPagingItems<Movie>,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CinemaxTopAppBar(
                titleResourceId = uiState.contentType.toTitleResourceId(),
                onBackButtonClick = onBackButtonClick
            )
        }
    ) { innerPadding ->
        when (uiState.contentType) {
            ContentType.Upcoming -> MoviesDisplay(
                movies = movies,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Suppress("ReusedModifierInstance")
@Composable
private fun MoviesDisplay(
    movies: LazyPagingItems<Movie>,
    modifier: Modifier = Modifier,
    swipeRefreshState: SwipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = movies.loadState.refresh.isLoading
    )
) {
    SnackbarPagingErrorHandler(items = movies)
    CinemaxSwipeRefresh(
        swipeRefreshState = swipeRefreshState,
        onRefresh = movies::refresh
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.medium),
            contentPadding = PaddingValues(CinemaxTheme.spacing.extraMedium)
        ) {
            if (movies.loadState.refresh !is LoadState.NotLoading) {
                items(PlaceholderCount) { VerticalMovieItemPlaceholder() }
            } else {
                items(movies) { movie ->
                    if (movie == null) {
                        VerticalMovieItemPlaceholder()
                    } else {
                        VerticalMovieItem(movie = movie)
                    }
                }
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

private const val PlaceholderCount = 20
