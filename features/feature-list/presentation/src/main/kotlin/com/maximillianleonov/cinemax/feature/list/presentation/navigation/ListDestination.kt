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

package com.maximillianleonov.cinemax.feature.list.presentation.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.maximillianleonov.cinemax.core.presentation.common.ContentType
import com.maximillianleonov.cinemax.core.presentation.navigation.CinemaxNavigationDestination
import com.maximillianleonov.cinemax.feature.list.presentation.ListRoute

object ListDestination : CinemaxNavigationDestination {
    override val route = "list_route"
    override val destination = "list_destination"

    const val contentTypeArgument = "contentType"
    val routeWithArgument = "$route/{$contentTypeArgument}"

    fun createNavigationRoute(contentType: ContentType) = "$route/${contentType.value}"

    fun fromSavedStateHandle(savedStateHandle: SavedStateHandle) = ContentType[
        checkNotNull(savedStateHandle[contentTypeArgument]) { CONTENT_TYPE_NULL_MESSAGE }
    ]
}

fun NavGraphBuilder.listGraph() = composable(
    route = ListDestination.routeWithArgument,
    arguments = listOf(
        navArgument(ListDestination.contentTypeArgument) { type = NavType.StringType }
    )
) { ListRoute() }

private const val CONTENT_TYPE_NULL_MESSAGE = "Content type is null."
