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

package com.maximillianleonov.cinemax.benchmark.list

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.Direction
import com.maximillianleonov.cinemax.benchmark.util.findObject
import com.maximillianleonov.cinemax.benchmark.util.findScrollable
import com.maximillianleonov.cinemax.benchmark.util.scrollAndFindObject
import com.maximillianleonov.cinemax.benchmark.util.waitForContent

internal fun MacrobenchmarkScope.listWaitForContent() = waitForContent(ContentTestTag)

internal fun MacrobenchmarkScope.listScrollContent() {
    val content = device.findScrollable(ContentTestTag)
    with(content) {
        fling(Direction.DOWN)
        device.waitForIdle()
        fling(Direction.RIGHT)
        device.waitForIdle()
        fling(Direction.DOWN)
    }
}

internal fun MacrobenchmarkScope.listNavigateToDetailsScreen() {
    waitForContent(ContentItemTestTag)
    val content = device.findScrollable(ContentTestTag)
    content.scrollAndFindObject(ContentItemTestTag).click()
}

internal fun MacrobenchmarkScope.listNavigateBack() = with(device) {
    findObject(BackTestTag).click()
    waitForIdle()
}

private const val TestTag = "list"
private const val ContentTestTag = "$TestTag:content"
private const val ContentItemTestTag = "contentitem"
private const val BackTestTag = "back"
