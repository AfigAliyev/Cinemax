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

package com.maximillianleonov.cinemax.data.local.paging

import com.maximillianleonov.cinemax.core.data.local.common.DefaultRemoteMediator
import com.maximillianleonov.cinemax.data.local.entity.nowplaying.NowPlayingTvShowEntity
import com.maximillianleonov.cinemax.data.local.entity.nowplaying.NowPlayingTvShowRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.mapper.toNowPlayingTvShowEntity
import com.maximillianleonov.cinemax.data.local.source.NowPlayingLocalDataSource
import com.maximillianleonov.cinemax.data.remote.dto.response.TvShowResponseDto
import com.maximillianleonov.cinemax.data.remote.dto.tvshow.TvShowDto
import com.maximillianleonov.cinemax.data.remote.source.NowPlayingRemoteDataSource

class NowPlayingTvShowRemoteMediator(
    private val localDataSource: NowPlayingLocalDataSource,
    private val remoteDataSource: NowPlayingRemoteDataSource
) : DefaultRemoteMediator<NowPlayingTvShowEntity,
    NowPlayingTvShowRemoteKeyEntity,
    TvShowDto,
    TvShowResponseDto>() {

    override suspend fun getDataFromService(page: Int) = remoteDataSource.getTvShows(page)

    override fun dtoToEntity(dto: TvShowDto) = dto.toNowPlayingTvShowEntity()

    override fun entityToRemoteKey(
        id: Int,
        prevPage: Int?,
        nextPage: Int?
    ) = NowPlayingTvShowRemoteKeyEntity(
        id = id,
        prevPage = prevPage,
        nextPage = nextPage
    )

    override suspend fun getRemoteKeyById(id: Int) = localDataSource.getTvShowRemoteKeyById(id)

    override suspend fun deleteAndInsertAll(
        isLoadTypeRefresh: Boolean,
        remoteKeys: List<NowPlayingTvShowRemoteKeyEntity>,
        data: List<NowPlayingTvShowEntity>
    ) = localDataSource.handleTvShowsPaging(
        shouldDeleteTvShowsAndRemoteKeys = isLoadTypeRefresh,
        remoteKeys = remoteKeys,
        tvShows = data
    )
}
