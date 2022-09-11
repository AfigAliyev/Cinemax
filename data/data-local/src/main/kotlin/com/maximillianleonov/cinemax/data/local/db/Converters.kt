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

package com.maximillianleonov.cinemax.data.local.db

import androidx.room.TypeConverter
import com.maximillianleonov.cinemax.core.data.remote.common.JsonParser
import com.maximillianleonov.cinemax.core.data.remote.serializer.LocalDateSerializer
import com.maximillianleonov.cinemax.data.remote.dto.common.CreditsDto
import kotlinx.datetime.LocalDate

object Converters {
    @TypeConverter
    fun localDateToJson(value: LocalDate?): String = JsonParser.toJson(LocalDateSerializer(), value)

    @TypeConverter
    fun jsonToLocalDate(json: String): LocalDate? = JsonParser.fromJson(LocalDateSerializer(), json)

    @TypeConverter
    fun intListToJson(value: List<Int>): String = JsonParser.toJson(value)

    @TypeConverter
    fun jsonToIntList(json: String): List<Int> = JsonParser.fromJson(json)

    @TypeConverter
    fun stringListToJson(value: List<String>): String = JsonParser.toJson(value)

    @TypeConverter
    fun jsonToStringList(json: String): List<String> = JsonParser.fromJson(json)

    @TypeConverter
    fun creditsToJson(value: CreditsDto): String = JsonParser.toJson(value)

    @TypeConverter
    fun jsonToCredits(json: String): CreditsDto = JsonParser.fromJson(json)
}
