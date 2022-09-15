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

package com.maximillianleonov.cinemax.data.local.repository

import com.maximillianleonov.cinemax.data.local.source.DetailsLocalDataSource
import com.maximillianleonov.cinemax.data.local.source.DiscoverLocalDataSource
import com.maximillianleonov.cinemax.data.local.source.NowPlayingLocalDataSource
import com.maximillianleonov.cinemax.data.local.source.PopularLocalDataSource
import com.maximillianleonov.cinemax.data.local.source.SettingsLocalDataSource
import com.maximillianleonov.cinemax.data.local.source.TopRatedLocalDataSource
import com.maximillianleonov.cinemax.data.local.source.TrendingLocalDataSource
import com.maximillianleonov.cinemax.data.local.source.UpcomingLocalDataSource
import com.maximillianleonov.cinemax.domain.repository.SettingsRepository
import javax.inject.Inject

@Suppress("LongParameterList")
class SettingsRepositoryImpl @Inject constructor(
    private val localDataSource: SettingsLocalDataSource,
    private val upcomingLocalDataSource: UpcomingLocalDataSource,
    private val topRatedLocalDataSource: TopRatedLocalDataSource,
    private val popularLocalDataSource: PopularLocalDataSource,
    private val nowPlayingLocalDataSource: NowPlayingLocalDataSource,
    private val discoverLocalDataSource: DiscoverLocalDataSource,
    private val trendingLocalDataSource: TrendingLocalDataSource,
    private val detailsLocalDataSource: DetailsLocalDataSource
) : SettingsRepository {
    override suspend fun clearCache() {
        upcomingLocalDataSource.deleteAll()
        topRatedLocalDataSource.deleteAll()
        popularLocalDataSource.deleteAll()
        nowPlayingLocalDataSource.deleteAll()
        discoverLocalDataSource.deleteAll()
        trendingLocalDataSource.deleteAll()
        detailsLocalDataSource.deleteAll()
    }

    override fun getVersion(): String = localDataSource.getVersion()
}
