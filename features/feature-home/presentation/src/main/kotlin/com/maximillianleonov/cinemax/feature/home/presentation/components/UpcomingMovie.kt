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

package com.maximillianleonov.cinemax.feature.home.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.maximillianleonov.cinemax.core.presentation.R
import com.maximillianleonov.cinemax.core.presentation.components.CinemaxPlaceholder
import com.maximillianleonov.cinemax.core.presentation.components.MoviesContainer
import com.maximillianleonov.cinemax.core.presentation.components.SubcomposeAsyncImageHandler
import com.maximillianleonov.cinemax.core.presentation.model.Movie
import com.maximillianleonov.cinemax.core.presentation.theme.CinemaxTheme
import com.maximillianleonov.cinemax.core.presentation.util.format

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun UpcomingMoviesContainer(
    movies: List<Movie>,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState()
    val shouldShowPlaceholder = movies.isEmpty()
    val count = if (shouldShowPlaceholder) {
        UpcomingMoviesContainerPagerPlaceholderCount
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
            count = count,
            contentPadding = PaddingValues(horizontal = CinemaxTheme.spacing.extraLarge)
        ) { page ->
            if (shouldShowPlaceholder) {
                UpcomingMovieItemPlaceholder(
                    modifier = Modifier.pagerTransition(pagerScope = this, page = page)
                )
            } else {
                UpcomingMovieItem(
                    modifier = Modifier.pagerTransition(pagerScope = this, page = page),
                    movie = movies[page]
                )
            }
        }
        Spacer(modifier = Modifier.height(CinemaxTheme.spacing.smallMedium))
        DefaultHorizontalPagerIndicator(
            modifier = Modifier
                .padding(horizontal = CinemaxTheme.spacing.extraMedium)
                .align(Alignment.CenterHorizontally),
            pagerState = pagerState
        )
    }
}

@Composable
private fun UpcomingMovieItem(
    movie: Movie,
    modifier: Modifier = Modifier,
    shape: Shape = CinemaxTheme.shapes.medium
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(UpcomingMovieHeight),
        shape = shape
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = movie.backdropPath,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop
            ) { SubcomposeAsyncImageHandler() }

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
                    text = movie.title,
                    style = CinemaxTheme.typography.semiBold.h4,
                    color = CinemaxTheme.colors.textWhite
                )
                Spacer(modifier = Modifier.height(CinemaxTheme.spacing.extraSmall))
                Text(
                    text = movie.releaseDate?.let { releaseDate ->
                        stringResource(
                            id = R.string.upcoming_movie_release_date_text,
                            releaseDate.format(UpcomingMovieDatePattern)
                        )
                    } ?: stringResource(id = R.string.no_release_date),
                    style = CinemaxTheme.typography.medium.h6,
                    color = CinemaxTheme.colors.textWhiteGrey
                )
            }
        }
    }
}

@Composable
private fun UpcomingMovieItemPlaceholder(
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    shape: Shape = CinemaxTheme.shapes.medium,
    highlight: PlaceholderHighlight = PlaceholderHighlight.shimmer()
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(UpcomingMovieHeight),
        shape = shape
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CinemaxPlaceholder()

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
                    modifier = Modifier
                        .fillMaxWidth()
                        .placeholder(
                            visible = true,
                            color = CinemaxTheme.colors.textWhite,
                            shape = shape,
                            highlight = highlight
                        ),
                    text = EmptyUpcomingMovieText,
                    style = CinemaxTheme.typography.semiBold.h4
                )
                Spacer(modifier = Modifier.height(CinemaxTheme.spacing.extraSmall))
                Text(
                    modifier = Modifier
                        .fillMaxWidth(EmptyUpcomingMovieItemSecondTextMaxWidthFraction)
                        .placeholder(
                            visible = visible,
                            color = CinemaxTheme.colors.textWhiteGrey,
                            shape = shape,
                            highlight = highlight
                        ),
                    text = EmptyUpcomingMovieText,
                    style = CinemaxTheme.typography.medium.h6
                )
            }
        }
    }
}

private val UpcomingMovieHeight = 154.dp
private const val UpcomingMovieDatePattern = "MMMM d, yyyy"
private const val UpcomingMoviesContainerPagerPlaceholderCount = 20
private const val EmptyUpcomingMovieItemSecondTextMaxWidthFraction = 0.5f
private const val EmptyUpcomingMovieText = ""
