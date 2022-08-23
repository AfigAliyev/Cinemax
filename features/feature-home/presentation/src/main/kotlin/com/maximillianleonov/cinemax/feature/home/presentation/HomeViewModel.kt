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
import com.maximillianleonov.cinemax.domain.usecase.GetNowPlayingMoviesUseCase
import com.maximillianleonov.cinemax.domain.usecase.GetNowPlayingTvShowsUseCase
import com.maximillianleonov.cinemax.domain.usecase.GetPopularMoviesUseCase
import com.maximillianleonov.cinemax.domain.usecase.GetPopularTvShowsUseCase
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

@Suppress("TooManyFunctions", "LongParameterList")
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getTopRatedTvShowsUseCase: GetTopRatedTvShowsUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getPopularTvShowsUseCase: GetPopularTvShowsUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getNowPlayingTvShowsUseCase: GetNowPlayingTvShowsUseCase
) : ViewModel(), EventHandler<HomeEvent> {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private var upcomingMoviesJob = loadUpcomingMovies()
    private var topRatedMoviesJob = loadTopRatedMovies()
    private var topRatedTvShowsJob = loadTopRatedTvShows()
    private var popularMoviesJob = loadPopularMovies()
    private var popularTvShowsJob = loadPopularTvShows()
    private var nowPlayingMoviesJob = loadNowPlayingMovies()
    private var nowPlayingTvShowsJob = loadNowPlayingTvShows()

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

    private fun loadPopularMovies() = viewModelScope.launch {
        getPopularMoviesUseCase().handle(
            onLoading = ::handlePopularMoviesLoading,
            onSuccess = ::handlePopularMoviesSuccess,
            onFailure = ::handlePopularMoviesFailure
        )
    }

    private fun loadPopularTvShows() = viewModelScope.launch {
        getPopularTvShowsUseCase().handle(
            onLoading = ::handlePopularTvShowsLoading,
            onSuccess = ::handlePopularTvShowsSuccess,
            onFailure = ::handlePopularTvShowsFailure
        )
    }

    private fun loadNowPlayingMovies() = viewModelScope.launch {
        getNowPlayingMoviesUseCase().handle(
            onLoading = ::handleNowPlayingMoviesLoading,
            onSuccess = ::handleNowPlayingMoviesSuccess,
            onFailure = ::handleNowPlayingMoviesFailure
        )
    }

    private fun loadNowPlayingTvShows() = viewModelScope.launch {
        getNowPlayingTvShowsUseCase().handle(
            onLoading = ::handleNowPlayingTvShowsLoading,
            onSuccess = ::handleNowPlayingTvShowsSuccess,
            onFailure = ::handleNowPlayingTvShowsFailure
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

    private fun handlePopularMoviesLoading(popularMovies: List<MovieModel>?) {
        _uiState.update {
            it.copy(
                popularMovies = popularMovies?.map(MovieModel::toMovie).orEmpty(),
                loadStates = it.loadStates + (ContentLoadType.PopularMovies to true)
            )
        }
    }

    private fun handlePopularMoviesSuccess(popularMovies: List<MovieModel>) {
        _uiState.update {
            it.copy(
                popularMovies = popularMovies.map(MovieModel::toMovie),
                loadStates = it.loadStates + (ContentLoadType.PopularMovies to false)
            )
        }
    }

    private fun handlePopularMoviesFailure(throwable: Throwable) {
        _uiState.update {
            it.copy(
                error = throwable.toErrorMessage(),
                loadStates = it.loadStates + (ContentLoadType.PopularMovies to false)
            )
        }
    }

    private fun handlePopularTvShowsLoading(popularTvShows: List<TvShowModel>?) {
        _uiState.update {
            it.copy(
                popularTvShows = popularTvShows?.map(TvShowModel::toTvShow).orEmpty(),
                loadStates = it.loadStates + (ContentLoadType.PopularTvShows to true)
            )
        }
    }

    private fun handlePopularTvShowsSuccess(popularTvShows: List<TvShowModel>) {
        _uiState.update {
            it.copy(
                popularTvShows = popularTvShows.map(TvShowModel::toTvShow),
                loadStates = it.loadStates + (ContentLoadType.PopularTvShows to false)
            )
        }
    }

    private fun handlePopularTvShowsFailure(throwable: Throwable) {
        _uiState.update {
            it.copy(
                error = throwable.toErrorMessage(),
                loadStates = it.loadStates + (ContentLoadType.PopularTvShows to false)
            )
        }
    }

    private fun handleNowPlayingMoviesLoading(nowPlayingMovies: List<MovieModel>?) {
        _uiState.update {
            it.copy(
                nowPlayingMovies = nowPlayingMovies?.map(MovieModel::toMovie).orEmpty(),
                loadStates = it.loadStates + (ContentLoadType.NowPlayingMovies to true)
            )
        }
    }

    private fun handleNowPlayingMoviesSuccess(nowPlayingMovies: List<MovieModel>) {
        _uiState.update {
            it.copy(
                nowPlayingMovies = nowPlayingMovies.map(MovieModel::toMovie),
                loadStates = it.loadStates + (ContentLoadType.NowPlayingMovies to false)
            )
        }
    }

    private fun handleNowPlayingMoviesFailure(throwable: Throwable) {
        _uiState.update {
            it.copy(
                error = throwable.toErrorMessage(),
                loadStates = it.loadStates + (ContentLoadType.NowPlayingMovies to false)
            )
        }
    }

    private fun handleNowPlayingTvShowsLoading(nowPlayingTvShows: List<TvShowModel>?) {
        _uiState.update {
            it.copy(
                nowPlayingTvShows = nowPlayingTvShows?.map(TvShowModel::toTvShow).orEmpty(),
                loadStates = it.loadStates + (ContentLoadType.NowPlayingTvShows to true)
            )
        }
    }

    private fun handleNowPlayingTvShowsSuccess(nowPlayingTvShows: List<TvShowModel>) {
        _uiState.update {
            it.copy(
                nowPlayingTvShows = nowPlayingTvShows.map(TvShowModel::toTvShow),
                loadStates = it.loadStates + (ContentLoadType.NowPlayingTvShows to false)
            )
        }
    }

    private fun handleNowPlayingTvShowsFailure(throwable: Throwable) {
        _uiState.update {
            it.copy(
                error = throwable.toErrorMessage(),
                loadStates = it.loadStates + (ContentLoadType.NowPlayingTvShows to false)
            )
        }
    }

    private fun onRefresh() {
        upcomingMoviesJob.cancel()
        topRatedMoviesJob.cancel()
        topRatedTvShowsJob.cancel()
        popularMoviesJob.cancel()
        popularTvShowsJob.cancel()
        nowPlayingMoviesJob.cancel()
        nowPlayingTvShowsJob.cancel()
        upcomingMoviesJob = loadUpcomingMovies()
        topRatedMoviesJob = loadTopRatedMovies()
        topRatedTvShowsJob = loadTopRatedTvShows()
        popularMoviesJob = loadPopularMovies()
        popularTvShowsJob = loadPopularTvShows()
        nowPlayingMoviesJob = loadNowPlayingMovies()
        nowPlayingTvShowsJob = loadNowPlayingTvShows()
    }

    private fun onClearError() = _uiState.update { it.copy(error = null) }
}
