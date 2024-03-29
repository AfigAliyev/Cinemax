/*
 * Copyright 2022 Afig Aliyev
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
import com.maximillianleonov.cinemax.core.network.api.service.TvShowService
import com.maximillianleonov.cinemax.core.network.model.common.NetworkMediaType
import com.maximillianleonov.cinemax.core.network.model.response.TvShowResponseDto
import com.maximillianleonov.cinemax.core.network.util.Constants
import javax.inject.Inject

class TvShowNetworkDataSource @Inject constructor(private val tvShowService: TvShowService) {
    suspend fun getByMediaType(
        mediaType: NetworkMediaType.TvShow,
        page: Int = Constants.DEFAULT_PAGE
    ): CinemaxResult<TvShowResponseDto> = when (mediaType) {
        NetworkMediaType.TvShow.TOP_RATED -> tvShowService.getTopRated(page)
        NetworkMediaType.TvShow.POPULAR -> tvShowService.getPopular(page)
        NetworkMediaType.TvShow.NOW_PLAYING -> tvShowService.getOnTheAir(page)
        NetworkMediaType.TvShow.DISCOVER -> tvShowService.getDiscover(page)
        NetworkMediaType.TvShow.TRENDING -> tvShowService.getTrending(page)
    }

    suspend fun search(
        query: String,
        page: Int = Constants.DEFAULT_PAGE
    ): CinemaxResult<TvShowResponseDto> = tvShowService.search(query, page)
}
