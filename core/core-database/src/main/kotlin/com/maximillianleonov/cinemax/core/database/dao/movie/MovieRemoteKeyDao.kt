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

package com.maximillianleonov.cinemax.core.database.dao.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maximillianleonov.cinemax.core.database.model.common.MediaType
import com.maximillianleonov.cinemax.core.database.model.movie.MovieRemoteKeyEntity
import com.maximillianleonov.cinemax.core.database.util.Constants.Tables.MOVIES_REMOTE_KEYS

@Dao
interface MovieRemoteKeyDao {
    @Query("SELECT * FROM $MOVIES_REMOTE_KEYS WHERE id = :id AND media_type = :mediaType")
    suspend fun getByIdAndMediaType(id: Int, mediaType: MediaType.Movie): MovieRemoteKeyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<MovieRemoteKeyEntity>)

    @Query("DELETE FROM $MOVIES_REMOTE_KEYS WHERE media_type = :mediaType")
    suspend fun deleteByMediaType(mediaType: MediaType.Movie)
}
