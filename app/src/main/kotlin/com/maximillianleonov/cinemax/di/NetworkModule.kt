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

import com.maximillianleonov.cinemax.BuildConfig
import com.maximillianleonov.cinemax.core.network.api.CinemaxApi
import com.maximillianleonov.cinemax.core.network.api.CinemaxApiKeyProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
object NetworkModule {
    @[Provides Singleton]
    fun provideCinemaxApi(apiKeyProvider: CinemaxApiKeyProvider) = CinemaxApi(apiKeyProvider)

    @Provides
    fun provideCinemaxApiKeyProvider() = object : CinemaxApiKeyProvider {
        override val apiKey: String = BuildConfig.CINEMAX_API_KEY
    }

    @Provides
    fun provideMovieService(api: CinemaxApi) = api.movieService

    @Provides
    fun provideTvShowService(api: CinemaxApi) = api.tvShowService
}
