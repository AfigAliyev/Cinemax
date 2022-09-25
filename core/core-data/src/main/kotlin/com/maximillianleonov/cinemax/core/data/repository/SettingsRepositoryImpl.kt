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

package com.maximillianleonov.cinemax.core.data.repository

import com.maximillianleonov.cinemax.core.database.source.MovieDatabaseDataSource
import com.maximillianleonov.cinemax.core.database.source.MovieDetailsDatabaseDataSource
import com.maximillianleonov.cinemax.core.database.source.SettingsDatabaseDataSource
import com.maximillianleonov.cinemax.core.database.source.TvShowDatabaseDataSource
import com.maximillianleonov.cinemax.core.database.source.TvShowDetailsDatabaseDataSource
import com.maximillianleonov.cinemax.core.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val databaseDataSource: SettingsDatabaseDataSource,
    private val movieDatabaseDataSource: MovieDatabaseDataSource,
    private val tvShowDatabaseDataSource: TvShowDatabaseDataSource,
    private val movieDetailsDatabaseDataSource: MovieDetailsDatabaseDataSource,
    private val tvShowDetailsDatabaseDataSource: TvShowDetailsDatabaseDataSource
) : SettingsRepository {
    override fun getVersion(): String = databaseDataSource.getVersion()

    override suspend fun clearCache() {
        movieDatabaseDataSource.deleteAll()
        tvShowDatabaseDataSource.deleteAll()
        movieDetailsDatabaseDataSource.deleteAll()
        tvShowDetailsDatabaseDataSource.deleteAll()
    }
}
