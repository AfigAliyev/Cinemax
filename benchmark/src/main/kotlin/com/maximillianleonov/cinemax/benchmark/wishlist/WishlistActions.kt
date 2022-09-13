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

package com.maximillianleonov.cinemax.benchmark.wishlist

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.Direction
import com.maximillianleonov.cinemax.benchmark.util.findObject
import com.maximillianleonov.cinemax.benchmark.util.findScrollable
import com.maximillianleonov.cinemax.benchmark.util.waitForContent

internal fun MacrobenchmarkScope.wishlistWaitForContent() = waitForContent(ContentTestTag)

internal fun MacrobenchmarkScope.wishlistScrollContent() {
    val content = device.findScrollable(ContentTestTag)
    with(content) {
        fling(Direction.RIGHT)
        findObject(TvShowsTabTestTag).click()
        device.waitForIdle()
    }
}

private const val TestTag = "wishlist"
private const val ContentTestTag = "$TestTag:content"
private const val TabTestTag = "tab"
private const val TvShowsTabTestTag = "$TabTestTag:1"
