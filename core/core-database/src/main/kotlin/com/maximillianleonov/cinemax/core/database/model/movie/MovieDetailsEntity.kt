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

package com.maximillianleonov.cinemax.core.database.model.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maximillianleonov.cinemax.core.database.model.common.Credits
import com.maximillianleonov.cinemax.core.database.model.common.Genre
import com.maximillianleonov.cinemax.core.database.util.Constants
import kotlinx.datetime.LocalDate

@Entity(tableName = Constants.Tables.MOVIE_DETAILS)
data class MovieDetailsEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = Constants.Fields.ID)
    val id: Int,

    @ColumnInfo(name = Constants.Fields.ADULT)
    val adult: Boolean,

    @ColumnInfo(name = Constants.Fields.BACKDROP_PATH)
    val backdropPath: String?,

    @ColumnInfo(name = Constants.Fields.BUDGET)
    val budget: Int,

    @ColumnInfo(name = Constants.Fields.GENRES)
    val genres: List<Genre>,

    @ColumnInfo(name = Constants.Fields.HOMEPAGE)
    val homepage: String?,

    @ColumnInfo(name = Constants.Fields.IMDB_ID)
    val imdbId: String?,

    @ColumnInfo(name = Constants.Fields.ORIGINAL_LANGUAGE)
    val originalLanguage: String,

    @ColumnInfo(name = Constants.Fields.ORIGINAL_TITLE)
    val originalTitle: String,

    @ColumnInfo(name = Constants.Fields.OVERVIEW)
    val overview: String,

    @ColumnInfo(name = Constants.Fields.POPULARITY)
    val popularity: Double,

    @ColumnInfo(name = Constants.Fields.POSTER_PATH)
    val posterPath: String?,

    @ColumnInfo(name = Constants.Fields.RELEASE_DATE)
    val releaseDate: LocalDate?,

    @ColumnInfo(name = Constants.Fields.REVENUE)
    val revenue: Int,

    @ColumnInfo(name = Constants.Fields.RUNTIME)
    val runtime: Int?,

    @ColumnInfo(name = Constants.Fields.STATUS)
    val status: String,

    @ColumnInfo(name = Constants.Fields.TAGLINE)
    val tagline: String?,

    @ColumnInfo(name = Constants.Fields.TITLE)
    val title: String,

    @ColumnInfo(name = Constants.Fields.VIDEO)
    val video: Boolean,

    @ColumnInfo(name = Constants.Fields.VOTE_AVERAGE)
    val voteAverage: Double,

    @ColumnInfo(name = Constants.Fields.VOTE_COUNT)
    val voteCount: Int,

    @ColumnInfo(name = Constants.Fields.CREDITS)
    val credits: Credits
)
