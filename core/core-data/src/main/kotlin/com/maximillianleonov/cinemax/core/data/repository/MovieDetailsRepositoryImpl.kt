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
import com.maximillianleonov.cinemax.core.data.mapper.asMovieDetailsEntity
import com.maximillianleonov.cinemax.core.data.mapper.asMovieDetailsModel
import com.maximillianleonov.cinemax.core.data.mapper.listMap
import com.maximillianleonov.cinemax.core.database.source.MovieDetailsDatabaseDataSource
import com.maximillianleonov.cinemax.core.database.source.WishlistDatabaseDataSource
import com.maximillianleonov.cinemax.core.datastore.PreferencesDataStoreDataSource
import com.maximillianleonov.cinemax.core.domain.model.MovieDetailsModel
import com.maximillianleonov.cinemax.core.domain.repository.MovieDetailsRepository
import com.maximillianleonov.cinemax.core.network.common.networkBoundResource
import com.maximillianleonov.cinemax.core.network.model.movie.NetworkMovieDetails
import com.maximillianleonov.cinemax.core.network.source.MovieDetailsNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val databaseDataSource: MovieDetailsDatabaseDataSource,
    private val networkDataSource: MovieDetailsNetworkDataSource,
    private val wishlistDatabaseDataSource: WishlistDatabaseDataSource,
    private val preferencesDataStoreDataSource: PreferencesDataStoreDataSource
) : MovieDetailsRepository {
    override fun getById(id: Int): Flow<CinemaxResult<MovieDetailsModel?>> = networkBoundResource(
        query = {
            databaseDataSource.getById(id).map {
                it?.asMovieDetailsModel(
                    isWishlisted = wishlistDatabaseDataSource.isMovieWishlisted(it.id)
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
            databaseDataSource.deleteAndInsert(response.asMovieDetailsEntity())
        }
    )

    override fun getByIds(ids: List<Int>): Flow<CinemaxResult<List<MovieDetailsModel>>> =
        networkBoundResource(
            query = {
                databaseDataSource.getByIds(ids).listMap {
                    it.asMovieDetailsModel(
                        isWishlisted = wishlistDatabaseDataSource.isMovieWishlisted(it.id)
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
                    response.map(NetworkMovieDetails::asMovieDetailsEntity)
                )
            }
        )
}
