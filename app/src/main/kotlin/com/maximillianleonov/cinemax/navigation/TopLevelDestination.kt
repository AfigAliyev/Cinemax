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

package com.maximillianleonov.cinemax.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.maximillianleonov.cinemax.core.navigation.CinemaxNavigationDestination
import com.maximillianleonov.cinemax.core.ui.R
import com.maximillianleonov.cinemax.feature.home.navigation.HomeDestination
import com.maximillianleonov.cinemax.feature.search.navigation.SearchDestination
import com.maximillianleonov.cinemax.feature.settings.navigation.SettingsDestination
import com.maximillianleonov.cinemax.feature.wishlist.navigation.WishlistDestination

enum class TopLevelDestination(
    override val route: String,
    override val destination: String,
    @DrawableRes val iconResourceId: Int,
    @StringRes val textResourceId: Int
) : CinemaxNavigationDestination {
    Home(
        route = HomeDestination.route,
        destination = HomeDestination.destination,
        iconResourceId = R.drawable.ic_home,
        textResourceId = R.string.home
    ),
    Search(
        route = SearchDestination.route,
        destination = SearchDestination.destination,
        iconResourceId = R.drawable.ic_search,
        textResourceId = R.string.search
    ),
    Wishlist(
        route = WishlistDestination.route,
        destination = WishlistDestination.destination,
        iconResourceId = R.drawable.ic_wishlist,
        textResourceId = R.string.wishlist
    ),
    Settings(
        route = SettingsDestination.route,
        destination = SettingsDestination.destination,
        iconResourceId = R.drawable.ic_settings,
        textResourceId = R.string.settings
    )
}
