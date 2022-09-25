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

package com.maximillianleonov.cinemax.core.common.result

sealed interface CinemaxResult<out T> {
    data class Loading<T>(val value: T? = null) : CinemaxResult<T> {
        override fun toString(): String = "Loading($value)"
    }

    sealed interface Success<out T> : CinemaxResult<T> {
        val value: T

        class Value<T>(override val value: T) : Success<T> {
            override fun toString(): String = "Value($value)"
        }

        data class HttpResponse<T>(
            override val value: T,
            override val statusCode: Int,
            override val statusMessage: String?,
            override val url: String?
        ) : Success<T>, com.maximillianleonov.cinemax.core.common.result.HttpResponse {
            override fun toString(): String =
                "HttpResponse($value, $statusCode, $statusMessage, $url)"
        }
    }

    sealed interface Failure<out T> : CinemaxResult<T> {
        val error: Throwable
        val value: T?

        data class Error<T>(override val error: Throwable, override val value: T? = null) :
            Failure<T> {
            override fun toString(): String = "Error($error, $value)"
        }

        data class HttpError<T>(override val error: HttpException, override val value: T? = null) :
            Failure<T>, HttpResponse {
            override val statusCode: Int = error.statusCode
            override val statusMessage: String? = error.statusMessage
            override val url: String? = error.url

            override fun toString(): String = "HttpError($error, $value)"
        }
    }

    companion object {
        fun <T> loading(value: T? = null) = Loading(value)

        fun <T> success(value: T) = Success.Value(value)
        fun <T> success(value: T, statusCode: Int, statusMessage: String?, url: String?) =
            Success.HttpResponse(value, statusCode, statusMessage, url)

        fun <T> failure(error: Throwable, value: T? = null) = Failure.Error(error, value)
        fun <T> failure(error: HttpException, value: T? = null) = Failure.HttpError(error, value)
    }
}
