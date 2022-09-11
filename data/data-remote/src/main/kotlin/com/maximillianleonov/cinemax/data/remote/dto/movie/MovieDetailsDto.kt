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

package com.maximillianleonov.cinemax.data.remote.dto.movie

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
data class MovieDetailsDto(
    @SerialName(Constants.Fields.ID)
    val id: Int,

    @SerialName(Constants.Fields.ADULT)
    val adult: Boolean,

    @SerialName(Constants.Fields.BACKDROP_PATH)
    val backdropPath: String?,

    @SerialName(Constants.Fields.BELONGS_TO_COLLECTION)
    val belongsToCollection: BelongsToCollectionDto?,

    @SerialName(Constants.Fields.BUDGET)
    val budget: Int,

    @SerialName(Constants.Fields.GENRES)
    val genres: List<GenreDto>,

    @SerialName(Constants.Fields.HOMEPAGE)
    val homepage: String?,

    @SerialName(Constants.Fields.IMDB_ID)
    val imdbId: String?,

    @SerialName(Constants.Fields.ORIGINAL_LANGUAGE)
    val originalLanguage: String,

    @SerialName(Constants.Fields.ORIGINAL_TITLE)
    val originalTitle: String,

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

    @Serializable(LocalDateSerializer::class)
    @SerialName(Constants.Fields.RELEASE_DATE)
    val releaseDate: LocalDate?,

    @SerialName(Constants.Fields.REVENUE)
    val revenue: Int,

    @SerialName(Constants.Fields.RUNTIME)
    val runtime: Int?,

    @SerialName(Constants.Fields.SPOKEN_LANGUAGES)
    val spokenLanguages: List<SpokenLanguageDto>,

    @SerialName(Constants.Fields.STATUS)
    val status: String,

    @SerialName(Constants.Fields.TAGLINE)
    val tagline: String?,

    @SerialName(Constants.Fields.TITLE)
    val title: String,

    @SerialName(Constants.Fields.VIDEO)
    val video: Boolean,

    @SerialName(Constants.Fields.VOTE_AVERAGE)
    val voteAverage: Double,

    @SerialName(Constants.Fields.VOTE_COUNT)
    val voteCount: Int,

    @SerialName(Constants.Fields.CREDITS)
    val credits: CreditsDto
)
