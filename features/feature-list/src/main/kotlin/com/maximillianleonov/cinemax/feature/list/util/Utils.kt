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

package com.maximillianleonov.cinemax.feature.list.util

import androidx.annotation.StringRes
import com.maximillianleonov.cinemax.core.ui.R
import com.maximillianleonov.cinemax.core.ui.common.ContentType
import com.maximillianleonov.cinemax.core.ui.common.ContentType.List.Discover
import com.maximillianleonov.cinemax.core.ui.common.ContentType.List.NowPlaying
import com.maximillianleonov.cinemax.core.ui.common.ContentType.List.Popular
import com.maximillianleonov.cinemax.core.ui.common.ContentType.List.TopRated
import com.maximillianleonov.cinemax.core.ui.common.ContentType.List.Trending
import com.maximillianleonov.cinemax.core.ui.common.ContentType.List.Upcoming

@StringRes
internal fun ContentType.List.toTitleResourceId() = contentTypesTitleResources.getValue(this)

private val contentTypesTitleResources = mapOf(
    Upcoming to R.string.upcoming_movies,
    TopRated to R.string.top_rated,
    Popular to R.string.most_popular,
    NowPlaying to R.string.now_playing,
    Discover to R.string.discover,
    Trending to R.string.trending
)
