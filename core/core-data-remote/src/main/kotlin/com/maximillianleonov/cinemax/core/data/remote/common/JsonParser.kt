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

package com.maximillianleonov.cinemax.core.data.remote.common

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object JsonParser {
    inline fun <reified T> toJson(value: T): String = defaultJson.encodeToString(value)
    inline fun <reified T> toJson(
        serializer: SerializationStrategy<T>,
        value: T
    ): String = defaultJson.encodeToString(serializer, value)

    inline fun <reified T> fromJson(json: String): T = defaultJson.decodeFromString(json)
    inline fun <reified T> fromJson(
        deserializer: DeserializationStrategy<T>,
        json: String
    ): T = defaultJson.decodeFromString(deserializer, json)
}

@OptIn(ExperimentalSerializationApi::class)
@PublishedApi
internal val defaultJson = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
}
