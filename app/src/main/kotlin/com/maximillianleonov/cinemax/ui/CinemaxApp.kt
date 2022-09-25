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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxSnackbarHost
import com.maximillianleonov.cinemax.core.designsystem.component.LocalSnackbarHostState
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme
import com.maximillianleonov.cinemax.navigation.CinemaxNavHost
import com.maximillianleonov.cinemax.ui.component.CinemaxBottomBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CinemaxApp(
    appState: CinemaxAppState = rememberCinemaxAppState(),
    systemBarsColor: Color = CinemaxTheme.colors.primaryDark
) {
    LaunchedEffect(systemBarsColor) { appState.setSystemBarsColor(systemBarsColor) }

    CinemaxTheme {
        CompositionLocalProvider(
            LocalSnackbarHostState provides appState.snackbarHostState
        ) {
            Scaffold(
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
                },
                snackbarHost = {
                    CinemaxSnackbarHost(
                        modifier = Modifier.windowInsetsPadding(
                            if (appState.shouldShowBottomBar) {
                                WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
                            } else {
                                WindowInsets.safeDrawing
                            }
                        ),
                        snackbarHostState = appState.snackbarHostState
                    )
                },
                contentWindowInsets = WindowInsets(
                    left = 0.dp,
                    top = 0.dp,
                    right = 0.dp,
                    bottom = 0.dp
                )
            ) { innerPadding ->
                CinemaxNavHost(
                    modifier = Modifier
                        .padding(paddingValues = innerPadding)
                        .consumedWindowInsets(paddingValues = innerPadding),
                    navController = appState.navController,
                    startDestination = appState.startDestination,
                    onNavigateToDestination = appState::navigate,
                    onBackClick = appState::onBackClick,
                    onShowMessage = { message -> appState.showMessage(message) },
                    onSetSystemBarsColorTransparent = { appState.setSystemBarsColor(Color.Transparent) },
                    onResetSystemBarsColor = { appState.setSystemBarsColor(systemBarsColor) }
                )
            }
        }
    }
}

private val BottomBarEnterTransition = fadeIn() + expandVertically(expandFrom = Alignment.Top)
private val BottomBarExitTransition = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
