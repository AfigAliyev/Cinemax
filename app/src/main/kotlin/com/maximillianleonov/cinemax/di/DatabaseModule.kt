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

package com.maximillianleonov.cinemax.di

import android.content.Context
import androidx.room.Room
import com.maximillianleonov.cinemax.BuildConfig
import com.maximillianleonov.cinemax.core.database.CinemaxDatabase
import com.maximillianleonov.cinemax.core.database.util.CinemaxVersionProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideCinemaxDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        CinemaxDatabase::class.java,
        CinemaxDatabase.CINEMAX_DATABASE
    ).build()

    @Provides
    fun provideCinemaxVersionProvider() = object : CinemaxVersionProvider {
        override val version: String = BuildConfig.VERSION_NAME
    }

    @Provides
    fun provideMovieDao(database: CinemaxDatabase) = database.movieDao

    @Provides
    fun provideTvShowDao(database: CinemaxDatabase) = database.tvShowDao

    @Provides
    fun provideMovieRemoteKeyDao(database: CinemaxDatabase) = database.movieRemoteKeyDao

    @Provides
    fun provideTvShowRemoteKeyDao(database: CinemaxDatabase) = database.tvShowRemoteKeyDao

    @Provides
    fun provideMovieDetailsDao(database: CinemaxDatabase) = database.movieDetailsDao

    @Provides
    fun provideTvShowDetailsDao(database: CinemaxDatabase) = database.tvShowDetailsDao

    @Provides
    fun provideWishlistDao(database: CinemaxDatabase) = database.wishlistDao
}
