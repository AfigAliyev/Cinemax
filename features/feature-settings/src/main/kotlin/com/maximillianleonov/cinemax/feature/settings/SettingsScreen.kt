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

package com.maximillianleonov.cinemax.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.maximillianleonov.cinemax.core.ui.components.SnackbarUserMessageHandler
import com.maximillianleonov.cinemax.core.ui.theme.CinemaxTheme
import com.maximillianleonov.cinemax.feature.settings.components.SettingsGroupItem

@Composable
internal fun SettingsRoute(
    onShowMessage: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    SettingsScreen(
        uiState = uiState,
        onShowMessage = onShowMessage,
        onUserMessageDismiss = { viewModel.onEvent(SettingsEvent.ClearUserMessage) },
        modifier = modifier
    )
}

@Composable
private fun SettingsScreen(
    uiState: SettingsUiState,
    onShowMessage: (String) -> Unit,
    onUserMessageDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    SnackbarUserMessageHandler(
        userMessage = uiState.userMessage,
        onShowMessage = onShowMessage,
        onDismiss = onUserMessageDismiss
    )
    LazyColumn(
        modifier = modifier
            .padding(CinemaxTheme.spacing.extraMedium)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.extraMedium)
    ) {
        items(uiState.settingsGroups) { settingsGroup ->
            SettingsGroupItem(settingsGroup = settingsGroup)
        }
    }
}
