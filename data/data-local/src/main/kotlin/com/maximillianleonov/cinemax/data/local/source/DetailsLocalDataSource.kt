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
import com.maximillianleonov.cinemax.data.local.entity.details.MovieDetailsEntity
import com.maximillianleonov.cinemax.data.local.entity.details.TvShowDetailsEntity
import javax.inject.Inject

class DetailsLocalDataSource @Inject constructor(private val db: CinemaxDatabase) {
    private val movieDao = db.movieDetailsDao
    private val tvShowDao = db.tvShowDetailsDao

    fun getMovieById(id: Int) = movieDao.getById(id)
    fun getTvShowById(id: Int) = tvShowDao.getById(id)

    fun getMoviesByIds(ids: List<Int>) = movieDao.getByIds(ids)
    fun getTvShowsByIds(ids: List<Int>) = tvShowDao.getByIds(ids)

    suspend fun deleteAndInsertMovie(entity: MovieDetailsEntity) = db.withTransaction {
        movieDao.deleteById(entity.id)
        movieDao.insert(entity)
    }

    suspend fun deleteAndInsertMovies(entities: List<MovieDetailsEntity>) = db.withTransaction {
        entities.forEach { entity ->
            movieDao.deleteById(entity.id)
            movieDao.insert(entity)
        }
    }

    suspend fun deleteAndInsertTvShow(entity: TvShowDetailsEntity) = db.withTransaction {
        tvShowDao.deleteById(entity.id)
        tvShowDao.insert(entity)
    }

    suspend fun deleteAndInsertTvShows(entities: List<TvShowDetailsEntity>) = db.withTransaction {
        entities.forEach { entity ->
            tvShowDao.deleteById(entity.id)
            tvShowDao.insert(entity)
        }
    }

    suspend fun deleteAll() {
        movieDao.deleteAll()
        tvShowDao.deleteAll()
    }
}
