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

import com.maximillianleonov.cinemax.core.common.result.CinemaxResult
import com.maximillianleonov.cinemax.core.data.mapper.asTvShowDetailsEntity
import com.maximillianleonov.cinemax.core.data.mapper.asTvShowDetailsModel
import com.maximillianleonov.cinemax.core.data.mapper.listMap
import com.maximillianleonov.cinemax.core.database.source.TvShowDetailsDatabaseDataSource
import com.maximillianleonov.cinemax.core.database.source.WishlistDatabaseDataSource
import com.maximillianleonov.cinemax.core.datastore.PreferencesDataStoreDataSource
import com.maximillianleonov.cinemax.core.domain.model.TvShowDetailsModel
import com.maximillianleonov.cinemax.core.domain.repository.TvShowDetailsRepository
import com.maximillianleonov.cinemax.core.network.common.networkBoundResource
import com.maximillianleonov.cinemax.core.network.model.tvshow.NetworkTvShowDetails
import com.maximillianleonov.cinemax.core.network.source.TvShowDetailsNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TvShowDetailsRepositoryImpl @Inject constructor(
    private val databaseDataSource: TvShowDetailsDatabaseDataSource,
    private val networkDataSource: TvShowDetailsNetworkDataSource,
    private val wishlistDatabaseDataSource: WishlistDatabaseDataSource,
    private val preferencesDataStoreDataSource: PreferencesDataStoreDataSource
) : TvShowDetailsRepository {
    override fun getById(id: Int): Flow<CinemaxResult<TvShowDetailsModel?>> = networkBoundResource(
        query = {
            databaseDataSource.getById(id).map {
                it?.asTvShowDetailsModel(
                    isWishlisted = wishlistDatabaseDataSource.isTvShowWishlisted(it.id)
                )
            }
        },
        fetch = {
            networkDataSource.getById(
                id = id,
                language = preferencesDataStoreDataSource.getContentLanguage().first()
            )
        },
        saveFetchResult = { response ->
            databaseDataSource.deleteAndInsert(response.asTvShowDetailsEntity())
        }
    )

    override fun getByIds(ids: List<Int>): Flow<CinemaxResult<List<TvShowDetailsModel>>> =
        networkBoundResource(
            query = {
                databaseDataSource.getByIds(ids).listMap {
                    it.asTvShowDetailsModel(
                        isWishlisted = wishlistDatabaseDataSource.isTvShowWishlisted(it.id)
                    )
                }
            },
            fetch = {
                networkDataSource.getByIds(
                    ids = ids,
                    language = preferencesDataStoreDataSource.getContentLanguage().first()
                )
            },
            saveFetchResult = { response ->
                databaseDataSource.deleteAndInsertAll(
                    response.map(NetworkTvShowDetails::asTvShowDetailsEntity)
                )
            }
        )
}
