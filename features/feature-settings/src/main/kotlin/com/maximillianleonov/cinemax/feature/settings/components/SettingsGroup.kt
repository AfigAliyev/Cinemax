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

package com.maximillianleonov.cinemax.feature.settings.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme
import com.maximillianleonov.cinemax.feature.settings.model.PreferenceNames
import com.maximillianleonov.cinemax.feature.settings.model.SettingsGroup

@Composable
internal fun SettingsGroupItem(
    settingsGroup: SettingsGroup,
    onPreferencesSelection: (PreferenceNames, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = CinemaxTheme.colors.primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(width = 1.dp, color = CinemaxTheme.colors.primaryVariant)
    ) {
        Column {
            Spacer(modifier = Modifier.height(CinemaxTheme.spacing.extraMedium))

            Text(
                modifier = Modifier.padding(horizontal = CinemaxTheme.spacing.medium),
                text = stringResource(id = settingsGroup.titleResourceId),
                style = CinemaxTheme.typography.semiBold.h3,
                color = CinemaxTheme.colors.textPrimary
            )

            Spacer(modifier = Modifier.height(CinemaxTheme.spacing.small))

            settingsGroup.settings.forEachIndexed { index, settingsItem ->
                SettingsItem(
                    settings = settingsItem,
                    onPreferencesSelection = onPreferencesSelection
                )

                if (index < settingsGroup.settings.lastIndex) {
                    Divider(
                        color = CinemaxTheme.colors.primaryVariant,
                        thickness = 1.dp
                    )
                }
            }

            Spacer(modifier = Modifier.height(CinemaxTheme.spacing.medium))
        }
    }
}
