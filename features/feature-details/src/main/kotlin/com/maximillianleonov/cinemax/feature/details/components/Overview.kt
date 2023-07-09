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

package com.maximillianleonov.cinemax.feature.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.maximillianleonov.cinemax.core.designsystem.component.cinemaxPlaceholder
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme
import com.maximillianleonov.cinemax.core.ui.R

@Composable
internal fun Overview(
    overview: String,
    modifier: Modifier = Modifier,
    isPlaceholder: Boolean = false
) {
    Column(
        modifier = modifier
            .padding(horizontal = CinemaxTheme.spacing.extraMedium)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.small)
    ) {
        Text(
            text = stringResource(id = R.string.overview),
            style = CinemaxTheme.typography.semiBold.h4,
            color = CinemaxTheme.colors.white
        )
        Text(
            modifier = if (isPlaceholder) {
                Modifier
                    .fillMaxWidth()
                    .cinemaxPlaceholder(color = CinemaxTheme.colors.grey)
            } else {
                Modifier
            },
            text = overview.ifEmpty { stringResource(id = R.string.no_overview) },
            style = CinemaxTheme.typography.regular.h5,
            color = CinemaxTheme.colors.whiteGrey
        )
    }
}

@Composable
internal fun OverviewPlaceholder(modifier: Modifier = Modifier) {
    Overview(modifier = modifier, overview = PlaceholderText, isPlaceholder = true)
}

private const val PlaceholderText = ""
