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
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until

internal fun MacrobenchmarkScope.listWaitForContent() = with(device) {
    wait(Until.hasObject(By.res(ContentTestTag)), Timeout)
    waitForIdle()
}

internal fun MacrobenchmarkScope.listScrollContent() = with(device) {
    val content = findObject(By.res(ContentTestTag))
    content.fling(Direction.DOWN)
    waitForIdle()
    content.fling(Direction.RIGHT)
    waitForIdle()
    content.fling(Direction.DOWN)
}

internal fun MacrobenchmarkScope.listNavigateBack() = with(device) {
    findObject(By.res(BackTestTag)).click()
    waitForIdle()
}

private const val TestTag = "list"
private const val ContentTestTag = "$TestTag:content"
private const val BackTestTag = "back"
private const val Timeout = 30_000L
