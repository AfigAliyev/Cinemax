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
import com.maximillianleonov.cinemax.core.presentation.common.ContentType
import com.maximillianleonov.cinemax.core.presentation.components.SnackbarErrorHandler
import com.maximillianleonov.cinemax.core.presentation.theme.CinemaxTheme
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
        onRetry = { viewModel.onEvent(HomeEvent.Retry) },
        onDismiss = { viewModel.onEvent(HomeEvent.ClearError) }
    )
}

@Composable
internal fun HomeScreen(
    uiState: HomeUiState,
    onNavigateToListDestination: (ContentType) -> Unit,
    onRetry: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    SnackbarErrorHandler(
        errorMessage = uiState.error,
        onRetry = onRetry,
        onDismiss = onDismiss
    )
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.extraMedium),
        contentPadding = PaddingValues(vertical = CinemaxTheme.spacing.extraMedium)
    ) {
        item {
            UpcomingMoviesContainer(
                movies = uiState.upcomingMovies,
                onSeeAllClick = { onNavigateToListDestination(ContentType.Upcoming) }
            )
        }
    }
}
