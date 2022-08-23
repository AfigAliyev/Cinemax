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

package com.maximillianleonov.cinemax.core.presentation.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.maximillianleonov.cinemax.core.presentation.R
import com.maximillianleonov.cinemax.core.presentation.model.Genre
import com.maximillianleonov.cinemax.domain.model.Genres

@JvmName("toMovieGenres")
internal fun List<Genres.Movie>.toGenres() = map { genre ->
    Genre(id = genre.id, nameResourceId = genre.toNameResourceId())
}

@JvmName("toTvShowGenres")
internal fun List<Genres.TvShow>.toGenres() = map { genre ->
    Genre(id = genre.id, nameResourceId = genre.toNameResourceId())
}

@Composable
internal fun List<Genre>.toNames() = map { genre -> stringResource(id = genre.nameResourceId) }

@Suppress("ComplexMethod")
private fun Genres.Movie.toNameResourceId() = when (this) {
    Genres.Movie.ACTION -> R.string.action
    Genres.Movie.ADVENTURE -> R.string.adventure
    Genres.Movie.ANIMATION -> R.string.animation
    Genres.Movie.COMEDY -> R.string.comedy
    Genres.Movie.CRIME -> R.string.crime
    Genres.Movie.DOCUMENTARY -> R.string.documentary
    Genres.Movie.DRAMA -> R.string.drama
    Genres.Movie.FAMILY -> R.string.family
    Genres.Movie.FANTASY -> R.string.fantasy
    Genres.Movie.HISTORY -> R.string.history
    Genres.Movie.HORROR -> R.string.horror
    Genres.Movie.MUSIC -> R.string.music
    Genres.Movie.MYSTERY -> R.string.mystery
    Genres.Movie.ROMANCE -> R.string.romance
    Genres.Movie.SCIENCE_FICTION -> R.string.science_fiction
    Genres.Movie.TV_MOVIE -> R.string.tv_movie
    Genres.Movie.THRILLER -> R.string.thriller
    Genres.Movie.WAR -> R.string.war
    Genres.Movie.WESTERN -> R.string.western
}

@Suppress("ComplexMethod")
private fun Genres.TvShow.toNameResourceId() = when (this) {
    Genres.TvShow.ACTION_ADVENTURE -> R.string.action_adventure
    Genres.TvShow.ANIMATION -> R.string.animation
    Genres.TvShow.COMEDY -> R.string.comedy
    Genres.TvShow.CRIME -> R.string.crime
    Genres.TvShow.DOCUMENTARY -> R.string.documentary
    Genres.TvShow.DRAMA -> R.string.drama
    Genres.TvShow.FAMILY -> R.string.family
    Genres.TvShow.KIDS -> R.string.kids
    Genres.TvShow.MYSTERY -> R.string.mystery
    Genres.TvShow.NEWS -> R.string.news
    Genres.TvShow.REALITY -> R.string.reality
    Genres.TvShow.SCIENCE_FICTION_FANTASY -> R.string.science_fiction_fantasy
    Genres.TvShow.SOAP -> R.string.soap
    Genres.TvShow.TALK -> R.string.talk
    Genres.TvShow.WAR_POLITICS -> R.string.war_politics
    Genres.TvShow.WESTERN -> R.string.western
}
