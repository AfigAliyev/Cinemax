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

package com.maximillianleonov.cinemax.core.domain.usecase

import com.maximillianleonov.cinemax.core.domain.model.WishlistModel
import com.maximillianleonov.cinemax.core.domain.repository.TvShowDetailsRepository
import com.maximillianleonov.cinemax.core.domain.repository.WishlistRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class GetWishlistTvShowsUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository,
    private val tvShowDetailsRepository: TvShowDetailsRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke() = wishlistRepository.getTvShows().flatMapLatest { wishlistTvShows ->
        val ids = wishlistTvShows.map(WishlistModel::id)
        tvShowDetailsRepository.getByIds(ids)
    }
}
