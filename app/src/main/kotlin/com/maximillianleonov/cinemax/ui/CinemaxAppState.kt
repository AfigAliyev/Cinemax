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

package com.maximillianleonov.cinemax.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.maximillianleonov.cinemax.core.ui.navigation.CinemaxNavigationDestination
import com.maximillianleonov.cinemax.navigation.TopLevelDestination

@Composable
fun rememberCinemaxAppState(
    navController: NavHostController = rememberNavController(),
    startDestination: TopLevelDestination = TopLevelDestination.Home
) = remember(navController, startDestination) {
    CinemaxAppState(navController, startDestination)
}

@Stable
class CinemaxAppState(
    val navController: NavHostController,
    val startDestination: TopLevelDestination
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    var currentTopLevelDestination by mutableStateOf(startDestination)

    val shouldShowBottomBar: Boolean
        @Composable get() = currentDestination?.route == currentTopLevelDestination.route

    /**
     * Top level destinations to be used in the BottomBar.
     */
    val topLevelDestinations = TopLevelDestination.values()

    /**
     * UI logic for navigating to a particular destination in the app. The NavigationOptions to
     * navigate with are based on the type of destination, which could be a top level destination or
     * just a regular destination.
     *
     * Top level destinations have only one copy of the destination of the back stack, and save and
     * restore state whenever you navigate to and from it.
     * Regular destinations can have multiple copies in the back stack and state isn't saved nor
     * restored.
     *
     * @param destination The [CinemaxNavigationDestination] the app needs to navigate to.
     * @param route Optional route to navigate to in case the destination contains arguments.
     */
    fun navigate(destination: CinemaxNavigationDestination, route: String? = null) =
        with(navController) {
            if (destination is TopLevelDestination) {
                navigate(route ?: destination.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items.
                    popUpTo(graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item.
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item.
                    restoreState = true
                }
                // Update the current top level destination with the new one.
                currentTopLevelDestination = destination
            } else {
                navigate(route ?: destination.route)
            }
        }

    fun onBackClick() = navController.popBackStack()
}
