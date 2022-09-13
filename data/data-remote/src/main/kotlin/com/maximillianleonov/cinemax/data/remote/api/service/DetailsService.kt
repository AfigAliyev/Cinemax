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
import com.maximillianleonov.cinemax.core.data.util.Constants.Fields.buildAppendToResponse
import com.maximillianleonov.cinemax.core.domain.result.Result
import com.maximillianleonov.cinemax.data.remote.dto.movie.MovieDetailsDto
import com.maximillianleonov.cinemax.data.remote.dto.tvshow.TvShowDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailsService {
    @GET(Constants.Remote.DETAILS_MOVIE_PATH)
    suspend fun getMovieById(
        @Path(Constants.Fields.ID) id: Int,
        @Query(Constants.Fields.APPEND_TO_RESPONSE) appendToResponse: String = buildAppendToResponse(
            Constants.Fields.CREDITS
        )
    ): Result<MovieDetailsDto>

    @GET(Constants.Remote.DETAILS_TV_SHOW_PATH)
    suspend fun getTvShowById(
        @Path(Constants.Fields.ID) id: Int,
        @Query(Constants.Fields.APPEND_TO_RESPONSE) appendToResponse: String = buildAppendToResponse(
            Constants.Fields.CREDITS
        )
    ): Result<TvShowDetailsDto>
}
