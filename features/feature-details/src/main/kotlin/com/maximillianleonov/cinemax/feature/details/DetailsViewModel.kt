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

package com.maximillianleonov.cinemax.feature.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximillianleonov.cinemax.core.ui.common.ContentType
import com.maximillianleonov.cinemax.core.ui.common.EventHandler
import com.maximillianleonov.cinemax.core.ui.mapper.toMovieDetails
import com.maximillianleonov.cinemax.core.ui.mapper.toTvShowDetails
import com.maximillianleonov.cinemax.core.ui.util.handle
import com.maximillianleonov.cinemax.core.ui.util.toErrorMessage
import com.maximillianleonov.cinemax.domain.model.MovieDetailsModel
import com.maximillianleonov.cinemax.domain.model.TvShowDetailsModel
import com.maximillianleonov.cinemax.domain.usecase.GetMovieDetailsUseCase
import com.maximillianleonov.cinemax.domain.usecase.GetTvShowDetailsUseCase
import com.maximillianleonov.cinemax.feature.details.navigation.DetailsDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("TooManyFunctions")
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), EventHandler<DetailsEvent> {
    private val _uiState = MutableStateFlow(getInitialUiState(savedStateHandle))
    val uiState = _uiState.asStateFlow()

    private var contentJob = loadContent()

    override fun onEvent(event: DetailsEvent) = when (event) {
        DetailsEvent.Refresh -> onRefresh()
        DetailsEvent.Retry -> onRetry()
        DetailsEvent.ClearError -> onClearError()
    }

    private fun getInitialUiState(savedStateHandle: SavedStateHandle): DetailsUiState {
        val contentType = DetailsDestination.fromSavedStateHandle(savedStateHandle)
        return DetailsUiState(contentType = contentType)
    }

    private fun loadContent() = when (val contentType = uiState.value.contentType) {
        is ContentType.Details.Movie -> loadMovie(contentType.id)
        is ContentType.Details.TvShow -> loadTvShow(contentType.id)
    }

    private fun onRefresh() {
        contentJob.cancel()
        contentJob = loadContent()
    }

    private fun onRetry() {
        onClearError()
        onRefresh()
    }

    private fun onClearError() = _uiState.update { it.copy(error = null) }

    private fun loadMovie(id: Int) = viewModelScope.launch {
        getMovieDetailsUseCase(id).handle(
            onLoading = ::handleMovieLoading,
            onSuccess = ::handleMovieSuccess,
            onFailure = ::handleFailure
        )
    }

    private fun loadTvShow(id: Int) = viewModelScope.launch {
        getTvShowDetailsUseCase(id).handle(
            onLoading = ::handleTvShowLoading,
            onSuccess = ::handleTvShowSuccess,
            onFailure = ::handleFailure
        )
    }

    private fun handleMovieLoading(movie: MovieDetailsModel?) =
        _uiState.update { it.copy(movie = movie?.toMovieDetails(), isLoading = true) }

    private fun handleTvShowLoading(tvShow: TvShowDetailsModel?) =
        _uiState.update { it.copy(tvShow = tvShow?.toTvShowDetails(), isLoading = true) }

    private fun handleMovieSuccess(movie: MovieDetailsModel?) =
        _uiState.update { it.copy(movie = movie?.toMovieDetails(), isLoading = false) }

    private fun handleTvShowSuccess(tvShow: TvShowDetailsModel?) =
        _uiState.update { it.copy(tvShow = tvShow?.toTvShowDetails(), isLoading = false) }

    private fun handleFailure(error: Throwable) =
        _uiState.update { it.copy(error = error.toErrorMessage(), isLoading = false) }
}
