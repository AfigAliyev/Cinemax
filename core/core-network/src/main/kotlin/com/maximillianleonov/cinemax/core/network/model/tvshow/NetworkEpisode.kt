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

package com.maximillianleonov.cinemax.core.network.model.tvshow

import com.maximillianleonov.cinemax.core.network.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkEpisode(
    @SerialName(Constants.Fields.ID)
    val id: Int,

    @SerialName(Constants.Fields.NAME)
    val name: String,

    @SerialName(Constants.Fields.AIR_DATE)
    val airDate: String,

    @SerialName(Constants.Fields.EPISODE_NUMBER)
    val episodeNumber: Int,

    @SerialName(Constants.Fields.OVERVIEW)
    val overview: String,

    @SerialName(Constants.Fields.PRODUCTION_CODE)
    val productionCode: String,

    @SerialName(Constants.Fields.RUNTIME)
    val runtime: Int?,

    @SerialName(Constants.Fields.SEASON_NUMBER)
    val seasonNumber: Int,

    @SerialName(Constants.Fields.SHOW_ID)
    val showId: Int,

    @SerialName(Constants.Fields.STILL_PATH)
    val stillPath: String?,

    @SerialName(Constants.Fields.VOTE_AVERAGE)
    val voteAverage: Double,

    @SerialName(Constants.Fields.VOTE_COUNT)
    val voteCount: Int
)
