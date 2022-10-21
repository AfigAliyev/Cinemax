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

import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.maximillianleonov.cinemax.core.domain.usecase.GetSettingsPrivacyPolicyUrlUseCase
import com.maximillianleonov.cinemax.core.domain.usecase.GetSettingsVersionUseCase
import com.maximillianleonov.cinemax.core.ui.R
import com.maximillianleonov.cinemax.core.ui.common.EventHandler
import com.maximillianleonov.cinemax.feature.settings.model.Settings
import com.maximillianleonov.cinemax.feature.settings.model.SettingsGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSettingsPrivacyPolicyUrlUseCase: GetSettingsPrivacyPolicyUrlUseCase,
    private val getSettingsVersionUseCase: GetSettingsVersionUseCase
) : ViewModel(), EventHandler<SettingsEvent> {
    private val _uiState = MutableStateFlow(getInitialUiState())
    val uiState = _uiState.asStateFlow()

    override fun onEvent(event: SettingsEvent) = Unit

    private fun getInitialUiState(): SettingsUiState {
        val privacyPolicyUrl = getSettingsPrivacyPolicyUrlUseCase().toUri()
        val version = getSettingsVersionUseCase()

        val aboutSettings = listOf(
            Settings.IntentAction(
                iconResourceId = R.drawable.ic_shield,
                titleResourceId = R.string.privacy_policy,
                intent = Intent(Intent.ACTION_VIEW, privacyPolicyUrl)
            ),
            Settings.Info(
                iconResourceId = R.drawable.ic_info,
                titleResourceId = R.string.version,
                value = version
            )
        )

        val aboutSettingsGroup = SettingsGroup(
            titleResourceId = R.string.about,
            settings = aboutSettings
        )

        val settingsGroups = listOf(aboutSettingsGroup)
        return SettingsUiState(settingsGroups = settingsGroups)
    }
}
