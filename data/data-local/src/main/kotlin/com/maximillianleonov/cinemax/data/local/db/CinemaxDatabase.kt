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

package com.maximillianleonov.cinemax.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.maximillianleonov.cinemax.data.local.dao.toprated.TopRatedMovieDao
import com.maximillianleonov.cinemax.data.local.dao.toprated.TopRatedMovieRemoteKeyDao
import com.maximillianleonov.cinemax.data.local.dao.upcoming.UpcomingMovieDao
import com.maximillianleonov.cinemax.data.local.dao.upcoming.UpcomingMovieRemoteKeyDao
import com.maximillianleonov.cinemax.data.local.entity.toprated.TopRatedMovieEntity
import com.maximillianleonov.cinemax.data.local.entity.toprated.TopRatedMovieRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.entity.upcoming.UpcomingMovieEntity
import com.maximillianleonov.cinemax.data.local.entity.upcoming.UpcomingMovieRemoteKeyEntity

@Database(
    entities = [
        UpcomingMovieEntity::class, UpcomingMovieRemoteKeyEntity::class,
        TopRatedMovieEntity::class, TopRatedMovieRemoteKeyEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CinemaxDatabase : RoomDatabase() {
    abstract val upcomingMovieDao: UpcomingMovieDao
    abstract val upcomingMovieRemoteKeyDao: UpcomingMovieRemoteKeyDao

    abstract val topRatedMovieDao: TopRatedMovieDao
    abstract val topRatedMovieRemoteKeyDao: TopRatedMovieRemoteKeyDao

    companion object {
        const val CINEMAX_DATABASE = "cinemax.db"
    }
}
