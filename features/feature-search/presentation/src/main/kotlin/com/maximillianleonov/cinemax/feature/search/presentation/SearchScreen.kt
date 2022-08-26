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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.hilt.navigation.compose.hiltViewModel
import com.maximillianleonov.cinemax.core.presentation.R
import com.maximillianleonov.cinemax.core.presentation.common.ContentType
import com.maximillianleonov.cinemax.core.presentation.components.CinemaxTextField
import com.maximillianleonov.cinemax.core.presentation.components.MoviesAndTvShowsContainer
import com.maximillianleonov.cinemax.core.presentation.model.Movie
import com.maximillianleonov.cinemax.core.presentation.model.TvShow
import com.maximillianleonov.cinemax.core.presentation.theme.CinemaxTheme

@Composable
internal fun SearchRoute(
    onSeeAllClick: (ContentType.List) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    SearchScreen(
        uiState = uiState,
        onQueryChange = { viewModel.onEvent(SearchEvent.ChangeQuery(it)) },
        onSeeAllClick = onSeeAllClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SearchScreen(
    uiState: SearchUiState,
    onQueryChange: (String) -> Unit,
    onSeeAllClick: (ContentType.List) -> Unit,
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current
) {
    Column(modifier = modifier.fillMaxSize()) {
        CinemaxTextField(
            modifier = Modifier
                .padding(
                    start = CinemaxTheme.spacing.extraMedium,
                    top = CinemaxTheme.spacing.small,
                    end = CinemaxTheme.spacing.extraMedium,
                    bottom = CinemaxTheme.spacing.extraMedium
                )
                .fillMaxWidth(),
            value = uiState.query,
            onValueChange = onQueryChange,
            placeholderResourceId = R.string.search_placeholder,
            iconResourceId = R.drawable.ic_search,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
        )
        AnimatedContent(targetState = uiState.isSearching) { isSearching ->
            @Suppress("ForbiddenComment")
            if (isSearching) {
                // TODO: Search results.
            } else {
                MoviesAndTvShowsBlock(
                    movies = uiState.movies,
                    tvShows = uiState.tvShows,
                    onSeeAllClick = onSeeAllClick
                )
            }
        }
    }
}

@Composable
private fun MoviesAndTvShowsBlock(
    movies: Map<ContentType.Main, List<Movie>>,
    tvShows: Map<ContentType.Main, List<TvShow>>,
    onSeeAllClick: (ContentType.List) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
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
}
