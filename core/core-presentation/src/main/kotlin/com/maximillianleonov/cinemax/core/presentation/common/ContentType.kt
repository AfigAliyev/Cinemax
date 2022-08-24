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

package com.maximillianleonov.cinemax.core.presentation.common

import com.maximillianleonov.cinemax.core.presentation.util.Constants

object ContentType {
    enum class Main {
        UpcomingMovies,
        TopRatedMovies,
        TopRatedTvShows,
        PopularMovies,
        PopularTvShows,
        NowPlayingMovies,
        NowPlayingTvShows,
        DiscoverMovies,
        DiscoverTvShows
    }

    enum class List(val value: String) {
        Upcoming(UpcomingContentType);

        companion object {
            private val contentTypes = values().associateBy(List::value)
            operator fun get(contentType: String) = checkNotNull(contentTypes[contentType]) {
                "${Constants.Messages.INVALID_CONTENT_TYPE} $contentType"
            }
        }
    }
}

private const val UpcomingContentType = "upcoming"
