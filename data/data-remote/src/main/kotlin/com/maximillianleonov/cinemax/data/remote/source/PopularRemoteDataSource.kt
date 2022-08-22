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

package com.maximillianleonov.cinemax.data.remote.source

import com.maximillianleonov.cinemax.core.data.util.Constants
import com.maximillianleonov.cinemax.data.remote.api.CinemaxApi
import javax.inject.Inject

class PopularRemoteDataSource @Inject constructor(api: CinemaxApi) {
    private val movieService = api.movieService
    private val tvShowService = api.tvShowService

    suspend fun getMovies(
        page: Int = Constants.Remote.DEFAULT_PAGE
    ) = movieService.getPopularMovies(page)

    suspend fun getTvShows(
        page: Int = Constants.Remote.DEFAULT_PAGE
    ) = tvShowService.getPopularTvShows(page)
}
