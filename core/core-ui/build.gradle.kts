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

plugins {
    id("cinemax.android.library")
    id("cinemax.android.library.compose")
}

android.namespace = "com.maximillianleonov.cinemax.core.ui"

dependencies {
    api(project(":core:core-designsystem"))
    api(project(":core:core-navigation"))
    api(project(":core:core-model"))
    api(project(":core:core-domain"))

    implementation(libs.kotlinx.datetime)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.androidx.paging.compose)
}
