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

package com.maximillianleonov.cinemax.benchmark

import androidx.benchmark.macro.ExperimentalBaselineProfilesApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.maximillianleonov.cinemax.benchmark.home.homeNavigateToListScreen
import com.maximillianleonov.cinemax.benchmark.home.homeNavigateToSearchScreen
import com.maximillianleonov.cinemax.benchmark.home.homeScrollContent
import com.maximillianleonov.cinemax.benchmark.home.homeWaitForContent
import com.maximillianleonov.cinemax.benchmark.list.listNavigateBack
import com.maximillianleonov.cinemax.benchmark.list.listScrollContent
import com.maximillianleonov.cinemax.benchmark.list.listWaitForContent
import com.maximillianleonov.cinemax.benchmark.search.searchSearchContent
import com.maximillianleonov.cinemax.benchmark.search.searchWaitForContent
import com.maximillianleonov.cinemax.benchmark.util.PackageName
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalBaselineProfilesApi::class)
@RunWith(AndroidJUnit4ClassRunner::class)
class BaselineProfileGenerator {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generate() = baselineProfileRule.collectBaselineProfile(packageName = PackageName) {
        pressHome()
        startActivityAndWait()

        homeWaitForContent()
        homeScrollContent()
        homeNavigateToListScreen()

        listWaitForContent()
        listScrollContent()
        listNavigateBack()

        homeNavigateToSearchScreen()

        searchWaitForContent()
        searchSearchContent()
    }
}
