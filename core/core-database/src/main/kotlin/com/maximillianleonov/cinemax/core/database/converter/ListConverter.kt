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

package com.maximillianleonov.cinemax.core.database.converter

import androidx.room.TypeConverter
import com.maximillianleonov.cinemax.core.database.model.common.Genre
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class ListConverter {
    @TypeConverter
    internal fun intListToString(list: List<Int>): String = Json.encodeToString(list)

    @TypeConverter
    internal fun stringToIntList(string: String): List<Int> = Json.decodeFromString(string)

    @TypeConverter
    internal fun stringListToString(list: List<String>): String = Json.encodeToString(list)

    @TypeConverter
    internal fun stringToStringList(string: String): List<String> = Json.decodeFromString(string)

    @TypeConverter
    internal fun genreListToString(list: List<Genre>): String = Json.encodeToString(list)

    @TypeConverter
    internal fun stringToGenreList(string: String): List<Genre> = Json.decodeFromString(string)
}
