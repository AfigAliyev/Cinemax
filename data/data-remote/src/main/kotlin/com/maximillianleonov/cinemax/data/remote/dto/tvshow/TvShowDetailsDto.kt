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

package com.maximillianleonov.cinemax.data.remote.dto.tvshow

import com.maximillianleonov.cinemax.core.data.remote.serializer.LocalDateSerializer
import com.maximillianleonov.cinemax.core.data.util.Constants
import com.maximillianleonov.cinemax.data.remote.dto.common.CreditsDto
import com.maximillianleonov.cinemax.data.remote.dto.common.GenreDto
import com.maximillianleonov.cinemax.data.remote.dto.common.ProductionCompanyDto
import com.maximillianleonov.cinemax.data.remote.dto.common.ProductionCountryDto
import com.maximillianleonov.cinemax.data.remote.dto.common.SpokenLanguageDto
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowDetailsDto(
    @SerialName(Constants.Fields.ID)
    val id: Int,

    @SerialName(Constants.Fields.NAME)
    val name: String,

    @SerialName(Constants.Fields.ADULT)
    val adult: Boolean,

    @SerialName(Constants.Fields.BACKDROP_PATH)
    val backdropPath: String?,

    @SerialName(Constants.Fields.CREATED_BY)
    val createdBy: List<CreatedByDto>,

    @SerialName(Constants.Fields.CREDITS)
    val credits: CreditsDto,

    @SerialName(Constants.Fields.EPISODE_RUN_TIME)
    val episodeRunTime: List<Int>,

    @Serializable(LocalDateSerializer::class)
    @SerialName(Constants.Fields.FIRST_AIR_DATE)
    val firstAirDate: LocalDate?,

    @SerialName(Constants.Fields.GENRES)
    val genres: List<GenreDto>,

    @SerialName(Constants.Fields.HOMEPAGE)
    val homepage: String,

    @SerialName(Constants.Fields.IN_PRODUCTION)
    val inProduction: Boolean,

    @SerialName(Constants.Fields.LANGUAGES)
    val languages: List<String>,

    @Serializable(LocalDateSerializer::class)
    @SerialName(Constants.Fields.LAST_AIR_DATE)
    val lastAirDate: LocalDate?,

    @SerialName(Constants.Fields.LAST_EPISODE_TO_AIR)
    val lastEpisodeToAir: EpisodeDto,

    @SerialName(Constants.Fields.NETWORKS)
    val networks: List<NetworkDto>,

    @SerialName(Constants.Fields.NEXT_EPISODE_TO_AIR)
    val nextEpisodeToAir: EpisodeDto?,

    @SerialName(Constants.Fields.NUMBER_OF_EPISODES)
    val numberOfEpisodes: Int,

    @SerialName(Constants.Fields.NUMBER_OF_SEASONS)
    val numberOfSeasons: Int,

    @SerialName(Constants.Fields.ORIGIN_COUNTRY)
    val originCountry: List<String>,

    @SerialName(Constants.Fields.ORIGINAL_LANGUAGE)
    val originalLanguage: String,

    @SerialName(Constants.Fields.ORIGINAL_NAME)
    val originalName: String,

    @SerialName(Constants.Fields.OVERVIEW)
    val overview: String,

    @SerialName(Constants.Fields.POPULARITY)
    val popularity: Double,

    @SerialName(Constants.Fields.POSTER_PATH)
    val posterPath: String?,

    @SerialName(Constants.Fields.PRODUCTION_COMPANIES)
    val productionCompanies: List<ProductionCompanyDto>,

    @SerialName(Constants.Fields.PRODUCTION_COUNTRIES)
    val productionCountries: List<ProductionCountryDto>,

    @SerialName(Constants.Fields.SEASONS)
    val seasons: List<SeasonDto>,

    @SerialName(Constants.Fields.SPOKEN_LANGUAGES)
    val spokenLanguages: List<SpokenLanguageDto>,

    @SerialName(Constants.Fields.STATUS)
    val status: String,

    @SerialName(Constants.Fields.TAGLINE)
    val tagline: String,

    @SerialName(Constants.Fields.TYPE)
    val type: String,

    @SerialName(Constants.Fields.VOTE_AVERAGE)
    val voteAverage: Double,

    @SerialName(Constants.Fields.VOTE_COUNT)
    val voteCount: Int
)
