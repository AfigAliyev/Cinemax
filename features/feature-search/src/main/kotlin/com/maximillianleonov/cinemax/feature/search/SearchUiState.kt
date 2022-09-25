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

import androidx.paging.PagingData
import com.maximillianleonov.cinemax.core.model.MediaType
import com.maximillianleonov.cinemax.core.model.Movie
import com.maximillianleonov.cinemax.core.model.TvShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchUiState(
    val query: String = "",
    val isSearching: Boolean = false,
    val searchMovies: Flow<PagingData<Movie>> = emptyFlow(),
    val searchTvShows: Flow<PagingData<TvShow>> = emptyFlow(),
    val movies: Map<MediaType.Movie, List<Movie>> = emptyMap(),
    val tvShows: Map<MediaType.TvShow, List<TvShow>> = emptyMap(),
    val loadStates: Map<MediaType, Boolean> = emptyMap(),
    val error: Throwable? = null,
    val isOfflineModeAvailable: Boolean = false
)

internal val SearchUiState.isLoading: Boolean get() = loadStates.values.any { it }
