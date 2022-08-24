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

package com.maximillianleonov.cinemax.data.local.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import com.maximillianleonov.cinemax.core.data.local.common.defaultPagingConfig
import com.maximillianleonov.cinemax.core.data.remote.common.networkBoundResource
import com.maximillianleonov.cinemax.core.domain.result.Result
import com.maximillianleonov.cinemax.data.local.entity.discover.DiscoverMovieEntity
import com.maximillianleonov.cinemax.data.local.entity.discover.DiscoverTvShowEntity
import com.maximillianleonov.cinemax.data.local.mapper.listMap
import com.maximillianleonov.cinemax.data.local.mapper.pagingMap
import com.maximillianleonov.cinemax.data.local.mapper.toDiscoverMovieEntity
import com.maximillianleonov.cinemax.data.local.mapper.toDiscoverTvShowEntity
import com.maximillianleonov.cinemax.data.local.mapper.toMovieModel
import com.maximillianleonov.cinemax.data.local.mapper.toTvShowModel
import com.maximillianleonov.cinemax.data.local.paging.DiscoverMovieRemoteMediator
import com.maximillianleonov.cinemax.data.local.paging.DiscoverTvShowRemoteMediator
import com.maximillianleonov.cinemax.data.local.source.DiscoverLocalDataSource
import com.maximillianleonov.cinemax.data.remote.dto.movie.MovieDto
import com.maximillianleonov.cinemax.data.remote.dto.tvshow.TvShowDto
import com.maximillianleonov.cinemax.data.remote.source.DiscoverRemoteDataSource
import com.maximillianleonov.cinemax.domain.model.MovieModel
import com.maximillianleonov.cinemax.domain.model.TvShowModel
import com.maximillianleonov.cinemax.domain.repository.DiscoverRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverRepositoryImpl @Inject constructor(
    private val localDataSource: DiscoverLocalDataSource,
    private val remoteDataSource: DiscoverRemoteDataSource
) : DiscoverRepository {
    override fun getMovies(): Flow<Result<List<MovieModel>>> = networkBoundResource(
        query = { localDataSource.getMovies().listMap(DiscoverMovieEntity::toMovieModel) },
        fetch = { remoteDataSource.getMovies() },
        saveFetchResult = { response ->
            localDataSource.deleteAndInsertMovies(
                response.results.map(MovieDto::toDiscoverMovieEntity)
            )
        }
    )

    @OptIn(ExperimentalPagingApi::class)
    override fun getMoviesPaging(): Flow<PagingData<MovieModel>> = Pager(
        config = defaultPagingConfig,
        remoteMediator = DiscoverMovieRemoteMediator(localDataSource, remoteDataSource),
        pagingSourceFactory = { localDataSource.getMoviesPaging() }
    ).flow.pagingMap(DiscoverMovieEntity::toMovieModel)

    override fun getTvShows(): Flow<Result<List<TvShowModel>>> = networkBoundResource(
        query = { localDataSource.getTvShows().listMap(DiscoverTvShowEntity::toTvShowModel) },
        fetch = { remoteDataSource.getTvShows() },
        saveFetchResult = { response ->
            localDataSource.deleteAndInsertTvShows(
                response.results.map(TvShowDto::toDiscoverTvShowEntity)
            )
        }
    )

    @OptIn(ExperimentalPagingApi::class)
    override fun getTvShowsPaging(): Flow<PagingData<TvShowModel>> = Pager(
        config = defaultPagingConfig,
        remoteMediator = DiscoverTvShowRemoteMediator(localDataSource, remoteDataSource),
        pagingSourceFactory = { localDataSource.getTvShowsPaging() }
    ).flow.pagingMap(DiscoverTvShowEntity::toTvShowModel)
}
