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

package com.maximillianleonov.cinemax.core.data.local.common

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.maximillianleonov.cinemax.core.data.remote.common.ContentDto
import com.maximillianleonov.cinemax.core.data.remote.common.ResponseDto
import com.maximillianleonov.cinemax.core.data.util.Constants
import com.maximillianleonov.cinemax.core.domain.result.HttpException
import com.maximillianleonov.cinemax.core.domain.result.Result
import com.maximillianleonov.cinemax.core.domain.result.isFailure
import com.maximillianleonov.cinemax.core.domain.result.isSuccess
import java.io.IOException

interface DefaultRemoteMediatorCallback {
    suspend fun getRemoteKeyById(id: Int): RemoteKeyEntity
    suspend fun deleteAndInsertAll(
        isLoadTypeRefresh: Boolean,
        remoteKeys: List<RemoteKeyEntity>,
        data: List<ContentEntity>
    )
}

@OptIn(ExperimentalPagingApi::class)
abstract class DefaultRemoteMediator(
    private val callback: DefaultRemoteMediatorCallback
) : RemoteMediator<Int, ContentEntity>() {

    protected abstract suspend fun getDataFromService(page: Int): Result<ResponseDto<ContentDto>>
    protected abstract fun dtoToEntity(dto: ContentDto): ContentEntity
    protected abstract fun entityToRemoteKey(
        id: Int,
        prevPage: Int?,
        nextPage: Int?
    ): RemoteKeyEntity

    @Suppress("ReturnCount")
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ContentEntity>
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

            val response = getDataFromService(currentPage)

            when {
                response.isSuccess() -> {
                    val data = response.value.results.map(::dtoToEntity)
                    val endOfPaginationReached = data.isEmpty()

                    val prevPage = if (currentPage == 1) null else currentPage - 1
                    val nextPage = if (endOfPaginationReached) null else currentPage + 1

                    val remoteKeys = data.map { entity ->
                        entityToRemoteKey(
                            id = entity.remoteId,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }

                    callback.deleteAndInsertAll(
                        isLoadTypeRefresh = loadType == LoadType.REFRESH,
                        remoteKeys = remoteKeys,
                        data = data
                    )

                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }
                response.isFailure() -> {
                    return MediatorResult.Error(response.error)
                }
                else -> error("${Constants.Messages.UNHANDLED_STATE} $response")
            }
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ContentEntity>
    ): RemoteKeyEntity? = state.anchorPosition?.let { position ->
        state.closestItemToPosition(position)?.let { entity ->
            callback.getRemoteKeyById(id = entity.remoteId)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, ContentEntity>
    ): RemoteKeyEntity? = state.pages.firstOrNull {
        it.data.isNotEmpty()
    }?.data?.firstOrNull()?.let { entity ->
        callback.getRemoteKeyById(id = entity.remoteId)
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, ContentEntity>
    ): RemoteKeyEntity? = state.pages.lastOrNull {
        it.data.isNotEmpty()
    }?.data?.lastOrNull()?.let { entity ->
        callback.getRemoteKeyById(id = entity.remoteId)
    }
}
