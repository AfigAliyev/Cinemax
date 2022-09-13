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

package com.maximillianleonov.cinemax.domain.model

import kotlinx.datetime.LocalDate

data class TvShowDetailsModel(
    val id: Int,
    val name: String,
    val adult: Boolean,
    val backdropPath: String?,
    val episodeRunTime: List<Int>,
    val firstAirDate: LocalDate?,
    val genres: List<GenreModel>,
    val homepage: String,
    val inProduction: Boolean,
    val languages: List<String>,
    val lastAirDate: LocalDate?,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val status: String,
    val tagline: String,
    val type: String,
    val voteAverage: Double,
    val voteCount: Int,
    val credits: CreditsModel,
    val isWishlisted: Boolean
)
