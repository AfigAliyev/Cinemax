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

package com.maximillianleonov.cinemax.data.remote.source

import com.maximillianleonov.cinemax.core.data.util.Constants
import com.maximillianleonov.cinemax.core.domain.result.Result
import com.maximillianleonov.cinemax.core.domain.result.isFailure
import com.maximillianleonov.cinemax.core.domain.result.isSuccess
import com.maximillianleonov.cinemax.data.remote.api.CinemaxApi
import com.maximillianleonov.cinemax.data.remote.dto.movie.MovieDetailsDto
import com.maximillianleonov.cinemax.data.remote.dto.tvshow.TvShowDetailsDto
import javax.inject.Inject

class DetailsRemoteDataSource @Inject constructor(api: CinemaxApi) {
    private val service = api.detailsService

    suspend fun getMovieById(id: Int) = service.getMovieById(id)
    suspend fun getTvShowById(id: Int) = service.getTvShowById(id)

    suspend fun getMoviesByIds(ids: List<Int>): Result<List<MovieDetailsDto>> {
        val movies = ids.map { id ->
            val response = service.getMovieById(id)

            when {
                response.isSuccess() -> response.value
                response.isFailure() -> return Result.Failure.Error(response.error)
                else -> error("${Constants.Messages.UNHANDLED_STATE} $response")
            }
        }

        return Result.Success.Value(movies)
    }

    suspend fun getTvShowsByIds(ids: List<Int>): Result<List<TvShowDetailsDto>> {
        val tvShows = ids.map { id ->
            val response = service.getTvShowById(id)

            when {
                response.isSuccess() -> response.value
                response.isFailure() -> return Result.Failure.Error(response.error)
                else -> error("${Constants.Messages.UNHANDLED_STATE} $response")
            }
        }

        return Result.Success.Value(tvShows)
    }
}
