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

import com.maximillianleonov.cinemax.core.presentation.common.State
import com.maximillianleonov.cinemax.core.presentation.model.ErrorMessage
import com.maximillianleonov.cinemax.core.presentation.model.Movie
import com.maximillianleonov.cinemax.core.presentation.model.TvShow
import com.maximillianleonov.cinemax.feature.home.presentation.common.ContentLoadType

data class HomeUiState(
    val upcomingMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val topRatedTvShows: List<TvShow> = emptyList(),
    val popularMovies: List<Movie> = emptyList(),
    val popularTvShows: List<TvShow> = emptyList(),
    val loadStates: Map<ContentLoadType, Boolean> = emptyMap(),
    val error: ErrorMessage? = null
) : State {
    val isLoading: Boolean get() = loadStates.values.any { it }
}
