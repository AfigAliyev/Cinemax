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

package com.maximillianleonov.cinemax.core.data.remote.api

import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class CinemaxApiKeyProviderTest {
    @MockK
    lateinit var cinemaxApiKeyProvider: CinemaxApiKeyProvider

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `requireApiKey() should return not null value`() {
        val expected = ""
        every { cinemaxApiKeyProvider.apiKey } returns expected
        val actual = cinemaxApiKeyProvider.requireApiKey()
        verify { checkNotNull(cinemaxApiKeyProvider.apiKey) }
        confirmVerified(cinemaxApiKeyProvider)
        assertEquals(expected, actual)
    }

    @Test
    fun `requireApiKey() should throw IllegalStateException`() {
        every { cinemaxApiKeyProvider.apiKey } returns null
        assertThrows(IllegalStateException::class.java) { cinemaxApiKeyProvider.requireApiKey() }
    }
}
