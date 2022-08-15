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

package com.maximillianleonov.cinemax.core.presentation.util

import com.maximillianleonov.cinemax.core.domain.result.Result
import com.maximillianleonov.cinemax.core.domain.result.isFailure
import com.maximillianleonov.cinemax.core.domain.result.isLoading
import com.maximillianleonov.cinemax.core.domain.result.isSuccess
import kotlinx.coroutines.flow.Flow

fun <T> Result<T>.handle(
    onLoading: (T?) -> Unit,
    onSuccess: (T) -> Unit,
    onFailure: (Throwable) -> Unit
) = when {
    isLoading() -> onLoading(value)
    isSuccess() -> onSuccess(value)
    isFailure() -> onFailure(error)
    else -> error("{${Constants.Messages.UNHANDLED_STATE} $this}")
}

suspend fun <T> Flow<Result<T>>.handle(
    onLoading: (T?) -> Unit,
    onSuccess: (T) -> Unit,
    onFailure: (Throwable) -> Unit
) = collect { result ->
    result.handle(
        onLoading = onLoading,
        onSuccess = onSuccess,
        onFailure = onFailure
    )
}
