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

package com.maximillianleonov.cinemax.data.local.entity.discover

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maximillianleonov.cinemax.core.data.local.common.TvShowEntity
import com.maximillianleonov.cinemax.core.data.util.Constants
import kotlinx.datetime.LocalDate

@Entity(tableName = Constants.Tables.DISCOVER_TV_SHOWS)
data class DiscoverTvShowEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.Fields.ID)
    override val id: Int = 0,

    @ColumnInfo(name = Constants.Fields.REMOTE_ID)
    override val remoteId: Int,

    @ColumnInfo(name = Constants.Fields.NAME)
    override val name: String,

    @ColumnInfo(name = Constants.Fields.OVERVIEW)
    override val overview: String,

    @ColumnInfo(name = Constants.Fields.POPULARITY)
    override val popularity: Double,

    @ColumnInfo(name = Constants.Fields.FIRST_AIR_DATE)
    override val firstAirDate: LocalDate?,

    @ColumnInfo(name = Constants.Fields.GENRE_IDS)
    override val genreIds: List<Int>,

    @ColumnInfo(name = Constants.Fields.ORIGINAL_NAME)
    override val originalName: String,

    @ColumnInfo(name = Constants.Fields.ORIGINAL_LANGUAGE)
    override val originalLanguage: String,

    @ColumnInfo(name = Constants.Fields.ORIGIN_COUNTRY)
    override val originCountry: List<String>,

    @ColumnInfo(name = Constants.Fields.VOTE_AVERAGE)
    override val voteAverage: Double,

    @ColumnInfo(name = Constants.Fields.VOTE_COUNT)
    override val voteCount: Int,

    @ColumnInfo(name = Constants.Fields.POSTER_PATH)
    override val posterPath: String?,

    @ColumnInfo(name = Constants.Fields.BACKDROP_PATH)
    override val backdropPath: String?
) : TvShowEntity
