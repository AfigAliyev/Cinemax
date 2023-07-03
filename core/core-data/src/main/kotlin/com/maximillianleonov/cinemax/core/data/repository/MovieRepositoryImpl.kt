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
import com.maximillianleonov.cinemax.core.data.mapper.asMovieEntity
import com.maximillianleonov.cinemax.core.data.mapper.asMovieModel
import com.maximillianleonov.cinemax.core.data.mapper.asNetworkMediaType
import com.maximillianleonov.cinemax.core.data.mapper.listMap
import com.maximillianleonov.cinemax.core.data.mapper.pagingMap
import com.maximillianleonov.cinemax.core.data.paging.MovieRemoteMediator
import com.maximillianleonov.cinemax.core.data.paging.SearchMoviePagingSource
import com.maximillianleonov.cinemax.core.data.util.defaultPagingConfig
import com.maximillianleonov.cinemax.core.database.model.movie.MovieEntity
import com.maximillianleonov.cinemax.core.database.source.MovieDatabaseDataSource
import com.maximillianleonov.cinemax.core.domain.model.MediaTypeModel
import com.maximillianleonov.cinemax.core.domain.model.MovieModel
import com.maximillianleonov.cinemax.core.domain.repository.MovieRepository
import com.maximillianleonov.cinemax.core.network.common.networkBoundResource
import com.maximillianleonov.cinemax.core.network.source.MovieNetworkDataSource
import com.maximillianleonov.cinemax.core.network.util.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val databaseDataSource: MovieDatabaseDataSource,
    private val networkDataSource: MovieNetworkDataSource
) : MovieRepository {
    override fun getByMediaType(
        mediaTypeModel: MediaTypeModel.Movie
    ): Flow<CinemaxResult<List<MovieModel>>> {
        val mediaType = mediaTypeModel.asMediaType()
        return networkBoundResource(
            query = {
                databaseDataSource.getByMediaType(
                    mediaType = mediaType,
                    pageSize = PAGE_SIZE
                ).listMap(MovieEntity::asMovieModel)
            },
            fetch = { networkDataSource.getByMediaType(mediaType.asNetworkMediaType()) },
            saveFetchResult = { response ->
                databaseDataSource.deleteByMediaTypeAndInsertAll(
                    mediaType = mediaType,
                    movies = response.results.map { it.asMovieEntity(mediaType) }
                )
            }
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagingByMediaType(
        mediaTypeModel: MediaTypeModel.Movie
    ): Flow<PagingData<MovieModel>> {
        val mediaType = mediaTypeModel.asMediaType()
        return Pager(
            config = defaultPagingConfig,
            remoteMediator = MovieRemoteMediator(databaseDataSource, networkDataSource, mediaType),
            pagingSourceFactory = { databaseDataSource.getPagingByMediaType(mediaType) }
        ).flow.pagingMap(MovieEntity::asMovieModel)
    }

    override fun search(query: String): Flow<PagingData<MovieModel>> = Pager(
        config = defaultPagingConfig,
        pagingSourceFactory = { SearchMoviePagingSource(query, networkDataSource) }
    ).flow
}
