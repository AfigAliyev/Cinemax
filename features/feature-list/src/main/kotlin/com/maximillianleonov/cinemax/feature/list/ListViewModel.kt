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
import com.maximillianleonov.cinemax.core.domain.model.MovieModel
import com.maximillianleonov.cinemax.core.domain.model.TvShowModel
import com.maximillianleonov.cinemax.core.domain.usecase.GetMoviesPagingUseCase
import com.maximillianleonov.cinemax.core.domain.usecase.GetTvShowsPagingUseCase
import com.maximillianleonov.cinemax.core.ui.mapper.asMediaTypeModel
import com.maximillianleonov.cinemax.core.ui.mapper.asMovie
import com.maximillianleonov.cinemax.core.ui.mapper.asMovieMediaType
import com.maximillianleonov.cinemax.core.ui.mapper.asTvShow
import com.maximillianleonov.cinemax.core.ui.mapper.asTvShowMediaType
import com.maximillianleonov.cinemax.core.ui.mapper.pagingMap
import com.maximillianleonov.cinemax.feature.list.navigation.ListDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getMoviesPagingUseCase: GetMoviesPagingUseCase,
    private val getTvShowsPagingUseCase: GetTvShowsPagingUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(getInitialUiState(savedStateHandle))
    val uiState = _uiState.asStateFlow()

    private fun getInitialUiState(savedStateHandle: SavedStateHandle): ListUiState {
        val mediaType = ListDestination.fromSavedStateHandle(savedStateHandle)

        val movies = getMoviesPagingUseCase(mediaType.asMovieMediaType().asMediaTypeModel())
            .pagingMap(MovieModel::asMovie)
            .cachedIn(viewModelScope)

        val tvShows = mediaType.asTvShowMediaType()?.let { tvShowMediaType ->
            getTvShowsPagingUseCase(tvShowMediaType.asMediaTypeModel())
                .pagingMap(TvShowModel::asTvShow)
                .cachedIn(viewModelScope)
        } ?: emptyFlow()

        return ListUiState(
            mediaType = mediaType,
            movies = movies,
            tvShows = tvShows
        )
    }
}
