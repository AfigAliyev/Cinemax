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

import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.maximillianleonov.cinemax.core.ui.model.UserMessage
import com.maximillianleonov.cinemax.core.ui.model.asString
import com.maximillianleonov.cinemax.core.ui.theme.CinemaxTheme

@Composable
fun CinemaxSnackbarHost(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    shape: Shape = CinemaxTheme.shapes.small,
    backgroundColor: Color = CinemaxTheme.colors.primarySoft,
    contentColor: Color = CinemaxTheme.colors.textWhite,
    actionColor: Color = CinemaxTheme.colors.primaryBlue
) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier
    ) { snackbarData ->
        Snackbar(
            snackbarData = snackbarData,
            shape = shape,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            actionColor = actionColor
        )
    }
}

@Composable
fun SnackbarUserMessageHandler(
    userMessage: UserMessage?,
    onDismiss: () -> Unit,
    snackbarHostState: SnackbarHostState = LocalSnackbarHostState.current,
    duration: SnackbarDuration = SnackbarDuration.Short
) {
    if (userMessage == null) return
    val message = userMessage.asString()
    LaunchedEffect(snackbarHostState, userMessage) {
        snackbarHostState.showSnackbar(message = message, duration = duration)
        onDismiss()
    }
}

val LocalSnackbarHostState = staticCompositionLocalOf<SnackbarHostState> {
    error(LocalSnackbarHostStateErrorMessage)
}

private const val LocalSnackbarHostStateErrorMessage = "No SnackbarHostState provided."
