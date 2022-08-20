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

package com.maximillianleonov.cinemax.data.remote.api

import com.maximillianleonov.cinemax.core.data.remote.api.CinemaxApiKeyProvider
import com.maximillianleonov.cinemax.core.data.remote.util.retrofit
import com.maximillianleonov.cinemax.data.remote.api.service.MovieService
import com.maximillianleonov.cinemax.data.remote.api.service.TvShowService
import retrofit2.create
import javax.inject.Inject

class CinemaxApi @Inject constructor(private val apiKeyProvider: CinemaxApiKeyProvider) {
    val movieService: MovieService by lazy { retrofit(apiKeyProvider).create() }
    val tvShowService: TvShowService by lazy { retrofit(apiKeyProvider).create() }
}
