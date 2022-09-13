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

package com.maximillianleonov.cinemax.benchmark.util

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until

internal fun UiDevice.findObject(resourceId: String) = findObject(By.res(resourceId))
internal fun UiObject2.hasObject(resourceId: String) = hasObject(By.res(resourceId))
internal fun UiObject2.findObject(resourceId: String) = findObject(By.res(resourceId))

internal fun UiDevice.findScrollable(resourceId: String) = findObject(resourceId).apply {
    setGestureMargin(displayWidth / GestureMarginDisplayWidthDivider)
}

internal fun UiObject2.scrollAndFindObject(resourceId: String): UiObject2 {
    val scrollable = UiScrollable(UiSelector().resourceId(resourceName))
    while (true) {
        if (hasObject(resourceId)) {
            return findObject(resourceId)
        }
        scrollable.scrollForward()
    }
}

internal fun MacrobenchmarkScope.waitForContent(resourceId: String, timeout: Long = Timeout) =
    with(device) {
        wait(Until.hasObject(By.res(resourceId)), timeout)
        waitForIdle()
    }

internal const val PackageName = "com.maximillianleonov.cinemax"

private const val Timeout = 300_000L
private const val GestureMarginDisplayWidthDivider = 5
