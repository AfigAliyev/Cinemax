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

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.util.Properties

class ApiKeyProviderConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists() && localPropertiesFile.isFile) {
            localPropertiesFile.inputStream().use { input ->
                localProperties.load(input)
            }
        }

        val cinemaxApiKey = checkNotNull(
            localProperties.getProperty("cinemax.apikey") ?: System.getenv("CINEMAX_API_KEY")
        )

        extensions.configure<BaseAppModuleExtension> {
            defaultConfig.buildConfigField("String", "CINEMAX_API_KEY", "\"$cinemaxApiKey\"")
        }
    }
}
