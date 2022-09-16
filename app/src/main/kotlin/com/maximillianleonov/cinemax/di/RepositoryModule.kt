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

import com.maximillianleonov.cinemax.data.local.repository.DetailsRepositoryImpl
import com.maximillianleonov.cinemax.data.local.repository.DiscoverRepositoryImpl
import com.maximillianleonov.cinemax.data.local.repository.NowPlayingRepositoryImpl
import com.maximillianleonov.cinemax.data.local.repository.PopularRepositoryImpl
import com.maximillianleonov.cinemax.data.local.repository.SearchRepositoryImpl
import com.maximillianleonov.cinemax.data.local.repository.SettingsRepositoryImpl
import com.maximillianleonov.cinemax.data.local.repository.TopRatedRepositoryImpl
import com.maximillianleonov.cinemax.data.local.repository.TrendingRepositoryImpl
import com.maximillianleonov.cinemax.data.local.repository.UpcomingRepositoryImpl
import com.maximillianleonov.cinemax.data.local.repository.WishlistRepositoryImpl
import com.maximillianleonov.cinemax.domain.repository.DetailsRepository
import com.maximillianleonov.cinemax.domain.repository.DiscoverRepository
import com.maximillianleonov.cinemax.domain.repository.NowPlayingRepository
import com.maximillianleonov.cinemax.domain.repository.PopularRepository
import com.maximillianleonov.cinemax.domain.repository.SearchRepository
import com.maximillianleonov.cinemax.domain.repository.SettingsRepository
import com.maximillianleonov.cinemax.domain.repository.TopRatedRepository
import com.maximillianleonov.cinemax.domain.repository.TrendingRepository
import com.maximillianleonov.cinemax.domain.repository.UpcomingRepository
import com.maximillianleonov.cinemax.domain.repository.WishlistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@[Module InstallIn(ViewModelComponent::class)]
interface RepositoryModule {
    @Binds
    fun bindUpcomingRepository(upcomingRepositoryImpl: UpcomingRepositoryImpl): UpcomingRepository

    @Binds
    fun bindTopRatedRepository(topRatedRepositoryImpl: TopRatedRepositoryImpl): TopRatedRepository

    @Binds
    fun bindPopularRepository(popularRepositoryImpl: PopularRepositoryImpl): PopularRepository

    @Binds
    fun bindNowPlayingRepository(nowPlayingRepositoryImpl: NowPlayingRepositoryImpl): NowPlayingRepository

    @Binds
    fun bindDiscoverRepository(discoverRepositoryImpl: DiscoverRepositoryImpl): DiscoverRepository

    @Binds
    fun bindTrendingRepository(trendingRepositoryImpl: TrendingRepositoryImpl): TrendingRepository

    @Binds
    fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    fun bindDetailsRepository(detailsRepositoryImpl: DetailsRepositoryImpl): DetailsRepository

    @Binds
    fun bindWishlistRepository(wishlistRepositoryImpl: WishlistRepositoryImpl): WishlistRepository

    @Binds
    fun bindSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository
}
