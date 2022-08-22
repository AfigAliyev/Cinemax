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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximillianleonov.cinemax.core.presentation.common.EventHandler
import com.maximillianleonov.cinemax.core.presentation.mapper.toMovie
import com.maximillianleonov.cinemax.core.presentation.mapper.toTvShow
import com.maximillianleonov.cinemax.core.presentation.util.handle
import com.maximillianleonov.cinemax.core.presentation.util.toErrorMessage
import com.maximillianleonov.cinemax.domain.model.MovieModel
import com.maximillianleonov.cinemax.domain.model.TvShowModel
import com.maximillianleonov.cinemax.domain.usecase.GetTopRatedMoviesUseCase
import com.maximillianleonov.cinemax.domain.usecase.GetTopRatedTvShowsUseCase
import com.maximillianleonov.cinemax.domain.usecase.GetUpcomingMoviesUseCase
import com.maximillianleonov.cinemax.feature.home.presentation.common.ContentLoadType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("TooManyFunctions")
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getTopRatedTvShowsUseCase: GetTopRatedTvShowsUseCase
) : ViewModel(), EventHandler<HomeEvent> {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private var upcomingMoviesJob = loadUpcomingMovies()
    private var topRatedMoviesJob = loadTopRatedMovies()
    private var topRatedTvShowsJob = loadTopRatedTvShows()

    override fun onEvent(event: HomeEvent) = when (event) {
        HomeEvent.Refresh -> onRefresh()
        HomeEvent.ClearError -> onClearError()
    }

    private fun loadUpcomingMovies() = viewModelScope.launch {
        getUpcomingMoviesUseCase().handle(
            onLoading = ::handleUpcomingMoviesLoading,
            onSuccess = ::handleUpcomingMoviesSuccess,
            onFailure = ::handleUpcomingMoviesFailure
        )
    }

    private fun loadTopRatedMovies() = viewModelScope.launch {
        getTopRatedMoviesUseCase().handle(
            onLoading = ::handleTopRatedMoviesLoading,
            onSuccess = ::handleTopRatedMoviesSuccess,
            onFailure = ::handleTopRatedMoviesFailure
        )
    }

    private fun loadTopRatedTvShows() = viewModelScope.launch {
        getTopRatedTvShowsUseCase().handle(
            onLoading = ::handleTopRatedTvShowsLoading,
            onSuccess = ::handleTopRatedTvShowsSuccess,
            onFailure = ::handleTopRatedTvShowsFailure
        )
    }

    private fun handleUpcomingMoviesLoading(upcomingMovies: List<MovieModel>?) {
        _uiState.update {
            it.copy(
                upcomingMovies = upcomingMovies?.map(MovieModel::toMovie).orEmpty(),
                loadStates = it.loadStates + (ContentLoadType.UpcomingMovies to true)
            )
        }
    }

    private fun handleUpcomingMoviesSuccess(upcomingMovies: List<MovieModel>) {
        _uiState.update {
            it.copy(
                upcomingMovies = upcomingMovies.map(MovieModel::toMovie),
                loadStates = it.loadStates + (ContentLoadType.UpcomingMovies to false)
            )
        }
    }

    private fun handleUpcomingMoviesFailure(throwable: Throwable) {
        _uiState.update {
            it.copy(
                error = throwable.toErrorMessage(),
                loadStates = it.loadStates + (ContentLoadType.UpcomingMovies to false)
            )
        }
    }

    private fun handleTopRatedMoviesLoading(topRatedMovies: List<MovieModel>?) {
        _uiState.update {
            it.copy(
                topRatedMovies = topRatedMovies?.map(MovieModel::toMovie).orEmpty(),
                loadStates = it.loadStates + (ContentLoadType.TopRatedMovies to true)
            )
        }
    }

    private fun handleTopRatedMoviesSuccess(topRatedMovies: List<MovieModel>) {
        _uiState.update {
            it.copy(
                topRatedMovies = topRatedMovies.map(MovieModel::toMovie),
                loadStates = it.loadStates + (ContentLoadType.TopRatedMovies to false)
            )
        }
    }

    private fun handleTopRatedMoviesFailure(throwable: Throwable) {
        _uiState.update {
            it.copy(
                error = throwable.toErrorMessage(),
                loadStates = it.loadStates + (ContentLoadType.TopRatedMovies to false)
            )
        }
    }

    private fun handleTopRatedTvShowsLoading(topRatedTvShows: List<TvShowModel>?) {
        _uiState.update {
            it.copy(
                topRatedTvShows = topRatedTvShows?.map(TvShowModel::toTvShow).orEmpty(),
                loadStates = it.loadStates + (ContentLoadType.TopRatedTvShows to true)
            )
        }
    }

    private fun handleTopRatedTvShowsSuccess(topRatedTvShows: List<TvShowModel>) {
        _uiState.update {
            it.copy(
                topRatedTvShows = topRatedTvShows.map(TvShowModel::toTvShow),
                loadStates = it.loadStates + (ContentLoadType.TopRatedTvShows to false)
            )
        }
    }

    private fun handleTopRatedTvShowsFailure(throwable: Throwable) {
        _uiState.update {
            it.copy(
                error = throwable.toErrorMessage(),
                loadStates = it.loadStates + (ContentLoadType.TopRatedTvShows to false)
            )
        }
    }

    private fun onRefresh() {
        upcomingMoviesJob.cancel()
        topRatedMoviesJob.cancel()
        topRatedTvShowsJob.cancel()
        upcomingMoviesJob = loadUpcomingMovies()
        topRatedMoviesJob = loadTopRatedMovies()
        topRatedTvShowsJob = loadTopRatedTvShows()
    }

    private fun onClearError() = _uiState.update { it.copy(error = null) }
}
