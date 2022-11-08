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

package com.maximillianleonov.cinemax.core.ui.mapper

import com.maximillianleonov.cinemax.core.model.ReleaseDate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter

internal fun LocalDate.asReleaseDate() = ReleaseDate(
    fullDate = format(FullDatePattern),
    year = year.toString()
)

private fun LocalDate.format(pattern: String): String =
    toJavaLocalDate().format(DateTimeFormatter.ofPattern(pattern))

private const val FullDatePattern = "d MMMM, yyyy"
