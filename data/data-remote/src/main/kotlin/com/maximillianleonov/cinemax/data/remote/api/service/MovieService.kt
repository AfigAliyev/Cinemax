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

package com.maximillianleonov.cinemax.data.remote.api.service

import com.maximillianleonov.cinemax.core.data.util.Constants
import com.maximillianleonov.cinemax.core.domain.result.Result
import com.maximillianleonov.cinemax.data.remote.dto.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET(Constants.Remote.UPCOMING_MOVIE_PATH)
    suspend fun getUpcomingMovies(
        @Query(Constants.Fields.PAGE) page: Int = Constants.Remote.DEFAULT_PAGE
    ): Result<MovieResponseDto>

    @GET(Constants.Remote.TOP_RATED_MOVIE_PATH)
    suspend fun getTopRatedMovies(
        @Query(Constants.Fields.PAGE) page: Int = Constants.Remote.DEFAULT_PAGE
    ): Result<MovieResponseDto>

    @GET(Constants.Remote.POPULAR_MOVIE_PATH)
    suspend fun getPopularMovies(
        @Query(Constants.Fields.PAGE) page: Int = Constants.Remote.DEFAULT_PAGE
    ): Result<MovieResponseDto>
}
