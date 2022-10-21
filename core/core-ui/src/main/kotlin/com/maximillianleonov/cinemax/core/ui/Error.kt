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

package com.maximillianleonov.cinemax.core.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxCenteredBox
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxOutlinedButton
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme
import com.maximillianleonov.cinemax.core.model.UserMessage
import com.maximillianleonov.cinemax.core.ui.mapper.asString

@Composable
fun CinemaxError(
    errorMessage: UserMessage,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CinemaxTheme.shapes.medium,
    containerColor: Color = CinemaxTheme.colors.primaryVariant,
    errorTextColor: Color = CinemaxTheme.colors.error,
    actionButtonColor: Color = CinemaxTheme.colors.accent,
    errorTextStyle: TextStyle = CinemaxTheme.typography.regular.h4,
    @StringRes actionButtonTextResourceId: Int = R.string.retry,
    shouldShowOfflineMode: Boolean = false,
    onOfflineModeClick: () -> Unit = {},
    offlineModeButtonColor: Color = CinemaxTheme.colors.secondary,
    @StringRes offlineModeButtonTextResourceId: Int = R.string.offline_mode
) {
    Column(
        modifier = modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.small)
    ) {
        Card(
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = containerColor)
        ) {
            Column(
                modifier = Modifier.padding(CinemaxTheme.spacing.extraMedium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.small)
            ) {
                Text(
                    text = errorMessage.asString(),
                    style = errorTextStyle,
                    color = errorTextColor
                )
                CinemaxOutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    textResourceId = actionButtonTextResourceId,
                    onClick = onRetry,
                    containerColor = CinemaxTheme.colors.primaryVariant,
                    contentColor = actionButtonColor
                )
            }
        }
        if (shouldShowOfflineMode) {
            CinemaxOutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                textResourceId = offlineModeButtonTextResourceId,
                onClick = onOfflineModeClick,
                containerColor = CinemaxTheme.colors.primary,
                contentColor = offlineModeButtonColor
            )
        }
    }
}

@Composable
fun CinemaxCenteredError(
    errorMessage: UserMessage,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CinemaxTheme.shapes.medium,
    containerColor: Color = CinemaxTheme.colors.primaryVariant,
    errorTextColor: Color = CinemaxTheme.colors.error,
    actionButtonColor: Color = CinemaxTheme.colors.accent,
    @StringRes actionButtonTextResourceId: Int = R.string.retry,
    shouldShowOfflineMode: Boolean = false,
    onOfflineModeClick: () -> Unit = {},
    offlineModeButtonColor: Color = CinemaxTheme.colors.secondary,
    @StringRes offlineModeButtonTextResourceId: Int = R.string.offline_mode
) {
    CinemaxCenteredBox(
        modifier = modifier
            .padding(horizontal = CinemaxTheme.spacing.extraMedium)
            .fillMaxSize()
    ) {
        CinemaxError(
            errorMessage = errorMessage,
            onRetry = onRetry,
            shape = shape,
            containerColor = containerColor,
            errorTextColor = errorTextColor,
            actionButtonColor = actionButtonColor,
            actionButtonTextResourceId = actionButtonTextResourceId,
            shouldShowOfflineMode = shouldShowOfflineMode,
            onOfflineModeClick = onOfflineModeClick,
            offlineModeButtonColor = offlineModeButtonColor,
            offlineModeButtonTextResourceId = offlineModeButtonTextResourceId
        )
    }
}
