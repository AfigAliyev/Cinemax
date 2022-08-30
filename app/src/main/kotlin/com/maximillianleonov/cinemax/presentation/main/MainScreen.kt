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

package com.maximillianleonov.cinemax.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.maximillianleonov.cinemax.core.presentation.components.CinemaxSnackbarHost
import com.maximillianleonov.cinemax.core.presentation.components.LocalSnackbarHostState
import com.maximillianleonov.cinemax.core.presentation.theme.CinemaxTheme
import com.maximillianleonov.cinemax.presentation.navigation.CinemaxBottomNavigation
import com.maximillianleonov.cinemax.presentation.navigation.CinemaxNavHost

@Composable
fun MainRoute(
    modifier: Modifier = Modifier,
    systemUiController: SystemUiController = rememberSystemUiController(),
    systemBarsColor: Color = CinemaxTheme.colors.primaryDark,
    systemBarsDarkIcons: Boolean = false
) {
    MainScreen(modifier = modifier)
    LaunchedEffect(systemUiController, systemBarsColor, systemBarsDarkIcons) {
        systemUiController.setSystemBarsColor(
            color = systemBarsColor,
            darkIcons = systemBarsDarkIcons
        )
    }
}

@Suppress("ReusedModifierInstance")
@Composable
private fun MainScreen(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController()
) {
    CompositionLocalProvider(
        LocalSnackbarHostState provides scaffoldState.snackbarHostState
    ) {
        Scaffold(
            modifier = modifier,
            scaffoldState = scaffoldState,
            bottomBar = { CinemaxBottomNavigation(navController = navController) },
            snackbarHost = { CinemaxSnackbarHost(it) }
        ) { innerPadding ->
            CinemaxNavHost(
                navController = navController,
                modifier = Modifier.padding(paddingValues = innerPadding)
            )
        }
    }
}
