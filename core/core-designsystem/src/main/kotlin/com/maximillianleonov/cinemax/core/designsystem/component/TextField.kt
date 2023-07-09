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

package com.maximillianleonov.cinemax.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CinemaxTextField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes placeholderResourceId: Int,
    @DrawableRes iconResourceId: Int,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit) = {
        Text(text = stringResource(id = placeholderResourceId))
    },
    leadingIcon: @Composable (() -> Unit) = {
        CinemaxIcon(
            iconResourceId = iconResourceId,
            contentDescription = stringResource(id = placeholderResourceId)
        )
    },
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = true,
    shape: Shape = CinemaxTheme.shapes.extraMedium,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = CinemaxTheme.colors.white,
        unfocusedTextColor = CinemaxTheme.colors.white,
        cursorColor = CinemaxTheme.colors.primaryBlue,
        selectionColors = TextSelectionColors(
            handleColor = CinemaxTheme.colors.primaryBlue,
            backgroundColor = CinemaxTheme.colors.primaryBlue.copy(
                alpha = TextSelectionColorsBackgroundColorAlpha
            )
        ),
        focusedContainerColor = CinemaxTheme.colors.primarySoft,
        unfocusedContainerColor = CinemaxTheme.colors.primarySoft,
        focusedLeadingIconColor = CinemaxTheme.colors.grey,
        unfocusedLeadingIconColor = CinemaxTheme.colors.grey,
        focusedTrailingIconColor = CinemaxTheme.colors.grey,
        unfocusedTrailingIconColor = CinemaxTheme.colors.grey,
        focusedPlaceholderColor = CinemaxTheme.colors.grey,
        unfocusedPlaceholderColor = CinemaxTheme.colors.grey,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
    )
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        shape = shape,
        colors = colors
    )
}

private const val TextSelectionColorsBackgroundColorAlpha = 0.4f
