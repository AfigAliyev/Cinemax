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

package com.maximillianleonov.cinemax.domain.repository

import com.maximillianleonov.cinemax.core.domain.result.Result
import com.maximillianleonov.cinemax.domain.model.MovieDetailsModel
import com.maximillianleonov.cinemax.domain.model.TvShowDetailsModel
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {
    fun getMovieById(id: Int): Flow<Result<MovieDetailsModel?>>
    fun getTvShowById(id: Int): Flow<Result<TvShowDetailsModel?>>
}
