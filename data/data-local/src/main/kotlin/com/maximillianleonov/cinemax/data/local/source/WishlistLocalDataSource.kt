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

package com.maximillianleonov.cinemax.data.local.source

import com.maximillianleonov.cinemax.data.local.db.CinemaxDatabase
import com.maximillianleonov.cinemax.data.local.entity.wishlist.WishlistContentType
import com.maximillianleonov.cinemax.data.local.mapper.toWishlistEntity
import javax.inject.Inject

class WishlistLocalDataSource @Inject constructor(private val db: CinemaxDatabase) {
    private val dao = db.wishlistDao

    fun getMovies() = dao.getByContentType(WishlistContentType.Movie)
    fun getTvShows() = dao.getByContentType(WishlistContentType.TvShow)

    suspend fun addMovieToWishlist(id: Int) =
        dao.insert(WishlistContentType.Movie.toWishlistEntity(id))

    suspend fun addTvShowToWishlist(id: Int) =
        dao.insert(WishlistContentType.TvShow.toWishlistEntity(id))

    suspend fun removeMovieFromWishlist(id: Int) =
        dao.deleteByContentTypeAndRemoteId(WishlistContentType.Movie, id)

    suspend fun removeTvShowFromWishlist(id: Int) =
        dao.deleteByContentTypeAndRemoteId(WishlistContentType.TvShow, id)

    suspend fun isMovieWishlisted(id: Int) = dao.isWishlisted(WishlistContentType.Movie, id)
    suspend fun isTvShowWishlisted(id: Int) = dao.isWishlisted(WishlistContentType.TvShow, id)
}
