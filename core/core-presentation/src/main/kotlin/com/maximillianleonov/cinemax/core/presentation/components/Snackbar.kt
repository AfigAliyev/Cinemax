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

import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.maximillianleonov.cinemax.core.presentation.R
import com.maximillianleonov.cinemax.core.presentation.model.ErrorMessage
import com.maximillianleonov.cinemax.core.presentation.theme.CinemaxTheme

@Composable
fun CinemaxSnackbarHost(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier
    ) { snackbarData ->
        Snackbar(
            snackbarData = snackbarData,
            shape = CinemaxTheme.shapes.small,
            backgroundColor = CinemaxTheme.colors.primarySoft,
            contentColor = CinemaxTheme.colors.secondaryRed,
            actionColor = CinemaxTheme.colors.primaryBlue
        )
    }
}

@Composable
fun SnackbarErrorHandler(
    errorMessage: ErrorMessage?,
    onRetry: () -> Unit,
    onDismiss: () -> Unit,
    snackbarHostState: SnackbarHostState = LocalSnackbarHostState.current
) {
    if (errorMessage == null) return
    val message = stringResource(id = errorMessage.messageResourceId)
    val actionLabel = stringResource(id = R.string.retry)
    LaunchedEffect(key1 = snackbarHostState) {
        val snackbarResult = snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = SnackbarDuration.Indefinite
        )
        onDismiss()
        if (snackbarResult == SnackbarResult.ActionPerformed) {
            onRetry()
        }
    }
}

val LocalSnackbarHostState = staticCompositionLocalOf<SnackbarHostState> {
    error(LocalSnackbarHostStateErrorMessage)
}

private const val LocalSnackbarHostStateErrorMessage = "No SnackbarHostState provided."
