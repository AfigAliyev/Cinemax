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

package com.maximillianleonov.cinemax.data.local.dao.discover

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maximillianleonov.cinemax.core.data.local.common.ContentDao
import com.maximillianleonov.cinemax.core.data.util.Constants.Tables.DISCOVER_MOVIES
import com.maximillianleonov.cinemax.data.local.entity.discover.DiscoverMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DiscoverMovieDao : ContentDao<DiscoverMovieEntity> {
    @Query("SELECT * FROM $DISCOVER_MOVIES")
    override fun getAll(): Flow<List<DiscoverMovieEntity>>

    @Query("SELECT * FROM $DISCOVER_MOVIES")
    override fun getAllPaging(): PagingSource<Int, DiscoverMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertAll(entities: List<DiscoverMovieEntity>)

    @Query("DELETE FROM $DISCOVER_MOVIES")
    override suspend fun deleteAll()
}
