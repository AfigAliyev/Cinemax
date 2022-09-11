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
import com.maximillianleonov.cinemax.data.local.entity.popular.PopularMovieEntity
import com.maximillianleonov.cinemax.data.local.entity.popular.PopularMovieRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.mapper.toPopularMovieEntity
import com.maximillianleonov.cinemax.data.local.source.PopularLocalDataSource
import com.maximillianleonov.cinemax.data.remote.dto.movie.MovieDto
import com.maximillianleonov.cinemax.data.remote.dto.response.MovieResponseDto
import com.maximillianleonov.cinemax.data.remote.source.PopularRemoteDataSource

class PopularMovieRemoteMediator(
    private val localDataSource: PopularLocalDataSource,
    private val remoteDataSource: PopularRemoteDataSource
) : DefaultRemoteMediator<PopularMovieEntity,
    PopularMovieRemoteKeyEntity,
    MovieDto,
    MovieResponseDto>() {

    override suspend fun getDataFromService(page: Int) = remoteDataSource.getMovies(page)

    override fun dtoToEntity(dto: MovieDto) = dto.toPopularMovieEntity()

    override fun entityToRemoteKey(
        id: Int,
        prevPage: Int?,
        nextPage: Int?
    ) = PopularMovieRemoteKeyEntity(
        id = id,
        prevPage = prevPage,
        nextPage = nextPage
    )

    override suspend fun getRemoteKeyById(id: Int) = localDataSource.getMovieRemoteKeyById(id)

    override suspend fun deleteAndInsertAll(
        isLoadTypeRefresh: Boolean,
        remoteKeys: List<PopularMovieRemoteKeyEntity>,
        data: List<PopularMovieEntity>
    ) = localDataSource.handleMoviesPaging(
        shouldDeleteMoviesAndRemoteKeys = isLoadTypeRefresh,
        remoteKeys = remoteKeys,
        movies = data
    )
}
