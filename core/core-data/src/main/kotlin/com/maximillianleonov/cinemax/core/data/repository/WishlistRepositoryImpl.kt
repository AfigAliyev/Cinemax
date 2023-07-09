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

package com.maximillianleonov.cinemax.core.data.repository

import com.maximillianleonov.cinemax.core.data.mapper.asWishlistModel
import com.maximillianleonov.cinemax.core.data.mapper.listMap
import com.maximillianleonov.cinemax.core.database.model.wishlist.WishlistEntity
import com.maximillianleonov.cinemax.core.database.source.WishlistDatabaseDataSource
import com.maximillianleonov.cinemax.core.domain.model.WishlistModel
import com.maximillianleonov.cinemax.core.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val databaseDataSource: WishlistDatabaseDataSource
) : WishlistRepository {
    override fun getMovies(): Flow<List<WishlistModel>> =
        databaseDataSource.getMovies().listMap(WishlistEntity::asWishlistModel)

    override fun getTvShows(): Flow<List<WishlistModel>> =
        databaseDataSource.getTvShows().listMap(WishlistEntity::asWishlistModel)

    override suspend fun addMovieToWishlist(id: Int) = databaseDataSource.addMovieToWishlist(id)

    override suspend fun addTvShowToWishlist(id: Int) = databaseDataSource.addTvShowToWishlist(id)

    override suspend fun removeMovieFromWishlist(id: Int) =
        databaseDataSource.removeMovieFromWishlist(id)

    override suspend fun removeTvShowFromWishlist(id: Int) =
        databaseDataSource.removeTvShowFromWishlist(id)
}
