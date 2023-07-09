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

package com.maximillianleonov.cinemax.core.data.mapper

import com.maximillianleonov.cinemax.core.database.model.common.MediaType
import com.maximillianleonov.cinemax.core.domain.model.MediaTypeModel
import com.maximillianleonov.cinemax.core.network.model.common.NetworkMediaType

internal fun MediaTypeModel.Movie.asMediaType() = MediaType.Movie[mediaType]
internal fun MediaTypeModel.TvShow.asMediaType() = MediaType.TvShow[mediaType]

internal fun MediaType.Movie.asNetworkMediaType() = NetworkMediaType.Movie[mediaType]
internal fun MediaType.TvShow.asNetworkMediaType() = NetworkMediaType.TvShow[mediaType]

internal fun MediaType.Wishlist.asMediaTypeModel() = when (this) {
    MediaType.Wishlist.Movie -> MediaTypeModel.Wishlist.Movie
    MediaType.Wishlist.TvShow -> MediaTypeModel.Wishlist.TvShow
}
