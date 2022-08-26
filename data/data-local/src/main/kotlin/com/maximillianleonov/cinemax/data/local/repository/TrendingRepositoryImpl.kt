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
import com.maximillianleonov.cinemax.data.local.entity.trending.TrendingMovieEntity
import com.maximillianleonov.cinemax.data.local.entity.trending.TrendingTvShowEntity
import com.maximillianleonov.cinemax.data.local.mapper.listMap
import com.maximillianleonov.cinemax.data.local.mapper.pagingMap
import com.maximillianleonov.cinemax.data.local.mapper.toMovieModel
import com.maximillianleonov.cinemax.data.local.mapper.toTrendingMovieEntity
import com.maximillianleonov.cinemax.data.local.mapper.toTrendingTvShowEntity
import com.maximillianleonov.cinemax.data.local.mapper.toTvShowModel
import com.maximillianleonov.cinemax.data.local.paging.TrendingMovieRemoteMediator
import com.maximillianleonov.cinemax.data.local.paging.TrendingTvShowRemoteMediator
import com.maximillianleonov.cinemax.data.local.source.TrendingLocalDataSource
import com.maximillianleonov.cinemax.data.remote.dto.movie.MovieDto
import com.maximillianleonov.cinemax.data.remote.dto.tvshow.TvShowDto
import com.maximillianleonov.cinemax.data.remote.source.TrendingRemoteDataSource
import com.maximillianleonov.cinemax.domain.model.MovieModel
import com.maximillianleonov.cinemax.domain.model.TvShowModel
import com.maximillianleonov.cinemax.domain.repository.TrendingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrendingRepositoryImpl @Inject constructor(
    private val localDataSource: TrendingLocalDataSource,
    private val remoteDataSource: TrendingRemoteDataSource
) : TrendingRepository {
    override fun getMovies(): Flow<Result<List<MovieModel>>> = networkBoundResource(
        query = { localDataSource.getMovies().listMap(TrendingMovieEntity::toMovieModel) },
        fetch = { remoteDataSource.getMovies() },
        saveFetchResult = { response ->
            localDataSource.deleteAndInsertMovies(
                response.results.map(MovieDto::toTrendingMovieEntity)
            )
        }
    )

    @OptIn(ExperimentalPagingApi::class)
    override fun getMoviesPaging(): Flow<PagingData<MovieModel>> = Pager(
        config = defaultPagingConfig,
        remoteMediator = TrendingMovieRemoteMediator(localDataSource, remoteDataSource),
        pagingSourceFactory = { localDataSource.getMoviesPaging() }
    ).flow.pagingMap(TrendingMovieEntity::toMovieModel)

    override fun getTvShows(): Flow<Result<List<TvShowModel>>> = networkBoundResource(
        query = { localDataSource.getTvShows().listMap(TrendingTvShowEntity::toTvShowModel) },
        fetch = { remoteDataSource.getTvShows() },
        saveFetchResult = { response ->
            localDataSource.deleteAndInsertTvShows(
                response.results.map(TvShowDto::toTrendingTvShowEntity)
            )
        }
    )

    @OptIn(ExperimentalPagingApi::class)
    override fun getTvShowsPaging(): Flow<PagingData<TvShowModel>> = Pager(
        config = defaultPagingConfig,
        remoteMediator = TrendingTvShowRemoteMediator(localDataSource, remoteDataSource),
        pagingSourceFactory = { localDataSource.getTvShowsPaging() }
    ).flow.pagingMap(TrendingTvShowEntity::toTvShowModel)
}
