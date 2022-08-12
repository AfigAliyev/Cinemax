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

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.maximillianleonov.cinemax.feature.home.presentation.HomeScreen

@Suppress("ForbiddenComment")
@Composable
fun CinemaxNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier.padding(paddingValues = innerPadding),
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) { HomeScreen() }
        composable(route = Screen.Search.route) { /* TODO: Not yet implemented. */ }
        composable(route = Screen.Wishlist.route) { /* TODO: Not yet implemented. */ }
        composable(route = Screen.Settings.route) { /* TODO: Not yet implemented. */ }
        composable(route = Screen.List.route) { /* TODO: Not yet implemented. */ }
        composable(route = Screen.Details.route) { /* TODO: Not yet implemented. */ }
    }
}
