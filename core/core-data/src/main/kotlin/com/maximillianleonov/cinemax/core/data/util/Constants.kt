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

package com.maximillianleonov.cinemax.core.data.util

object Constants {
    object Remote {
        const val API_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY_QUERY_PARAM = "api_key"
        const val PAGE_SIZE = 20
        const val DEFAULT_PAGE = 1

        const val UPCOMING_MOVIE_PATH = "movie/upcoming"
        const val TOP_RATED_MOVIE_PATH = "movie/top_rated"

        const val TOP_RATED_TV_SHOW_PATH = "tv/top_rated"
    }

    object Tables {
        const val UPCOMING_MOVIES = "upcoming_movies"
        const val UPCOMING_MOVIES_REMOTE_KEYS = "upcoming_movies_remote_keys"

        const val TOP_RATED_MOVIES = "top_rated_movies"
        const val TOP_RATED_MOVIES_REMOTE_KEYS = "top_rated_movies_remote_keys"

        const val TOP_RATED_TV_SHOWS = "top_rated_tv_shows"
        const val TOP_RATED_TV_SHOWS_REMOTE_KEYS = "top_rated_tv_shows_remote_keys"
    }

    object Fields {
        const val ID = "id"
        const val REMOTE_ID = "remote_id"

        const val PAGE = "page"
        const val TOTAL_PAGES = "total_pages"
        const val RESULTS = "results"
        const val TOTAL_RESULTS = "total_results"
        const val DATES = "dates"
        const val TITLE = "title"
        const val NAME = "name"
        const val OVERVIEW = "overview"
        const val POPULARITY = "popularity"
        const val RELEASE_DATE = "release_date"
        const val FIRST_AIR_DATE = "first_air_date"
        const val ADULT = "adult"
        const val GENRE_IDS = "genre_ids"
        const val ORIGINAL_TITLE = "original_title"
        const val ORIGINAL_NAME = "original_name"
        const val ORIGINAL_LANGUAGE = "original_language"
        const val ORIGIN_COUNTRY = "origin_country"
        const val VOTE_AVERAGE = "vote_average"
        const val VOTE_COUNT = "vote_count"
        const val POSTER_PATH = "poster_path"
        const val BACKDROP_PATH = "backdrop_path"
        const val VIDEO = "video"
        const val MAXIMUM = "maximum"
        const val MINIMUM = "minimum"

        const val PREV_PAGE = "prev_page"
        const val NEXT_PAGE = "next_page"
    }

    object Messages {
        const val UNHANDLED_STATE = "Unhandled state."
        const val INVALID_GENRE_ID = "Invalid genre id."
    }
}
