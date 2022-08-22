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

package com.maximillianleonov.cinemax.core.data.local.common

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

interface Dao<T : Entity>

interface ContentDao<T : ContentEntity> : Dao<T> {
    fun getAll(): Flow<List<T>>
    fun getAllPaging(): PagingSource<Int, T>
    suspend fun insertAll(entities: List<@JvmSuppressWildcards T>)
    suspend fun deleteAll()
}

interface RemoteKeyDao<T : RemoteKeyEntity> : Dao<T> {
    suspend fun getById(id: Int): T
    suspend fun insertAll(entities: List<@JvmSuppressWildcards T>)
    suspend fun deleteAll()
}
