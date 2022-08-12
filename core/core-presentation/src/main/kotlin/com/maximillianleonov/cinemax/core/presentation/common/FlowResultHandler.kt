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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximillianleonov.cinemax.core.domain.result.Result
import com.maximillianleonov.cinemax.core.presentation.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

interface FlowResultHandler {
    fun <T, R, S : State> Flow<Result<T>>.handleResult(
        onLoading: (S, R?) -> S,
        onSuccess: (S, R) -> S,
        onFailure: (S, Throwable) -> S,
        updateUiState: ((S) -> S) -> Unit
    )

    fun <T, R, S : State> Flow<Result<T>>.handleResult(
        onLoading: (S, R?) -> S,
        onSuccess: (S, R) -> S,
        onFailure: (S, Throwable) -> S,
        updateUiState: ((S) -> S) -> Unit,
        coroutineScope: CoroutineScope
    )

    fun <T, R, S : State> Flow<Result<List<T>>>.handleResultList(
        onLoading: (S, List<R>) -> S,
        onSuccess: (S, List<R>) -> S,
        onFailure: (S, Throwable) -> S,
        updateUiState: ((S) -> S) -> Unit
    )

    fun <T, R, S : State> Flow<Result<List<T>>>.handleResultList(
        onLoading: (S, List<R>) -> S,
        onSuccess: (S, List<R>) -> S,
        onFailure: (S, Throwable) -> S,
        updateUiState: ((S) -> S) -> Unit,
        coroutineScope: CoroutineScope
    )
}

fun FlowResultHandler(): FlowResultHandler = FlowResultHandlerImpl()

private class FlowResultHandlerImpl : FlowResultHandler, ResultHandler by ResultHandler() {
    override fun <T, R, S : State> Flow<Result<T>>.handleResult(
        onLoading: (S, R?) -> S,
        onSuccess: (S, R) -> S,
        onFailure: (S, Throwable) -> S,
        updateUiState: ((S) -> S) -> Unit
    ) {
        require(this is ViewModel) { "${Constants.Messages.UNHANDLED_STATE} $this" }
        handleResult(
            onLoading = onLoading,
            onSuccess = onSuccess,
            onFailure = onFailure,
            updateUiState = updateUiState,
            coroutineScope = viewModelScope
        )
    }

    override fun <T, R, S : State> Flow<Result<T>>.handleResult(
        onLoading: (S, R?) -> S,
        onSuccess: (S, R) -> S,
        onFailure: (S, Throwable) -> S,
        updateUiState: ((S) -> S) -> Unit,
        coroutineScope: CoroutineScope
    ) {
        onEach { result ->
            result.handleResult(
                onLoading = onLoading,
                onSuccess = onSuccess,
                onFailure = onFailure,
                updateUiState = updateUiState
            )
        }.launchIn(coroutineScope)
    }

    override fun <T, R, S : State> Flow<Result<List<T>>>.handleResultList(
        onLoading: (S, List<R>) -> S,
        onSuccess: (S, List<R>) -> S,
        onFailure: (S, Throwable) -> S,
        updateUiState: ((S) -> S) -> Unit
    ) {
        require(this is ViewModel) { "${Constants.Messages.UNHANDLED_STATE} $this" }
        handleResultList(
            onLoading = onLoading,
            onSuccess = onSuccess,
            onFailure = onFailure,
            updateUiState = updateUiState,
            coroutineScope = viewModelScope
        )
    }

    override fun <T, R, S : State> Flow<Result<List<T>>>.handleResultList(
        onLoading: (S, List<R>) -> S,
        onSuccess: (S, List<R>) -> S,
        onFailure: (S, Throwable) -> S,
        updateUiState: ((S) -> S) -> Unit,
        coroutineScope: CoroutineScope
    ) {
        onEach { result ->
            result.handleResultList(
                onLoading = onLoading,
                onSuccess = onSuccess,
                onFailure = onFailure,
                updateUiState = updateUiState
            )
        }.launchIn(coroutineScope)
    }
}
