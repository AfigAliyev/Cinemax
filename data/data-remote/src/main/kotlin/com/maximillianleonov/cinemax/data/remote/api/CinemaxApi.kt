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

package com.maximillianleonov.cinemax.data.remote.api

import com.maximillianleonov.cinemax.core.data.remote.api.CinemaxApiKeyProvider
import com.maximillianleonov.cinemax.core.data.remote.util.retrofit
import com.maximillianleonov.cinemax.data.remote.api.service.DetailsService
import com.maximillianleonov.cinemax.data.remote.api.service.DiscoverService
import com.maximillianleonov.cinemax.data.remote.api.service.NowPlayingService
import com.maximillianleonov.cinemax.data.remote.api.service.PopularService
import com.maximillianleonov.cinemax.data.remote.api.service.SearchService
import com.maximillianleonov.cinemax.data.remote.api.service.TopRatedService
import com.maximillianleonov.cinemax.data.remote.api.service.TrendingService
import com.maximillianleonov.cinemax.data.remote.api.service.UpcomingService
import retrofit2.create
import javax.inject.Inject

class CinemaxApi @Inject constructor(private val apiKeyProvider: CinemaxApiKeyProvider) {
    val upcomingService: UpcomingService by lazy { retrofit(apiKeyProvider).create() }
    val topRatedService: TopRatedService by lazy { retrofit(apiKeyProvider).create() }
    val popularService: PopularService by lazy { retrofit(apiKeyProvider).create() }
    val nowPlayingService: NowPlayingService by lazy { retrofit(apiKeyProvider).create() }
    val discoverService: DiscoverService by lazy { retrofit(apiKeyProvider).create() }
    val trendingService: TrendingService by lazy { retrofit(apiKeyProvider).create() }
    val searchService: SearchService by lazy { retrofit(apiKeyProvider).create() }
    val detailsService: DetailsService by lazy { retrofit(apiKeyProvider).create() }
}
