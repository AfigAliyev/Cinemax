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

package com.maximillianleonov.cinemax.feature.details

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxSwipeRefresh
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxTopAppBar
import com.maximillianleonov.cinemax.core.model.MediaType
import com.maximillianleonov.cinemax.core.model.UserMessage
import com.maximillianleonov.cinemax.core.ui.CinemaxBackButton
import com.maximillianleonov.cinemax.core.ui.CinemaxCenteredError
import com.maximillianleonov.cinemax.core.ui.SnackbarUserMessageHandler
import com.maximillianleonov.cinemax.core.ui.mapper.asUserMessage
import com.maximillianleonov.cinemax.feature.details.components.MovieDetailsItem
import com.maximillianleonov.cinemax.feature.details.components.MovieDetailsItemPlaceholder
import com.maximillianleonov.cinemax.feature.details.components.TvShowDetailsItem
import com.maximillianleonov.cinemax.feature.details.components.TvShowDetailsItemPlaceholder

@Composable
internal fun DetailsRoute(
    onBackButtonClick: () -> Unit,
    onShowMessage: (String) -> Unit,
    onSetSystemBarsColorTransparent: () -> Unit,
    onResetSystemBarsColor: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DetailsScreen(
        uiState = uiState,
        onShowMessage = onShowMessage,
        onRefresh = { viewModel.onEvent(DetailsEvent.Refresh) },
        onBackButtonClick = onBackButtonClick,
        onWishlistMovieClick = { viewModel.onEvent(DetailsEvent.WishlistMovie) },
        onWishlistTvShowClick = { viewModel.onEvent(DetailsEvent.WishlistTvShow) },
        onRetry = { viewModel.onEvent(DetailsEvent.Retry) },
        onOfflineModeClick = { viewModel.onEvent(DetailsEvent.ClearError) },
        onUserMessageDismiss = { viewModel.onEvent(DetailsEvent.ClearUserMessage) },
        modifier = modifier
    )

    DisposableEffect(onSetSystemBarsColorTransparent, onResetSystemBarsColor) {
        onSetSystemBarsColorTransparent()
        onDispose { onResetSystemBarsColor() }
    }
}

@Composable
private fun DetailsScreen(
    uiState: DetailsUiState,
    onShowMessage: (String) -> Unit,
    onRefresh: () -> Unit,
    onBackButtonClick: () -> Unit,
    onWishlistMovieClick: () -> Unit,
    onWishlistTvShowClick: () -> Unit,
    onRetry: () -> Unit,
    onOfflineModeClick: () -> Unit,
    onUserMessageDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    SnackbarUserMessageHandler(
        userMessage = uiState.userMessage,
        onShowMessage = onShowMessage,
        onDismiss = onUserMessageDismiss
    )
    CinemaxSwipeRefresh(
        modifier = modifier,
        isRefreshing = uiState.isLoading,
        onRefresh = onRefresh,
        indicatorPadding = WindowInsets.safeDrawing.asPaddingValues()
    ) {
        if (uiState.error != null) {
            ErrorContent(
                errorMessage = uiState.error.asUserMessage(),
                isOfflineModeAvailable = uiState.isOfflineModeAvailable,
                onBackButtonClick = onBackButtonClick,
                onRetry = onRetry,
                onOfflineModeClick = onOfflineModeClick
            )
        } else {
            DetailsContent(
                uiState = uiState,
                onBackButtonClick = onBackButtonClick,
                onWishlistMovieClick = onWishlistMovieClick,
                onWishlistTvShowClick = onWishlistTvShowClick
            )
        }
    }
}

@Composable
private fun DetailsContent(
    uiState: DetailsUiState,
    onBackButtonClick: () -> Unit,
    onWishlistMovieClick: () -> Unit,
    onWishlistTvShowClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier) {
        when (uiState.mediaType) {
            is MediaType.Details.Movie -> {
                if (uiState.movie == null) {
                    MovieDetailsItemPlaceholder(
                        onBackButtonClick = onBackButtonClick,
                        onWishlistButtonClick = onWishlistMovieClick
                    )
                } else {
                    MovieDetailsItem(
                        movieDetails = uiState.movie,
                        onBackButtonClick = onBackButtonClick,
                        onWishlistButtonClick = onWishlistMovieClick
                    )
                }
            }

            is MediaType.Details.TvShow -> {
                if (uiState.tvShow == null) {
                    TvShowDetailsItemPlaceholder(
                        onBackButtonClick = onBackButtonClick,
                        onWishlistButtonClick = onWishlistMovieClick
                    )
                } else {
                    TvShowDetailsItem(
                        tvShowDetails = uiState.tvShow,
                        onBackButtonClick = onBackButtonClick,
                        onWishlistButtonClick = onWishlistTvShowClick
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun ErrorContent(
    errorMessage: UserMessage,
    isOfflineModeAvailable: Boolean,
    onBackButtonClick: () -> Unit,
    onRetry: () -> Unit,
    onOfflineModeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CinemaxTopAppBar(
                title = {},
                navigationIcon = { CinemaxBackButton(onClick = onBackButtonClick) }
            )
        }
    ) { innerPadding ->
        CinemaxCenteredError(
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
            errorMessage = errorMessage,
            onRetry = onRetry,
            shouldShowOfflineMode = isOfflineModeAvailable,
            onOfflineModeClick = onOfflineModeClick
        )
    }
}
