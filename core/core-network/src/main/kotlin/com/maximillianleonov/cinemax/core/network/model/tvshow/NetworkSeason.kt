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

package com.maximillianleonov.cinemax.core.network.model.tvshow

import com.maximillianleonov.cinemax.core.network.serializer.LocalDateSerializer
import com.maximillianleonov.cinemax.core.network.util.Constants
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkSeason(
    @SerialName(Constants.Fields.ID)
    val id: Int,

    @SerialName(Constants.Fields.NAME)
    val name: String,

    @SerialName(Constants.Fields.OVERVIEW)
    val overview: String,

    @Serializable(LocalDateSerializer::class)
    @SerialName(Constants.Fields.AIR_DATE)
    val airDate: LocalDate?,

    @SerialName(Constants.Fields.EPISODE_COUNT)
    val episodeCount: Int,

    @SerialName(Constants.Fields.SEASON_NUMBER)
    val seasonNumber: Int,

    @SerialName(Constants.Fields.POSTER_PATH)
    val posterPath: String?
)
