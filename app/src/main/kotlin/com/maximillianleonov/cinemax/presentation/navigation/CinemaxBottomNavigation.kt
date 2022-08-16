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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.maximillianleonov.cinemax.presentation.components.CinemaxBottomNavigationBar

@Suppress("ReusedModifierInstance")
@Composable
fun CinemaxBottomNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route

    val bottomNavigationSections = BottomNavigationSection.values()
    val bottomNavigationRoutes = bottomNavigationSections.map(BottomNavigationSection::route)
    AnimatedVisibility(
        visible = currentRoute in bottomNavigationRoutes,
        enter = BottomBarEnterTransition,
        exit = BottomBarExitTransition
    ) {
        CinemaxBottomNavigationBar(
            tabs = bottomNavigationSections,
            currentRoute = checkNotNull(currentRoute) { MESSAGE_CURRENT_ROUTE_IS_NULL },
            onSelect = { route -> navController.navigateOnce(route = route) },
            modifier = modifier
        )
    }
}

private fun NavHostController.navigateOnce(route: String) = navigate(route = route) {
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

private val BottomBarEnterTransition = fadeIn() + expandVertically(expandFrom = Alignment.Top)
private val BottomBarExitTransition = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()

private const val MESSAGE_CURRENT_ROUTE_IS_NULL = "Current route is null."
