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

package com.maximillianleonov.cinemax.feature.details.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.maximillianleonov.cinemax.core.ui.common.ContentType
import com.maximillianleonov.cinemax.core.ui.navigation.CinemaxNavigationDestination
import com.maximillianleonov.cinemax.feature.details.DetailsRoute

object DetailsDestination : CinemaxNavigationDestination {
    override val route = "details_route"
    override val destination = "details_destination"

    const val idArgument = "id"
    const val contentTypeArgument = "contentType"
    val routeWithArgument = "$route/{$idArgument}/{$contentTypeArgument}"

    fun createNavigationRoute(contentType: ContentType.Details) =
        "$route/${contentType.contentId}/${contentType.contentType}"

    fun fromSavedStateHandle(savedStateHandle: SavedStateHandle) = ContentType.Details.from(
        id = checkNotNull(savedStateHandle[idArgument]) { CONTENT_ID_NULL_MESSAGE },
        contentType = checkNotNull(savedStateHandle[contentTypeArgument]) { CONTENT_TYPE_NULL_MESSAGE }
    )
}

fun NavGraphBuilder.detailsGraph(onBackButtonClick: () -> Unit) = composable(
    route = DetailsDestination.routeWithArgument,
    arguments = listOf(
        navArgument(DetailsDestination.idArgument) { type = NavType.IntType },
        navArgument(DetailsDestination.contentTypeArgument) { type = NavType.StringType }
    )
) { DetailsRoute(onBackButtonClick = onBackButtonClick) }

private const val CONTENT_ID_NULL_MESSAGE = "Content id is null."
private const val CONTENT_TYPE_NULL_MESSAGE = "Content type is null."
