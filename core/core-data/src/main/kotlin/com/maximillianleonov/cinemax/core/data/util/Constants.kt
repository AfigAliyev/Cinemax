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
        const val POPULAR_MOVIE_PATH = "movie/popular"
        const val NOW_PLAYING_MOVIE_PATH = "movie/now_playing"
        const val DISCOVER_MOVIE_PATH = "discover/movie"
        const val TRENDING_MOVIE_PATH = "trending/movie/day"
        const val SEARCH_MOVIE_PATH = "search/movie"
        const val DETAILS_MOVIE_PATH = "movie/{id}"

        const val TOP_RATED_TV_SHOW_PATH = "tv/top_rated"
        const val POPULAR_TV_SHOW_PATH = "tv/popular"
        const val ON_THE_AIR_TV_SHOW_PATH = "tv/on_the_air"
        const val DISCOVER_TV_SHOW_PATH = "discover/tv"
        const val TRENDING_TV_SHOW_PATH = "trending/tv/day"
        const val SEARCH_TV_SHOW_PATH = "search/tv"
        const val DETAILS_TV_SHOW_PATH = "tv/{id}"
    }

    object Tables {
        const val UPCOMING_MOVIES = "upcoming_movies"
        const val UPCOMING_MOVIES_REMOTE_KEYS = "upcoming_movies_remote_keys"

        const val TOP_RATED_MOVIES = "top_rated_movies"
        const val TOP_RATED_MOVIES_REMOTE_KEYS = "top_rated_movies_remote_keys"
        const val TOP_RATED_TV_SHOWS = "top_rated_tv_shows"
        const val TOP_RATED_TV_SHOWS_REMOTE_KEYS = "top_rated_tv_shows_remote_keys"

        const val POPULAR_MOVIES = "popular_movies"
        const val POPULAR_MOVIES_REMOTE_KEYS = "popular_movies_remote_keys"
        const val POPULAR_TV_SHOWS = "popular_tv_shows"
        const val POPULAR_TV_SHOWS_REMOTE_KEYS = "popular_tv_shows_remote_keys"

        const val NOW_PLAYING_MOVIES = "now_playing_movies"
        const val NOW_PLAYING_MOVIES_REMOTE_KEYS = "now_playing_movies_remote_keys"
        const val NOW_PLAYING_TV_SHOWS = "now_playing_tv_shows"
        const val NOW_PLAYING_TV_SHOWS_REMOTE_KEYS = "now_playing_tv_shows_remote_keys"

        const val DISCOVER_MOVIES = "discover_movies"
        const val DISCOVER_MOVIES_REMOTE_KEYS = "discover_movies_remote_keys"
        const val DISCOVER_TV_SHOWS = "discover_tv_shows"
        const val DISCOVER_TV_SHOWS_REMOTE_KEYS = "discover_tv_shows_remote_keys"

        const val TRENDING_MOVIES = "trending_movies"
        const val TRENDING_MOVIES_REMOTE_KEYS = "trending_movies_remote_keys"
        const val TRENDING_TV_SHOWS = "trending_tv_shows"
        const val TRENDING_TV_SHOWS_REMOTE_KEYS = "trending_tv_shows_remote_keys"

        const val MOVIE_DETAILS = "movie_details"
        const val TV_SHOW_DETAILS = "tv_show_details"
    }

    object Fields {
        const val ID = "id"
        const val REMOTE_ID = "remote_id"

        const val IMDB_ID = "imdb_id"
        const val CAST_ID = "cast_id"
        const val CREDIT_ID = "credit_id"
        const val SHOW_ID = "show_id"
        const val CAST = "cast"
        const val CREW = "crew"
        const val CREDITS = "credits"
        const val CHARACTER = "character"
        const val BUDGET = "budget"
        const val BELONGS_TO_COLLECTION = "belongs_to_collection"
        const val CREATED_BY = "created_by"
        const val PRODUCTION_COMPANIES = "production_companies"
        const val PRODUCTION_COUNTRIES = "production_countries"
        const val PRODUCTION_CODE = "production_code"
        const val SPOKEN_LANGUAGES = "spoken_languages"
        const val SEASON_NUMBER = "season_number"
        const val STATUS = "status"
        const val SEASONS = "seasons"
        const val TYPE = "type"
        const val TAGLINE = "tagline"
        const val HOMEPAGE = "homepage"
        const val AIR_DATE = "air_date"
        const val EPISODE_NUMBER = "episode_number"
        const val EPISODE_COUNT = "episode_count"
        const val EPISODE_RUN_TIME = "episode_run_time"
        const val PAGE = "page"
        const val TOTAL_PAGES = "total_pages"
        const val RESULTS = "results"
        const val TOTAL_RESULTS = "total_results"
        const val DATES = "dates"
        const val DEPARTMENT = "department"
        const val TITLE = "title"
        const val NAME = "name"
        const val ENGLISH_NAME = "english_name"
        const val OVERVIEW = "overview"
        const val POPULARITY = "popularity"
        const val RELEASE_DATE = "release_date"
        const val FIRST_AIR_DATE = "first_air_date"
        const val LAST_AIR_DATE = "last_air_date"
        const val LAST_EPISODE_TO_AIR = "last_episode_to_air"
        const val NEXT_EPISODE_TO_AIR = "next_episode_to_air"
        const val ADULT = "adult"
        const val KNOWN_FOR_DEPARTMENT = "known_for_department"
        const val NETWORKS = "networks"
        const val NUMBER_OF_EPISODES = "number_of_episodes"
        const val NUMBER_OF_SEASONS = "number_of_seasons"
        const val LANGUAGES = "languages"
        const val IN_PRODUCTION = "in_production"
        const val ORDER = "order"
        const val JOB = "job"
        const val REVENUE = "revenue"
        const val RUNTIME = "runtime"
        const val GENDER = "gender"
        const val GENRES = "genres"
        const val GENRE_IDS = "genre_ids"
        const val ORIGINAL_TITLE = "original_title"
        const val ORIGINAL_NAME = "original_name"
        const val ORIGINAL_LANGUAGE = "original_language"
        const val ORIGIN_COUNTRY = "origin_country"
        const val VOTE_AVERAGE = "vote_average"
        const val VOTE_COUNT = "vote_count"
        const val POSTER_PATH = "poster_path"
        const val BACKDROP_PATH = "backdrop_path"
        const val PROFILE_PATH = "profile_path"
        const val LOGO_PATH = "logo_path"
        const val STILL_PATH = "still_path"
        const val VIDEO = "video"
        const val MAXIMUM = "maximum"
        const val MINIMUM = "minimum"
        const val ISO_3166_1 = "iso_3166_1"
        const val ISO_639_1 = "iso_639_1"

        const val PREV_PAGE = "prev_page"
        const val NEXT_PAGE = "next_page"

        const val QUERY = "query"
    }

    object Messages {
        const val UNHANDLED_STATE = "Unhandled state."
    }
}
