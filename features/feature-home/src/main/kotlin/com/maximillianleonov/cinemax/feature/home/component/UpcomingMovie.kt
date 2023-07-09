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

package com.maximillianleonov.cinemax.feature.home.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxImagePlaceholder
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxNetworkImage
import com.maximillianleonov.cinemax.core.designsystem.component.cinemaxPlaceholder
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme
import com.maximillianleonov.cinemax.core.model.Movie
import com.maximillianleonov.cinemax.core.ui.MoviesContainer
import com.maximillianleonov.cinemax.core.ui.R
import com.maximillianleonov.cinemax.core.ui.util.format
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun UpcomingMoviesContainer(
    movies: List<Movie>,
    onSeeAllClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState()
    val shouldShowPlaceholder = movies.isEmpty()
    val pageCount = if (shouldShowPlaceholder) {
        PlaceholderCount
    } else {
        movies.size
    }

    MoviesContainer(
        titleResourceId = R.string.upcoming_movies,
        onSeeAllClick = onSeeAllClick,
        modifier = modifier
    ) {
        HorizontalPager(
            state = pagerState,
            pageCount = pageCount,
            contentPadding = PaddingValues(horizontal = CinemaxTheme.spacing.extraLarge)
        ) { page ->
            if (shouldShowPlaceholder) {
                UpcomingMovieItemPlaceholder(
                    modifier = Modifier.pagerTransition(pagerState = pagerState, page = page)
                )
            } else {
                with(movies[page]) {
                    UpcomingMovieItem(
                        modifier = Modifier.pagerTransition(
                            pagerState = pagerState,
                            page = page
                        ),
                        title = title,
                        backdropPath = backdropPath,
                        releaseDate = releaseDate,
                        onClick = { onMovieClick(id) }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(CinemaxTheme.spacing.smallMedium))
        DefaultHorizontalPagerIndicator(
            modifier = Modifier
                .padding(horizontal = CinemaxTheme.spacing.extraMedium)
                .align(Alignment.CenterHorizontally),
            pagerState = pagerState,
            pageCount = pageCount
        )
    }
}

@Composable
private fun UpcomingMovieItem(
    title: String,
    backdropPath: String?,
    releaseDate: LocalDate?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CinemaxTheme.shapes.medium,
    isPlaceholder: Boolean = false
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(UpcomingMovieItemHeight)
            .clip(shape)
            .then(if (isPlaceholder) Modifier else Modifier.clickable(onClick = onClick)),
        shape = shape
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (isPlaceholder) {
                CinemaxImagePlaceholder()
            } else {
                CinemaxNetworkImage(
                    modifier = Modifier.fillMaxSize(),
                    model = backdropPath,
                    contentDescription = title
                )
            }

            Column(
                modifier = Modifier
                    .padding(
                        start = CinemaxTheme.spacing.medium,
                        bottom = CinemaxTheme.spacing.medium,
                        end = CinemaxTheme.spacing.largest
                    )
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    modifier = if (isPlaceholder) {
                        Modifier
                            .fillMaxWidth()
                            .cinemaxPlaceholder(color = CinemaxTheme.colors.white)
                    } else {
                        Modifier
                    },
                    text = title,
                    style = CinemaxTheme.typography.semiBold.h4,
                    color = CinemaxTheme.colors.white
                )
                Spacer(modifier = Modifier.height(CinemaxTheme.spacing.extraSmall))
                Text(
                    modifier = if (isPlaceholder) {
                        Modifier
                            .fillMaxWidth(
                                UpcomingMovieItemPlaceholderSecondTextMaxWidthFraction
                            )
                            .cinemaxPlaceholder(color = CinemaxTheme.colors.grey)
                    } else {
                        Modifier
                    },
                    text = releaseDate?.let { releaseDate ->
                        stringResource(
                            id = R.string.upcoming_movie_release_date_text,
                            releaseDate.format(UpcomingMovieItemDatePattern)
                        )
                    } ?: stringResource(id = R.string.no_release_date),
                    style = CinemaxTheme.typography.medium.h6,
                    color = CinemaxTheme.colors.whiteGrey
                )
            }
        }
    }
}

@Composable
private fun UpcomingMovieItemPlaceholder(modifier: Modifier = Modifier) {
    UpcomingMovieItem(
        modifier = modifier,
        title = UpcomingMovieItemPlaceholderText,
        backdropPath = UpcomingMovieItemPlaceholderText,
        releaseDate = null,
        onClick = {},
        isPlaceholder = true
    )
}

private val UpcomingMovieItemHeight = 154.dp
private const val UpcomingMovieItemDatePattern = "MMMM d, yyyy"
private const val UpcomingMovieItemPlaceholderSecondTextMaxWidthFraction = 0.5f
private const val UpcomingMovieItemPlaceholderText = ""

private const val PlaceholderCount = 20
