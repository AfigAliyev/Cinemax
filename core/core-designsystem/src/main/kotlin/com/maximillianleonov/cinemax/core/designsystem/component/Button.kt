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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme

@Composable
fun CinemaxOutlinedButton(
    @StringRes textResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CinemaxTheme.shapes.medium,
    containerColor: Color = CinemaxTheme.colors.primarySoft,
    contentColor: Color = CinemaxTheme.colors.primaryBlue,
    borderColor: Color = contentColor,
    textStyle: TextStyle = CinemaxTheme.typography.medium.h5
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(brush = SolidColor(borderColor))
    ) {
        Text(text = stringResource(id = textResourceId), style = textStyle)
    }
}

@Composable
fun CinemaxIconButton(
    @DrawableRes iconResourceId: Int,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current
) {
    CinemaxIconButtonContent(
        modifier = modifier,
        onClick = onClick
    ) {
        CinemaxIcon(
            iconResourceId = iconResourceId,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Composable
fun CinemaxIconButton(
    imageVector: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current
) {
    CinemaxIconButtonContent(
        modifier = modifier,
        onClick = onClick
    ) {
        CinemaxIcon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("ReusedModifierInstance")
@Composable
private fun CinemaxIconButtonContent(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalMinimumInteractiveComponentEnforcement provides false
    ) {
        IconButton(modifier = modifier, onClick = onClick, content = content)
    }
}
