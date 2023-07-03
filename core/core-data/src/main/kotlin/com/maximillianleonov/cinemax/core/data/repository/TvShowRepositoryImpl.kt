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

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import com.maximillianleonov.cinemax.core.common.result.CinemaxResult
import com.maximillianleonov.cinemax.core.data.mapper.asMediaType
import com.maximillianleonov.cinemax.core.data.mapper.asNetworkMediaType
import com.maximillianleonov.cinemax.core.data.mapper.asTvShowEntity
import com.maximillianleonov.cinemax.core.data.mapper.asTvShowModel
import com.maximillianleonov.cinemax.core.data.mapper.listMap
import com.maximillianleonov.cinemax.core.data.mapper.pagingMap
import com.maximillianleonov.cinemax.core.data.paging.SearchTvShowPagingSource
import com.maximillianleonov.cinemax.core.data.paging.TvShowRemoteMediator
import com.maximillianleonov.cinemax.core.data.util.defaultPagingConfig
import com.maximillianleonov.cinemax.core.database.model.tvshow.TvShowEntity
import com.maximillianleonov.cinemax.core.database.source.TvShowDatabaseDataSource
import com.maximillianleonov.cinemax.core.domain.model.MediaTypeModel
import com.maximillianleonov.cinemax.core.domain.model.TvShowModel
import com.maximillianleonov.cinemax.core.domain.repository.TvShowRepository
import com.maximillianleonov.cinemax.core.network.common.networkBoundResource
import com.maximillianleonov.cinemax.core.network.source.TvShowNetworkDataSource
import com.maximillianleonov.cinemax.core.network.util.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
    private val databaseDataSource: TvShowDatabaseDataSource,
    private val networkDataSource: TvShowNetworkDataSource
) : TvShowRepository {
    override fun getByMediaType(
        mediaTypeModel: MediaTypeModel.TvShow
    ): Flow<CinemaxResult<List<TvShowModel>>> {
        val mediaType = mediaTypeModel.asMediaType()
        return networkBoundResource(
            query = {
                databaseDataSource.getByMediaType(
                    mediaType = mediaType,
                    pageSize = PAGE_SIZE
                ).listMap(TvShowEntity::asTvShowModel)
            },
            fetch = { networkDataSource.getByMediaType(mediaType.asNetworkMediaType()) },
            saveFetchResult = { response ->
                databaseDataSource.deleteByMediaTypeAndInsertAll(
                    mediaType = mediaType,
                    tvShows = response.results.map { it.asTvShowEntity(mediaType) }
                )
            }
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagingByMediaType(
        mediaTypeModel: MediaTypeModel.TvShow
    ): Flow<PagingData<TvShowModel>> {
        val mediaType = mediaTypeModel.asMediaType()
        return Pager(
            config = defaultPagingConfig,
            remoteMediator = TvShowRemoteMediator(databaseDataSource, networkDataSource, mediaType),
            pagingSourceFactory = { databaseDataSource.getPagingByMediaType(mediaType) }
        ).flow.pagingMap(TvShowEntity::asTvShowModel)
    }

    override fun search(query: String): Flow<PagingData<TvShowModel>> = Pager(
        config = defaultPagingConfig,
        pagingSourceFactory = { SearchTvShowPagingSource(query, networkDataSource) }
    ).flow
}
