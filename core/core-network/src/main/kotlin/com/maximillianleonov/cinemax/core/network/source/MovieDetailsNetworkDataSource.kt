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

package com.maximillianleonov.cinemax.core.network.source

import com.maximillianleonov.cinemax.core.common.result.CinemaxResult
import com.maximillianleonov.cinemax.core.common.result.isFailure
import com.maximillianleonov.cinemax.core.common.result.isSuccess
import com.maximillianleonov.cinemax.core.network.api.service.MovieService
import com.maximillianleonov.cinemax.core.network.model.movie.NetworkMovieDetails
import com.maximillianleonov.cinemax.core.network.util.Constants
import com.maximillianleonov.cinemax.core.network.util.MESSAGE_UNHANDLED_STATE
import javax.inject.Inject

class MovieDetailsNetworkDataSource @Inject constructor(private val movieService: MovieService) {
    suspend fun getById(
        id: Int,
        appendToResponse: String = Constants.Fields.DETAILS_APPEND_TO_RESPONSE
    ): CinemaxResult<NetworkMovieDetails> = movieService.getDetailsById(id, appendToResponse)

    suspend fun getByIds(
        ids: List<Int>,
        appendToResponse: String = Constants.Fields.DETAILS_APPEND_TO_RESPONSE
    ): CinemaxResult<List<NetworkMovieDetails>> {
        val movies = ids.map { id ->
            val response = movieService.getDetailsById(id, appendToResponse)

            when {
                response.isSuccess() -> response.value
                response.isFailure() -> return CinemaxResult.failure(response.error)
                else -> error("$MESSAGE_UNHANDLED_STATE $response")
            }
        }

        return CinemaxResult.success(movies)
    }
}
