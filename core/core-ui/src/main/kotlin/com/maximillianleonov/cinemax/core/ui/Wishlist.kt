/*
 * Copyright 2022 Afig Aliyev
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

package com.maximillianleonov.cinemax.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxIconButton
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme

@Composable
fun CinemaxWishlistButton(
    isWishlisted: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CinemaxIconButton(
        modifier = modifier
            .size(CinemaxWishlistButtonShapeSize)
            .background(
                color = CinemaxTheme.colors.primarySoft,
                shape = CinemaxTheme.shapes.smallMedium
            ),
        iconResourceId = R.drawable.ic_wishlist,
        contentDescription = stringResource(id = R.string.wishlist),
        onClick = onClick,
        tint = CinemaxTheme.colors.secondaryRed.let { color ->
            if (isWishlisted) color else color.copy(alpha = DisabledContentAlpha)
        }
    )
}

private val CinemaxWishlistButtonShapeSize = 32.dp
private const val DisabledContentAlpha = 0.5f
