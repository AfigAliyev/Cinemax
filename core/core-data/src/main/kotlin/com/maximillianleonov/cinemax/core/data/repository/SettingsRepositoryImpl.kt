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

package com.maximillianleonov.cinemax.core.data.repository

import com.maximillianleonov.cinemax.core.data.util.titlecase
import com.maximillianleonov.cinemax.core.database.source.SettingsDatabaseDataSource
import com.maximillianleonov.cinemax.core.datastore.PreferencesDataStoreDataSource
import com.maximillianleonov.cinemax.core.domain.repository.SettingsRepository
import java.util.Locale
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    databaseDataSource: SettingsDatabaseDataSource,
    private val preferencesDataStoreDataSource: PreferencesDataStoreDataSource
) : SettingsRepository {
    override val repoUrl = databaseDataSource.repoUrl
    override val privacyPolicyUrl = databaseDataSource.privacyPolicyUrl
    override val version = databaseDataSource.version

    override fun getAvailableLanguages(): Map<String, String> {
        val languages = mutableMapOf<String, String>()
        Locale.getAvailableLocales()
            .sortedBy(Locale::getDisplayLanguage)
            .forEach { locale ->
                languages[locale.language] = locale.displayLanguage.titlecase()
            }
        return languages
    }

    override fun getContentLanguage() = preferencesDataStoreDataSource.getContentLanguage()
    override suspend fun setContentLanguage(contentLanguage: String) {
        preferencesDataStoreDataSource.setContentLanguage(contentLanguage)
    }
}
