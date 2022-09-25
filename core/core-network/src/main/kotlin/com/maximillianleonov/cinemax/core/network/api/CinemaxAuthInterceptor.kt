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

package com.maximillianleonov.cinemax.core.network.api

import com.maximillianleonov.cinemax.core.network.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

internal class CinemaxAuthInterceptor(private val apiKeyProvider: CinemaxApiKeyProvider) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url.newBuilder()
            .addQueryParameter(API_KEY_QUERY_PARAM, apiKeyProvider.requireApiKey())
            .build()

        val newRequest = request.newBuilder().url(url).build()
        return chain.proceed(newRequest)
    }

    private companion object {
        private const val API_KEY_QUERY_PARAM = Constants.API_KEY_QUERY_PARAM
    }
}
