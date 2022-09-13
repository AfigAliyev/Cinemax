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

package com.maximillianleonov.cinemax.data.local.dao.wishlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maximillianleonov.cinemax.core.data.util.Constants.Tables.WISHLIST
import com.maximillianleonov.cinemax.data.local.entity.wishlist.WishlistContentType
import com.maximillianleonov.cinemax.data.local.entity.wishlist.WishlistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {
    @Query("SELECT * FROM $WISHLIST WHERE content_type = :contentType")
    fun getByContentType(contentType: WishlistContentType): Flow<List<WishlistEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: WishlistEntity)

    @Query("DELETE FROM $WISHLIST WHERE content_type = :contentType AND remote_id = :id")
    suspend fun deleteByContentTypeAndRemoteId(contentType: WishlistContentType, id: Int)

    @Query("SELECT EXISTS(SELECT * FROM $WISHLIST WHERE content_type = :contentType AND remote_id = :id)")
    suspend fun isWishlisted(contentType: WishlistContentType, id: Int): Boolean
}
