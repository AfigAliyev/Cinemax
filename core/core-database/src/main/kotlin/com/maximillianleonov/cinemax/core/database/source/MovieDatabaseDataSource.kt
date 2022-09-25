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

package com.maximillianleonov.cinemax.core.database.source

import androidx.paging.PagingSource
import com.maximillianleonov.cinemax.core.database.dao.movie.MovieDao
import com.maximillianleonov.cinemax.core.database.dao.movie.MovieRemoteKeyDao
import com.maximillianleonov.cinemax.core.database.model.common.MediaType
import com.maximillianleonov.cinemax.core.database.model.movie.MovieEntity
import com.maximillianleonov.cinemax.core.database.model.movie.MovieRemoteKeyEntity
import com.maximillianleonov.cinemax.core.database.util.CinemaxDatabaseTransactionProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDatabaseDataSource @Inject constructor(
    private val movieDao: MovieDao,
    private val movieRemoteKeyDao: MovieRemoteKeyDao,
    private val transactionProvider: CinemaxDatabaseTransactionProvider
) {
    fun getByMediaType(mediaType: MediaType.Movie): Flow<List<MovieEntity>> =
        movieDao.getByMediaType(mediaType)

    fun getPagingByMediaType(mediaType: MediaType.Movie): PagingSource<Int, MovieEntity> =
        movieDao.getPagingByMediaType(mediaType)

    suspend fun insertAll(movies: List<MovieEntity>) = movieDao.insertAll(movies)

    suspend fun deleteByMediaTypeAndInsertAll(
        mediaType: MediaType.Movie,
        movies: List<MovieEntity>
    ) = transactionProvider.runWithTransaction {
        movieDao.deleteByMediaType(mediaType)
        movieDao.insertAll(movies)
    }

    suspend fun deleteByMediaType(mediaType: MediaType.Movie) =
        movieDao.deleteByMediaType(mediaType)

    suspend fun deleteAll() = movieDao.deleteAll()

    suspend fun getRemoteKeyByIdAndMediaType(id: Int, mediaType: MediaType.Movie) =
        movieRemoteKeyDao.getByIdAndMediaType(id, mediaType)

    suspend fun handlePaging(
        mediaType: MediaType.Movie,
        movies: List<MovieEntity>,
        remoteKeys: List<MovieRemoteKeyEntity>,
        shouldDeleteMoviesAndRemoteKeys: Boolean
    ) = transactionProvider.runWithTransaction {
        if (shouldDeleteMoviesAndRemoteKeys) {
            movieDao.deleteByMediaType(mediaType)
            movieRemoteKeyDao.deleteByMediaType(mediaType)
        }
        movieRemoteKeyDao.insertAll(remoteKeys)
        movieDao.insertAll(movies)
    }
}
