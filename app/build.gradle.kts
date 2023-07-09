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
    id("cinemax.android.application")
    id("cinemax.android.application.compose")
    id("cinemax.android.lint")
    id("cinemax.android.signingconfig")
    id("cinemax.apikey.provider")

    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "com.maximillianleonov.cinemax"

    defaultConfig {
        applicationId = "com.maximillianleonov.cinemax"
        versionCode = 6
        versionName = "1.0.5"
    }

    buildFeatures.buildConfig = true
}

dependencies {
    implementation(project(":core:core-common"))
    implementation(project(":core:core-data"))
    implementation(project(":core:core-database"))
    implementation(project(":core:core-datastore"))
    implementation(project(":core:core-network"))
    implementation(project(":core:core-domain"))
    implementation(project(":core:core-model"))
    implementation(project(":core:core-ui"))
    implementation(project(":core:core-designsystem"))
    implementation(project(":core:core-navigation"))

    implementation(project(":features:feature-home"))
    implementation(project(":features:feature-search"))
    implementation(project(":features:feature-wishlist"))
    implementation(project(":features:feature-settings"))
    implementation(project(":features:feature-list"))
    implementation(project(":features:feature-details"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.androidx.room.runtime)
}
