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
import com.maximillianleonov.cinemax.data.local.entity.toprated.TopRatedMovieEntity
import com.maximillianleonov.cinemax.data.local.entity.toprated.TopRatedMovieRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.mapper.toTopRatedMovieEntity
import com.maximillianleonov.cinemax.data.local.source.TopRatedLocalDataSource
import com.maximillianleonov.cinemax.data.remote.dto.MovieResponseDto
import com.maximillianleonov.cinemax.data.remote.dto.movie.MovieDto
import com.maximillianleonov.cinemax.data.remote.source.TopRatedRemoteDataSource

class TopRatedMovieRemoteMediator(
    private val localDataSource: TopRatedLocalDataSource,
    private val remoteDataSource: TopRatedRemoteDataSource
) : DefaultRemoteMediator<TopRatedMovieEntity,
    TopRatedMovieRemoteKeyEntity,
    MovieDto,
    MovieResponseDto>() {

    override suspend fun getDataFromService(page: Int) = remoteDataSource.getMovies(page = page)

    override fun dtoToEntity(dto: MovieDto) = dto.toTopRatedMovieEntity()

    override fun entityToRemoteKey(
        id: Int,
        prevPage: Int?,
        nextPage: Int?
    ) = TopRatedMovieRemoteKeyEntity(
        id = id,
        prevPage = prevPage,
        nextPage = nextPage
    )

    override suspend fun getRemoteKeyById(id: Int) = localDataSource.getMovieRemoteKeyById(id = id)

    override suspend fun deleteAndInsertAll(
        isLoadTypeRefresh: Boolean,
        remoteKeys: List<TopRatedMovieRemoteKeyEntity>,
        data: List<TopRatedMovieEntity>
    ) = localDataSource.withTransaction {
        if (isLoadTypeRefresh) {
            localDataSource.deleteMoviesAndRemoteKeys()
        }
        localDataSource.insertMoviesAndRemoteKeys(data = data, remoteKeys = remoteKeys)
    }
}
