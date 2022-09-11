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

package com.maximillianleonov.cinemax.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.maximillianleonov.cinemax.core.ui.navigation.CinemaxNavigationDestination
import com.maximillianleonov.cinemax.feature.details.navigation.DetailsDestination
import com.maximillianleonov.cinemax.feature.details.navigation.detailsGraph
import com.maximillianleonov.cinemax.feature.home.navigation.homeGraph
import com.maximillianleonov.cinemax.feature.list.navigation.ListDestination
import com.maximillianleonov.cinemax.feature.list.navigation.listGraph
import com.maximillianleonov.cinemax.feature.search.navigation.searchGraph

@Suppress("ForbiddenComment")
@Composable
fun CinemaxNavHost(
    navController: NavHostController,
    startDestination: CinemaxNavigationDestination,
    onNavigateToDestination: (CinemaxNavigationDestination, String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route
    ) {
        homeGraph(
            onNavigateToListDestination = {
                onNavigateToDestination(ListDestination, ListDestination.createNavigationRoute(it))
            },
            onNavigateToDetailsDestination = {
                onNavigateToDestination(
                    DetailsDestination,
                    DetailsDestination.createNavigationRoute(it)
                )
            }
        )
        searchGraph(
            onNavigateToListDestination = {
                onNavigateToDestination(ListDestination, ListDestination.createNavigationRoute(it))
            },
            onNavigateToDetailsDestination = {
                onNavigateToDestination(
                    DetailsDestination,
                    DetailsDestination.createNavigationRoute(it)
                )
            }
        )
        listGraph(
            onBackButtonClick = onBackClick,
            onNavigateToDetailsDestination = {
                onNavigateToDestination(
                    DetailsDestination,
                    DetailsDestination.createNavigationRoute(it)
                )
            }
        )
        detailsGraph(onBackButtonClick = onBackClick)

        composable(route = "wishlist_route") { /* TODO: Not yet implemented. */ }
        composable(route = "settings_route") { /* TODO: Not yet implemented. */ }
    }
}
