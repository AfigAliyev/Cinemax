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

package com.maximillianleonov.cinemax.feature.list.presentation.util

import androidx.annotation.StringRes
import com.maximillianleonov.cinemax.core.presentation.R
import com.maximillianleonov.cinemax.core.presentation.common.ContentType

@StringRes
internal fun ContentType.List.toTitleResourceId() = when (this) {
    ContentType.List.Upcoming -> R.string.upcoming_movies
    ContentType.List.TopRated -> R.string.top_rated
    ContentType.List.Popular -> R.string.most_popular
    ContentType.List.NowPlaying -> R.string.now_playing
    ContentType.List.Discover -> R.string.discover
}
