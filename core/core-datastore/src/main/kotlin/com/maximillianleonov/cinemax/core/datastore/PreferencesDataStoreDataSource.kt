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

package com.maximillianleonov.cinemax.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.maximillianleonov.cinemax.core.datastore.util.Constants.CONTENT_LANGUAGE_KEY
import com.maximillianleonov.cinemax.core.datastore.util.getDefaultLanguage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataStoreDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStore: DataStore<Preferences>
) {
    fun getContentLanguage(): Flow<String> = dataStore.data.map { preferences ->
        preferences[CONTENT_LANGUAGE_KEY] ?: run {
            val defaultLanguage = context.getDefaultLanguage()
            setContentLanguage(defaultLanguage)
            defaultLanguage
        }
    }

    suspend fun setContentLanguage(contentLanguage: String) = dataStore.edit { preferences ->
        preferences[CONTENT_LANGUAGE_KEY] = contentLanguage
    }
}
