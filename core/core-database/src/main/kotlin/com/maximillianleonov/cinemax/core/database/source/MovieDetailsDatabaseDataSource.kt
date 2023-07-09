/*
 * Copyright 2022 Afig Aliyev
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

import com.maximillianleonov.cinemax.core.database.dao.movie.MovieDetailsDao
import com.maximillianleonov.cinemax.core.database.model.movie.MovieDetailsEntity
import com.maximillianleonov.cinemax.core.database.util.CinemaxDatabaseTransactionProvider
import javax.inject.Inject

class MovieDetailsDatabaseDataSource @Inject constructor(
    private val movieDetailsDao: MovieDetailsDao,
    private val transactionProvider: CinemaxDatabaseTransactionProvider
) {
    fun getById(id: Int) = movieDetailsDao.getById(id)
    fun getByIds(ids: List<Int>) = movieDetailsDao.getByIds(ids)

    suspend fun deleteAndInsert(movieDetails: MovieDetailsEntity) =
        transactionProvider.runWithTransaction {
            movieDetailsDao.deleteById(id = movieDetails.id)
            movieDetailsDao.insert(movieDetails)
        }

    suspend fun deleteAndInsertAll(movieDetailsList: List<MovieDetailsEntity>) =
        transactionProvider.runWithTransaction {
            movieDetailsList.forEach { movieDetails ->
                movieDetailsDao.deleteById(id = movieDetails.id)
                movieDetailsDao.insert(movieDetails)
            }
        }
}
