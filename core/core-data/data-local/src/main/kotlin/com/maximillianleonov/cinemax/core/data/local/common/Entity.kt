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

package com.maximillianleonov.cinemax.core.data.local.common

import java.time.LocalDate

interface Entity {
    val id: Int
}

interface ContentEntity : Entity {
    override val id: Int
    val remoteId: Int
}

interface RemoteKeyEntity : Entity {
    override val id: Int
    val prevPage: Int?
    val nextPage: Int?
}

interface MovieEntity : ContentEntity {
    override val id: Int
    override val remoteId: Int
    val title: String
    val overview: String
    val popularity: Double
    val releaseDate: LocalDate
    val adult: Boolean
    val genreIds: List<Int>
    val originalTitle: String
    val originalLanguage: String
    val voteAverage: Double
    val voteCount: Int
    val posterPath: String?
    val backdropPath: String?
    val video: Boolean
}

interface TvShowEntity : ContentEntity {
    override val id: Int
    override val remoteId: Int
    val name: String
    val overview: String
    val popularity: Double
    val firstAirDate: LocalDate
    val genreIds: List<Int>
    val originalName: String
    val originalLanguage: String
    val originCountry: List<String>
    val voteAverage: Double
    val voteCount: Int
    val posterPath: String?
    val backdropPath: String?
}
