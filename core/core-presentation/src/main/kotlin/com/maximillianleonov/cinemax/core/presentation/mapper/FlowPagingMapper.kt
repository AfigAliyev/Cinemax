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

package com.maximillianleonov.cinemax.core.presentation.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.maximillianleonov.cinemax.core.presentation.util.MapperFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface FlowPagingMapper {
    fun <T : Any, R : Any> Flow<PagingData<T>>.pagingMap(): Flow<PagingData<R>>
}

class FlowPagingMapperImpl : FlowPagingMapper {
    override fun <T : Any, R : Any> Flow<PagingData<T>>.pagingMap(): Flow<PagingData<R>> =
        pagingMap(transform = MapperFactory.domainMapper())
}

private fun <T : Any, R : Any> Flow<PagingData<T>>.pagingMap(
    transform: (T) -> R
): Flow<PagingData<R>> = map { it.map(transform) }
