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

package com.maximillianleonov.cinemax.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.maximillianleonov.cinemax.R
import com.maximillianleonov.cinemax.feature.home.presentation.navigation.HomeDestination
import com.maximillianleonov.cinemax.feature.search.presentation.navigation.SearchDestination

enum class BottomNavigationSection(
    val route: String,
    @StringRes val stringResourceId: Int,
    @DrawableRes val drawableResourceId: Int
) {
    Home(
        route = HomeDestination.route,
        stringResourceId = R.string.home,
        drawableResourceId = R.drawable.ic_home
    ),
    Search(
        route = SearchDestination.route,
        stringResourceId = R.string.search,
        drawableResourceId = R.drawable.ic_search
    ),
    Wishlist(
        route = "wishlist",
        stringResourceId = R.string.wishlist,
        drawableResourceId = R.drawable.ic_heart
    ),
    Settings(
        route = "settings",
        stringResourceId = R.string.settings,
        drawableResourceId = R.drawable.ic_settings
    )
}
