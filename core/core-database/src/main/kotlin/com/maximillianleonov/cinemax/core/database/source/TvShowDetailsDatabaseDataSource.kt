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

import com.maximillianleonov.cinemax.core.database.dao.tvshow.TvShowDetailsDao
import com.maximillianleonov.cinemax.core.database.model.tvshow.TvShowDetailsEntity
import com.maximillianleonov.cinemax.core.database.util.CinemaxDatabaseTransactionProvider
import javax.inject.Inject

class TvShowDetailsDatabaseDataSource @Inject constructor(
    private val tvShowDetailsDao: TvShowDetailsDao,
    private val transactionProvider: CinemaxDatabaseTransactionProvider
) {
    fun getById(id: Int) = tvShowDetailsDao.getById(id)
    fun getByIds(ids: List<Int>) = tvShowDetailsDao.getByIds(ids)

    suspend fun deleteAndInsert(tvShowDetails: TvShowDetailsEntity) =
        transactionProvider.runWithTransaction {
            tvShowDetailsDao.deleteById(id = tvShowDetails.id)
            tvShowDetailsDao.insert(tvShowDetails)
        }

    suspend fun deleteAndInsertAll(tvShowDetailsList: List<TvShowDetailsEntity>) =
        transactionProvider.runWithTransaction {
            tvShowDetailsList.forEach { tvShowDetails ->
                tvShowDetailsDao.deleteById(id = tvShowDetails.id)
                tvShowDetailsDao.insert(tvShowDetails)
            }
        }

    suspend fun deleteAll() {
        tvShowDetailsDao.deleteAll()
    }
}
