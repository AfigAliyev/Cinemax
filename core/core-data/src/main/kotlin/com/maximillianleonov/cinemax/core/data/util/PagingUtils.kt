/*
 * Copyright 2022 Afig Aliyev
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

package com.maximillianleonov.cinemax.core.data.util

import androidx.paging.PagingConfig
import androidx.paging.PagingState
import com.maximillianleonov.cinemax.core.network.util.PAGE_SIZE

internal object PagingUtils {
    internal suspend fun <T : Any, R> getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, T>,
        getRemoteKeyByEntity: suspend (T) -> R
    ): R? = state.anchorPosition?.let { position ->
        state.closestItemToPosition(position)?.let { entity ->
            getRemoteKeyByEntity(entity)
        }
    }

    internal suspend fun <T : Any, R> getRemoteKeyForFirstItem(
        state: PagingState<Int, T>,
        getRemoteKeyByEntity: suspend (T) -> R
    ): R? = state.pages.firstOrNull {
        it.data.isNotEmpty()
    }?.data?.firstOrNull()?.let { entity ->
        getRemoteKeyByEntity(entity)
    }

    internal suspend fun <T : Any, R> getRemoteKeyForLastItem(
        state: PagingState<Int, T>,
        getRemoteKeyByEntity: suspend (T) -> R
    ): R? = state.pages.lastOrNull {
        it.data.isNotEmpty()
    }?.data?.lastOrNull()?.let { entity ->
        getRemoteKeyByEntity(entity)
    }
}

internal val defaultPagingConfig = PagingConfig(pageSize = PAGE_SIZE)
