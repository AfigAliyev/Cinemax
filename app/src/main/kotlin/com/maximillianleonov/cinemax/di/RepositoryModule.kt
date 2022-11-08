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

package com.maximillianleonov.cinemax.di

import com.maximillianleonov.cinemax.core.data.repository.MovieDetailsRepositoryImpl
import com.maximillianleonov.cinemax.core.data.repository.MovieRepositoryImpl
import com.maximillianleonov.cinemax.core.data.repository.SettingsRepositoryImpl
import com.maximillianleonov.cinemax.core.data.repository.TvShowDetailsRepositoryImpl
import com.maximillianleonov.cinemax.core.data.repository.TvShowRepositoryImpl
import com.maximillianleonov.cinemax.core.data.repository.WishlistRepositoryImpl
import com.maximillianleonov.cinemax.core.domain.repository.MovieDetailsRepository
import com.maximillianleonov.cinemax.core.domain.repository.MovieRepository
import com.maximillianleonov.cinemax.core.domain.repository.SettingsRepository
import com.maximillianleonov.cinemax.core.domain.repository.TvShowDetailsRepository
import com.maximillianleonov.cinemax.core.domain.repository.TvShowRepository
import com.maximillianleonov.cinemax.core.domain.repository.WishlistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    @ViewModelScoped
    fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    @ViewModelScoped
    fun bindTvShowRepository(tvShowRepositoryImpl: TvShowRepositoryImpl): TvShowRepository

    @Binds
    @ViewModelScoped
    fun bindMovieDetailsRepository(
        movieDetailsRepositoryImpl: MovieDetailsRepositoryImpl
    ): MovieDetailsRepository

    @Binds
    @ViewModelScoped
    fun bindTvShowDetailsRepository(
        tvShowDetailsRepositoryImpl: TvShowDetailsRepositoryImpl
    ): TvShowDetailsRepository

    @Binds
    @ViewModelScoped
    fun bindWishlistRepository(wishlistRepositoryImpl: WishlistRepositoryImpl): WishlistRepository

    @Binds
    @ViewModelScoped
    fun bindSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository
}
