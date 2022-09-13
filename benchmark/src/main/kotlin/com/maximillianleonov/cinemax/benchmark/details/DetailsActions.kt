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

package com.maximillianleonov.cinemax.benchmark.details

import androidx.benchmark.macro.MacrobenchmarkScope
import com.maximillianleonov.cinemax.benchmark.util.findObject
import com.maximillianleonov.cinemax.benchmark.util.waitForContent

internal fun MacrobenchmarkScope.detailsWaitForContent() = waitForContent(ScrollableContentTestTag)

internal fun MacrobenchmarkScope.detailsWaitForDetailsItem() = waitForContent(DetailsItemTestTag)

internal fun MacrobenchmarkScope.detailsClickWishlist() = with(device) {
    findObject(WishlistTestTag).click()
    waitForIdle()
}

internal fun MacrobenchmarkScope.detailsNavigateBack() = with(device) {
    findObject(BackTestTag).click()
    waitForIdle()
}

private const val TestTag = "details"
private const val ScrollableContentTestTag = "$TestTag:content"
private const val DetailsItemTestTag = "detailsitem"
private const val WishlistTestTag = "wishlist"
private const val BackTestTag = "back"
