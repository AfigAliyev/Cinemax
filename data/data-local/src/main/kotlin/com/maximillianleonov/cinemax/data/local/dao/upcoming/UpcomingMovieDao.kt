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

package com.maximillianleonov.cinemax.data.local.dao.upcoming

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maximillianleonov.cinemax.core.data.local.common.ContentDao
import com.maximillianleonov.cinemax.core.data.util.Constants.Tables.UPCOMING_MOVIES
import com.maximillianleonov.cinemax.data.local.entity.upcoming.UpcomingMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UpcomingMovieDao : ContentDao<UpcomingMovieEntity> {
    @Query("SELECT * FROM $UPCOMING_MOVIES")
    override fun getAll(): Flow<List<UpcomingMovieEntity>>

    @Query("SELECT * FROM $UPCOMING_MOVIES")
    override fun getAllPaging(): PagingSource<Int, UpcomingMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertAll(entities: List<UpcomingMovieEntity>)

    @Query("DELETE FROM $UPCOMING_MOVIES")
    override suspend fun deleteAll()
}
