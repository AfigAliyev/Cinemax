/*
 * Copyright 2022 Afig Aliyev
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
import com.maximillianleonov.cinemax.core.common.result.handle
import com.maximillianleonov.cinemax.core.domain.model.MovieModel
import com.maximillianleonov.cinemax.core.domain.model.TvShowModel
import com.maximillianleonov.cinemax.core.domain.usecase.GetMoviesUseCase
import com.maximillianleonov.cinemax.core.domain.usecase.GetTvShowsUseCase
import com.maximillianleonov.cinemax.core.model.MediaType
import com.maximillianleonov.cinemax.core.model.Movie
import com.maximillianleonov.cinemax.core.model.TvShow
import com.maximillianleonov.cinemax.core.ui.common.EventHandler
import com.maximillianleonov.cinemax.core.ui.mapper.asMediaTypeModel
import com.maximillianleonov.cinemax.core.ui.mapper.asMovie
import com.maximillianleonov.cinemax.core.ui.mapper.asTvShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getTvShowsUseCase: GetTvShowsUseCase
) : ViewModel(), EventHandler<HomeEvent> {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadContent()
    }

    override fun onEvent(event: HomeEvent) = when (event) {
        HomeEvent.Refresh -> onRefresh()
        HomeEvent.Retry -> onRetry()
        HomeEvent.ClearError -> onClearError()
    }

    private fun loadContent() {
        val movieMediaTypes = listOf(
            MediaType.Movie.Upcoming,
            MediaType.Movie.TopRated,
            MediaType.Movie.Popular,
            MediaType.Movie.NowPlaying
        )
        movieMediaTypes.forEach(::loadMovies)

        val tvShowMediaTypes = listOf(
            MediaType.TvShow.TopRated,
            MediaType.TvShow.Popular,
            MediaType.TvShow.NowPlaying
        )
        tvShowMediaTypes.forEach(::loadTvShows)
    }

    private fun onRefresh() {
        viewModelScope.coroutineContext.cancelChildren()
        loadContent()
    }

    private fun onRetry() {
        onClearError()
        onRefresh()
    }

    private fun onClearError() = _uiState.update { it.copy(error = null) }

    private fun loadMovies(mediaType: MediaType.Movie) = viewModelScope.launch {
        getMoviesUseCase(mediaType.asMediaTypeModel()).handle {
            onLoading { movies ->
                _uiState.update {
                    it.copy(
                        movies = it.movies + (
                            mediaType to movies?.map(MovieModel::asMovie).orEmpty()
                            ),
                        loadStates = it.loadStates + (mediaType to true)
                    )
                }
            }
            onSuccess { movies ->
                _uiState.update {
                    it.copy(
                        movies = it.movies + (mediaType to movies.map(MovieModel::asMovie)),
                        loadStates = it.loadStates + (mediaType to false)
                    )
                }
            }
            onFailure { error -> handleFailure(error = error, mediaType = mediaType) }
        }
    }

    private fun loadTvShows(mediaType: MediaType.TvShow) = viewModelScope.launch {
        getTvShowsUseCase(mediaType.asMediaTypeModel()).handle {
            onLoading { tvShows ->
                _uiState.update {
                    it.copy(
                        tvShows = it.tvShows + (
                            mediaType to tvShows?.map(TvShowModel::asTvShow).orEmpty()
                            ),
                        loadStates = it.loadStates + (mediaType to true)
                    )
                }
            }
            onSuccess { tvShows ->
                _uiState.update {
                    it.copy(
                        tvShows = it.tvShows + (mediaType to tvShows.map(TvShowModel::asTvShow)),
                        loadStates = it.loadStates + (mediaType to false)
                    )
                }
            }
            onFailure { error -> handleFailure(error = error, mediaType = mediaType) }
        }
    }

    private fun handleFailure(error: Throwable, mediaType: MediaType) =
        _uiState.update {
            it.copy(
                error = error,
                isOfflineModeAvailable = it.movies.values.all(List<Movie>::isNotEmpty) &&
                    it.tvShows.values.all(List<TvShow>::isNotEmpty),
                loadStates = it.loadStates + (mediaType to false)
            )
        }
}
