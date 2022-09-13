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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.maximillianleonov.cinemax.core.ui.R
import com.maximillianleonov.cinemax.core.ui.mapper.toNames
import com.maximillianleonov.cinemax.core.ui.model.MovieDetails

@Suppress("ReusedModifierInstance")
@Composable
internal fun MovieDetailsItem(
    movieDetails: MovieDetails,
    onBackButtonClick: () -> Unit,
    onWishlistButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    with(movieDetails) {
        DetailsItem(
            modifier = modifier,
            title = title,
            overview = overview,
            posterPath = posterPath,
            releaseDate = releaseDate,
            runtime = if (runtime == 0) {
                stringResource(id = R.string.no_runtime)
            } else {
                stringResource(id = R.string.details_runtime_text, runtime.toString())
            },
            genres = genres.toNames(),
            voteAverage = voteAverage,
            credits = credits,
            isWishlisted = isWishlisted,
            onBackButtonClick = onBackButtonClick,
            onWishlistButtonClick = onWishlistButtonClick
        )
    }
}

@Composable
internal fun MovieDetailsItemPlaceholder(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DetailsItemPlaceholder(onBackButtonClick = onBackButtonClick, modifier = modifier)
}
