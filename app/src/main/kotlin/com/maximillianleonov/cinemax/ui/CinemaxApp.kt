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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.maximillianleonov.cinemax.core.ui.theme.CinemaxTheme
import com.maximillianleonov.cinemax.navigation.CinemaxNavHost
import com.maximillianleonov.cinemax.ui.components.CinemaxBottomBar

@OptIn(ExperimentalLayoutApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CinemaxApp(
    appState: CinemaxAppState = rememberCinemaxAppState(),
    systemUiController: SystemUiController = rememberSystemUiController(),
    systemBarsColor: Color = CinemaxTheme.colors.primaryDark,
    systemBarsDarkIcons: Boolean = false
) {
    LaunchedEffect(systemUiController, systemBarsColor, systemBarsDarkIcons) {
        systemUiController.setSystemBarsColor(
            color = systemBarsColor,
            darkIcons = systemBarsDarkIcons
        )
    }

    CinemaxTheme {
        Scaffold(
            modifier = Modifier.semantics { testTagsAsResourceId = true },
            bottomBar = {
                AnimatedVisibility(
                    visible = appState.shouldShowBottomBar,
                    enter = BottomBarEnterTransition,
                    exit = BottomBarExitTransition
                ) {
                    CinemaxBottomBar(
                        destinations = appState.topLevelDestinations,
                        currentDestination = appState.currentTopLevelDestination,
                        onNavigateToDestination = appState::navigate
                    )
                }
            }
        ) { innerPadding ->
            CinemaxNavHost(
                navController = appState.navController,
                startDestination = appState.startDestination,
                onNavigateToDestination = appState::navigate,
                onBackClick = appState::onBackClick,
                modifier = Modifier
                    .padding(paddingValues = innerPadding)
                    .consumedWindowInsets(paddingValues = innerPadding)
            )
        }
    }
}

private val BottomBarEnterTransition = fadeIn() + expandVertically(expandFrom = Alignment.Top)
private val BottomBarExitTransition = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
