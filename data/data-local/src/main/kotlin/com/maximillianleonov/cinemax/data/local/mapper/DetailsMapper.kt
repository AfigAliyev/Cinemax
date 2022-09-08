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

package com.maximillianleonov.cinemax.data.local.mapper

import com.maximillianleonov.cinemax.data.local.entity.details.MovieDetailsEntity
import com.maximillianleonov.cinemax.data.local.entity.details.TvShowDetailsEntity
import com.maximillianleonov.cinemax.data.remote.dto.common.GenreDto
import com.maximillianleonov.cinemax.data.remote.dto.movie.MovieDetailsDto
import com.maximillianleonov.cinemax.data.remote.dto.tvshow.TvShowDetailsDto
import com.maximillianleonov.cinemax.domain.model.MovieDetailsModel
import com.maximillianleonov.cinemax.domain.model.TvShowDetailsModel

fun MovieDetailsDto.toMovieDetailsEntity() = MovieDetailsEntity(
    id = id,
    adult = adult,
    backdropPath = backdropPath,
    budget = budget,
    genreIds = genres.map(GenreDto::id),
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
    credits = credits
)

fun MovieDetailsEntity.toMovieDetailsModel() = MovieDetailsModel(
    id = id,
    adult = adult,
    backdropPath = backdropPath?.toImageUrl(),
    budget = budget,
    genres = genreIds.toGenres(),
    homepage = homepage,
    imdbId = imdbId,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath?.toImageUrl(),
    releaseDate = releaseDate,
    revenue = revenue,
    runtime = runtime,
    status = status,
    tagline = tagline,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
    credits = credits.toCreditsModel()
)

fun TvShowDetailsDto.toTvShowDetailsEntity() = TvShowDetailsEntity(
    id = id,
    name = name,
    adult = adult,
    backdropPath = backdropPath,
    episodeRunTime = episodeRunTime,
    firstAirDate = firstAirDate,
    genreIds = genres.map(GenreDto::id),
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
    credits = credits
)

fun TvShowDetailsEntity.toTvShowDetailsModel() = TvShowDetailsModel(
    id = id,
    name = name,
    adult = adult,
    backdropPath = backdropPath?.toImageUrl(),
    episodeRunTime = episodeRunTime,
    firstAirDate = firstAirDate,
    genres = genreIds.toGenres(),
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
    posterPath = posterPath?.toImageUrl(),
    status = status,
    tagline = tagline,
    type = type,
    voteAverage = voteAverage,
    voteCount = voteCount,
    credits = credits.toCreditsModel()
)
