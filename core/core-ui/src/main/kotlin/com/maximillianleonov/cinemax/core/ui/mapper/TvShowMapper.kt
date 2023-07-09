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

package com.maximillianleonov.cinemax.core.ui.mapper

import com.maximillianleonov.cinemax.core.domain.model.TvShowDetailsModel
import com.maximillianleonov.cinemax.core.domain.model.TvShowModel
import com.maximillianleonov.cinemax.core.model.TvShow
import com.maximillianleonov.cinemax.core.model.TvShowDetails
import com.maximillianleonov.cinemax.core.ui.util.roundToOneDecimal

fun TvShowModel.asTvShow() = TvShow(
    id = id,
    name = name,
    overview = overview,
    firstAirDate = firstAirDate,
    genres = genres.asGenres(),
    voteAverage = voteAverage.roundToOneDecimal(),
    posterPath = posterPath,
    backdropPath = backdropPath
)

fun TvShowDetailsModel.asTvShowDetails() = TvShowDetails(
    id = id,
    name = name,
    backdropPath = backdropPath,
    episodeRunTime = episodeRunTime.maxOrNull() ?: NoTvShowRuntimeValue,
    firstAirDate = firstAirDate,
    genres = genres.asGenres(),
    numberOfEpisodes = numberOfEpisodes,
    numberOfSeasons = numberOfSeasons,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    voteAverage = voteAverage.roundToOneDecimal(),
    credits = credits.asCredits(),
    isWishlisted = isWishlisted
)

const val NoTvShowRuntimeValue = 0
