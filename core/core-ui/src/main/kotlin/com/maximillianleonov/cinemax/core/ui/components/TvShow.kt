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

package com.maximillianleonov.cinemax.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.maximillianleonov.cinemax.core.ui.R
import com.maximillianleonov.cinemax.core.ui.mapper.toNames
import com.maximillianleonov.cinemax.core.ui.model.ErrorMessage
import com.maximillianleonov.cinemax.core.ui.model.TvShow
import com.maximillianleonov.cinemax.core.ui.model.TvShowDetails
import com.maximillianleonov.cinemax.core.ui.theme.CinemaxTheme
import com.maximillianleonov.cinemax.core.ui.util.error
import com.maximillianleonov.cinemax.core.ui.util.isEmpty
import com.maximillianleonov.cinemax.core.ui.util.isError
import com.maximillianleonov.cinemax.core.ui.util.isFinished
import com.maximillianleonov.cinemax.core.ui.util.isLoading
import com.maximillianleonov.cinemax.core.ui.util.isNotEmpty
import com.maximillianleonov.cinemax.core.ui.util.toErrorMessage

@Composable
fun TvShowsContainer(
    tvShows: List<TvShow>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    shouldShowPlaceholder: Boolean = tvShows.isEmpty()
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .testTag(tag = TvShowsContainerTestTag),
        horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.smallMedium),
        contentPadding = PaddingValues(horizontal = CinemaxTheme.spacing.smallMedium)
    ) {
        if (shouldShowPlaceholder) {
            items(PlaceholderCount) { HorizontalTvShowItemPlaceholder() }
        } else {
            items(tvShows) { tvShow ->
                HorizontalTvShowItem(tvShow = tvShow, onClick = onClick)
            }
        }
    }
}

@Composable
fun TvShowsDisplay(
    tvShows: LazyPagingItems<TvShow>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    swipeRefreshState: SwipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = tvShows.loadState.refresh.isLoading()
    ),
    emptyDisplay: @Composable LazyItemScope.() -> Unit = {
        NoResultsDisplay(
            modifier = Modifier.fillParentMaxSize(),
            messageResourceId = R.string.no_tv_show_results,
            imageResourceId = R.drawable.no_search_results
        )
    },
    loadingDisplay: @Composable LazyItemScope.() -> Unit = { CinemaxCircularProgressIndicator() },
    errorDisplay: @Composable LazyItemScope.(errorMessage: ErrorMessage) -> Unit = { errorMessage ->
        CinemaxErrorDisplay(
            errorMessage = errorMessage,
            onRetry = tvShows::retry,
            modifier = Modifier.fillMaxWidth()
        )
    }
) {
    CinemaxSwipeRefresh(
        swipeRefreshState = swipeRefreshState,
        onRefresh = tvShows::refresh,
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.medium),
            contentPadding = PaddingValues(CinemaxTheme.spacing.extraMedium)
        ) {
            when {
                tvShows.isNotEmpty() -> {
                    items(tvShows) { tvShow ->
                        if (tvShow == null) {
                            VerticalTvShowItemPlaceholder()
                        } else {
                            VerticalTvShowItem(tvShow = tvShow, onClick = onClick)
                        }
                    }
                    if (tvShows.loadState.refresh.isError()) {
                        item { errorDisplay(errorMessage = tvShows.loadState.refresh.error.toErrorMessage()) }
                    }
                }
                tvShows.loadState.refresh.isLoading() -> {
                    items(PlaceholderCount) { VerticalTvShowItemPlaceholder() }
                }
                tvShows.loadState.refresh.isFinished() -> {
                    if (tvShows.isEmpty()) {
                        item(content = emptyDisplay)
                    }
                }
                tvShows.loadState.refresh.isError() -> {
                    item {
                        CinemaxCenteredBox(modifier = Modifier.fillParentMaxSize()) {
                            errorDisplay(
                                errorMessage = tvShows.loadState.refresh.error.toErrorMessage()
                            )
                        }
                    }
                }
            }
            if (tvShows.loadState.append.isLoading()) {
                item { CinemaxCenteredBox(modifier = Modifier.fillMaxWidth()) { loadingDisplay() } }
            }
            if (tvShows.loadState.append.isError()) {
                item { errorDisplay(errorMessage = tvShows.loadState.append.error.toErrorMessage()) }
            }
        }
    }
}

@Suppress("ReusedModifierInstance")
@Composable
fun HorizontalTvShowItem(
    tvShow: TvShow,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    with(tvShow) {
        HorizontalContentItem(
            title = name,
            posterPath = posterPath,
            voteAverage = voteAverage,
            genres = genres.toNames(),
            onClick = { onClick(id) },
            modifier = modifier
        )
    }
}

@Composable
fun HorizontalTvShowItemPlaceholder(
    modifier: Modifier = Modifier
) {
    HorizontalContentItemPlaceholder(modifier = modifier)
}

@Suppress("ReusedModifierInstance")
@Composable
fun VerticalTvShowItem(
    tvShow: TvShow,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    with(tvShow) {
        VerticalContentItem(
            title = name,
            overview = overview,
            posterPath = posterPath,
            voteAverage = voteAverage,
            releaseDate = firstAirDate,
            genres = genres.toNames(),
            onClick = { onClick(id) },
            modifier = modifier
        )
    }
}

@Suppress("ReusedModifierInstance")
@Composable
fun VerticalTvShowItem(
    tvShow: TvShowDetails,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    with(tvShow) {
        VerticalContentItem(
            title = name,
            overview = overview,
            posterPath = posterPath,
            voteAverage = voteAverage,
            releaseDate = firstAirDate,
            genres = genres.toNames(),
            onClick = { onClick(id) },
            modifier = modifier
        )
    }
}

@Composable
fun VerticalTvShowItemPlaceholder(
    modifier: Modifier = Modifier
) {
    VerticalContentItemPlaceholder(modifier = modifier)
}

private const val TvShowsContainerTestTag = "tvshowscontainer"
