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

package com.maximillianleonov.cinemax.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.maximillianleonov.cinemax.core.ui.theme.CinemaxTheme

@Composable
fun CinemaxSwipeRefresh(
    swipeRefreshState: SwipeRefreshState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = CinemaxTheme.colors.primarySoft,
    contentColor: Color = CinemaxTheme.colors.primaryBlue,
    indicator: @Composable (state: SwipeRefreshState, refreshTrigger: Dp) -> Unit =
        { state, refreshTrigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = refreshTrigger,
                backgroundColor = backgroundColor,
                contentColor = contentColor
            )
        },
    content: @Composable () -> Unit
) = SwipeRefresh(
    state = swipeRefreshState,
    onRefresh = onRefresh,
    modifier = modifier,
    indicator = indicator,
    content = content
)
