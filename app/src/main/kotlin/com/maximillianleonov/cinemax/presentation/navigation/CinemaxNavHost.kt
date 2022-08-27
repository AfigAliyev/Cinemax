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

package com.maximillianleonov.cinemax.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.maximillianleonov.cinemax.feature.details.presentation.navigation.detailsGraph
import com.maximillianleonov.cinemax.feature.home.presentation.navigation.HomeDestination
import com.maximillianleonov.cinemax.feature.home.presentation.navigation.homeGraph
import com.maximillianleonov.cinemax.feature.list.presentation.navigation.ListDestination
import com.maximillianleonov.cinemax.feature.list.presentation.navigation.listGraph
import com.maximillianleonov.cinemax.feature.search.presentation.navigation.searchGraph

@Suppress("ForbiddenComment")
@Composable
fun CinemaxNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = HomeDestination.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        homeGraph(
            onSeeAllClick = { contentType ->
                navController.navigate(route = ListDestination.createNavigationRoute(contentType))
            }
        )
        searchGraph(
            onSeeAllClick = { contentType ->
                navController.navigate(route = ListDestination.createNavigationRoute(contentType))
            }
        )
        listGraph(onBackButtonClick = navController::popBackStack)
        detailsGraph()

        composable(route = "wishlist") { /* TODO: Not yet implemented. */ }
        composable(route = "settings") { /* TODO: Not yet implemented. */ }
    }
}
