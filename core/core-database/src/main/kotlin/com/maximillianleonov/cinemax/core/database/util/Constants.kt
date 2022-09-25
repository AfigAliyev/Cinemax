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

package com.maximillianleonov.cinemax.core.database.util

internal object Constants {
    internal object Tables {
        internal const val MOVIES = "movies"
        internal const val TV_SHOWS = "tv_shows"

        internal const val MOVIES_REMOTE_KEYS = "movies_remote_keys"
        internal const val TV_SHOWS_REMOTE_KEYS = "tv_shows_remote_keys"

        internal const val MOVIE_DETAILS = "movie_details"
        internal const val TV_SHOW_DETAILS = "tv_show_details"

        internal const val WISHLIST = "wishlist"
    }

    internal object Fields {
        internal const val ID = "id"
        internal const val NETWORK_ID = "network_id"
        internal const val IMDB_ID = "imdb_id"
        internal const val CAST_ID = "cast_id"
        internal const val CREDIT_ID = "credit_id"
        internal const val CAST = "cast"
        internal const val CREW = "crew"
        internal const val CREDITS = "credits"
        internal const val CHARACTER = "character"
        internal const val BUDGET = "budget"
        internal const val PRODUCTION_COMPANIES = "production_companies"
        internal const val PRODUCTION_COUNTRIES = "production_countries"
        internal const val PRODUCTION_CODE = "production_code"
        internal const val STATUS = "status"
        internal const val SEASONS = "seasons"
        internal const val TYPE = "type"
        internal const val TAGLINE = "tagline"
        internal const val HOMEPAGE = "homepage"
        internal const val EPISODE_RUN_TIME = "episode_run_time"
        internal const val PAGE = "page"
        internal const val RESULTS = "results"
        internal const val DEPARTMENT = "department"
        internal const val TITLE = "title"
        internal const val NAME = "name"
        internal const val OVERVIEW = "overview"
        internal const val POPULARITY = "popularity"
        internal const val RELEASE_DATE = "release_date"
        internal const val FIRST_AIR_DATE = "first_air_date"
        internal const val LAST_AIR_DATE = "last_air_date"
        internal const val ADULT = "adult"
        internal const val KNOWN_FOR_DEPARTMENT = "known_for_department"
        internal const val NETWORKS = "networks"
        internal const val NUMBER_OF_EPISODES = "number_of_episodes"
        internal const val NUMBER_OF_SEASONS = "number_of_seasons"
        internal const val LANGUAGES = "languages"
        internal const val IN_PRODUCTION = "in_production"
        internal const val ORDER = "order"
        internal const val JOB = "job"
        internal const val REVENUE = "revenue"
        internal const val RUNTIME = "runtime"
        internal const val GENDER = "gender"
        internal const val GENRES = "genres"
        internal const val GENRE_IDS = "genre_ids"
        internal const val ORIGINAL_TITLE = "original_title"
        internal const val ORIGINAL_NAME = "original_name"
        internal const val ORIGINAL_LANGUAGE = "original_language"
        internal const val ORIGIN_COUNTRY = "origin_country"
        internal const val VOTE_AVERAGE = "vote_average"
        internal const val VOTE_COUNT = "vote_count"
        internal const val POSTER_PATH = "poster_path"
        internal const val BACKDROP_PATH = "backdrop_path"
        internal const val PROFILE_PATH = "profile_path"
        internal const val VIDEO = "video"
        internal const val MEDIA_TYPE = "media_type"
        internal const val PREV_PAGE = "prev_page"
        internal const val NEXT_PAGE = "next_page"
    }
}
