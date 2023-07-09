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

package com.maximillianleonov.cinemax.core.ui.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.maximillianleonov.cinemax.core.domain.model.GenreModel
import com.maximillianleonov.cinemax.core.model.Genre
import com.maximillianleonov.cinemax.core.ui.R

@Composable
fun List<Genre>.asNames() = map { genre -> stringResource(id = genre.nameResourceId) }

internal fun List<GenreModel>.asGenres() = map { genre ->
    Genre(nameResourceId = genre.asNameResourceId())
}

private fun GenreModel.asNameResourceId() = genresNameResources.getValue(this)

private val genresNameResources = mapOf(
    GenreModel.ACTION to R.string.action,
    GenreModel.ADVENTURE to R.string.adventure,
    GenreModel.ACTION_ADVENTURE to R.string.action_adventure,
    GenreModel.ANIMATION to R.string.animation,
    GenreModel.COMEDY to R.string.comedy,
    GenreModel.CRIME to R.string.crime,
    GenreModel.DOCUMENTARY to R.string.documentary,
    GenreModel.DRAMA to R.string.drama,
    GenreModel.FAMILY to R.string.family,
    GenreModel.FANTASY to R.string.fantasy,
    GenreModel.HISTORY to R.string.history,
    GenreModel.HORROR to R.string.horror,
    GenreModel.KIDS to R.string.kids,
    GenreModel.MUSIC to R.string.music,
    GenreModel.MYSTERY to R.string.mystery,
    GenreModel.NEWS to R.string.news,
    GenreModel.REALITY to R.string.reality,
    GenreModel.ROMANCE to R.string.romance,
    GenreModel.SCIENCE_FICTION to R.string.science_fiction,
    GenreModel.SCIENCE_FICTION_FANTASY to R.string.science_fiction_fantasy,
    GenreModel.SOAP to R.string.soap,
    GenreModel.TALK to R.string.talk,
    GenreModel.THRILLER to R.string.thriller,
    GenreModel.TV_MOVIE to R.string.tv_movie,
    GenreModel.WAR to R.string.war,
    GenreModel.WAR_POLITICS to R.string.war_politics,
    GenreModel.WESTERN to R.string.western
)
