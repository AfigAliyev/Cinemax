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

package com.maximillianleonov.cinemax.feature.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxCardNetworkImage
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxImagePlaceholder
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxNetworkImage
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxOverlay
import com.maximillianleonov.cinemax.core.designsystem.component.cinemaxPlaceholder
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme
import com.maximillianleonov.cinemax.core.model.Credits
import com.maximillianleonov.cinemax.core.ui.R
import com.maximillianleonov.cinemax.core.ui.RatingItem
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Suppress("LongParameterList")
@Composable
internal fun DetailsItem(
    title: String,
    overview: String,
    posterPath: String?,
    releaseDate: LocalDate?,
    runtime: String,
    genres: List<String>,
    voteAverage: Double,
    credits: Credits,
    isWishlisted: Boolean,
    onBackButtonClick: () -> Unit,
    onWishlistButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WindowInsets.safeDrawing.only(
        WindowInsetsSides.Horizontal + WindowInsetsSides.Top
    ),
    isPlaceholder: Boolean = false
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(BackdropHeight)
        ) {
            if (isPlaceholder) {
                Box(
                    modifier = Modifier
                        .background(color = CinemaxTheme.colors.primarySoft)
                        .fillMaxSize()
                )
            } else {
                CinemaxNetworkImage(
                    modifier = Modifier.fillMaxSize(),
                    model = posterPath,
                    contentDescription = title
                )
            }
            CinemaxOverlay(color = CinemaxTheme.colors.primaryDark, alpha = BackdropAlpha)
        }

        Scaffold(
            topBar = {
                if (isPlaceholder) {
                    TopAppBarPlaceholder(
                        isWishlisted = isWishlisted,
                        onBackButtonClick = onBackButtonClick,
                        onWishlistButtonClick = onWishlistButtonClick
                    )
                } else {
                    TopAppBar(
                        title = title,
                        isWishlisted = isWishlisted,
                        onBackButtonClick = onBackButtonClick,
                        onWishlistButtonClick = onWishlistButtonClick,
                        windowInsets = windowInsets
                    )
                }
            },
            containerColor = Color.Transparent,
            contentWindowInsets = windowInsets
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.extraMedium)
            ) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.extraMedium)
                    ) {
                        if (isPlaceholder) {
                            CinemaxImagePlaceholder(
                                modifier = Modifier
                                    .size(width = PosterWidth, height = PosterHeight)
                                    .clip(CinemaxTheme.shapes.smallMedium)
                            )
                        } else {
                            CinemaxCardNetworkImage(
                                modifier = Modifier.size(
                                    width = PosterWidth,
                                    height = PosterHeight
                                ),
                                model = posterPath,
                                contentDescription = title,
                                shape = CinemaxTheme.shapes.smallMedium
                            )
                        }
                        Column(
                            modifier = Modifier.padding(horizontal = CinemaxTheme.spacing.largest),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (isPlaceholder) {
                                IconAndTextPlaceholder(iconResourceId = R.drawable.ic_calendar)
                                IconAndTextPlaceholder(iconResourceId = R.drawable.ic_clock)
                                IconAndTextPlaceholder(iconResourceId = R.drawable.ic_film)
                            } else {
                                IconAndText(
                                    iconResourceId = R.drawable.ic_calendar,
                                    text = releaseDate?.year?.toString()
                                        ?: stringResource(id = R.string.no_release_date)
                                )
                                IconAndText(iconResourceId = R.drawable.ic_clock, text = runtime)
                                IconAndText(
                                    iconResourceId = R.drawable.ic_film,
                                    text = genres.joinToString(separator = GenreSeparator)
                                        .ifEmpty { stringResource(id = R.string.no_genre) }
                                )
                            }
                        }
                        if (isPlaceholder) {
                            RatingItem(
                                modifier = Modifier.cinemaxPlaceholder(
                                    color = CinemaxTheme.colors.secondaryOrange
                                ),
                                rating = PlaceholderRating
                            )
                        } else {
                            RatingItem(rating = voteAverage)
                        }
                    }
                }
                item {
                    if (isPlaceholder) OverviewPlaceholder() else Overview(overview = overview)
                }
                item {
                    if (isPlaceholder) CastAndCrewPlaceholder() else CastAndCrew(credits = credits)
                }
                item {
                    Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
                }
            }
        }
    }
}

@Composable
internal fun DetailsItemPlaceholder(
    onBackButtonClick: () -> Unit,
    onWishlistButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DetailsItem(
        modifier = modifier,
        title = PlaceholderText,
        overview = PlaceholderText,
        posterPath = null,
        releaseDate = null,
        runtime = PlaceholderText,
        genres = emptyList(),
        voteAverage = PlaceholderRating,
        credits = Credits(cast = emptyList(), crew = emptyList()),
        isWishlisted = false,
        onBackButtonClick = onBackButtonClick,
        onWishlistButtonClick = onWishlistButtonClick,
        isPlaceholder = true
    )
}

private val BackdropHeight = 552.dp
private val PosterWidth = 205.dp
private val PosterHeight = 287.dp
private const val BackdropAlpha = 0.2f
private const val GenreSeparator = ", "
private const val PlaceholderText = ""
private const val PlaceholderRating = 0.0
