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

package com.maximillianleonov.cinemax.feature.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximillianleonov.cinemax.core.ui.common.EventHandler
import com.maximillianleonov.cinemax.core.ui.mapper.toMovieDetails
import com.maximillianleonov.cinemax.core.ui.mapper.toTvShowDetails
import com.maximillianleonov.cinemax.core.ui.util.handle
import com.maximillianleonov.cinemax.core.ui.util.toErrorMessage
import com.maximillianleonov.cinemax.domain.model.MovieDetailsModel
import com.maximillianleonov.cinemax.domain.model.TvShowDetailsModel
import com.maximillianleonov.cinemax.domain.usecase.GetWishlistMoviesUseCase
import com.maximillianleonov.cinemax.domain.usecase.GetWishlistTvShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val getWishlistMoviesUseCase: GetWishlistMoviesUseCase,
    private val getWishlistTvShowsUseCase: GetWishlistTvShowsUseCase
) : ViewModel(), EventHandler<WishlistEvent> {
    private val _uiState = MutableStateFlow(WishlistUiState())
    val uiState = _uiState.asStateFlow()

    private var moviesJob = getMoviesJob()
    private var tvShowsJob = getTvShowsJob()

    override fun onEvent(event: WishlistEvent) = when (event) {
        WishlistEvent.RefreshMovies -> onRefreshMovies()
        WishlistEvent.RefreshTvShows -> onRefreshTvShows()
        WishlistEvent.Retry -> onRetry()
        WishlistEvent.ClearError -> onClearError()
    }

    private fun onRefreshMovies() {
        moviesJob.cancel()
        moviesJob = getMoviesJob()
    }

    private fun onRefreshTvShows() {
        tvShowsJob.cancel()
        tvShowsJob = getTvShowsJob()
    }

    private fun onRetry() {
        onClearError()
        onRefreshMovies()
        onRefreshTvShows()
    }

    private fun onClearError() = _uiState.update { it.copy(error = null) }

    private fun getMoviesJob() = viewModelScope.launch {
        getWishlistMoviesUseCase().handle(
            onLoading = { movies ->
                _uiState.update {
                    it.copy(
                        movies = movies?.map(MovieDetailsModel::toMovieDetails).orEmpty(),
                        isMoviesLoading = true
                    )
                }
            },
            onSuccess = { movies ->
                _uiState.update {
                    it.copy(
                        movies = movies.map(MovieDetailsModel::toMovieDetails),
                        isMoviesLoading = false
                    )
                }
            },
            onFailure = ::handleFailure
        )
    }

    private fun getTvShowsJob() = viewModelScope.launch {
        getWishlistTvShowsUseCase().handle(
            onLoading = { tvShows ->
                _uiState.update {
                    it.copy(
                        tvShows = tvShows?.map(TvShowDetailsModel::toTvShowDetails).orEmpty(),
                        isTvShowsLoading = true
                    )
                }
            },
            onSuccess = { tvShows ->
                _uiState.update {
                    it.copy(
                        tvShows = tvShows.map(TvShowDetailsModel::toTvShowDetails),
                        isTvShowsLoading = false
                    )
                }
            },
            onFailure = ::handleFailure
        )
    }

    private fun handleFailure(error: Throwable) = _uiState.update {
        it.copy(error = error.toErrorMessage(), isMoviesLoading = false, isTvShowsLoading = false)
    }
}
