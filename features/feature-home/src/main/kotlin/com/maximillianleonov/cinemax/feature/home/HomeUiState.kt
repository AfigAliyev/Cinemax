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

import com.maximillianleonov.cinemax.core.ui.common.ContentType
import com.maximillianleonov.cinemax.core.ui.common.State
import com.maximillianleonov.cinemax.core.ui.model.ErrorMessage
import com.maximillianleonov.cinemax.core.ui.model.Movie
import com.maximillianleonov.cinemax.core.ui.model.TvShow

data class HomeUiState(
    val movies: Map<ContentType.Main, List<Movie>> = emptyMap(),
    val tvShows: Map<ContentType.Main, List<TvShow>> = emptyMap(),
    val loadStates: Map<ContentType.Main, Boolean> = emptyMap(),
    val error: ErrorMessage? = null
) : State {
    val isLoading: Boolean get() = loadStates.values.any { it }
    val isError: Boolean get() = error != null
    val isOfflineModeAvailable: Boolean
        get() = movies.values.all(List<Movie>::isNotEmpty) &&
            tvShows.values.all(List<TvShow>::isNotEmpty)

    fun requireError() = checkNotNull(error)
}
