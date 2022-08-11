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

package com.maximillianleonov.cinemax.core.data.remote.util

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.maximillianleonov.cinemax.core.data.remote.api.CinemaxApiKeyProvider
import com.maximillianleonov.cinemax.core.data.remote.api.CinemaxAuthInterceptor
import com.maximillianleonov.cinemax.core.data.remote.common.defaultJson
import com.maximillianleonov.cinemax.core.data.remote.retrofit.ResultAdapterFactory
import com.maximillianleonov.cinemax.core.data.util.Constants
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@OptIn(ExperimentalSerializationApi::class)
fun retrofit(
    apiKeyProvider: CinemaxApiKeyProvider,
    json: Json = defaultJson
): Retrofit = Retrofit.Builder()
    .baseUrl(Constants.Remote.API_URL)
    .client(authorizedOkHttpClient(apiKeyProvider))
    .addConverterFactory(json.asConverterFactory(MIMETYPE_JSON))
    .addCallAdapterFactory(ResultAdapterFactory())
    .build()

private fun authorizedOkHttpClient(apiKeyProvider: CinemaxApiKeyProvider): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(CinemaxAuthInterceptor(apiKeyProvider))
        .build()
