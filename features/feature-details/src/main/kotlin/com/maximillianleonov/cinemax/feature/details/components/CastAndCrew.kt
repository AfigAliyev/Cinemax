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

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.maximillianleonov.cinemax.core.ui.R
import com.maximillianleonov.cinemax.core.ui.components.CinemaxCardImage
import com.maximillianleonov.cinemax.core.ui.components.CinemaxPlaceholder
import com.maximillianleonov.cinemax.core.ui.model.Credits
import com.maximillianleonov.cinemax.core.ui.theme.CinemaxTheme

@Composable
internal fun CastAndCrew(
    credits: Credits,
    modifier: Modifier = Modifier
) {
    val cast = credits.cast
    val crew = credits.crew

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.extraMedium)
    ) {
        if (cast.isNotEmpty()) {
            CastAndCrewContainer(titleResourceId = R.string.cast) {
                items(cast) { castItem ->
                    with(castItem) {
                        CastAndCrewItem(
                            profilePath = profilePath,
                            name = name,
                            description = character
                        )
                    }
                }
            }
        }
        if (crew.isNotEmpty()) {
            CastAndCrewContainer(titleResourceId = R.string.crew) {
                items(crew) { crewItem ->
                    with(crewItem) {
                        CastAndCrewItem(
                            profilePath = profilePath,
                            name = name,
                            description = job
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun CastAndCrewPlaceholder(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.extraMedium)
    ) {
        CastAndCrewContainerPlaceholder(titleResourceId = R.string.cast)
        CastAndCrewContainerPlaceholder(titleResourceId = R.string.crew)
    }
}

@Suppress("ModifierParameterPosition", "ComposableParametersOrdering")
@Composable
private fun CastAndCrewContainer(
    @StringRes titleResourceId: Int,
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.medium)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = CinemaxTheme.spacing.extraMedium),
            text = stringResource(id = titleResourceId),
            style = CinemaxTheme.typography.semiBold.h4,
            color = CinemaxTheme.colors.textWhite
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.smallMedium),
            contentPadding = PaddingValues(horizontal = CinemaxTheme.spacing.extraMedium),
            content = content
        )
    }
}

@Composable
private fun CastAndCrewContainerPlaceholder(
    @StringRes titleResourceId: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.medium)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = CinemaxTheme.spacing.extraMedium),
            text = stringResource(id = titleResourceId),
            style = CinemaxTheme.typography.semiBold.h4,
            color = CinemaxTheme.colors.textWhite
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.smallMedium),
            contentPadding = PaddingValues(horizontal = CinemaxTheme.spacing.extraMedium),
        ) {
            items(PlaceholderCount) {
                CastAndCrewItemPlaceholder()
            }
        }
    }
}

@Composable
private fun CastAndCrewItem(
    profilePath: String?,
    name: String,
    description: String,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.small)
    ) {
        CinemaxCardImage(
            modifier = Modifier.size(CastAndCrewItemImageSize),
            model = profilePath,
            contentDescription = name,
            shape = shape
        )
        Column(verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.extraSmall)) {
            Text(
                text = name,
                style = CinemaxTheme.typography.semiBold.h5,
                color = CinemaxTheme.colors.textWhite
            )
            Text(
                text = description,
                style = CinemaxTheme.typography.medium.h7,
                color = CinemaxTheme.colors.textGrey
            )
        }
    }
}

@Composable
private fun CastAndCrewItemPlaceholder(
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    color: Color = CinemaxTheme.colors.textGrey,
    visible: Boolean = true,
    placeholderShape: Shape = CinemaxTheme.shapes.medium,
    highlight: PlaceholderHighlight = PlaceholderHighlight.shimmer()
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.small)
    ) {
        CinemaxPlaceholder(
            modifier = Modifier
                .size(CastAndCrewItemImageSize)
                .clip(shape)
        )
        Column(verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.extraSmall)) {
            Text(
                modifier = Modifier
                    .width(PlaceholderTextWidth)
                    .placeholder(
                        visible = visible,
                        color = color,
                        shape = placeholderShape,
                        highlight = highlight
                    ),
                text = PlaceholderText,
                style = CinemaxTheme.typography.semiBold.h5,
                color = CinemaxTheme.colors.textWhite
            )
            Text(
                modifier = Modifier
                    .width(PlaceholderTextWidth)
                    .placeholder(
                        visible = visible,
                        color = color,
                        shape = placeholderShape,
                        highlight = highlight
                    ),
                text = PlaceholderText,
                style = CinemaxTheme.typography.medium.h7,
                color = CinemaxTheme.colors.textGrey
            )
        }
    }
}

private val CastAndCrewItemImageSize = 40.dp
private val PlaceholderTextWidth = 50.dp
private const val PlaceholderText = ""
private const val PlaceholderCount = 20
