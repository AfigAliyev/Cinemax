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

android.namespace = "com.maximillianleonov.cinemax.core.designsystem"

dependencies {
    api(libs.bundles.androidx.compose)
    api(libs.accompanist.swiperefresh)
    api(libs.accompanist.placeholder.material)
    api(libs.coil.compose)
    debugApi(libs.androidx.compose.ui.tooling)
    debugApi(libs.androidx.compose.ui.test.manifest)
}
