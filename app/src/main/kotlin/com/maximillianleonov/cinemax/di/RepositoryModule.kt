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

import com.maximillianleonov.cinemax.data.local.repository.PopularRepositoryImpl
import com.maximillianleonov.cinemax.data.local.repository.TopRatedRepositoryImpl
import com.maximillianleonov.cinemax.data.local.repository.UpcomingRepositoryImpl
import com.maximillianleonov.cinemax.domain.repository.PopularRepository
import com.maximillianleonov.cinemax.domain.repository.TopRatedRepository
import com.maximillianleonov.cinemax.domain.repository.UpcomingRepository
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
}
