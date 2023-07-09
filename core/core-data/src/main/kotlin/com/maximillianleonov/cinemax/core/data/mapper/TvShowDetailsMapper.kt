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

package com.maximillianleonov.cinemax.core.data.mapper

import com.maximillianleonov.cinemax.core.database.model.tvshow.TvShowDetailsEntity
import com.maximillianleonov.cinemax.core.domain.model.TvShowDetailsModel
import com.maximillianleonov.cinemax.core.network.model.tvshow.NetworkTvShowDetails

fun NetworkTvShowDetails.asTvShowDetailsEntity() = TvShowDetailsEntity(
    id = id,
    name = name,
    adult = adult,
    backdropPath = backdropPath?.asImageUrl(),
    episodeRunTime = episodeRunTime,
    firstAirDate = firstAirDate,
    genres = genres.asGenres(),
    homepage = homepage,
    inProduction = inProduction,
    languages = languages,
    lastAirDate = lastAirDate,
    numberOfEpisodes = numberOfEpisodes,
    numberOfSeasons = numberOfSeasons,
    originCountry = originCountry,
    originalLanguage = originalLanguage,
    originalName = originalName,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath?.asImageUrl(),
    status = status,
    tagline = tagline,
    type = type,
    voteAverage = voteAverage,
    voteCount = voteCount,
    credits = credits.asCredits()
)

fun TvShowDetailsEntity.asTvShowDetailsModel(isWishlisted: Boolean) = TvShowDetailsModel(
    id = id,
    name = name,
    adult = adult,
    backdropPath = backdropPath,
    episodeRunTime = episodeRunTime,
    firstAirDate = firstAirDate,
    genres = genres.asGenreModels(),
    homepage = homepage,
    inProduction = inProduction,
    languages = languages,
    lastAirDate = lastAirDate,
    numberOfEpisodes = numberOfEpisodes,
    numberOfSeasons = numberOfSeasons,
    originCountry = originCountry,
    originalLanguage = originalLanguage,
    originalName = originalName,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    status = status,
    tagline = tagline,
    type = type,
    voteAverage = voteAverage,
    voteCount = voteCount,
    credits = credits.asCreditsModel(),
    isWishlisted = isWishlisted
)
