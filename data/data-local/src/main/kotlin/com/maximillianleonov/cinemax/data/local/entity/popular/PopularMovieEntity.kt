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

package com.maximillianleonov.cinemax.data.local.entity.popular

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maximillianleonov.cinemax.core.data.local.common.MovieEntity
import com.maximillianleonov.cinemax.core.data.util.Constants
import kotlinx.datetime.LocalDate

@Entity(tableName = Constants.Tables.POPULAR_MOVIES)
data class PopularMovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.Fields.ID)
    override val id: Int = 0,

    @ColumnInfo(name = Constants.Fields.REMOTE_ID)
    override val remoteId: Int,

    @ColumnInfo(name = Constants.Fields.TITLE)
    override val title: String,

    @ColumnInfo(name = Constants.Fields.OVERVIEW)
    override val overview: String,

    @ColumnInfo(name = Constants.Fields.POPULARITY)
    override val popularity: Double,

    @ColumnInfo(name = Constants.Fields.RELEASE_DATE)
    override val releaseDate: LocalDate,

    @ColumnInfo(name = Constants.Fields.ADULT)
    override val adult: Boolean,

    @ColumnInfo(name = Constants.Fields.GENRE_IDS)
    override val genreIds: List<Int>,

    @ColumnInfo(name = Constants.Fields.ORIGINAL_TITLE)
    override val originalTitle: String,

    @ColumnInfo(name = Constants.Fields.ORIGINAL_LANGUAGE)
    override val originalLanguage: String,

    @ColumnInfo(name = Constants.Fields.VOTE_AVERAGE)
    override val voteAverage: Double,

    @ColumnInfo(name = Constants.Fields.VOTE_COUNT)
    override val voteCount: Int,

    @ColumnInfo(name = Constants.Fields.POSTER_PATH)
    override val posterPath: String?,

    @ColumnInfo(name = Constants.Fields.BACKDROP_PATH)
    override val backdropPath: String?,

    @ColumnInfo(name = Constants.Fields.VIDEO)
    override val video: Boolean
) : MovieEntity