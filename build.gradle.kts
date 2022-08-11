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
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.detekt)
}

detekt {
    toolVersion = libs.versions.detekt.get()
    source = files(rootDir)
    config = files("$rootDir/config/detekt/detekt.yml")
    buildUponDefaultConfig = true
    parallel = true
}

dependencies {
    detektPlugins(libs.detekt.formatting)
    detektPlugins(libs.kode.detekt.rules.compose)
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    exclude("config/**")
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        target("**/*.kt")
        targetExclude("build/**/*.kt", "**/build/**/*.kt", "config/**/*.kt")
        licenseHeaderFile(rootProject.file("config/spotless/copyright.kt"))
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        targetExclude("build/**/*.gradle.kts", "**/build/**/*.gradle.kts")
        licenseHeaderFile(
            rootProject.file("config/spotless/copyright.kt"),
            "(plugins |pluginManagement |import |@file)"
        )
    }
    format("xml") {
        target("**/*.xml")
        targetExclude("build/**/*.xml", "**/build/**/*.xml", "config/**/*.xml")
        licenseHeaderFile(rootProject.file("config/spotless/copyright.xml"), "(<[^!?])")
    }
}
