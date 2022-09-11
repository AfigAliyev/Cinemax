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

package com.maximillianleonov.cinemax.core.ui.mapper

import com.maximillianleonov.cinemax.core.ui.model.TvShow
import com.maximillianleonov.cinemax.core.ui.model.TvShowDetails
import com.maximillianleonov.cinemax.core.ui.util.roundToOneDecimal
import com.maximillianleonov.cinemax.domain.model.TvShowDetailsModel
import com.maximillianleonov.cinemax.domain.model.TvShowModel

fun TvShowModel.toTvShow() = TvShow(
    id = id,
    name = name,
    overview = overview,
    firstAirDate = firstAirDate,
    genres = genres.toGenres(),
    voteAverage = voteAverage.roundToOneDecimal(),
    posterPath = posterPath,
    backdropPath = backdropPath
)

fun TvShowDetailsModel.toTvShowDetails() = TvShowDetails(
    id = id,
    name = name,
    backdropPath = backdropPath,
    episodeRunTime = episodeRunTime.maxOrNull() ?: 0,
    firstAirDate = firstAirDate,
    genres = genres.toGenres(),
    numberOfEpisodes = numberOfEpisodes,
    numberOfSeasons = numberOfSeasons,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    voteAverage = voteAverage.roundToOneDecimal(),
    credits = credits.toCredits()
)
