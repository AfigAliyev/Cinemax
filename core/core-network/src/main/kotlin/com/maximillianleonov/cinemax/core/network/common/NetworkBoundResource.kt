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

package com.maximillianleonov.cinemax.core.network.common

import com.maximillianleonov.cinemax.core.common.result.CinemaxResult
import com.maximillianleonov.cinemax.core.common.result.isFailure
import com.maximillianleonov.cinemax.core.common.result.isSuccess
import com.maximillianleonov.cinemax.core.network.util.MESSAGE_UNHANDLED_STATE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> CinemaxResult<RequestType>,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
): Flow<CinemaxResult<ResultType>> = flow {
    emit(CinemaxResult.loading())
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(CinemaxResult.Loading(data))
        val response = fetch()

        when {
            response.isSuccess() -> {
                saveFetchResult(response.value)
                query().map { CinemaxResult.success(it) }
            }
            response.isFailure() -> {
                val throwable = response.error
                query().map { CinemaxResult.failure(throwable, it) }
            }
            else -> error("$MESSAGE_UNHANDLED_STATE $response")
        }
    } else {
        query().map { CinemaxResult.success(it) }
    }

    emitAll(flow)
}
