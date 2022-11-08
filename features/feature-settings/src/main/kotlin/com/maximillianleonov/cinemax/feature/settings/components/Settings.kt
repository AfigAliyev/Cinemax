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

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxCenteredBox
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxIcon
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme
import com.maximillianleonov.cinemax.core.ui.CinemaxSelectionDialog
import com.maximillianleonov.cinemax.core.ui.R
import com.maximillianleonov.cinemax.feature.settings.model.PreferenceNames
import com.maximillianleonov.cinemax.feature.settings.model.Settings

@Suppress("UnnecessaryEventHandlerParameter")
@Composable
internal fun SettingsItem(
    settings: Settings,
    onPreferencesSelection: (PreferenceNames, String) -> Unit,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    var selectionExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .then(
                when (settings) {
                    is Settings.Action -> Modifier.clickable(onClick = settings.onClick)
                    is Settings.IntentAction -> Modifier.clickable { context.startActivity(settings.intent) }
                    is Settings.Preferences -> Modifier.clickable { selectionExpanded = true }
                    is Settings.Info -> Modifier
                }
            )
            .padding(CinemaxTheme.spacing.medium)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.medium)
        ) {
            IconBox(
                iconResourceId = settings.iconResourceId,
                contentDescription = stringResource(id = settings.titleResourceId)
            )
            TitleText(titleResourceId = settings.titleResourceId)
        }

        Spacer(modifier = Modifier.width(CinemaxTheme.spacing.medium))

        when (settings) {
            is Settings.Info -> ValueText(value = settings.value)
            is Settings.Action, is Settings.IntentAction -> ForwardButton()
            is Settings.Preferences.Selection -> Selection(
                expanded = selectionExpanded,
                onDismiss = { selectionExpanded = false },
                value = settings.value,
                values = settings.values,
                onSelect = { onPreferencesSelection(settings.name, it) }
            )
        }
    }
}

@Composable
private fun IconBox(
    @DrawableRes iconResourceId: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    CinemaxCenteredBox(
        modifier = modifier
            .size(IconShapeSize)
            .background(color = CinemaxTheme.colors.primaryVariant, shape = CircleShape)
    ) {
        CinemaxIcon(
            iconResourceId = iconResourceId,
            contentDescription = contentDescription,
            tint = CinemaxTheme.colors.textSecondary
        )
    }
}

@Composable
private fun TitleText(
    @StringRes titleResourceId: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = CinemaxTheme.typography.medium.h5,
    color: Color = CinemaxTheme.colors.textPrimary
) {
    ValueText(
        modifier = modifier,
        value = stringResource(id = titleResourceId),
        style = style,
        color = color
    )
}

@Composable
private fun ValueText(
    value: String,
    modifier: Modifier = Modifier,
    style: TextStyle = CinemaxTheme.typography.medium.h5,
    color: Color = CinemaxTheme.colors.textPrimary,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = 1
) {
    Text(
        modifier = modifier,
        text = value,
        style = style,
        color = color,
        overflow = overflow,
        maxLines = maxLines
    )
}

@Composable
private fun ForwardButton(
    modifier: Modifier = Modifier,
    color: Color = CinemaxTheme.colors.accent
) {
    CinemaxIcon(
        modifier = modifier.size(IconShapeSize),
        iconResourceId = R.drawable.ic_arrow_forward,
        contentDescription = stringResource(id = R.string.forward),
        tint = color
    )
}

@Composable
private fun Selection(
    expanded: Boolean,
    onDismiss: () -> Unit,
    value: String,
    values: Map<String, String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        ValueText(value = value)

        CinemaxSelectionDialog(
            expanded = expanded,
            onDismiss = onDismiss,
            values = values.toList(),
            onSelect = onSelect
        )
    }
}

private val IconShapeSize = 32.dp
