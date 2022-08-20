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

package com.maximillianleonov.cinemax.data.local.source

import androidx.room.withTransaction
import com.maximillianleonov.cinemax.data.local.db.CinemaxDatabase
import com.maximillianleonov.cinemax.data.local.entity.toprated.TopRatedMovieEntity
import com.maximillianleonov.cinemax.data.local.entity.toprated.TopRatedMovieRemoteKeyEntity
import javax.inject.Inject

class TopRatedLocalDataSource @Inject constructor(private val db: CinemaxDatabase) {
    private val movieDao = db.topRatedMovieDao
    private val movieRemoteKeyDao = db.topRatedMovieRemoteKeyDao

    fun getMovies() = movieDao.getAll()
    fun getMoviesPaging() = movieDao.getAllPaging()
    suspend fun getMovieRemoteKeyById(id: Int) = movieRemoteKeyDao.getById(id = id)

    suspend fun insertMoviesAndRemoteKeys(
        data: List<TopRatedMovieEntity>,
        remoteKeys: List<TopRatedMovieRemoteKeyEntity>
    ) = db.withTransaction {
        movieRemoteKeyDao.insertAll(entities = remoteKeys)
        movieDao.insertAll(entities = data)
    }

    suspend fun deleteAndInsertMovies(entities: List<TopRatedMovieEntity>) = db.withTransaction {
        movieDao.deleteAll()
        movieDao.insertAll(entities)
    }

    suspend fun deleteMoviesAndRemoteKeys() = db.withTransaction {
        movieDao.deleteAll()
        movieRemoteKeyDao.deleteAll()
    }

    suspend fun withTransaction(block: suspend () -> Unit) = db.withTransaction(block)
}
