/*
 * Copyright 2022 Afig Aliyev
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

package com.maximillianleonov.cinemax.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.lerp
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme
import com.maximillianleonov.cinemax.core.ui.common.MediaTab
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun MediaTabPager(
    moviesTabContent: @Composable () -> Unit,
    tvShowsTabContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    backgroundColor: Color = CinemaxTheme.colors.primaryDark,
    selectedContentColor: Color = CinemaxTheme.colors.primaryBlue,
    unselectedContentColor: Color = CinemaxTheme.colors.white
) {
    val tabs = remember { MediaTab.entries }
    val pagerState = rememberPagerState(pageCount = tabs::size)
    val selectedTabIndex = pagerState.currentPage

    Column(modifier = modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    color = CinemaxTheme.colors.primaryBlue
                )
            },
            backgroundColor = backgroundColor,
            divider = { TabRowDefaults.Divider(color = CinemaxTheme.colors.primarySoft) }
        ) {
            tabs.forEach { tab ->
                val index = tab.ordinal
                val selected = selectedTabIndex == index

                Tab(
                    selected = selected,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = {
                        Text(
                            text = stringResource(id = tab.titleResourceId),
                            style = CinemaxTheme.typography.medium.h4
                        )
                    },
                    selectedContentColor = selectedContentColor,
                    unselectedContentColor = unselectedContentColor
                )
            }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState
        ) { page ->
            when (page) {
                MediaTab.Movies.ordinal -> moviesTabContent()
                MediaTab.TvShows.ordinal -> tvShowsTabContent()
            }
        }
    }
}

/**
 * Custom pager tab indicator offset that replaces the deprecated accompanist version.
 * This modifier animates the tab indicator position based on the pager state.
 */
private fun Modifier.pagerTabIndicatorOffset(
    pagerState: PagerState,
    tabPositions: List<TabPosition>
): Modifier = this
    .fillMaxWidth()
    .wrapContentSize(Alignment.BottomStart)
    .offset {
        val currentPage = pagerState.currentPage.coerceIn(0, tabPositions.lastIndex)
        val nextPage = (currentPage + 1).coerceIn(0, tabPositions.lastIndex)
        val fraction = pagerState.currentPageOffsetFraction.absoluteValue
        val currentTab = tabPositions[currentPage]
        val nextTab = tabPositions[nextPage]
        val offset = lerp(currentTab.left, nextTab.left, fraction)
        IntOffset(offset.roundToPx(), 0)
    }
    .width(tabPositions.getOrNull(pagerState.currentPage)?.width ?: Dp.Unspecified)
