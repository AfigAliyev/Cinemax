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

package com.maximillianleonov.cinemax.core.ui.mapper

import com.maximillianleonov.cinemax.core.domain.model.MediaTypeModel
import com.maximillianleonov.cinemax.core.model.MediaType

fun MediaType.Movie.asMediaTypeModel() = MediaTypeModel.Movie[mediaType]
fun MediaType.TvShow.asMediaTypeModel() = MediaTypeModel.TvShow[mediaType]

fun MediaType.Common.asMovieMediaType() = when (this) {
    MediaType.Common.Upcoming -> MediaType.Movie.Upcoming
    MediaType.Common.TopRated -> MediaType.Movie.TopRated
    MediaType.Common.Popular -> MediaType.Movie.Popular
    MediaType.Common.NowPlaying -> MediaType.Movie.NowPlaying
    MediaType.Common.Discover -> MediaType.Movie.Discover
    MediaType.Common.Trending -> MediaType.Movie.Trending
}

fun MediaType.Common.asTvShowMediaType() = when (this) {
    MediaType.Common.Upcoming -> null
    MediaType.Common.TopRated -> MediaType.TvShow.TopRated
    MediaType.Common.Popular -> MediaType.TvShow.Popular
    MediaType.Common.NowPlaying -> MediaType.TvShow.NowPlaying
    MediaType.Common.Discover -> MediaType.TvShow.Discover
    MediaType.Common.Trending -> MediaType.TvShow.Trending
}
