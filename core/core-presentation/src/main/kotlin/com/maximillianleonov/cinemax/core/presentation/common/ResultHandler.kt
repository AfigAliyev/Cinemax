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

package com.maximillianleonov.cinemax.core.presentation.common

import com.maximillianleonov.cinemax.core.domain.result.Result
import com.maximillianleonov.cinemax.core.domain.result.isFailure
import com.maximillianleonov.cinemax.core.domain.result.isLoading
import com.maximillianleonov.cinemax.core.domain.result.isSuccess
import com.maximillianleonov.cinemax.core.presentation.util.Constants
import com.maximillianleonov.cinemax.core.presentation.util.MapperFactory

interface ResultHandler {
    fun <T, R, S : State> Result<T>.handleResult(
        onLoading: (S, R?) -> S,
        onSuccess: (S, R) -> S,
        onFailure: (S, Throwable) -> S,
        updateUiState: ((S) -> S) -> Unit
    )

    fun <T, R, S : State> Result<List<T>>.handleResultList(
        onLoading: (S, List<R>) -> S,
        onSuccess: (S, List<R>) -> S,
        onFailure: (S, Throwable) -> S,
        updateUiState: ((S) -> S) -> Unit
    )
}

fun ResultHandler(): ResultHandler = ResultHandlerImpl()

private class ResultHandlerImpl : ResultHandler {
    override fun <T, R, S : State> Result<T>.handleResult(
        onLoading: (S, R?) -> S,
        onSuccess: (S, R) -> S,
        onFailure: (S, Throwable) -> S,
        updateUiState: ((S) -> S) -> Unit,
    ) = updateUiState { currentState ->
        handleResult(
            currentState = currentState,
            onLoading = onLoading,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun <T, R, S : State> Result<List<T>>.handleResultList(
        onLoading: (S, List<R>) -> S,
        onSuccess: (S, List<R>) -> S,
        onFailure: (S, Throwable) -> S,
        updateUiState: ((S) -> S) -> Unit
    ) {
        updateUiState { currentState ->
            handleResultList(
                currentState = currentState,
                onLoading = onLoading,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        }
    }
}

private fun <T, R, S : State> Result<T>.handleResult(
    currentState: S,
    onLoading: (S, R?) -> S,
    onSuccess: (S, R) -> S,
    onFailure: (S, Throwable) -> S
): S {
    val transform: (T) -> R = MapperFactory.domainMapper()
    return when {
        isLoading() -> onLoading(currentState, value?.let(transform))
        isSuccess() -> onSuccess(currentState, value.let(transform))
        isFailure() -> onFailure(currentState, error)
        else -> error("{${Constants.Messages.UNHANDLED_STATE} $this}")
    }
}

private fun <T, R, S : State> Result<List<T>>.handleResultList(
    currentState: S,
    onLoading: (S, List<R>) -> S,
    onSuccess: (S, List<R>) -> S,
    onFailure: (S, Throwable) -> S
): S {
    val transform: (T) -> R = MapperFactory.domainMapper()
    return when {
        isLoading() -> onLoading(currentState, value?.map(transform).orEmpty())
        isSuccess() -> onSuccess(currentState, value.map(transform))
        isFailure() -> onFailure(currentState, error)
        else -> error("{${Constants.Messages.UNHANDLED_STATE} $this}")
    }
}
