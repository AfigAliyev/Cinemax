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

package com.maximillianleonov.cinemax.data.remote.dto

import com.maximillianleonov.cinemax.core.data.remote.common.ResponseDto
import com.maximillianleonov.cinemax.core.data.util.Constants
import com.maximillianleonov.cinemax.data.remote.dto.movie.MovieDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponseDto(
    @SerialName(Constants.Fields.PAGE)
    override val page: Int,

    @SerialName(Constants.Fields.RESULTS)
    override val results: List<MovieDto>,

    @SerialName(Constants.Fields.TOTAL_PAGES)
    override val totalPages: Int,

    @SerialName(Constants.Fields.TOTAL_RESULTS)
    override val totalResults: Int,

    @SerialName(Constants.Fields.DATES)
    val dates: DatesDto? = null
) : ResponseDto<MovieDto>
