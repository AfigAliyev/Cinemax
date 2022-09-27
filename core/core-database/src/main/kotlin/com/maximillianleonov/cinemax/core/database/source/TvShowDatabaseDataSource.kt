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

package com.maximillianleonov.cinemax.core.database.source

import androidx.paging.PagingSource
import com.maximillianleonov.cinemax.core.database.dao.tvshow.TvShowDao
import com.maximillianleonov.cinemax.core.database.dao.tvshow.TvShowRemoteKeyDao
import com.maximillianleonov.cinemax.core.database.model.common.MediaType
import com.maximillianleonov.cinemax.core.database.model.tvshow.TvShowEntity
import com.maximillianleonov.cinemax.core.database.model.tvshow.TvShowRemoteKeyEntity
import com.maximillianleonov.cinemax.core.database.util.CinemaxDatabaseTransactionProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvShowDatabaseDataSource @Inject constructor(
    private val tvShowDao: TvShowDao,
    private val tvShowRemoteKeyDao: TvShowRemoteKeyDao,
    private val transactionProvider: CinemaxDatabaseTransactionProvider
) {
    fun getByMediaType(mediaType: MediaType.TvShow, pageSize: Int): Flow<List<TvShowEntity>> =
        tvShowDao.getByMediaType(mediaType, pageSize)

    fun getPagingByMediaType(mediaType: MediaType.TvShow): PagingSource<Int, TvShowEntity> =
        tvShowDao.getPagingByMediaType(mediaType)

    suspend fun insertAll(tvShows: List<TvShowEntity>) = tvShowDao.insertAll(tvShows)

    suspend fun deleteByMediaTypeAndInsertAll(
        mediaType: MediaType.TvShow,
        tvShows: List<TvShowEntity>
    ) = transactionProvider.runWithTransaction {
        tvShowDao.deleteByMediaType(mediaType)
        tvShowDao.insertAll(tvShows)
    }

    suspend fun deleteByMediaType(mediaType: MediaType.TvShow) =
        tvShowDao.deleteByMediaType(mediaType)

    suspend fun deleteAll() = tvShowDao.deleteAll()

    suspend fun getRemoteKeyByIdAndMediaType(id: Int, mediaType: MediaType.TvShow) =
        tvShowRemoteKeyDao.getByIdAndMediaType(id, mediaType)

    suspend fun handlePaging(
        mediaType: MediaType.TvShow,
        tvShows: List<TvShowEntity>,
        remoteKeys: List<TvShowRemoteKeyEntity>,
        shouldDeleteTvShowsAndRemoteKeys: Boolean
    ) = transactionProvider.runWithTransaction {
        if (shouldDeleteTvShowsAndRemoteKeys) {
            tvShowDao.deleteByMediaType(mediaType)
            tvShowRemoteKeyDao.deleteByMediaType(mediaType)
        }
        tvShowRemoteKeyDao.insertAll(remoteKeys)
        tvShowDao.insertAll(tvShows)
    }
}
