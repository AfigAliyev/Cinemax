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

package com.maximillianleonov.cinemax.data.local.dao.trending

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maximillianleonov.cinemax.core.data.local.common.ContentDao
import com.maximillianleonov.cinemax.core.data.util.Constants.Tables.TRENDING_TV_SHOWS
import com.maximillianleonov.cinemax.data.local.entity.trending.TrendingTvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrendingTvShowDao : ContentDao<TrendingTvShowEntity> {
    @Query("SELECT * FROM $TRENDING_TV_SHOWS")
    override fun getAll(): Flow<List<TrendingTvShowEntity>>

    @Query("SELECT * FROM $TRENDING_TV_SHOWS")
    override fun getAllPaging(): PagingSource<Int, TrendingTvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertAll(entities: List<TrendingTvShowEntity>)

    @Query("DELETE FROM $TRENDING_TV_SHOWS")
    override suspend fun deleteAll()
}
