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
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maximillianleonov.cinemax.core.ui.theme.CinemaxTheme
import com.maximillianleonov.cinemax.feature.settings.common.SettingsGroup

@Composable
internal fun SettingsGroupItem(
    settingsGroup: SettingsGroup,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        border = BorderStroke(width = 1.dp, color = CinemaxTheme.colors.primarySoft),
        elevation = 0.dp
    ) {
        Column {
            Spacer(modifier = Modifier.height(CinemaxTheme.spacing.extraMedium))

            Text(
                modifier = Modifier.padding(horizontal = CinemaxTheme.spacing.medium),
                text = stringResource(id = settingsGroup.titleResourceId),
                style = CinemaxTheme.typography.semiBold.h3,
                color = CinemaxTheme.colors.textWhite
            )

            Spacer(modifier = Modifier.height(CinemaxTheme.spacing.small))

            settingsGroup.settingsItems.forEachIndexed { index, settingsItem ->
                SettingsItem(settingsItem = settingsItem)

                if (index < settingsGroup.settingsItems.lastIndex) {
                    Divider(
                        color = CinemaxTheme.colors.primarySoft,
                        thickness = 1.dp
                    )
                }
            }

            Spacer(modifier = Modifier.height(CinemaxTheme.spacing.medium))
        }
    }
}
