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

package com.maximillianleonov.cinemax.core.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.maximillianleonov.cinemax.core.presentation.R
import com.maximillianleonov.cinemax.core.presentation.theme.CinemaxTheme

@Composable
fun CinemaxTopAppBar(
    @StringRes titleResourceId: Int,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) = TopAppBar(
    modifier = modifier,
    contentPadding = PaddingValues(horizontal = CinemaxTheme.spacing.extraMedium),
    elevation = elevation
) {
    CinemaxBackButton(onClick = onBackButtonClick)
    Text(
        modifier = Modifier
            .padding(end = CinemaxBackButtonShapeSize)
            .fillMaxWidth(),
        text = stringResource(id = titleResourceId),
        style = CinemaxTheme.typography.semiBold.h4,
        color = CinemaxTheme.colors.textWhite,
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CinemaxBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) = CompositionLocalProvider(
    LocalMinimumTouchTargetEnforcement provides false
) {
    IconButton(
        modifier = modifier
            .size(CinemaxBackButtonShapeSize)
            .background(
                color = CinemaxTheme.colors.primarySoft,
                shape = CinemaxTheme.shapes.smallMedium
            ),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = stringResource(id = R.string.back),
            tint = CinemaxTheme.colors.textWhite
        )
    }
}

private val CinemaxBackButtonShapeSize = 32.dp
