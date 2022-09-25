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

package com.maximillianleonov.cinemax.core.network.model.common

import com.maximillianleonov.cinemax.core.network.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkGenre(
    @SerialName(Constants.Fields.ID)
    val id: Int,

    @SerialName(Constants.Fields.NAME)
    val name: String
)

enum class NetworkGenreWithId(val genreName: String, val id: Int) {
    ACTION(ACTION_NAME, ACTION_GENRE_ID),
    ADVENTURE(ADVENTURE_NAME, ADVENTURE_GENRE_ID),
    ACTION_ADVENTURE(ACTION_ADVENTURE_NAME, ACTION_ADVENTURE_GENRE_ID),
    ANIMATION(ANIMATION_NAME, ANIMATION_GENRE_ID),
    COMEDY(COMEDY_NAME, COMEDY_GENRE_ID),
    CRIME(CRIME_NAME, CRIME_GENRE_ID),
    DOCUMENTARY(DOCUMENTARY_NAME, DOCUMENTARY_GENRE_ID),
    DRAMA(DRAMA_NAME, DRAMA_GENRE_ID),
    FAMILY(FAMILY_NAME, FAMILY_GENRE_ID),
    FANTASY(FANTASY_NAME, FANTASY_GENRE_ID),
    HISTORY(HISTORY_NAME, HISTORY_GENRE_ID),
    HORROR(HORROR_NAME, HORROR_GENRE_ID),
    KIDS(KIDS_NAME, KIDS_GENRE_ID),
    MUSIC(MUSIC_NAME, MUSIC_GENRE_ID),
    MYSTERY(MYSTERY_NAME, MYSTERY_GENRE_ID),
    NEWS(NEWS_NAME, NEWS_GENRE_ID),
    REALITY(REALITY_NAME, REALITY_GENRE_ID),
    ROMANCE(ROMANCE_NAME, ROMANCE_GENRE_ID),
    SCIENCE_FICTION(SCIENCE_FICTION_NAME, SCIENCE_FICTION_GENRE_ID),
    SCIENCE_FICTION_FANTASY(SCIENCE_FICTION_FANTASY_NAME, SCIENCE_FICTION_FANTASY_GENRE_ID),
    SOAP(SOAP_NAME, SOAP_GENRE_ID),
    TALK(TALK_NAME, TALK_GENRE_ID),
    THRILLER(THRILLER_NAME, THRILLER_GENRE_ID),
    TV_MOVIE(TV_MOVIE_NAME, TV_MOVIE_GENRE_ID),
    WAR(WAR_NAME, WAR_GENRE_ID),
    WAR_POLITICS(WAR_POLITICS_NAME, WAR_POLITICS_GENRE_ID),
    WESTERN(WESTERN_NAME, WESTERN_GENRE_ID);

    companion object {
        private val genres = values().associateBy(NetworkGenreWithId::id)
        operator fun get(id: Int) = checkNotNull(genres[id]) {
            "$INVALID_GENRE_ID_MESSAGE $id"
        }
    }
}

private const val ACTION_NAME = "action"
private const val ACTION_GENRE_ID = 28

private const val ADVENTURE_NAME = "adventure"
private const val ADVENTURE_GENRE_ID = 12

private const val ACTION_ADVENTURE_NAME = "action_adventure"
private const val ACTION_ADVENTURE_GENRE_ID = 10759

private const val ANIMATION_NAME = "animation"
private const val ANIMATION_GENRE_ID = 16

private const val COMEDY_NAME = "comedy"
private const val COMEDY_GENRE_ID = 35

private const val CRIME_NAME = "crime"
private const val CRIME_GENRE_ID = 80

private const val DOCUMENTARY_NAME = "documentary"
private const val DOCUMENTARY_GENRE_ID = 99

private const val DRAMA_NAME = "drama"
private const val DRAMA_GENRE_ID = 18

private const val FAMILY_NAME = "family"
private const val FAMILY_GENRE_ID = 10751

private const val FANTASY_NAME = "fantasy"
private const val FANTASY_GENRE_ID = 14

private const val HISTORY_NAME = "history"
private const val HISTORY_GENRE_ID = 36

private const val HORROR_NAME = "horror"
private const val HORROR_GENRE_ID = 27

private const val KIDS_NAME = "kids"
private const val KIDS_GENRE_ID = 10762

private const val MUSIC_NAME = "music"
private const val MUSIC_GENRE_ID = 10402

private const val MYSTERY_NAME = "mystery"
private const val MYSTERY_GENRE_ID = 9648

private const val NEWS_NAME = "news"
private const val NEWS_GENRE_ID = 10763

private const val REALITY_NAME = "reality"
private const val REALITY_GENRE_ID = 10764

private const val ROMANCE_NAME = "romance"
private const val ROMANCE_GENRE_ID = 10749

private const val SCIENCE_FICTION_NAME = "science_fiction"
private const val SCIENCE_FICTION_GENRE_ID = 878

private const val SCIENCE_FICTION_FANTASY_NAME = "science_fiction_fantasy"
private const val SCIENCE_FICTION_FANTASY_GENRE_ID = 10765

private const val SOAP_NAME = "soap"
private const val SOAP_GENRE_ID = 10766

private const val TALK_NAME = "talk"
private const val TALK_GENRE_ID = 10767

private const val THRILLER_NAME = "thriller"
private const val THRILLER_GENRE_ID = 53

private const val TV_MOVIE_NAME = "tv_movie"
private const val TV_MOVIE_GENRE_ID = 10770

private const val WAR_NAME = "war"
private const val WAR_GENRE_ID = 10752

private const val WAR_POLITICS_NAME = "war_politics"
private const val WAR_POLITICS_GENRE_ID = 10768

private const val WESTERN_NAME = "western"
private const val WESTERN_GENRE_ID = 37

private const val INVALID_GENRE_ID_MESSAGE = "Invalid genre id."
