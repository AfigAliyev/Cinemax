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

package com.maximillianleonov.cinemax.core.network.util

internal object Constants {
    internal const val API_URL = "https://api.themoviedb.org/3/"
    internal const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    internal const val API_KEY_QUERY_PARAM = "api_key"
    internal const val PAGE_SIZE = 20
    internal const val DEFAULT_PAGE = 1

    internal object Path {
        internal const val UPCOMING_MOVIE = "movie/upcoming"
        internal const val TOP_RATED_MOVIE = "movie/top_rated"
        internal const val POPULAR_MOVIE = "movie/popular"
        internal const val NOW_PLAYING_MOVIE = "movie/now_playing"
        internal const val DISCOVER_MOVIE = "discover/movie"
        internal const val TRENDING_MOVIE = "trending/movie/day"
        internal const val DETAILS_MOVIE = "movie/{id}"
        internal const val SEARCH_MOVIE = "search/movie"

        internal const val TOP_RATED_TV_SHOW = "tv/top_rated"
        internal const val POPULAR_TV_SHOW = "tv/popular"
        internal const val ON_THE_AIR_TV_SHOW = "tv/on_the_air"
        internal const val DISCOVER_TV_SHOW = "discover/tv"
        internal const val TRENDING_TV_SHOW = "trending/tv/day"
        internal const val DETAILS_TV_SHOW = "tv/{id}"
        internal const val SEARCH_TV_SHOW = "search/tv"
    }

    internal object Fields {
        internal const val ID = "id"
        internal const val IMDB_ID = "imdb_id"
        internal const val CAST_ID = "cast_id"
        internal const val CREDIT_ID = "credit_id"
        internal const val SHOW_ID = "show_id"
        internal const val CAST = "cast"
        internal const val CREW = "crew"
        internal const val CREDITS = "credits"
        internal const val CHARACTER = "character"
        internal const val BUDGET = "budget"
        internal const val BELONGS_TO_COLLECTION = "belongs_to_collection"
        internal const val CREATED_BY = "created_by"
        internal const val PRODUCTION_COMPANIES = "production_companies"
        internal const val PRODUCTION_COUNTRIES = "production_countries"
        internal const val PRODUCTION_CODE = "production_code"
        internal const val SPOKEN_LANGUAGES = "spoken_languages"
        internal const val SEASON_NUMBER = "season_number"
        internal const val STATUS = "status"
        internal const val SEASONS = "seasons"
        internal const val TYPE = "type"
        internal const val TAGLINE = "tagline"
        internal const val HOMEPAGE = "homepage"
        internal const val AIR_DATE = "air_date"
        internal const val EPISODE_NUMBER = "episode_number"
        internal const val EPISODE_COUNT = "episode_count"
        internal const val EPISODE_RUN_TIME = "episode_run_time"
        internal const val PAGE = "page"
        internal const val TOTAL_PAGES = "total_pages"
        internal const val RESULTS = "results"
        internal const val TOTAL_RESULTS = "total_results"
        internal const val DATES = "dates"
        internal const val DEPARTMENT = "department"
        internal const val TITLE = "title"
        internal const val NAME = "name"
        internal const val ENGLISH_NAME = "english_name"
        internal const val OVERVIEW = "overview"
        internal const val POPULARITY = "popularity"
        internal const val RELEASE_DATE = "release_date"
        internal const val FIRST_AIR_DATE = "first_air_date"
        internal const val LAST_AIR_DATE = "last_air_date"
        internal const val LAST_EPISODE_TO_AIR = "last_episode_to_air"
        internal const val NEXT_EPISODE_TO_AIR = "next_episode_to_air"
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
        internal const val LOGO_PATH = "logo_path"
        internal const val STILL_PATH = "still_path"
        internal const val VIDEO = "video"
        internal const val MAXIMUM = "maximum"
        internal const val MINIMUM = "minimum"
        internal const val ISO_3166_1 = "iso_3166_1"
        internal const val ISO_639_1 = "iso_639_1"
        internal const val APPEND_TO_RESPONSE = "append_to_response"
        internal const val QUERY = "query"

        internal val DETAILS_APPEND_TO_RESPONSE = buildAppendToResponse(CREDITS)

        private fun buildAppendToResponse(vararg fields: String) =
            fields.joinToString(separator = APPEND_TO_RESPONSE_SEPARATOR)

        private const val APPEND_TO_RESPONSE_SEPARATOR = ","
    }
}

const val PAGE_SIZE = Constants.PAGE_SIZE
const val DEFAULT_PAGE = Constants.DEFAULT_PAGE

@PublishedApi
internal const val MESSAGE_UNHANDLED_STATE = "Unhandled state."
