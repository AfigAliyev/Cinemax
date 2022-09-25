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

package com.maximillianleonov.cinemax.core.database.dao.wishlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maximillianleonov.cinemax.core.database.model.common.MediaType
import com.maximillianleonov.cinemax.core.database.model.wishlist.WishlistEntity
import com.maximillianleonov.cinemax.core.database.util.Constants.Tables.WISHLIST
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {
    @Query("SELECT * FROM $WISHLIST WHERE media_type = :mediaType")
    fun getByMediaType(mediaType: MediaType.Wishlist): Flow<List<WishlistEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wishlist: WishlistEntity)

    @Query("DELETE FROM $WISHLIST WHERE media_type = :mediaType AND network_id = :id")
    suspend fun deleteByMediaTypeAndNetworkId(mediaType: MediaType.Wishlist, id: Int)

    @Query("SELECT EXISTS(SELECT * FROM $WISHLIST WHERE media_type = :mediaType AND network_id = :id)")
    suspend fun isWishlisted(mediaType: MediaType.Wishlist, id: Int): Boolean
}
