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

package com.maximillianleonov.cinemax.core.ui.common

import com.maximillianleonov.cinemax.core.ui.util.Constants

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
        DiscoverTvShows,
        TrendingMovies,
        TrendingTvShows
    }

    enum class List(val value: String) {
        Upcoming(UpcomingContentType),
        TopRated(TopRatedContentType),
        Popular(PopularContentType),
        NowPlaying(NowPlayingContentType),
        Discover(DiscoverContentType),
        Trending(TrendingContentType);

        companion object {
            private val contentTypes = values().associateBy(List::value)
            operator fun get(contentType: String) = checkNotNull(contentTypes[contentType]) {
                "${Constants.Messages.INVALID_CONTENT_TYPE} $contentType"
            }
        }
    }

    sealed class Details(val contentId: Int, val contentType: String) {
        data class Movie(val id: Int) : Details(contentId = id, contentType = MovieContentType)
        data class TvShow(val id: Int) : Details(contentId = id, contentType = TvShowContentType)

        companion object {
            fun from(id: Int, contentType: String) = when (contentType) {
                MovieContentType -> Movie(id = id)
                TvShowContentType -> TvShow(id = id)
                else -> error("${Constants.Messages.INVALID_CONTENT_TYPE} $contentType")
            }
        }
    }

    enum class Wishlist { Movie, TvShow }
}

private const val MovieContentType = "movie"
private const val TvShowContentType = "tv_show"
private const val UpcomingContentType = "upcoming"
private const val TopRatedContentType = "top_rated"
private const val PopularContentType = "popular"
private const val NowPlayingContentType = "now_playing"
private const val DiscoverContentType = "discover"
private const val TrendingContentType = "trending"
