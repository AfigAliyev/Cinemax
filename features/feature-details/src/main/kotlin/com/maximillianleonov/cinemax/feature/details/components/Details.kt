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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.maximillianleonov.cinemax.core.ui.R
import com.maximillianleonov.cinemax.core.ui.components.CinemaxCardImage
import com.maximillianleonov.cinemax.core.ui.components.CinemaxCenteredBox
import com.maximillianleonov.cinemax.core.ui.components.CinemaxImage
import com.maximillianleonov.cinemax.core.ui.components.CinemaxOverlay
import com.maximillianleonov.cinemax.core.ui.components.CinemaxPlaceholder
import com.maximillianleonov.cinemax.core.ui.components.RatingItem
import com.maximillianleonov.cinemax.core.ui.model.Credits
import com.maximillianleonov.cinemax.core.ui.theme.CinemaxTheme
import kotlinx.datetime.LocalDate

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
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        @Suppress("ForbiddenComment")
        TopBar(
            modifier = Modifier
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal + WindowInsetsSides.Top
                    )
                )
                .padding(top = CinemaxTheme.spacing.small)
                .zIndex(1f),
            title = title,
            isWishlisted = false, // TODO: Not yet implemented.
            onBackButtonClick = onBackButtonClick,
            onWishlistButtonClick = { /* TODO: Not yet implemented. */ }
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.extraMedium)
        ) {
            item {
                CinemaxCenteredBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(BackdropHeight)
                ) {
                    CinemaxImage(
                        modifier = Modifier.fillMaxSize(),
                        model = posterPath,
                        contentDescription = title
                    )
                    CinemaxOverlay(color = CinemaxTheme.colors.primaryDark, alpha = BackdropAlpha)
                    Column(
                        modifier = Modifier.windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal + WindowInsetsSides.Top
                            )
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.extraMedium)
                    ) {
                        Spacer(modifier = Modifier.height(TopBarHeight))
                        CinemaxCardImage(
                            modifier = Modifier.size(width = PosterWidth, height = PosterHeight),
                            model = posterPath,
                            contentDescription = title,
                            shape = CinemaxTheme.shapes.smallMedium
                        )
                        Column(
                            modifier = Modifier.padding(horizontal = CinemaxTheme.spacing.largest),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
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
                        RatingItem(rating = voteAverage)
                    }
                }
            }
            item {
                Overview(overview = overview)
            }
            item {
                CastAndCrew(credits = credits)
            }
            item {
                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
            }
        }
    }
}

@Composable
internal fun DetailsItemPlaceholder(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    shape: Shape = CinemaxTheme.shapes.medium,
    highlight: PlaceholderHighlight = PlaceholderHighlight.shimmer()
) {
    Box(modifier = modifier) {
        TopBarPlaceholder(
            modifier = Modifier
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal + WindowInsetsSides.Top
                    )
                )
                .padding(top = CinemaxTheme.spacing.small)
                .zIndex(1f),
            onBackButtonClick = onBackButtonClick,
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.extraMedium)
        ) {
            item {
                CinemaxCenteredBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(BackdropHeight)
                ) {
                    Box(
                        modifier = Modifier
                            .background(color = CinemaxTheme.colors.primarySoft)
                            .fillMaxSize()
                    )
                    CinemaxOverlay(color = CinemaxTheme.colors.primaryDark, alpha = BackdropAlpha)
                    Column(
                        modifier = Modifier.windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal + WindowInsetsSides.Top
                            )
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.extraMedium)
                    ) {
                        Spacer(modifier = Modifier.height(TopBarHeight))
                        CinemaxPlaceholder(
                            modifier = Modifier
                                .size(
                                    width = PosterWidth,
                                    height = PosterHeight
                                )
                                .clip(CinemaxTheme.shapes.smallMedium)
                        )
                        Column(
                            modifier = Modifier.padding(horizontal = CinemaxTheme.spacing.largest),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconAndTextPlaceholder(iconResourceId = R.drawable.ic_calendar)
                            IconAndTextPlaceholder(iconResourceId = R.drawable.ic_clock)
                            IconAndTextPlaceholder(iconResourceId = R.drawable.ic_film)
                        }
                        RatingItem(
                            modifier = Modifier.placeholder(
                                visible = visible,
                                color = CinemaxTheme.colors.secondaryOrange,
                                shape = shape,
                                highlight = highlight
                            ),
                            rating = PlaceholderRating
                        )
                    }
                }
            }
            item {
                OverviewPlaceholder()
            }
            item {
                CastAndCrewPlaceholder()
            }
            item {
                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
            }
        }
    }
}

private val BackdropHeight = 552.dp
private val PosterWidth = 205.dp
private val PosterHeight = 287.dp
private const val BackdropAlpha = 0.2f
private const val GenreSeparator = ", "
private const val PlaceholderRating = 0.0
