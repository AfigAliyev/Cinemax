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

import com.maximillianleonov.cinemax.core.database.model.movie.MovieDetailsEntity
import com.maximillianleonov.cinemax.core.domain.model.MovieDetailsModel
import com.maximillianleonov.cinemax.core.network.model.movie.NetworkMovieDetails

fun NetworkMovieDetails.asMovieDetailsEntity() = MovieDetailsEntity(
    id = id,
    adult = adult,
    backdropPath = backdropPath?.asImageUrl(),
    budget = budget,
    genres = genres.asGenres(),
    homepage = homepage,
    imdbId = imdbId,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath?.asImageUrl(),
    releaseDate = releaseDate,
    revenue = revenue,
    runtime = runtime,
    status = status,
    tagline = tagline,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
    credits = credits.asCredits()
)

fun MovieDetailsEntity.asMovieDetailsModel(isWishlisted: Boolean) = MovieDetailsModel(
    id = id,
    adult = adult,
    backdropPath = backdropPath,
    budget = budget,
    genres = genres.asGenreModels(),
    homepage = homepage,
    imdbId = imdbId,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    revenue = revenue,
    runtime = runtime,
    status = status,
    tagline = tagline,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
    credits = credits.asCreditsModel(),
    isWishlisted = isWishlisted
)
