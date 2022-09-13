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

package com.maximillianleonov.cinemax.benchmark.search

import android.view.KeyEvent
import androidx.benchmark.macro.MacrobenchmarkScope
import com.maximillianleonov.cinemax.benchmark.util.findObject
import com.maximillianleonov.cinemax.benchmark.util.waitForContent

internal fun MacrobenchmarkScope.searchWaitForContent() = waitForContent(ContentTestTag)

internal fun MacrobenchmarkScope.searchSearchContent() = with(device) {
    findObject(TextFieldTestTag).click()
    pressKeyCode(KeyEvent.KEYCODE_T)
    pressKeyCode(KeyEvent.KEYCODE_E)
    pressKeyCode(KeyEvent.KEYCODE_S)
    pressKeyCode(KeyEvent.KEYCODE_T)
    waitForIdle()
    pressBack()
    waitForIdle()
}

internal fun MacrobenchmarkScope.searchNavigateToWishlistScreen() = with(device) {
    waitForContent(WishlistRouteTestTag)
    findObject(WishlistRouteTestTag).click()
    waitForIdle()
}

private const val TestTag = "search"
private const val BottomBarTestTag = "bottombar"
private const val ContentTestTag = "$TestTag:content"
private const val WishlistRouteTestTag = "$BottomBarTestTag:wishlist_route"
private const val TextFieldTestTag = "$TestTag:textfield"
