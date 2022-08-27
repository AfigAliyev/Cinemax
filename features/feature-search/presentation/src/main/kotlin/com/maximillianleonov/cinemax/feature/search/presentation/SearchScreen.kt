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

package com.maximillianleonov.cinemax.feature.search.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.maximillianleonov.cinemax.core.presentation.R
import com.maximillianleonov.cinemax.core.presentation.common.ContentType
import com.maximillianleonov.cinemax.core.presentation.components.CinemaxTextField
import com.maximillianleonov.cinemax.core.presentation.components.MoviesAndTvShowsContainer
import com.maximillianleonov.cinemax.core.presentation.components.MoviesDisplay
import com.maximillianleonov.cinemax.core.presentation.components.TvShowsDisplay
import com.maximillianleonov.cinemax.core.presentation.model.Movie
import com.maximillianleonov.cinemax.core.presentation.model.TvShow
import com.maximillianleonov.cinemax.core.presentation.theme.CinemaxTheme
import com.maximillianleonov.cinemax.feature.search.presentation.common.SearchTab
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun SearchRoute(
    onSeeAllClick: (ContentType.List) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchMovies = uiState.searchMovies.collectAsLazyPagingItems()
    val searchTvShows = uiState.searchTvShows.collectAsLazyPagingItems()
    SearchScreen(
        uiState = uiState,
        searchMovies = searchMovies,
        searchTvShows = searchTvShows,
        onQueryChange = { viewModel.onEvent(SearchEvent.ChangeQuery(it)) },
        onSeeAllClick = onSeeAllClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SearchScreen(
    uiState: SearchUiState,
    searchMovies: LazyPagingItems<Movie>,
    searchTvShows: LazyPagingItems<TvShow>,
    onQueryChange: (String) -> Unit,
    onSeeAllClick: (ContentType.List) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        SearchTextField(query = uiState.query, onQueryChange = onQueryChange)
        AnimatedContent(targetState = uiState.isSearching) { isSearching ->
            if (isSearching) {
                SearchResultsBlock(searchMovies = searchMovies, searchTvShows = searchTvShows)
            } else {
                SuggestionsBlock(
                    movies = uiState.movies,
                    tvShows = uiState.tvShows,
                    onSeeAllClick = onSeeAllClick
                )
            }
        }
    }
}

@Composable
private fun SearchTextField(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current
) = CinemaxTextField(
    modifier = modifier
        .padding(
            start = CinemaxTheme.spacing.extraMedium,
            top = CinemaxTheme.spacing.small,
            end = CinemaxTheme.spacing.extraMedium,
            bottom = CinemaxTheme.spacing.extraMedium
        )
        .fillMaxWidth(),
    value = query,
    onValueChange = onQueryChange,
    placeholderResourceId = R.string.search_placeholder,
    iconResourceId = R.drawable.ic_search,
    keyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Words,
        imeAction = ImeAction.Search
    ),
    keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
)

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun SearchResultsBlock(
    searchMovies: LazyPagingItems<Movie>,
    searchTvShows: LazyPagingItems<TvShow>,
    modifier: Modifier = Modifier,
    tabs: Array<SearchTab> = SearchTab.values(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val pagerState = rememberPagerState()
    val selectedTabIndex = pagerState.currentPage
    Column(modifier = modifier.fillMaxSize()) {
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
                SearchTab.Movies.ordinal -> MoviesDisplay(movies = searchMovies)
                SearchTab.TvShows.ordinal -> TvShowsDisplay(tvShows = searchTvShows)
            }
        }
    }
}

@Composable
private fun SuggestionsBlock(
    movies: Map<ContentType.Main, List<Movie>>,
    tvShows: Map<ContentType.Main, List<TvShow>>,
    onSeeAllClick: (ContentType.List) -> Unit,
    modifier: Modifier = Modifier
) = LazyColumn(
    modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.extraMedium),
    contentPadding = PaddingValues(bottom = CinemaxTheme.spacing.extraMedium)
) {
    item {
        MoviesAndTvShowsContainer(
            titleResourceId = R.string.discover,
            onSeeAllClick = { onSeeAllClick(ContentType.List.Discover) },
            movies = movies[ContentType.Main.DiscoverMovies].orEmpty(),
            tvShows = tvShows[ContentType.Main.DiscoverTvShows].orEmpty()
        )
    }
    item {
        MoviesAndTvShowsContainer(
            titleResourceId = R.string.trending,
            onSeeAllClick = { onSeeAllClick(ContentType.List.Trending) },
            movies = movies[ContentType.Main.TrendingMovies].orEmpty(),
            tvShows = tvShows[ContentType.Main.TrendingTvShows].orEmpty()
        )
    }
}
