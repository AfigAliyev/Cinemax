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
import com.maximillianleonov.cinemax.core.network.model.response.TvShowResponseDto
import com.maximillianleonov.cinemax.core.network.model.tvshow.NetworkTvShowDetails
import com.maximillianleonov.cinemax.core.network.util.Constants.DEFAULT_PAGE
import com.maximillianleonov.cinemax.core.network.util.Constants.Fields.APPEND_TO_RESPONSE
import com.maximillianleonov.cinemax.core.network.util.Constants.Fields.DETAILS_APPEND_TO_RESPONSE
import com.maximillianleonov.cinemax.core.network.util.Constants.Fields.ID
import com.maximillianleonov.cinemax.core.network.util.Constants.Fields.LANGUAGE
import com.maximillianleonov.cinemax.core.network.util.Constants.Fields.PAGE
import com.maximillianleonov.cinemax.core.network.util.Constants.Fields.QUERY
import com.maximillianleonov.cinemax.core.network.util.Constants.Path.DETAILS_TV_SHOW
import com.maximillianleonov.cinemax.core.network.util.Constants.Path.DISCOVER_TV_SHOW
import com.maximillianleonov.cinemax.core.network.util.Constants.Path.ON_THE_AIR_TV_SHOW
import com.maximillianleonov.cinemax.core.network.util.Constants.Path.POPULAR_TV_SHOW
import com.maximillianleonov.cinemax.core.network.util.Constants.Path.SEARCH_TV_SHOW
import com.maximillianleonov.cinemax.core.network.util.Constants.Path.TOP_RATED_TV_SHOW
import com.maximillianleonov.cinemax.core.network.util.Constants.Path.TRENDING_TV_SHOW
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowService {
    @GET(TOP_RATED_TV_SHOW)
    suspend fun getTopRated(
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int = DEFAULT_PAGE
    ): CinemaxResult<TvShowResponseDto>

    @GET(POPULAR_TV_SHOW)
    suspend fun getPopular(
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int = DEFAULT_PAGE
    ): CinemaxResult<TvShowResponseDto>

    @GET(ON_THE_AIR_TV_SHOW)
    suspend fun getOnTheAir(
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int = DEFAULT_PAGE
    ): CinemaxResult<TvShowResponseDto>

    @GET(DISCOVER_TV_SHOW)
    suspend fun getDiscover(
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int = DEFAULT_PAGE
    ): CinemaxResult<TvShowResponseDto>

    @GET(TRENDING_TV_SHOW)
    suspend fun getTrending(
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int = DEFAULT_PAGE
    ): CinemaxResult<TvShowResponseDto>

    @GET(DETAILS_TV_SHOW)
    suspend fun getDetailsById(
        @Path(ID) id: Int,
        @Query(LANGUAGE) language: String,
        @Query(APPEND_TO_RESPONSE) appendToResponse: String = DETAILS_APPEND_TO_RESPONSE
    ): CinemaxResult<NetworkTvShowDetails>

    @GET(SEARCH_TV_SHOW)
    suspend fun search(
        @Query(QUERY) query: String,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int = DEFAULT_PAGE
    ): CinemaxResult<TvShowResponseDto>
}
