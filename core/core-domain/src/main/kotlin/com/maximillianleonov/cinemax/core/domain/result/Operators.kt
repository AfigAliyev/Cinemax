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

package com.maximillianleonov.cinemax.core.domain.result

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun <T> Result<T>.isLoading(): Boolean {
    contract {
        returns(true) implies (this@isLoading is Result.Loading<T>)
    }
    return this is Result.Loading
}

fun <T> Result<T>.asLoading(): Result.Loading<T> = this as Result.Loading<T>

@OptIn(ExperimentalContracts::class)
fun <T> Result<T>.isSuccess(): Boolean {
    contract {
        returns(true) implies (this@isSuccess is Result.Success<T>)
    }
    return this is Result.Success
}

fun <T> Result<T>.asSuccess(): Result.Success<T> = this as Result.Success<T>

@OptIn(ExperimentalContracts::class)
fun <T> Result<T>.isFailure(): Boolean {
    contract {
        returns(true) implies (this@isFailure is Result.Failure<T>)
    }
    return this is Result.Failure<T>
}

fun <T> Result<T>.asFailure(): Result.Failure<T> = this as Result.Failure<T>

fun <T> Result<T>.asError(): Result.Failure.Error<T> = this as Result.Failure.Error<T>

fun <T> Result<T>.asHttpError(): Result.Failure.HttpError = this as Result.Failure.HttpError

@Suppress("UNCHECKED_CAST")
fun <T, R> Result<T>.map(transform: (value: T) -> R): Result<R> = when (this) {
    is Result.Loading -> Result.Loading(value?.let(transform))
    is Result.Success -> Result.Success.Value(transform(value))
    is Result.Failure<T> -> Result.Failure.Error(error, transform(value as T))
}

fun <T, R> Result<T>.flatMap(transform: (result: Result<T>) -> Result<R>): Result<R> =
    let(transform)
