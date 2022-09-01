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

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.maximillianleonov.cinemax.core.ui.common.ContentType.List.Discover
import com.maximillianleonov.cinemax.core.ui.common.ContentType.List.NowPlaying
import com.maximillianleonov.cinemax.core.ui.common.ContentType.List.Popular
import com.maximillianleonov.cinemax.core.ui.common.ContentType.List.TopRated
import com.maximillianleonov.cinemax.core.ui.common.ContentType.List.Trending
import com.maximillianleonov.cinemax.core.ui.common.ContentType.List.Upcoming
import com.maximillianleonov.cinemax.core.ui.common.EventHandler
import com.maximillianleonov.cinemax.core.ui.mapper.pagingMap
import com.maximillianleonov.cinemax.core.ui.mapper.toMovie
import com.maximillianleonov.cinemax.core.ui.mapper.toTvShow
import com.maximillianleonov.cinemax.domain.model.MovieModel
import com.maximillianleonov.cinemax.domain.model.TvShowModel
import com.maximillianleonov.cinemax.domain.usecase.MoviePagingUseCases
import com.maximillianleonov.cinemax.domain.usecase.TvShowPagingUseCases
import com.maximillianleonov.cinemax.feature.list.navigation.ListDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val moviePagingUseCases: MoviePagingUseCases,
    private val tvShowPagingUseCases: TvShowPagingUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel(), EventHandler<ListEvent> {
    private val _uiState = MutableStateFlow(getInitialUiState(savedStateHandle = savedStateHandle))
    val uiState = _uiState.asStateFlow()

    override fun onEvent(event: ListEvent) = Unit

    private fun getInitialUiState(savedStateHandle: SavedStateHandle): ListUiState {
        val contentType = ListDestination.fromSavedStateHandle(savedStateHandle)

        val movies = when (contentType) {
            Upcoming -> moviePagingUseCases.getUpcomingMoviesPagingUseCase()
            TopRated -> moviePagingUseCases.getTopRatedMoviesPagingUseCase()
            Popular -> moviePagingUseCases.getPopularMoviesPagingUseCase()
            NowPlaying -> moviePagingUseCases.getNowPlayingMoviesPagingUseCase()
            Discover -> moviePagingUseCases.getDiscoverMoviesPagingUseCase()
            Trending -> moviePagingUseCases.getTrendingMoviesPagingUseCase()
        }.pagingMap(MovieModel::toMovie).cachedIn(viewModelScope)

        val tvShows = when (contentType) {
            Upcoming -> emptyFlow()
            TopRated -> tvShowPagingUseCases.getTopRatedTvShowsPagingUseCase()
            Popular -> tvShowPagingUseCases.getPopularTvShowsPagingUseCase()
            NowPlaying -> tvShowPagingUseCases.getNowPlayingTvShowsPagingUseCase()
            Discover -> tvShowPagingUseCases.getDiscoverTvShowsPagingUseCase()
            Trending -> tvShowPagingUseCases.getTrendingTvShowsPagingUseCase()
        }.pagingMap(TvShowModel::toTvShow).cachedIn(viewModelScope)

        return ListUiState(
            contentType = contentType,
            movies = movies,
            tvShows = tvShows
        )
    }
}
