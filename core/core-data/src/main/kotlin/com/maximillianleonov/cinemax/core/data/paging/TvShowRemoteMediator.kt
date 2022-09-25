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

package com.maximillianleonov.cinemax.core.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.maximillianleonov.cinemax.core.common.result.HttpException
import com.maximillianleonov.cinemax.core.common.result.isFailure
import com.maximillianleonov.cinemax.core.common.result.isSuccess
import com.maximillianleonov.cinemax.core.data.mapper.asNetworkMediaType
import com.maximillianleonov.cinemax.core.data.mapper.asTvShowEntity
import com.maximillianleonov.cinemax.core.data.util.Constants
import com.maximillianleonov.cinemax.core.data.util.PagingUtils.getRemoteKeyClosestToCurrentPosition
import com.maximillianleonov.cinemax.core.data.util.PagingUtils.getRemoteKeyForFirstItem
import com.maximillianleonov.cinemax.core.data.util.PagingUtils.getRemoteKeyForLastItem
import com.maximillianleonov.cinemax.core.database.model.common.MediaType
import com.maximillianleonov.cinemax.core.database.model.tvshow.TvShowEntity
import com.maximillianleonov.cinemax.core.database.model.tvshow.TvShowRemoteKeyEntity
import com.maximillianleonov.cinemax.core.database.source.TvShowDatabaseDataSource
import com.maximillianleonov.cinemax.core.network.source.TvShowNetworkDataSource
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class TvShowRemoteMediator(
    private val databaseDataSource: TvShowDatabaseDataSource,
    private val networkDataSource: TvShowNetworkDataSource,
    private val mediaType: MediaType.TvShow
) : RemoteMediator<Int, TvShowEntity>() {
    @Suppress("ReturnCount")
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TvShowEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKey?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKey = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKey?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKey != null
                    )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKey?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKey != null
                    )
                    nextPage
                }
            }

            val response = networkDataSource.getByMediaType(
                mediaType = mediaType.asNetworkMediaType(),
                page = currentPage
            )

            when {
                response.isSuccess() -> {
                    val tvShows = response.value.results.map { it.asTvShowEntity(mediaType) }
                    val endOfPaginationReached = tvShows.isEmpty()

                    val prevPage = if (currentPage == 1) null else currentPage - 1
                    val nextPage = if (endOfPaginationReached) null else currentPage + 1

                    val remoteKeys = tvShows.map { entity ->
                        TvShowRemoteKeyEntity(
                            id = entity.networkId,
                            mediaType = mediaType,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }

                    databaseDataSource.handlePaging(
                        mediaType = mediaType,
                        tvShows = tvShows,
                        remoteKeys = remoteKeys,
                        shouldDeleteTvShowsAndRemoteKeys = loadType == LoadType.REFRESH
                    )

                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }
                response.isFailure() -> return MediatorResult.Error(response.error)
                else -> error("${Constants.Messages.UNHANDLED_STATE} $response")
            }
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, TvShowEntity>
    ): TvShowRemoteKeyEntity? = getRemoteKeyClosestToCurrentPosition(state) { entity ->
        databaseDataSource.getRemoteKeyByIdAndMediaType(
            id = entity.networkId,
            mediaType = mediaType
        )
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, TvShowEntity>
    ): TvShowRemoteKeyEntity? = getRemoteKeyForFirstItem(state) { entity ->
        databaseDataSource.getRemoteKeyByIdAndMediaType(
            id = entity.networkId,
            mediaType = mediaType
        )
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, TvShowEntity>
    ): TvShowRemoteKeyEntity? = getRemoteKeyForLastItem(state) { entity ->
        databaseDataSource.getRemoteKeyByIdAndMediaType(
            id = entity.networkId,
            mediaType = mediaType
        )
    }
}
