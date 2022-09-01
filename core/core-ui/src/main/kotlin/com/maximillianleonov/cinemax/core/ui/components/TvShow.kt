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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.maximillianleonov.cinemax.core.ui.R
import com.maximillianleonov.cinemax.core.ui.mapper.toNames
import com.maximillianleonov.cinemax.core.ui.model.TvShow
import com.maximillianleonov.cinemax.core.ui.theme.CinemaxTheme
import com.maximillianleonov.cinemax.core.ui.util.isEmpty
import com.maximillianleonov.cinemax.core.ui.util.isLoading

@Composable
fun TvShowsContainer(
    tvShows: List<TvShow>,
    modifier: Modifier = Modifier
) {
    val shouldShowPlaceholder = tvShows.isEmpty()
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.smallMedium),
        contentPadding = PaddingValues(horizontal = CinemaxTheme.spacing.smallMedium)
    ) {
        if (shouldShowPlaceholder) {
            items(PlaceholderCount) { HorizontalTvShowItemPlaceholder() }
        } else {
            items(tvShows) { tvShow ->
                HorizontalTvShowItem(tvShow = tvShow)
            }
        }
    }
}

@Suppress("ReusedModifierInstance")
@Composable
fun TvShowsDisplay(
    tvShows: LazyPagingItems<TvShow>,
    modifier: Modifier = Modifier,
    swipeRefreshState: SwipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = tvShows.loadState.refresh.isLoading
    )
) {
    CinemaxSwipeRefresh(
        swipeRefreshState = swipeRefreshState,
        onRefresh = tvShows::refresh
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.medium),
            contentPadding = PaddingValues(CinemaxTheme.spacing.extraMedium)
        ) {
            if (tvShows.loadState.refresh is LoadState.NotLoading) {
                items(tvShows) { tvShow ->
                    if (tvShow == null) {
                        VerticalTvShowItemPlaceholder()
                    } else {
                        VerticalTvShowItem(tvShow = tvShow)
                    }
                }
                if (tvShows.isEmpty) {
                    item {
                        NoResultsDisplay(
                            modifier = Modifier.fillParentMaxSize(),
                            messageResourceId = R.string.no_tv_show_results
                        )
                    }
                }
            } else {
                items(PlaceholderCount) { VerticalTvShowItemPlaceholder() }
            }
            if (tvShows.loadState.append is LoadState.Loading) {
                item {
                    CinemaxCenteredBox(modifier = Modifier.fillMaxWidth()) {
                        CinemaxCircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun HorizontalTvShowItem(
    tvShow: TvShow,
    modifier: Modifier = Modifier
) = with(tvShow) {
    HorizontalContentItem(
        title = name,
        posterPath = posterPath,
        voteAverage = voteAverage,
        genres = genres.toNames(),
        modifier = modifier
    )
}

@Composable
fun HorizontalTvShowItemPlaceholder(
    modifier: Modifier = Modifier
) = HorizontalContentItemPlaceholder(modifier = modifier)

@Composable
fun VerticalTvShowItem(
    tvShow: TvShow,
    modifier: Modifier = Modifier
) = with(tvShow) {
    VerticalContentItem(
        title = name,
        overview = overview,
        posterPath = posterPath,
        voteAverage = voteAverage,
        releaseDate = firstAirDate,
        genres = genres.toNames(),
        modifier = modifier
    )
}

@Composable
fun VerticalTvShowItemPlaceholder(
    modifier: Modifier = Modifier
) = VerticalContentItemPlaceholder(modifier = modifier)
