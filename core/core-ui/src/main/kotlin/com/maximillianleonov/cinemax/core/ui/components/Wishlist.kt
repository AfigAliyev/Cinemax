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

package com.maximillianleonov.cinemax.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maximillianleonov.cinemax.core.ui.R
import com.maximillianleonov.cinemax.core.ui.theme.CinemaxTheme

@OptIn(ExperimentalMaterialApi::class)
@Suppress("ReusedModifierInstance")
@Composable
fun CinemaxWishlistButton(
    isWishlisted: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    disabledContentAlpha: Float = ContentAlpha.disabled
) {
    CompositionLocalProvider(
        LocalMinimumTouchTargetEnforcement provides false
    ) {
        IconButton(
            modifier = modifier
                .size(CinemaxWishlistButtonShapeSize)
                .background(
                    color = CinemaxTheme.colors.primarySoft,
                    shape = CinemaxTheme.shapes.smallMedium
                )
                .testTag(tag = TestTag),
            onClick = onClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_wishlist),
                contentDescription = stringResource(id = R.string.wishlist),
                tint = CinemaxTheme.colors.secondaryRed.let { color ->
                    if (isWishlisted) color else color.copy(alpha = disabledContentAlpha)
                }
            )
        }
    }
}

private val CinemaxWishlistButtonShapeSize = 32.dp

private const val TestTag = "wishlist"
