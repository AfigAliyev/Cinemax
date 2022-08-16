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
import com.maximillianleonov.cinemax.data.local.entity.upcoming.UpcomingMovieEntity
import com.maximillianleonov.cinemax.data.local.entity.upcoming.UpcomingMovieRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.mapper.toUpcomingMovieEntity
import com.maximillianleonov.cinemax.data.local.source.MovieLocalDataSource
import com.maximillianleonov.cinemax.data.remote.dto.MovieResponseDto
import com.maximillianleonov.cinemax.data.remote.dto.movie.MovieDto
import com.maximillianleonov.cinemax.data.remote.source.MovieRemoteDataSource

class UpcomingMovieRemoteMediator(
    private val localDataSource: MovieLocalDataSource,
    private val remoteDataSource: MovieRemoteDataSource
) : DefaultRemoteMediator<UpcomingMovieEntity,
    UpcomingMovieRemoteKeyEntity,
    MovieDto,
    MovieResponseDto>() {

    override suspend fun getDataFromService(page: Int) =
        remoteDataSource.getUpcomingMovies(page = page)

    override fun dtoToEntity(dto: MovieDto) = dto.toUpcomingMovieEntity()

    override fun entityToRemoteKey(
        id: Int,
        prevPage: Int?,
        nextPage: Int?
    ) = UpcomingMovieRemoteKeyEntity(
        id = id,
        prevPage = prevPage,
        nextPage = nextPage
    )

    override suspend fun getRemoteKeyById(id: Int) =
        localDataSource.getUpcomingMovieRemoteKeyById(id = id)

    override suspend fun deleteAndInsertAll(
        isLoadTypeRefresh: Boolean,
        remoteKeys: List<UpcomingMovieRemoteKeyEntity>,
        data: List<UpcomingMovieEntity>
    ) = localDataSource.withTransaction {
        if (isLoadTypeRefresh) {
            localDataSource.deleteUpcomingMoviesAndRemoteKeys()
        }
        localDataSource.insertUpcomingMoviesAndRemoteKeys(data = data, remoteKeys = remoteKeys)
    }
}
