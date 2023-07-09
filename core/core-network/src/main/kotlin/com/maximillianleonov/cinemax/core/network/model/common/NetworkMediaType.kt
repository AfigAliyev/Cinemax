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

package com.maximillianleonov.cinemax.core.network.model.common

sealed interface NetworkMediaType {
    enum class Movie(val mediaType: String) : NetworkMediaType {
        UPCOMING(UPCOMING_MEDIA_TYPE),
        TOP_RATED(TOP_RATED_MEDIA_TYPE),
        POPULAR(POPULAR_MEDIA_TYPE),
        NOW_PLAYING(NOW_PLAYING_MEDIA_TYPE),
        DISCOVER(DISCOVER_MEDIA_TYPE),
        TRENDING(TRENDING_MEDIA_TYPE);

        companion object {
            private val mediaTypes = values().associateBy(Movie::mediaType)
            operator fun get(mediaType: String) = checkNotNull(mediaTypes[mediaType]) {
                "$INVALID_MEDIA_TYPE_MESSAGE $mediaType"
            }
        }
    }

    enum class TvShow(val mediaType: String) : NetworkMediaType {
        TOP_RATED(TOP_RATED_MEDIA_TYPE),
        POPULAR(POPULAR_MEDIA_TYPE),
        NOW_PLAYING(NOW_PLAYING_MEDIA_TYPE),
        DISCOVER(DISCOVER_MEDIA_TYPE),
        TRENDING(TRENDING_MEDIA_TYPE);

        companion object {
            private val mediaTypes = values().associateBy(TvShow::mediaType)
            operator fun get(mediaType: String) = checkNotNull(mediaTypes[mediaType]) {
                "$INVALID_MEDIA_TYPE_MESSAGE $mediaType"
            }
        }
    }
}

private const val UPCOMING_MEDIA_TYPE = "upcoming"
private const val TOP_RATED_MEDIA_TYPE = "top_rated"
private const val POPULAR_MEDIA_TYPE = "popular"
private const val NOW_PLAYING_MEDIA_TYPE = "now_playing"
private const val DISCOVER_MEDIA_TYPE = "discover"
private const val TRENDING_MEDIA_TYPE = "trending"

private const val INVALID_MEDIA_TYPE_MESSAGE = "Invalid media type."
