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

package com.maximillianleonov.cinemax.core.presentation.util

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

val LoadState.isLoading: Boolean
    get() = this is LoadState.Loading

val CombinedLoadStates.isError: Boolean
    get() = listOf(append, prepend, refresh).any { loadState -> loadState is LoadState.Error }

val CombinedLoadStates.error: Throwable?
    get() = when {
        append is LoadState.Error -> append as LoadState.Error
        prepend is LoadState.Error -> prepend as LoadState.Error
        refresh is LoadState.Error -> refresh as LoadState.Error
        else -> null
    }?.error
