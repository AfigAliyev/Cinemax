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

package com.maximillianleonov.cinemax.feature.list.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.maximillianleonov.cinemax.core.presentation.common.ContentType
import com.maximillianleonov.cinemax.core.presentation.common.EventHandler
import com.maximillianleonov.cinemax.core.presentation.mapper.pagingMap
import com.maximillianleonov.cinemax.core.presentation.mapper.toMovie
import com.maximillianleonov.cinemax.domain.model.MovieModel
import com.maximillianleonov.cinemax.domain.usecase.GetUpcomingMoviesPagingUseCase
import com.maximillianleonov.cinemax.feature.list.presentation.navigation.ListDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getUpcomingMoviesPagingUseCase: GetUpcomingMoviesPagingUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), EventHandler<ListEvent> {
    private val _uiState = MutableStateFlow(getInitialUiState(savedStateHandle = savedStateHandle))
    val uiState = _uiState.asStateFlow()

    override fun onEvent(event: ListEvent) = Unit

    private fun getInitialUiState(savedStateHandle: SavedStateHandle): ListUiState {
        val contentType = ListDestination.fromSavedStateHandle(savedStateHandle)

        val movies = when (contentType) {
            ContentType.List.Upcoming -> getUpcomingMoviesPagingUseCase()
        }.pagingMap(MovieModel::toMovie).cachedIn(viewModelScope)

        return ListUiState(
            contentType = contentType,
            movies = movies
        )
    }
}
