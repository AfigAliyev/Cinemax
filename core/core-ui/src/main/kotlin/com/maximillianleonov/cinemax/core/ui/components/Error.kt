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

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import com.maximillianleonov.cinemax.core.ui.R
import com.maximillianleonov.cinemax.core.ui.model.ErrorMessage
import com.maximillianleonov.cinemax.core.ui.theme.CinemaxTheme

@Composable
fun CinemaxErrorDisplay(
    errorMessage: ErrorMessage,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CinemaxTheme.shapes.medium,
    backgroundColor: Color = CinemaxTheme.colors.primarySoft,
    errorTextColor: Color = CinemaxTheme.colors.secondaryRed,
    actionButtonColor: Color = CinemaxTheme.colors.primaryBlue,
    @StringRes actionButtonTextResourceId: Int = R.string.retry,
    shouldShowOfflineMode: Boolean = false,
    onOfflineModeClick: () -> Unit = {},
    offlineModeButtonColor: Color = CinemaxTheme.colors.secondaryGreen,
    @StringRes offlineModeButtonTextResourceId: Int = R.string.offline_mode
) {
    Column(
        modifier = modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.small)
    ) {
        Card(
            shape = shape,
            backgroundColor = backgroundColor
        ) {
            Column(
                modifier = Modifier.padding(CinemaxTheme.spacing.extraMedium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.small)
            ) {
                Text(
                    text = stringResource(id = errorMessage.messageResourceId),
                    color = errorTextColor
                )
                OutlinedButton(
                    onClick = onRetry,
                    modifier = Modifier.fillMaxWidth(),
                    shape = CinemaxTheme.shapes.medium,
                    border = ButtonDefaults.outlinedBorder.copy(
                        brush = SolidColor(actionButtonColor)
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = CinemaxTheme.colors.primarySoft,
                        contentColor = actionButtonColor
                    )
                ) {
                    Text(text = stringResource(id = actionButtonTextResourceId))
                }
            }
        }
        if (shouldShowOfflineMode) {
            OutlinedButton(
                onClick = onOfflineModeClick,
                modifier = Modifier.fillMaxWidth(),
                shape = CinemaxTheme.shapes.medium,
                border = ButtonDefaults.outlinedBorder.copy(
                    brush = SolidColor(offlineModeButtonColor)
                ),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = CinemaxTheme.colors.primaryDark,
                    contentColor = offlineModeButtonColor
                )
            ) {
                Text(text = stringResource(id = offlineModeButtonTextResourceId))
            }
        }
    }
}
