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

package com.maximillianleonov.cinemax.data.local.entity.popular

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maximillianleonov.cinemax.core.data.local.common.RemoteKeyEntity
import com.maximillianleonov.cinemax.core.data.util.Constants

@Entity(tableName = Constants.Tables.POPULAR_MOVIES_REMOTE_KEYS)
data class PopularMovieRemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = Constants.Fields.ID)
    override val id: Int,

    @ColumnInfo(name = Constants.Fields.PREV_PAGE)
    override val prevPage: Int?,

    @ColumnInfo(name = Constants.Fields.NEXT_PAGE)
    override val nextPage: Int?
) : RemoteKeyEntity
