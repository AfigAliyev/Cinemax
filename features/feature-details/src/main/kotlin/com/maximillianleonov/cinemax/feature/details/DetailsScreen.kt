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

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.maximillianleonov.cinemax.core.ui.common.ContentType
import com.maximillianleonov.cinemax.core.ui.components.CinemaxBackButton
import com.maximillianleonov.cinemax.core.ui.components.CinemaxCenteredBox
import com.maximillianleonov.cinemax.core.ui.components.CinemaxErrorDisplay
import com.maximillianleonov.cinemax.core.ui.components.CinemaxSwipeRefresh
import com.maximillianleonov.cinemax.core.ui.components.SnackbarUserMessageHandler
import com.maximillianleonov.cinemax.core.ui.theme.CinemaxTheme
import com.maximillianleonov.cinemax.feature.details.components.MovieDetailsItem
import com.maximillianleonov.cinemax.feature.details.components.MovieDetailsItemPlaceholder
import com.maximillianleonov.cinemax.feature.details.components.TvShowDetailsItem
import com.maximillianleonov.cinemax.feature.details.components.TvShowDetailsItemPlaceholder

@Composable
internal fun DetailsRoute(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel(),
    systemUiController: SystemUiController = rememberSystemUiController(),
    systemBarsColor: Color = Color.Transparent,
    systemBarsDarkIcons: Boolean = false,
    defaultSystemBarsColor: Color = CinemaxTheme.colors.primaryDark
) {
    val uiState by viewModel.uiState.collectAsState()
    DetailsScreen(
        uiState = uiState,
        onRefresh = { viewModel.onEvent(DetailsEvent.Refresh) },
        onBackButtonClick = onBackButtonClick,
        onWishlistMovieClick = { viewModel.onEvent(DetailsEvent.WishlistMovie) },
        onWishlistTvShowClick = { viewModel.onEvent(DetailsEvent.WishlistTvShow) },
        onRetry = { viewModel.onEvent(DetailsEvent.Retry) },
        onOfflineModeClick = { viewModel.onEvent(DetailsEvent.ClearError) },
        onUserMessageDismiss = { viewModel.onEvent(DetailsEvent.ClearUserMessage) },
        modifier = modifier
    )

    DisposableEffect(
        systemUiController,
        systemBarsColor,
        systemBarsDarkIcons,
        defaultSystemBarsColor
    ) {
        systemUiController.setSystemBarsColor(
            color = systemBarsColor,
            darkIcons = systemBarsDarkIcons
        )
        onDispose {
            systemUiController.setSystemBarsColor(
                color = defaultSystemBarsColor,
                darkIcons = systemBarsDarkIcons
            )
        }
    }
}

@Composable
private fun DetailsScreen(
    uiState: DetailsUiState,
    onRefresh: () -> Unit,
    onBackButtonClick: () -> Unit,
    onWishlistMovieClick: () -> Unit,
    onWishlistTvShowClick: () -> Unit,
    onRetry: () -> Unit,
    onOfflineModeClick: () -> Unit,
    onUserMessageDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    swipeRefreshState: SwipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = uiState.isLoading
    )
) {
    SnackbarUserMessageHandler(
        userMessage = uiState.userMessage,
        onDismiss = onUserMessageDismiss
    )
    CinemaxSwipeRefresh(
        modifier = modifier,
        swipeRefreshState = swipeRefreshState,
        onRefresh = onRefresh,
        indicatorPadding = WindowInsets.safeDrawing.asPaddingValues()
    ) {
        if (uiState.isError) {
            ErrorDisplay(
                uiState = uiState,
                onBackButtonClick = onBackButtonClick,
                onRetry = onRetry,
                onOfflineModeClick = onOfflineModeClick
            )
        } else {
            ContentDisplay(
                uiState = uiState,
                onBackButtonClick = onBackButtonClick,
                onWishlistMovieClick = onWishlistMovieClick,
                onWishlistTvShowClick = onWishlistTvShowClick
            )
        }
    }
}

@Composable
private fun ContentDisplay(
    uiState: DetailsUiState,
    onBackButtonClick: () -> Unit,
    onWishlistMovieClick: () -> Unit,
    onWishlistTvShowClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.testTag(tag = ContentTestTag)) {
        when (uiState.contentType) {
            is ContentType.Details.Movie -> {
                if (uiState.movie == null) {
                    MovieDetailsItemPlaceholder(onBackButtonClick = onBackButtonClick)
                } else {
                    MovieDetailsItem(
                        movieDetails = uiState.movie,
                        onBackButtonClick = onBackButtonClick,
                        onWishlistButtonClick = onWishlistMovieClick
                    )
                }
            }
            is ContentType.Details.TvShow -> {
                if (uiState.tvShow == null) {
                    TvShowDetailsItemPlaceholder(onBackButtonClick = onBackButtonClick)
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

@Composable
private fun ErrorDisplay(
    uiState: DetailsUiState,
    onBackButtonClick: () -> Unit,
    onRetry: () -> Unit,
    onOfflineModeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CinemaxCenteredBox(
        modifier = modifier
            .padding(horizontal = CinemaxTheme.spacing.extraMedium)
            .safeDrawingPadding()
            .fillMaxSize()
    ) {
        CinemaxBackButton(
            modifier = Modifier
                .padding(top = CinemaxTheme.spacing.small)
                .align(Alignment.TopStart),
            onClick = onBackButtonClick
        )
        CinemaxErrorDisplay(
            errorMessage = uiState.requireError(),
            onRetry = onRetry,
            shouldShowOfflineMode = uiState.isOfflineModeAvailable,
            onOfflineModeClick = onOfflineModeClick
        )
    }
}

private const val TestTag = "details"
private const val ContentTestTag = "$TestTag:content"
