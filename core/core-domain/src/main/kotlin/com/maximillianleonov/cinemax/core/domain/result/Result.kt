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

sealed class Result<out R> {

    data class Loading<out T>(val value: T? = null) : Result<T>() {
        override fun toString(): String = "Loading($value)"
    }

    sealed class Success<out T> : Result<T>() {
        abstract val value: T

        class Value<out T>(override val value: T) : Success<T>()

        data class HttpResponse<out T>(
            override val value: T,
            override val statusCode: Int,
            override val statusMessage: String?,
            override val url: String?
        ) : Success<T>(), com.maximillianleonov.cinemax.core.domain.result.HttpResponse

        override fun toString(): String = "Success($value)"
    }

    sealed class Failure<out T>(open val value: T? = null) : Result<T>() {
        abstract val error: Throwable

        data class Error<out T>(override val error: Throwable, override val value: T? = null) :
            Failure<T>(value)

        data class HttpError(override val error: HttpException) : Failure<Nothing>(), HttpResponse {
            override val statusCode: Int get() = error.statusCode
            override val statusMessage: String? get() = error.statusMessage
            override val url: String? get() = error.url
        }

        override fun toString(): String = "Failure($error, $value)"
    }
}

typealias EmptyResult = Result<Nothing>
