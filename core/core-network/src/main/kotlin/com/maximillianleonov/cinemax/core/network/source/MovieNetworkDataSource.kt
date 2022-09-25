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

package com.maximillianleonov.cinemax.core.network.source

import com.maximillianleonov.cinemax.core.common.result.CinemaxResult
import com.maximillianleonov.cinemax.core.network.api.service.MovieService
import com.maximillianleonov.cinemax.core.network.model.common.NetworkMediaType
import com.maximillianleonov.cinemax.core.network.model.response.MovieResponseDto
import com.maximillianleonov.cinemax.core.network.util.Constants
import javax.inject.Inject

class MovieNetworkDataSource @Inject constructor(private val movieService: MovieService) {
    suspend fun getByMediaType(
        mediaType: NetworkMediaType.Movie,
        page: Int = Constants.DEFAULT_PAGE
    ): CinemaxResult<MovieResponseDto> = when (mediaType) {
        NetworkMediaType.Movie.UPCOMING -> movieService.getUpcoming(page)
        NetworkMediaType.Movie.TOP_RATED -> movieService.getTopRated(page)
        NetworkMediaType.Movie.POPULAR -> movieService.getPopular(page)
        NetworkMediaType.Movie.NOW_PLAYING -> movieService.getNowPlaying(page)
        NetworkMediaType.Movie.DISCOVER -> movieService.getDiscover(page)
        NetworkMediaType.Movie.TRENDING -> movieService.getTrending(page)
    }

    suspend fun search(
        query: String,
        page: Int = Constants.DEFAULT_PAGE
    ): CinemaxResult<MovieResponseDto> = movieService.search(query, page)
}
