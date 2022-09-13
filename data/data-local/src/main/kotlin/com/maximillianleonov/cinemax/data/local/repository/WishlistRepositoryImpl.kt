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

package com.maximillianleonov.cinemax.data.local.repository

import com.maximillianleonov.cinemax.data.local.entity.wishlist.WishlistEntity
import com.maximillianleonov.cinemax.data.local.mapper.listMap
import com.maximillianleonov.cinemax.data.local.mapper.toWishlistModel
import com.maximillianleonov.cinemax.data.local.source.WishlistLocalDataSource
import com.maximillianleonov.cinemax.domain.model.WishlistModel
import com.maximillianleonov.cinemax.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val localDataSource: WishlistLocalDataSource
) : WishlistRepository {
    override fun getMovies(): Flow<List<WishlistModel>> =
        localDataSource.getMovies().listMap(WishlistEntity::toWishlistModel)

    override fun getTvShows(): Flow<List<WishlistModel>> =
        localDataSource.getTvShows().listMap(WishlistEntity::toWishlistModel)

    override suspend fun addMovieToWishlist(id: Int) = localDataSource.addMovieToWishlist(id)

    override suspend fun addTvShowToWishlist(id: Int) = localDataSource.addTvShowToWishlist(id)

    override suspend fun removeMovieFromWishlist(id: Int) =
        localDataSource.removeMovieFromWishlist(id)

    override suspend fun removeTvShowFromWishlist(id: Int) =
        localDataSource.removeTvShowFromWishlist(id)
}
