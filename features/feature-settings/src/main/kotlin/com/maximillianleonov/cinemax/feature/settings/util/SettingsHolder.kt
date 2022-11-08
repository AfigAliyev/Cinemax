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

package com.maximillianleonov.cinemax.feature.settings.util

import android.content.Intent
import androidx.core.net.toUri
import com.maximillianleonov.cinemax.core.domain.usecase.GetSettingsAvailableLanguagesUseCase
import com.maximillianleonov.cinemax.core.domain.usecase.GetSettingsContentLanguageUseCase
import com.maximillianleonov.cinemax.core.domain.usecase.GetSettingsPrivacyPolicyUrlUseCase
import com.maximillianleonov.cinemax.core.domain.usecase.GetSettingsRepoUrlUseCase
import com.maximillianleonov.cinemax.core.domain.usecase.GetSettingsVersionUseCase
import com.maximillianleonov.cinemax.core.domain.usecase.SetSettingsContentLanguageUseCase
import com.maximillianleonov.cinemax.core.ui.R
import com.maximillianleonov.cinemax.feature.settings.model.PreferenceNames.ContentLanguage
import com.maximillianleonov.cinemax.feature.settings.model.Settings
import com.maximillianleonov.cinemax.feature.settings.model.SettingsGroup
import javax.inject.Inject

class SettingsHolder @Inject constructor(
    private val getSettingsAvailableLanguagesUseCase: GetSettingsAvailableLanguagesUseCase,
    private val getSettingsContentLanguageUseCase: GetSettingsContentLanguageUseCase,
    private val setSettingsContentLanguageUseCase: SetSettingsContentLanguageUseCase,
    private val getSettingsRepoUrlUseCase: GetSettingsRepoUrlUseCase,
    private val getSettingsPrivacyPolicyUrlUseCase: GetSettingsPrivacyPolicyUrlUseCase,
    private val getSettingsVersionUseCase: GetSettingsVersionUseCase
) {
    val aboutSettingsGroup = SettingsGroup(
        titleResourceId = R.string.about,
        settings = getAboutSettings()
    )

    val generalSettingsGroup: SettingsGroup
        get() = SettingsGroup(
            titleResourceId = R.string.general,
            settings = getGeneralSettings()
        )

    private var contentLanguageSettings = Settings.Preferences.Selection(
        iconResourceId = R.drawable.ic_language,
        titleResourceId = R.string.content_language,
        name = ContentLanguage,
        value = "",
        values = emptyMap()
    )

    suspend fun subscribeGeneralSettings(update: (SettingsGroup) -> Unit) {
        getSettingsContentLanguageUseCase().collect { contentLanguage ->
            val languages = getSettingsAvailableLanguagesUseCase()
            val language = languages.getValue(contentLanguage)

            contentLanguageSettings =
                contentLanguageSettings.copy(value = language, values = languages)
            update(generalSettingsGroup)
        }
    }

    suspend fun changeContentLanguage(contentLanguage: String) =
        setSettingsContentLanguageUseCase(contentLanguage)

    private fun getGeneralSettings() = listOf(contentLanguageSettings)

    private fun getAboutSettings(): List<Settings> {
        val repoUrl = getSettingsRepoUrlUseCase().toUri()
        val privacyPolicyUrl = getSettingsPrivacyPolicyUrlUseCase().toUri()
        val version = getSettingsVersionUseCase()

        return listOf(
            Settings.IntentAction(
                iconResourceId = R.drawable.ic_github,
                titleResourceId = R.string.source_code_github,
                intent = Intent(Intent.ACTION_VIEW, repoUrl)
            ),
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
    }
}
