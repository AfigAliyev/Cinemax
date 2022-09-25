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

package com.maximillianleonov.cinemax.core.database.model.common

import com.maximillianleonov.cinemax.core.database.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Crew(
    @SerialName(Constants.Fields.ID)
    val id: Int,

    @SerialName(Constants.Fields.ADULT)
    val adult: Boolean,

    @SerialName(Constants.Fields.CREDIT_ID)
    val creditId: String,

    @SerialName(Constants.Fields.DEPARTMENT)
    val department: String,

    @SerialName(Constants.Fields.GENDER)
    val gender: Int?,

    @SerialName(Constants.Fields.JOB)
    val job: String,

    @SerialName(Constants.Fields.KNOWN_FOR_DEPARTMENT)
    val knownForDepartment: String,

    @SerialName(Constants.Fields.NAME)
    val name: String,

    @SerialName(Constants.Fields.ORIGINAL_NAME)
    val originalName: String,

    @SerialName(Constants.Fields.POPULARITY)
    val popularity: Double,

    @SerialName(Constants.Fields.PROFILE_PATH)
    val profilePath: String?
)
