/*
 * Copyright 2022 Afig Aliyev
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

package com.maximillianleonov.cinemax.core.network.model.response

import com.maximillianleonov.cinemax.core.network.model.movie.NetworkMovie
import com.maximillianleonov.cinemax.core.network.model.tvshow.NetworkTvShow
import com.maximillianleonov.cinemax.core.network.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponseDto(
    @SerialName(Constants.Fields.PAGE)
    val page: Int,

    @SerialName(Constants.Fields.RESULTS)
    val results: List<NetworkMovie>,

    @SerialName(Constants.Fields.TOTAL_PAGES)
    val totalPages: Int,

    @SerialName(Constants.Fields.TOTAL_RESULTS)
    val totalResults: Int,

    @SerialName(Constants.Fields.DATES)
    val dates: NetworkDates?
)

@Serializable
data class TvShowResponseDto(
    @SerialName(Constants.Fields.PAGE)
    val page: Int,

    @SerialName(Constants.Fields.RESULTS)
    val results: List<NetworkTvShow>,

    @SerialName(Constants.Fields.TOTAL_PAGES)
    val totalPages: Int,

    @SerialName(Constants.Fields.TOTAL_RESULTS)
    val totalResults: Int,

    @SerialName(Constants.Fields.DATES)
    val dates: NetworkDates? = null
)
