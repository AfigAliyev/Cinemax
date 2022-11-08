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

import com.maximillianleonov.cinemax.core.domain.model.MovieDetailsModel
import com.maximillianleonov.cinemax.core.domain.model.MovieModel
import com.maximillianleonov.cinemax.core.model.Movie
import com.maximillianleonov.cinemax.core.model.MovieDetails
import com.maximillianleonov.cinemax.core.model.ReleaseDate
import com.maximillianleonov.cinemax.core.ui.util.roundToOneDecimal

fun MovieModel.asMovie() = Movie(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate?.asReleaseDate() ?: ReleaseDate(),
    genres = genres.asGenres(),
    voteAverage = voteAverage.roundToOneDecimal(),
    posterPath = posterPath,
    backdropPath = backdropPath
)

fun MovieDetailsModel.asMovieDetails() = MovieDetails(
    id = id,
    title = title,
    overview = overview,
    backdropPath = backdropPath,
    budget = budget,
    genres = genres.asGenres(),
    posterPath = posterPath,
    releaseDate = releaseDate?.asReleaseDate() ?: ReleaseDate(),
    runtime = runtime ?: NoMovieRuntimeValue,
    video = video,
    voteAverage = voteAverage.roundToOneDecimal(),
    voteCount = voteCount,
    credits = credits.asCredits(),
    isWishlisted = isWishlisted
)

const val NoMovieRuntimeValue = 0
