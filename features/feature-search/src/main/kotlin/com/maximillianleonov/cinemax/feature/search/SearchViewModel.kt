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

package com.maximillianleonov.cinemax.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.maximillianleonov.cinemax.core.ui.common.ContentType
import com.maximillianleonov.cinemax.core.ui.common.EventHandler
import com.maximillianleonov.cinemax.core.ui.mapper.pagingMap
import com.maximillianleonov.cinemax.core.ui.mapper.toMovie
import com.maximillianleonov.cinemax.core.ui.mapper.toTvShow
import com.maximillianleonov.cinemax.core.ui.util.handle
import com.maximillianleonov.cinemax.core.ui.util.toErrorMessage
import com.maximillianleonov.cinemax.domain.model.MovieModel
import com.maximillianleonov.cinemax.domain.model.TvShowModel
import com.maximillianleonov.cinemax.domain.usecase.GetSearchMoviesPagingUseCase
import com.maximillianleonov.cinemax.domain.usecase.GetSearchTvShowsPagingUseCase
import com.maximillianleonov.cinemax.domain.usecase.MovieUseCases
import com.maximillianleonov.cinemax.domain.usecase.TvShowUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("TooManyFunctions")
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieUseCases: MovieUseCases,
    private val tvShowUseCases: TvShowUseCases,
    private val getSearchMoviesPagingUseCase: GetSearchMoviesPagingUseCase,
    private val getSearchTvShowsPagingUseCase: GetSearchTvShowsPagingUseCase
) : ViewModel(), EventHandler<SearchEvent> {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private var contentJobs = getContentJobs()
    private var searchJob: Job? = null

    override fun onEvent(event: SearchEvent) = when (event) {
        is SearchEvent.ChangeQuery -> onQueryChange(query = event.value)
        SearchEvent.Refresh -> onRefresh()
        SearchEvent.ClearError -> onClearError()
    }

    private fun getContentJobs() = mapOf(
        ContentType.Main.DiscoverMovies to loadDiscoverMovies(),
        ContentType.Main.DiscoverTvShows to loadDiscoverTvShows(),
        ContentType.Main.TrendingMovies to loadTrendingMovies(),
        ContentType.Main.TrendingTvShows to loadTrendingTvShows()
    )

    private fun onQueryChange(query: String) {
        val isSearching = query.isNotBlank()
        _uiState.update {
            it.copy(query = query, isSearching = isSearching)
        }
        searchDebounced(query = query.trim())
    }

    private fun searchDebounced(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DURATION)
            search(query)
        }
    }

    private fun search(query: String) = _uiState.update {
        val searchMovies = if (it.isSearching) {
            getSearchMoviesPagingUseCase(query)
                .pagingMap(MovieModel::toMovie)
                .cachedIn(viewModelScope)
        } else {
            emptyFlow()
        }

        val searchTvShows = if (it.isSearching) {
            getSearchTvShowsPagingUseCase(query)
                .pagingMap(TvShowModel::toTvShow)
                .cachedIn(viewModelScope)
        } else {
            emptyFlow()
        }

        it.copy(
            searchMovies = searchMovies,
            searchTvShows = searchTvShows
        )
    }

    private fun loadDiscoverMovies() = viewModelScope.launch {
        movieUseCases.getDiscoverMoviesUseCase().handle(
            onLoading = ::handleDiscoverMoviesLoading,
            onSuccess = ::handleDiscoverMoviesSuccess,
            onFailure = ::handleDiscoverMoviesFailure
        )
    }

    private fun loadDiscoverTvShows() = viewModelScope.launch {
        tvShowUseCases.getDiscoverTvShowsUseCase().handle(
            onLoading = ::handleDiscoverTvShowsLoading,
            onSuccess = ::handleDiscoverTvShowsSuccess,
            onFailure = ::handleDiscoverTvShowsFailure
        )
    }

    private fun loadTrendingMovies() = viewModelScope.launch {
        movieUseCases.getTrendingMoviesUseCase().handle(
            onLoading = ::handleTrendingMoviesLoading,
            onSuccess = ::handleTrendingMoviesSuccess,
            onFailure = ::handleTrendingMoviesFailure
        )
    }

    private fun loadTrendingTvShows() = viewModelScope.launch {
        tvShowUseCases.getTrendingTvShowsUseCase().handle(
            onLoading = ::handleTrendingTvShowsLoading,
            onSuccess = ::handleTrendingTvShowsSuccess,
            onFailure = ::handleTrendingTvShowsFailure
        )
    }

    private fun handleDiscoverMoviesLoading(movies: List<MovieModel>?) =
        handleLoading(contentLoadType = ContentType.Main.DiscoverMovies, movies = movies)

    private fun handleDiscoverMoviesSuccess(movies: List<MovieModel>) =
        handleSuccess(contentLoadType = ContentType.Main.DiscoverMovies, movies = movies)

    private fun handleDiscoverMoviesFailure(throwable: Throwable) =
        handleFailure(error = throwable)

    private fun handleDiscoverTvShowsLoading(tvShows: List<TvShowModel>?) =
        handleLoading(contentLoadType = ContentType.Main.DiscoverTvShows, tvShows = tvShows)

    private fun handleDiscoverTvShowsSuccess(tvShows: List<TvShowModel>) =
        handleSuccess(contentLoadType = ContentType.Main.DiscoverTvShows, tvShows = tvShows)

    private fun handleDiscoverTvShowsFailure(throwable: Throwable) =
        handleFailure(error = throwable)

    private fun handleTrendingMoviesLoading(movies: List<MovieModel>?) =
        handleLoading(contentLoadType = ContentType.Main.TrendingMovies, movies = movies)

    private fun handleTrendingMoviesSuccess(movies: List<MovieModel>) =
        handleSuccess(contentLoadType = ContentType.Main.TrendingMovies, movies = movies)

    private fun handleTrendingMoviesFailure(throwable: Throwable) =
        handleFailure(error = throwable)

    private fun handleTrendingTvShowsLoading(tvShows: List<TvShowModel>?) =
        handleLoading(contentLoadType = ContentType.Main.TrendingTvShows, tvShows = tvShows)

    private fun handleTrendingTvShowsSuccess(tvShows: List<TvShowModel>) =
        handleSuccess(contentLoadType = ContentType.Main.TrendingTvShows, tvShows = tvShows)

    private fun handleTrendingTvShowsFailure(throwable: Throwable) =
        handleFailure(error = throwable)

    @JvmName("handleMoviesLoading")
    private fun handleLoading(contentLoadType: ContentType.Main, movies: List<MovieModel>?) =
        _uiState.update {
            it.copy(
                movies = it.movies + (
                    contentLoadType to movies?.map(MovieModel::toMovie).orEmpty()
                    )
            )
        }

    @JvmName("handleTvShowsLoading")
    private fun handleLoading(contentLoadType: ContentType.Main, tvShows: List<TvShowModel>?) =
        _uiState.update {
            it.copy(
                tvShows = it.tvShows + (
                    contentLoadType to tvShows?.map(TvShowModel::toTvShow).orEmpty()
                    )
            )
        }

    @JvmName("handleMoviesSuccess")
    private fun handleSuccess(contentLoadType: ContentType.Main, movies: List<MovieModel>) =
        _uiState.update {
            it.copy(movies = it.movies + (contentLoadType to movies.map(MovieModel::toMovie)))
        }

    @JvmName("handleTvShowsSuccess")
    private fun handleSuccess(contentLoadType: ContentType.Main, tvShows: List<TvShowModel>) =
        _uiState.update {
            it.copy(tvShows = it.tvShows + (contentLoadType to tvShows.map(TvShowModel::toTvShow)))
        }

    private fun handleFailure(error: Throwable) =
        _uiState.update { it.copy(error = error.toErrorMessage()) }

    private fun onRefresh() {
        contentJobs.values.forEach(Job::cancel)
        contentJobs = getContentJobs()
    }

    private fun onClearError() = _uiState.update { it.copy(error = null) }
}

private const val SEARCH_DEBOUNCE_DURATION = 500L
