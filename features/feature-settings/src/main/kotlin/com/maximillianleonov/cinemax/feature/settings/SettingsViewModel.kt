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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximillianleonov.cinemax.core.ui.common.EventHandler
import com.maximillianleonov.cinemax.feature.settings.model.PreferenceNames
import com.maximillianleonov.cinemax.feature.settings.model.SettingsGroupNames.About
import com.maximillianleonov.cinemax.feature.settings.model.SettingsGroupNames.General
import com.maximillianleonov.cinemax.feature.settings.util.SettingsHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsHolder: SettingsHolder
) : ViewModel(), EventHandler<SettingsEvent> {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    override fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.PreferencesSelection -> onPreferencesSelection(event)
        }
    }

    private fun onPreferencesSelection(event: SettingsEvent.PreferencesSelection) =
        when (event.name) {
            PreferenceNames.ContentLanguage -> onChangeContentLanguage(event.value)
        }

    private fun onChangeContentLanguage(contentLanguage: String) = viewModelScope.launch {
        settingsHolder.changeContentLanguage(contentLanguage)
    }

    private fun loadSettings() {
        loadGeneralSettings()
        loadAboutSettings()
    }

    private fun loadGeneralSettings() = viewModelScope.launch {
        _uiState.update {
            it.copy(
                settingsGroups = it.settingsGroups + (General to settingsHolder.generalSettingsGroup)
            )
        }
        settingsHolder.subscribeGeneralSettings { generalSettingsGroup ->
            _uiState.update {
                it.copy(
                    settingsGroups = it.settingsGroups + (General to generalSettingsGroup)
                )
            }
        }
    }

    private fun loadAboutSettings() = _uiState.update {
        it.copy(
            settingsGroups = it.settingsGroups + (About to settingsHolder.aboutSettingsGroup)
        )
    }
}
