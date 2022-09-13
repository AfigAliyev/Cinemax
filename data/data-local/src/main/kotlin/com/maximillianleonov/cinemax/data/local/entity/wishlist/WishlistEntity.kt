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

package com.maximillianleonov.cinemax.data.local.entity.wishlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maximillianleonov.cinemax.core.data.local.common.ContentEntity
import com.maximillianleonov.cinemax.core.data.util.Constants

@Entity(tableName = Constants.Tables.WISHLIST)
data class WishlistEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.Fields.ID)
    override val id: Int = 0,

    @ColumnInfo(name = Constants.Fields.REMOTE_ID)
    override val remoteId: Int,

    @ColumnInfo(name = Constants.Fields.CONTENT_TYPE)
    val contentType: WishlistContentType
) : ContentEntity

enum class WishlistContentType { Movie, TvShow }
