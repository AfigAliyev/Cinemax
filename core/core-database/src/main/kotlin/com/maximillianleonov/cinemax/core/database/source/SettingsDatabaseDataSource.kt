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

package com.maximillianleonov.cinemax.core.database.source

import com.maximillianleonov.cinemax.core.database.util.CinemaxVersionProvider
import com.maximillianleonov.cinemax.core.database.util.Constants
import javax.inject.Inject

class SettingsDatabaseDataSource @Inject constructor(versionProvider: CinemaxVersionProvider) {
    val privacyPolicyUrl = Constants.Urls.PRIVACY_POLICY_URL
    val version = versionProvider.version
}
