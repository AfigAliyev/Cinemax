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

package com.maximillianleonov.cinemax.benchmark.home

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.Direction
import com.maximillianleonov.cinemax.benchmark.util.findObject
import com.maximillianleonov.cinemax.benchmark.util.findScrollable
import com.maximillianleonov.cinemax.benchmark.util.scrollAndFindObject
import com.maximillianleonov.cinemax.benchmark.util.waitForContent

internal fun MacrobenchmarkScope.homeWaitForContent() = waitForContent(ContentTestTag)

internal fun MacrobenchmarkScope.homeScrollContent() {
    val content = device.findScrollable(ContentTestTag)
    with(content) {
        findObject(UpcomingTestTag).fling(Direction.RIGHT)
        scrollAndFindObject(NowPlayingTestTag)
            .scrollAndFindObject(MoviesContainerTestTag)
            .fling(Direction.RIGHT)
    }
}

internal fun MacrobenchmarkScope.homeNavigateToListScreen() = with(device) {
    findObject(SeeAllTestTag).click()
    waitForIdle()
}

internal fun MacrobenchmarkScope.homeNavigateToSearchScreen() = with(device) {
    waitForContent(SearchRouteTestTag)
    findObject(SearchRouteTestTag).click()
    waitForIdle()
}

private const val TestTag = "home"
private const val BottomBarTestTag = "bottombar"
private const val ContentTestTag = "$TestTag:content"
private const val UpcomingTestTag = "$TestTag:upcoming"
private const val NowPlayingTestTag = "$TestTag:nowplaying"
private const val SearchRouteTestTag = "$BottomBarTestTag:search_route"
private const val MoviesContainerTestTag = "moviescontainer"
private const val SeeAllTestTag = "seeall"
