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
import com.maximillianleonov.cinemax.data.local.entity.upcoming.UpcomingMovieEntity
import com.maximillianleonov.cinemax.data.local.entity.upcoming.UpcomingMovieRemoteKeyEntity
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(private val db: CinemaxDatabase) {
    private val upcomingMovieDao = db.upcomingMovieDao
    private val upcomingMovieRemoteKeyDao = db.upcomingMovieRemoteKeyDao

    fun getUpcomingMovies() = upcomingMovieDao.getAll()
    fun getUpcomingMoviesPaging() = upcomingMovieDao.getAllPaging()
    suspend fun getUpcomingMovieRemoteKeyById(id: Int) = upcomingMovieRemoteKeyDao.getById(id = id)
    suspend fun insertUpcomingMoviesAndRemoteKeys(
        data: List<UpcomingMovieEntity>,
        remoteKeys: List<UpcomingMovieRemoteKeyEntity>
    ) = db.withTransaction {
        upcomingMovieRemoteKeyDao.insertAll(entities = remoteKeys)
        upcomingMovieDao.insertAll(entities = data)
    }
    suspend fun deleteAndInsertUpcomingMovies(entities: List<UpcomingMovieEntity>) =
        db.withTransaction {
            upcomingMovieDao.deleteAll()
            upcomingMovieDao.insertAll(entities)
        }

    suspend fun deleteUpcomingMoviesAndRemoteKeys() = db.withTransaction {
        upcomingMovieDao.deleteAll()
        upcomingMovieRemoteKeyDao.deleteAll()
    }

    suspend fun withTransaction(block: suspend () -> Unit) = db.withTransaction(block)
}
