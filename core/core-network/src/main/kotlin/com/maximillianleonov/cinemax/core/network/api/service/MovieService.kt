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

package com.maximillianleonov.cinemax.core.network.api.service

import com.maximillianleonov.cinemax.core.common.result.CinemaxResult
import com.maximillianleonov.cinemax.core.network.model.movie.NetworkMovieDetails
import com.maximillianleonov.cinemax.core.network.model.response.MovieResponseDto
import com.maximillianleonov.cinemax.core.network.util.Constants.DEFAULT_PAGE
import com.maximillianleonov.cinemax.core.network.util.Constants.Fields.APPEND_TO_RESPONSE
import com.maximillianleonov.cinemax.core.network.util.Constants.Fields.DETAILS_APPEND_TO_RESPONSE
import com.maximillianleonov.cinemax.core.network.util.Constants.Fields.ID
import com.maximillianleonov.cinemax.core.network.util.Constants.Fields.LANGUAGE
import com.maximillianleonov.cinemax.core.network.util.Constants.Fields.PAGE
import com.maximillianleonov.cinemax.core.network.util.Constants.Fields.QUERY
import com.maximillianleonov.cinemax.core.network.util.Constants.Path.DETAILS_MOVIE
import com.maximillianleonov.cinemax.core.network.util.Constants.Path.DISCOVER_MOVIE
import com.maximillianleonov.cinemax.core.network.util.Constants.Path.NOW_PLAYING_MOVIE
import com.maximillianleonov.cinemax.core.network.util.Constants.Path.POPULAR_MOVIE
import com.maximillianleonov.cinemax.core.network.util.Constants.Path.SEARCH_MOVIE
import com.maximillianleonov.cinemax.core.network.util.Constants.Path.TOP_RATED_MOVIE
import com.maximillianleonov.cinemax.core.network.util.Constants.Path.TRENDING_MOVIE
import com.maximillianleonov.cinemax.core.network.util.Constants.Path.UPCOMING_MOVIE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET(UPCOMING_MOVIE)
    suspend fun getUpcoming(
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int = DEFAULT_PAGE
    ): CinemaxResult<MovieResponseDto>

    @GET(TOP_RATED_MOVIE)
    suspend fun getTopRated(
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int = DEFAULT_PAGE
    ): CinemaxResult<MovieResponseDto>

    @GET(POPULAR_MOVIE)
    suspend fun getPopular(
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int = DEFAULT_PAGE
    ): CinemaxResult<MovieResponseDto>

    @GET(NOW_PLAYING_MOVIE)
    suspend fun getNowPlaying(
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int = DEFAULT_PAGE
    ): CinemaxResult<MovieResponseDto>

    @GET(DISCOVER_MOVIE)
    suspend fun getDiscover(
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int = DEFAULT_PAGE
    ): CinemaxResult<MovieResponseDto>

    @GET(TRENDING_MOVIE)
    suspend fun getTrending(
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int = DEFAULT_PAGE
    ): CinemaxResult<MovieResponseDto>

    @GET(DETAILS_MOVIE)
    suspend fun getDetailsById(
        @Path(ID) id: Int,
        @Query(LANGUAGE) language: String,
        @Query(APPEND_TO_RESPONSE) appendToResponse: String = DETAILS_APPEND_TO_RESPONSE
    ): CinemaxResult<NetworkMovieDetails>

    @GET(SEARCH_MOVIE)
    suspend fun search(
        @Query(QUERY) query: String,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int = DEFAULT_PAGE
    ): CinemaxResult<MovieResponseDto>
}
