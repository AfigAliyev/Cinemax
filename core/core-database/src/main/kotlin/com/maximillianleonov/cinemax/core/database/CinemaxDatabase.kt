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

package com.maximillianleonov.cinemax.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.maximillianleonov.cinemax.core.database.converter.CreditsConverter
import com.maximillianleonov.cinemax.core.database.converter.ListConverter
import com.maximillianleonov.cinemax.core.database.converter.LocalDateConverter
import com.maximillianleonov.cinemax.core.database.dao.movie.MovieDao
import com.maximillianleonov.cinemax.core.database.dao.movie.MovieDetailsDao
import com.maximillianleonov.cinemax.core.database.dao.movie.MovieRemoteKeyDao
import com.maximillianleonov.cinemax.core.database.dao.tvshow.TvShowDao
import com.maximillianleonov.cinemax.core.database.dao.tvshow.TvShowDetailsDao
import com.maximillianleonov.cinemax.core.database.dao.tvshow.TvShowRemoteKeyDao
import com.maximillianleonov.cinemax.core.database.dao.wishlist.WishlistDao
import com.maximillianleonov.cinemax.core.database.model.movie.MovieDetailsEntity
import com.maximillianleonov.cinemax.core.database.model.movie.MovieEntity
import com.maximillianleonov.cinemax.core.database.model.movie.MovieRemoteKeyEntity
import com.maximillianleonov.cinemax.core.database.model.tvshow.TvShowDetailsEntity
import com.maximillianleonov.cinemax.core.database.model.tvshow.TvShowEntity
import com.maximillianleonov.cinemax.core.database.model.tvshow.TvShowRemoteKeyEntity
import com.maximillianleonov.cinemax.core.database.model.wishlist.WishlistEntity

@Database(
    entities = [
        MovieEntity::class, MovieRemoteKeyEntity::class,
        TvShowEntity::class, TvShowRemoteKeyEntity::class,
        MovieDetailsEntity::class, TvShowDetailsEntity::class,
        WishlistEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListConverter::class, LocalDateConverter::class, CreditsConverter::class)
abstract class CinemaxDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val tvShowDao: TvShowDao

    abstract val movieRemoteKeyDao: MovieRemoteKeyDao
    abstract val tvShowRemoteKeyDao: TvShowRemoteKeyDao

    abstract val movieDetailsDao: MovieDetailsDao
    abstract val tvShowDetailsDao: TvShowDetailsDao

    abstract val wishlistDao: WishlistDao

    companion object {
        const val CINEMAX_DATABASE = "cinemax.db"
    }
}
