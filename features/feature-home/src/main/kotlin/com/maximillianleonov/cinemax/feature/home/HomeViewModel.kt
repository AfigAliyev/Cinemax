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

package com.maximillianleonov.cinemax.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximillianleonov.cinemax.core.ui.common.ContentType
import com.maximillianleonov.cinemax.core.ui.common.EventHandler
import com.maximillianleonov.cinemax.core.ui.mapper.toMovie
import com.maximillianleonov.cinemax.core.ui.mapper.toTvShow
import com.maximillianleonov.cinemax.core.ui.util.handle
import com.maximillianleonov.cinemax.core.ui.util.toErrorMessage
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
class HomeViewModel @Inject constructor(
    private val movieUseCases: MovieUseCases,
    private val tvShowUseCases: TvShowUseCases
) : ViewModel(), EventHandler<HomeEvent> {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private var contentJobs = getContentJobs()

    override fun onEvent(event: HomeEvent) = when (event) {
        HomeEvent.Refresh -> onRefresh()
        HomeEvent.ClearError -> onClearError()
    }

    private fun getContentJobs() = mapOf(
        ContentType.Main.UpcomingMovies to loadUpcomingMovies(),
        ContentType.Main.TopRatedMovies to loadTopRatedMovies(),
        ContentType.Main.TopRatedTvShows to loadTopRatedTvShows(),
        ContentType.Main.PopularMovies to loadPopularMovies(),
        ContentType.Main.PopularTvShows to loadPopularTvShows(),
        ContentType.Main.NowPlayingMovies to loadNowPlayingMovies(),
        ContentType.Main.NowPlayingTvShows to loadNowPlayingTvShows()
    )

    private fun onRefresh() {
        contentJobs.values.forEach(Job::cancel)
        contentJobs = getContentJobs()
    }

    private fun onClearError() = _uiState.update { it.copy(error = null) }

    private fun loadUpcomingMovies() = viewModelScope.launch {
        movieUseCases.getUpcomingMoviesUseCase().handle(
            onLoading = ::handleUpcomingMoviesLoading,
            onSuccess = ::handleUpcomingMoviesSuccess,
            onFailure = ::handleUpcomingMoviesFailure
        )
    }

    private fun loadTopRatedMovies() = viewModelScope.launch {
        movieUseCases.getTopRatedMoviesUseCase().handle(
            onLoading = ::handleTopRatedMoviesLoading,
            onSuccess = ::handleTopRatedMoviesSuccess,
            onFailure = ::handleTopRatedMoviesFailure
        )
    }

    private fun loadTopRatedTvShows() = viewModelScope.launch {
        tvShowUseCases.getTopRatedTvShowsUseCase().handle(
            onLoading = ::handleTopRatedTvShowsLoading,
            onSuccess = ::handleTopRatedTvShowsSuccess,
            onFailure = ::handleTopRatedTvShowsFailure
        )
    }

    private fun loadPopularMovies() = viewModelScope.launch {
        movieUseCases.getPopularMoviesUseCase().handle(
            onLoading = ::handlePopularMoviesLoading,
            onSuccess = ::handlePopularMoviesSuccess,
            onFailure = ::handlePopularMoviesFailure
        )
    }

    private fun loadPopularTvShows() = viewModelScope.launch {
        tvShowUseCases.getPopularTvShowsUseCase().handle(
            onLoading = ::handlePopularTvShowsLoading,
            onSuccess = ::handlePopularTvShowsSuccess,
            onFailure = ::handlePopularTvShowsFailure
        )
    }

    private fun loadNowPlayingMovies() = viewModelScope.launch {
        movieUseCases.getNowPlayingMoviesUseCase().handle(
            onLoading = ::handleNowPlayingMoviesLoading,
            onSuccess = ::handleNowPlayingMoviesSuccess,
            onFailure = ::handleNowPlayingMoviesFailure
        )
    }

    private fun loadNowPlayingTvShows() = viewModelScope.launch {
        tvShowUseCases.getNowPlayingTvShowsUseCase().handle(
            onLoading = ::handleNowPlayingTvShowsLoading,
            onSuccess = ::handleNowPlayingTvShowsSuccess,
            onFailure = ::handleNowPlayingTvShowsFailure
        )
    }

    private fun handleUpcomingMoviesLoading(movies: List<MovieModel>?) =
        handleLoading(contentLoadType = ContentType.Main.UpcomingMovies, movies = movies)

    private fun handleUpcomingMoviesSuccess(movies: List<MovieModel>) =
        handleSuccess(contentLoadType = ContentType.Main.UpcomingMovies, movies = movies)

    private fun handleUpcomingMoviesFailure(throwable: Throwable) =
        handleFailure(contentLoadType = ContentType.Main.UpcomingMovies, error = throwable)

    private fun handleTopRatedMoviesLoading(movies: List<MovieModel>?) =
        handleLoading(contentLoadType = ContentType.Main.TopRatedMovies, movies = movies)

    private fun handleTopRatedMoviesSuccess(movies: List<MovieModel>) =
        handleSuccess(contentLoadType = ContentType.Main.TopRatedMovies, movies = movies)

    private fun handleTopRatedMoviesFailure(throwable: Throwable) =
        handleFailure(contentLoadType = ContentType.Main.TopRatedMovies, error = throwable)

    private fun handleTopRatedTvShowsLoading(tvShows: List<TvShowModel>?) =
        handleLoading(contentLoadType = ContentType.Main.TopRatedTvShows, tvShows = tvShows)

    private fun handleTopRatedTvShowsSuccess(tvShows: List<TvShowModel>) =
        handleSuccess(contentLoadType = ContentType.Main.TopRatedTvShows, tvShows = tvShows)

    private fun handleTopRatedTvShowsFailure(throwable: Throwable) =
        handleFailure(contentLoadType = ContentType.Main.TopRatedTvShows, error = throwable)

    private fun handlePopularMoviesLoading(movies: List<MovieModel>?) =
        handleLoading(contentLoadType = ContentType.Main.PopularMovies, movies = movies)

    private fun handlePopularMoviesSuccess(movies: List<MovieModel>) =
        handleSuccess(contentLoadType = ContentType.Main.PopularMovies, movies = movies)

    private fun handlePopularMoviesFailure(throwable: Throwable) =
        handleFailure(contentLoadType = ContentType.Main.PopularMovies, error = throwable)

    private fun handlePopularTvShowsLoading(tvShows: List<TvShowModel>?) =
        handleLoading(contentLoadType = ContentType.Main.PopularTvShows, tvShows = tvShows)

    private fun handlePopularTvShowsSuccess(tvShows: List<TvShowModel>) =
        handleSuccess(contentLoadType = ContentType.Main.PopularTvShows, tvShows = tvShows)

    private fun handlePopularTvShowsFailure(throwable: Throwable) =
        handleFailure(contentLoadType = ContentType.Main.PopularTvShows, error = throwable)

    private fun handleNowPlayingMoviesLoading(movies: List<MovieModel>?) =
        handleLoading(contentLoadType = ContentType.Main.NowPlayingMovies, movies = movies)

    private fun handleNowPlayingMoviesSuccess(movies: List<MovieModel>) =
        handleSuccess(contentLoadType = ContentType.Main.NowPlayingMovies, movies = movies)

    private fun handleNowPlayingMoviesFailure(throwable: Throwable) =
        handleFailure(contentLoadType = ContentType.Main.NowPlayingMovies, error = throwable)

    private fun handleNowPlayingTvShowsLoading(tvShows: List<TvShowModel>?) =
        handleLoading(contentLoadType = ContentType.Main.NowPlayingTvShows, tvShows = tvShows)

    private fun handleNowPlayingTvShowsSuccess(tvShows: List<TvShowModel>) =
        handleSuccess(contentLoadType = ContentType.Main.NowPlayingTvShows, tvShows = tvShows)

    private fun handleNowPlayingTvShowsFailure(throwable: Throwable) =
        handleFailure(contentLoadType = ContentType.Main.NowPlayingTvShows, error = throwable)

    @JvmName("handleMoviesLoading")
    private fun handleLoading(contentLoadType: ContentType.Main, movies: List<MovieModel>?) =
        _uiState.update {
            it.copy(
                movies = it.movies + (
                    contentLoadType to movies?.map(MovieModel::toMovie).orEmpty()
                    ),
                loadStates = it.loadStates + (contentLoadType to true)
            )
        }

    @JvmName("handleTvShowsLoading")
    private fun handleLoading(contentLoadType: ContentType.Main, tvShows: List<TvShowModel>?) =
        _uiState.update {
            it.copy(
                tvShows = it.tvShows + (
                    contentLoadType to tvShows?.map(TvShowModel::toTvShow).orEmpty()
                    ),
                loadStates = it.loadStates + (contentLoadType to true)
            )
        }

    @JvmName("handleMoviesSuccess")
    private fun handleSuccess(contentLoadType: ContentType.Main, movies: List<MovieModel>) =
        _uiState.update {
            it.copy(
                movies = it.movies + (contentLoadType to movies.map(MovieModel::toMovie)),
                loadStates = it.loadStates + (contentLoadType to false)
            )
        }

    @JvmName("handleTvShowsSuccess")
    private fun handleSuccess(contentLoadType: ContentType.Main, tvShows: List<TvShowModel>) =
        _uiState.update {
            it.copy(
                tvShows = it.tvShows + (contentLoadType to tvShows.map(TvShowModel::toTvShow)),
                loadStates = it.loadStates + (contentLoadType to false)
            )
        }

    private fun handleFailure(contentLoadType: ContentType.Main, error: Throwable) =
        _uiState.update {
            it.copy(
                loadStates = it.loadStates + (contentLoadType to false),
                error = error.toErrorMessage()
            )
        }
}
