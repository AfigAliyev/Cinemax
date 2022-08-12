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

package com.maximillianleonov.cinemax.data.remote.dto.movie

import com.maximillianleonov.cinemax.core.data.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    @SerialName(Constants.Fields.ID)
    val id: Int,

    @SerialName(Constants.Fields.NAME)
    val genreName: String
)

object Genres {
    enum class Movie(val id: Int, val genreName: String) {
        ACTION(ACTION_ID, ACTION_TEXT),
        ADVENTURE(ADVENTURE_ID, ADVENTURE_TEXT),
        ANIMATION(ANIMATION_ID, ANIMATION_TEXT),
        COMEDY(COMEDY_ID, COMEDY_TEXT),
        CRIME(CRIME_ID, CRIME_TEXT),
        DOCUMENTARY(DOCUMENTARY_ID, DOCUMENTARY_TEXT),
        DRAMA(DRAMA_ID, DRAMA_TEXT),
        FAMILY(FAMILY_ID, FAMILY_TEXT),
        FANTASY(FANTASY_ID, FANTASY_TEXT),
        HISTORY(HISTORY_ID, HISTORY_TEXT),
        HORROR(HORROR_ID, HORROR_TEXT),
        MUSIC(MUSIC_ID, MUSIC_TEXT),
        MYSTERY(MYSTERY_ID, MYSTERY_TEXT),
        ROMANCE(ROMANCE_ID, ROMANCE_TEXT),
        SCIENCE_FICTION(SCIENCE_FICTION_ID, SCIENCE_FICTION_TEXT),
        TV_MOVIE(TV_MOVIE_ID, TV_MOVIE_TEXT),
        THRILLER(THRILLER_ID, THRILLER_TEXT),
        WAR(WAR_ID, WAR_TEXT),
        WESTERN(WESTERN_ID, WESTERN_TEXT);

        companion object {
            private val genres = values().associateBy(Movie::id)
            operator fun get(id: Int) =
                checkNotNull(genres[id]) { "${Constants.Messages.INVALID_GENRE_ID} $id" }
        }
    }

    enum class TvShow(val id: Int, val genreName: String) {
        ACTION_ADVENTURE(ACTION_ADVENTURE_ID, ACTION_ADVENTURE_TEXT),
        ANIMATION(ANIMATION_ID, ANIMATION_TEXT),
        COMEDY(COMEDY_ID, COMEDY_TEXT),
        CRIME(CRIME_ID, CRIME_TEXT),
        DOCUMENTARY(DOCUMENTARY_ID, DOCUMENTARY_TEXT),
        DRAMA(DRAMA_ID, DRAMA_TEXT),
        FAMILY(FAMILY_ID, FAMILY_TEXT),
        KIDS(KIDS_ID, KIDS_TEXT),
        MYSTERY(MYSTERY_ID, MYSTERY_TEXT),
        NEWS(NEWS_ID, NEWS_TEXT),
        REALITY(REALITY_ID, REALITY_TEXT),
        SCIENCE_FICTION_FANTASY(SCIENCE_FICTION_FANTASY_ID, SCIENCE_FICTION_FANTASY_TEXT),
        SOAP(SOAP_ID, SOAP_TEXT),
        TALK(TALK_ID, TALK_TEXT),
        WAR_POLITICS(WAR_POLITICS_ID, WAR_POLITICS_TEXT),
        WESTERN(WESTERN_ID, WESTERN_TEXT);

        companion object {
            private val genres = values().associateBy(TvShow::id)
            operator fun get(id: Int) =
                checkNotNull(genres[id]) { "${Constants.Messages.INVALID_GENRE_ID} $id" }
        }
    }
}

private const val ACTION_ID = 28
private const val ACTION_TEXT = "Action"

private const val ADVENTURE_ID = 12
private const val ADVENTURE_TEXT = "Adventure"

private const val ACTION_ADVENTURE_ID = 10759
private const val ACTION_ADVENTURE_TEXT = "Action & Adventure"

private const val ANIMATION_ID = 16
private const val ANIMATION_TEXT = "Animation"

private const val COMEDY_ID = 35
private const val COMEDY_TEXT = "Comedy"

private const val CRIME_ID = 80
private const val CRIME_TEXT = "Crime"

private const val DOCUMENTARY_ID = 99
private const val DOCUMENTARY_TEXT = "Documentary"

private const val DRAMA_ID = 18
private const val DRAMA_TEXT = "Drama"

private const val FAMILY_ID = 10751
private const val FAMILY_TEXT = "Family"

private const val FANTASY_ID = 14
private const val FANTASY_TEXT = "Fantasy"

private const val HISTORY_ID = 36
private const val HISTORY_TEXT = "History"

private const val HORROR_ID = 27
private const val HORROR_TEXT = "Horror"

private const val KIDS_ID = 10762
private const val KIDS_TEXT = "Kids"

private const val MYSTERY_ID = 9648
private const val MYSTERY_TEXT = "Mystery"

private const val NEWS_ID = 10763
private const val NEWS_TEXT = "News"

private const val MUSIC_ID = 10402
private const val MUSIC_TEXT = "Music"

private const val REALITY_ID = 10764
private const val REALITY_TEXT = "Reality"

private const val ROMANCE_ID = 10749
private const val ROMANCE_TEXT = "Romance"

private const val SCIENCE_FICTION_ID = 878
private const val SCIENCE_FICTION_TEXT = "Science Fiction"

private const val SCIENCE_FICTION_FANTASY_ID = 10765
private const val SCIENCE_FICTION_FANTASY_TEXT = "Sci-Fi & Fantasy"

private const val SOAP_ID = 10766
private const val SOAP_TEXT = "Soap"

private const val TALK_ID = 10767
private const val TALK_TEXT = "Talk"

private const val THRILLER_ID = 53
private const val THRILLER_TEXT = "Thriller"

private const val TV_MOVIE_ID = 10770
private const val TV_MOVIE_TEXT = "TV MovieModel"

private const val WAR_ID = 10752
private const val WAR_TEXT = "War"

private const val WAR_POLITICS_ID = 10768
private const val WAR_POLITICS_TEXT = "War & Politics"

private const val WESTERN_ID = 37
private const val WESTERN_TEXT = "Western"
