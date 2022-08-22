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
import com.maximillianleonov.cinemax.data.local.dao.popular.PopularMovieDao
import com.maximillianleonov.cinemax.data.local.dao.popular.PopularMovieRemoteKeyDao
import com.maximillianleonov.cinemax.data.local.dao.popular.PopularTvShowDao
import com.maximillianleonov.cinemax.data.local.dao.popular.PopularTvShowRemoteKeyDao
import com.maximillianleonov.cinemax.data.local.dao.toprated.TopRatedMovieDao
import com.maximillianleonov.cinemax.data.local.dao.toprated.TopRatedMovieRemoteKeyDao
import com.maximillianleonov.cinemax.data.local.dao.toprated.TopRatedTvShowDao
import com.maximillianleonov.cinemax.data.local.dao.toprated.TopRatedTvShowRemoteKeyDao
import com.maximillianleonov.cinemax.data.local.dao.upcoming.UpcomingMovieDao
import com.maximillianleonov.cinemax.data.local.dao.upcoming.UpcomingMovieRemoteKeyDao
import com.maximillianleonov.cinemax.data.local.entity.popular.PopularMovieEntity
import com.maximillianleonov.cinemax.data.local.entity.popular.PopularMovieRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.entity.popular.PopularTvShowEntity
import com.maximillianleonov.cinemax.data.local.entity.popular.PopularTvShowRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.entity.toprated.TopRatedMovieEntity
import com.maximillianleonov.cinemax.data.local.entity.toprated.TopRatedMovieRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.entity.toprated.TopRatedTvShowEntity
import com.maximillianleonov.cinemax.data.local.entity.toprated.TopRatedTvShowRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.entity.upcoming.UpcomingMovieEntity
import com.maximillianleonov.cinemax.data.local.entity.upcoming.UpcomingMovieRemoteKeyEntity

@Database(
    entities = [
        UpcomingMovieEntity::class, UpcomingMovieRemoteKeyEntity::class,
        TopRatedMovieEntity::class, TopRatedMovieRemoteKeyEntity::class,
        TopRatedTvShowEntity::class, TopRatedTvShowRemoteKeyEntity::class,
        PopularMovieEntity::class, PopularMovieRemoteKeyEntity::class,
        PopularTvShowEntity::class, PopularTvShowRemoteKeyEntity::class
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
    abstract val topRatedTvShowDao: TopRatedTvShowDao
    abstract val topRatedTvShowRemoteKeyDao: TopRatedTvShowRemoteKeyDao

    abstract val popularMovieDao: PopularMovieDao
    abstract val popularMovieRemoteKeyDao: PopularMovieRemoteKeyDao
    abstract val popularTvShowDao: PopularTvShowDao
    abstract val popularTvShowRemoteKeyDao: PopularTvShowRemoteKeyDao

    companion object {
        const val CINEMAX_DATABASE = "cinemax.db"
    }
}
