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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximillianleonov.cinemax.core.presentation.common.ContentType
import com.maximillianleonov.cinemax.core.presentation.common.EventHandler
import com.maximillianleonov.cinemax.core.presentation.mapper.toMovie
import com.maximillianleonov.cinemax.core.presentation.mapper.toTvShow
import com.maximillianleonov.cinemax.core.presentation.util.handle
import com.maximillianleonov.cinemax.core.presentation.util.toErrorMessage
import com.maximillianleonov.cinemax.domain.model.MovieModel
import com.maximillianleonov.cinemax.domain.model.TvShowModel
import com.maximillianleonov.cinemax.domain.usecase.MovieUseCases
import com.maximillianleonov.cinemax.domain.usecase.TvShowUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("TooManyFunctions")
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieUseCases: MovieUseCases,
    private val tvShowUseCases: TvShowUseCases
) : ViewModel(), EventHandler<SearchEvent> {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private var contentJobs = getContentJobs()

    override fun onEvent(event: SearchEvent) = when (event) {
        is SearchEvent.ChangeQuery -> onQueryChange(newQuery = event.value)
        SearchEvent.Refresh -> onRefresh()
        SearchEvent.ClearError -> onClearError()
    }

    private fun getContentJobs() = mapOf(
        ContentType.Main.DiscoverMovies to loadDiscoverMovies(),
        ContentType.Main.DiscoverTvShows to loadDiscoverTvShows()
    )

    private fun onQueryChange(newQuery: String) = _uiState.update {
        it.copy(query = newQuery, isSearching = newQuery.isNotBlank())
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
