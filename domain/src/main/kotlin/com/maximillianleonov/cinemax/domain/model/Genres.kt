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

package com.maximillianleonov.cinemax.domain.model

import com.maximillianleonov.cinemax.domain.util.Constants

object Genres {
    enum class Movie(val id: Int) {
        ACTION(ACTION_ID),
        ADVENTURE(ADVENTURE_ID),
        ANIMATION(ANIMATION_ID),
        COMEDY(COMEDY_ID),
        CRIME(CRIME_ID),
        DOCUMENTARY(DOCUMENTARY_ID),
        DRAMA(DRAMA_ID),
        FAMILY(FAMILY_ID),
        FANTASY(FANTASY_ID),
        HISTORY(HISTORY_ID),
        HORROR(HORROR_ID),
        MUSIC(MUSIC_ID),
        MYSTERY(MYSTERY_ID),
        ROMANCE(ROMANCE_ID),
        SCIENCE_FICTION(SCIENCE_FICTION_ID),
        TV_MOVIE(TV_MOVIE_ID),
        THRILLER(THRILLER_ID),
        WAR(WAR_ID),
        WESTERN(WESTERN_ID);

        companion object {
            private val genres = values().associateBy(Movie::id)
            operator fun get(id: Int) =
                checkNotNull(genres[id]) { "${Constants.Messages.INVALID_GENRE_ID} $id" }
        }
    }

    enum class TvShow(val id: Int) {
        ACTION_ADVENTURE(ACTION_ADVENTURE_ID),
        ANIMATION(ANIMATION_ID),
        COMEDY(COMEDY_ID),
        CRIME(CRIME_ID),
        DOCUMENTARY(DOCUMENTARY_ID),
        DRAMA(DRAMA_ID),
        FAMILY(FAMILY_ID),
        KIDS(KIDS_ID),
        MYSTERY(MYSTERY_ID),
        NEWS(NEWS_ID),
        REALITY(REALITY_ID),
        SCIENCE_FICTION_FANTASY(SCIENCE_FICTION_FANTASY_ID),
        SOAP(SOAP_ID),
        TALK(TALK_ID),
        WAR_POLITICS(WAR_POLITICS_ID),
        WESTERN(WESTERN_ID);

        companion object {
            private val genres = values().associateBy(TvShow::id)
            operator fun get(id: Int) =
                checkNotNull(genres[id]) { "${Constants.Messages.INVALID_GENRE_ID} $id" }
        }
    }
}

private const val ACTION_ID = 28
private const val ADVENTURE_ID = 12
private const val ACTION_ADVENTURE_ID = 10759
private const val ANIMATION_ID = 16
private const val COMEDY_ID = 35
private const val CRIME_ID = 80
private const val DOCUMENTARY_ID = 99
private const val DRAMA_ID = 18
private const val FAMILY_ID = 10751
private const val FANTASY_ID = 14
private const val HISTORY_ID = 36
private const val HORROR_ID = 27
private const val KIDS_ID = 10762
private const val MYSTERY_ID = 9648
private const val NEWS_ID = 10763
private const val MUSIC_ID = 10402
private const val REALITY_ID = 10764
private const val ROMANCE_ID = 10749
private const val SCIENCE_FICTION_ID = 878
private const val SCIENCE_FICTION_FANTASY_ID = 10765
private const val SOAP_ID = 10766
private const val TALK_ID = 10767
private const val THRILLER_ID = 53
private const val TV_MOVIE_ID = 10770
private const val WAR_ID = 10752
private const val WAR_POLITICS_ID = 10768
private const val WESTERN_ID = 37
