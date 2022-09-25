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

package com.maximillianleonov.cinemax.core.ui.util

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

val LoadState.isLoading: Boolean get() = this is LoadState.Loading
val LoadState.isFinished: Boolean get() = this is LoadState.NotLoading
val LoadState.isError: Boolean get() = this is LoadState.Error

val LoadState.error: Throwable
    get() = (this as LoadState.Error).error

fun <T : Any> LazyPagingItems<T>.isEmpty() =
    loadState.append.endOfPaginationReached && itemCount == 0

fun <T : Any> LazyPagingItems<T>.isNotEmpty() = itemCount > 0
