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

data class HomeUiState(
    val upcomingMovies: List<Movie> = emptyList(),
    val isUpcomingMoviesLoading: Boolean = false,
    val topRatedMovies: List<Movie> = emptyList(),
    val isTopRatedMoviesLoading: Boolean = false,
    val topRatedTvShows: List<TvShow> = emptyList(),
    val isTopRatedTvShowsLoading: Boolean = false,
    val error: ErrorMessage? = null
) : State
