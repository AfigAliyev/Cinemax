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

import com.maximillianleonov.cinemax.data.remote.dto.movie.MovieDto
import com.maximillianleonov.cinemax.data.remote.dto.tvshow.TvShowDto
import com.maximillianleonov.cinemax.domain.model.MovieModel
import com.maximillianleonov.cinemax.domain.model.TvShowModel

internal fun MovieDto.toMovieModel() = MovieModel(
    id = id,
    title = title,
    overview = overview,
    popularity = popularity,
    releaseDate = releaseDate,
    adult = adult,
    genres = genreIds.toGenres(),
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    voteAverage = voteAverage,
    voteCount = voteCount,
    posterPath = posterPath?.toImageUrl(),
    backdropPath = backdropPath?.toImageUrl(),
    video = video
)

internal fun TvShowDto.toTvShowModel() = TvShowModel(
    id = id,
    name = name,
    overview = overview,
    popularity = popularity,
    firstAirDate = firstAirDate,
    genres = genreIds.toGenres(),
    originalName = originalName,
    originalLanguage = originalLanguage,
    originCountry = originCountry,
    voteAverage = voteAverage,
    voteCount = voteCount,
    posterPath = posterPath?.toImageUrl(),
    backdropPath = backdropPath?.toImageUrl()
)
