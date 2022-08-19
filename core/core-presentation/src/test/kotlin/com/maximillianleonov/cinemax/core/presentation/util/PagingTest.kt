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
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PagingTest {
    @Test
    fun `isLoading should return true`() {
        val loadState = mockk<LoadState.Loading>()
        assertTrue(loadState.isLoading)
    }

    @Test
    fun `isLoading should return false`() {
        val loadState = mockk<LoadState.NotLoading>()
        assertFalse(loadState.isLoading)
    }

    @Test
    fun `isError should return true`() {
        val combinedLoadStates = mockk<CombinedLoadStates>(relaxed = true)
        every { combinedLoadStates.append } returns LoadState.Error(mockk())
        assertTrue(combinedLoadStates.isError)
    }

    @Test
    fun `isError should return false`() {
        val combinedLoadStates = mockk<CombinedLoadStates>(relaxed = true)
        assertFalse(combinedLoadStates.isError)
    }

    @Test
    fun `error should return throwable`() {
        val combinedLoadStates = mockk<CombinedLoadStates>(relaxed = true)
        every { combinedLoadStates.append } returns LoadState.Error(mockk())
        assertNotNull(combinedLoadStates.error)
        assert(combinedLoadStates.error is Throwable)
    }

    @Test
    fun `error should return null`() {
        val combinedLoadStates = mockk<CombinedLoadStates>(relaxed = true)
        assertNull(combinedLoadStates.error)
    }
}
