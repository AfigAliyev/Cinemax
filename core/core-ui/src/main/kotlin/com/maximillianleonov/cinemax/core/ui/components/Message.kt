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

package com.maximillianleonov.cinemax.core.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.maximillianleonov.cinemax.core.ui.theme.CinemaxTheme

@Composable
fun NoResultsDisplay(
    @StringRes messageResourceId: Int,
    @DrawableRes imageResourceId: Int,
    modifier: Modifier = Modifier
) {
    CinemaxCenteredBox(modifier = modifier.padding(horizontal = CinemaxTheme.spacing.largest)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.medium)
        ) {
            Image(
                painter = painterResource(id = imageResourceId),
                contentDescription = stringResource(id = messageResourceId)
            )
            Text(
                text = stringResource(id = messageResourceId),
                style = CinemaxTheme.typography.medium.h3,
                color = CinemaxTheme.colors.textWhite,
                textAlign = TextAlign.Center
            )
        }
    }
}
