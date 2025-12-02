/*
 * Copyright 2022 Afig Aliyev
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
    `kotlin-dsl`
}

group = "com.maximillianleonov.cinemax.buildlogic"

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
    compileOnly(libs.spotless.gradle.plugin)
    compileOnly(libs.detekt.gradle.plugin)

    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "cinemax.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "cinemax.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "cinemax.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "cinemax.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "cinemax.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidBenchmark") {
            id = "cinemax.android.benchmark"
            implementationClass = "AndroidBenchmarkConventionPlugin"
        }
        register("androidLint") {
            id = "cinemax.android.lint"
            implementationClass = "AndroidLintConventionPlugin"
        }
        register("androidSigningConfig") {
            id = "cinemax.android.signingconfig"
            implementationClass = "AndroidSigningConfigConventionPlugin"
        }
        register("spotless") {
            id = "cinemax.spotless"
            implementationClass = "SpotlessConventionPlugin"
        }
        register("detekt") {
            id = "cinemax.detekt"
            implementationClass = "DetektConventionPlugin"
        }
        register("apiKeyProvider") {
            id = "cinemax.apikey.provider"
            implementationClass = "ApiKeyProviderConventionPlugin"
        }
    }
}
