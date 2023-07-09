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

package com.maximillianleonov.cinemax.feature.details.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.maximillianleonov.cinemax.core.model.MediaType
import com.maximillianleonov.cinemax.core.navigation.CinemaxNavigationDestination
import com.maximillianleonov.cinemax.feature.details.DetailsRoute

object DetailsDestination : CinemaxNavigationDestination {
    override val route = "details_route"
    override val destination = "details_destination"

    const val idArgument = "id"
    const val mediaTypeArgument = "mediaType"
    val routeWithArguments = "$route/{$idArgument}/{$mediaTypeArgument}"

    fun createNavigationRoute(mediaType: MediaType.Details) =
        "$route/${mediaType.mediaId}/${mediaType.mediaType}"

    fun fromSavedStateHandle(savedStateHandle: SavedStateHandle) = MediaType.Details.from(
        id = checkNotNull(savedStateHandle[idArgument]) { MediaIdNullMessage },
        mediaType = checkNotNull(savedStateHandle[mediaTypeArgument]) { MediaTypeNullMessage }
    )
}

fun NavGraphBuilder.detailsGraph(
    onBackButtonClick: () -> Unit,
    onShowMessage: (String) -> Unit,
    onSetSystemBarsColorTransparent: () -> Unit,
    onResetSystemBarsColor: () -> Unit
) = composable(
    route = DetailsDestination.routeWithArguments,
    arguments = listOf(
        navArgument(DetailsDestination.idArgument) { type = NavType.IntType },
        navArgument(DetailsDestination.mediaTypeArgument) { type = NavType.StringType }
    )
) {
    DetailsRoute(
        onBackButtonClick = onBackButtonClick,
        onShowMessage = onShowMessage,
        onSetSystemBarsColorTransparent = onSetSystemBarsColorTransparent,
        onResetSystemBarsColor = onResetSystemBarsColor
    )
}

private const val MediaIdNullMessage = "Media id is null."
private const val MediaTypeNullMessage = "Media type is null."
