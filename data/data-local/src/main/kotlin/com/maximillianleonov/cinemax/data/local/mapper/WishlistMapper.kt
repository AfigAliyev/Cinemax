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

package com.maximillianleonov.cinemax.data.local.mapper

import com.maximillianleonov.cinemax.data.local.entity.wishlist.WishlistContentType
import com.maximillianleonov.cinemax.data.local.entity.wishlist.WishlistEntity
import com.maximillianleonov.cinemax.domain.model.WishlistContentTypeModel
import com.maximillianleonov.cinemax.domain.model.WishlistModel

internal fun WishlistContentType.toWishlistEntity(id: Int) = WishlistEntity(
    contentType = this,
    remoteId = id
)

internal fun WishlistEntity.toWishlistModel() = WishlistModel(
    id = remoteId,
    contentType = contentType.toWishlistContentTypeModel()
)

internal fun WishlistContentType.toWishlistContentTypeModel() = when (this) {
    WishlistContentType.Movie -> WishlistContentTypeModel.Movie
    WishlistContentType.TvShow -> WishlistContentTypeModel.TvShow
}
