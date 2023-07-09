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

package com.maximillianleonov.cinemax.core.database.source

import com.maximillianleonov.cinemax.core.database.dao.wishlist.WishlistDao
import com.maximillianleonov.cinemax.core.database.mapper.asWishlistEntity
import com.maximillianleonov.cinemax.core.database.model.common.MediaType.Wishlist
import javax.inject.Inject

class WishlistDatabaseDataSource @Inject constructor(private val wishlistDao: WishlistDao) {
    fun getMovies() = wishlistDao.getByMediaType(Wishlist.Movie)
    fun getTvShows() = wishlistDao.getByMediaType(Wishlist.TvShow)

    suspend fun addMovieToWishlist(id: Int) =
        wishlistDao.insert(Wishlist.Movie.asWishlistEntity(id))

    suspend fun addTvShowToWishlist(id: Int) =
        wishlistDao.insert(Wishlist.TvShow.asWishlistEntity(id))

    suspend fun removeMovieFromWishlist(id: Int) =
        wishlistDao.deleteByMediaTypeAndNetworkId(Wishlist.Movie, id)

    suspend fun removeTvShowFromWishlist(id: Int) =
        wishlistDao.deleteByMediaTypeAndNetworkId(Wishlist.TvShow, id)

    suspend fun isMovieWishlisted(id: Int) = wishlistDao.isWishlisted(Wishlist.Movie, id)
    suspend fun isTvShowWishlisted(id: Int) = wishlistDao.isWishlisted(Wishlist.TvShow, id)
}
