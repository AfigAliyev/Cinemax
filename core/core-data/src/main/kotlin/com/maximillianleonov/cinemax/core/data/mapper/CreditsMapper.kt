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

package com.maximillianleonov.cinemax.core.data.mapper

import com.maximillianleonov.cinemax.core.database.model.common.Cast
import com.maximillianleonov.cinemax.core.database.model.common.Credits
import com.maximillianleonov.cinemax.core.database.model.common.Crew
import com.maximillianleonov.cinemax.core.domain.model.CastModel
import com.maximillianleonov.cinemax.core.domain.model.CreditsModel
import com.maximillianleonov.cinemax.core.domain.model.CrewModel
import com.maximillianleonov.cinemax.core.network.model.common.NetworkCast
import com.maximillianleonov.cinemax.core.network.model.common.NetworkCredits
import com.maximillianleonov.cinemax.core.network.model.common.NetworkCrew

fun NetworkCredits.asCredits() = Credits(
    cast = cast.map(NetworkCast::asCast),
    crew = crew.map(NetworkCrew::asCrew)
)

fun NetworkCast.asCast() = Cast(
    id = id,
    name = name,
    adult = adult,
    castId = castId,
    character = character,
    creditId = creditId,
    gender = gender,
    knownForDepartment = knownForDepartment,
    order = order,
    originalName = originalName,
    popularity = popularity,
    profilePath = profilePath?.asImageUrl()
)

fun NetworkCrew.asCrew() = Crew(
    id = id,
    adult = adult,
    creditId = creditId,
    department = department,
    gender = gender,
    job = job,
    knownForDepartment = knownForDepartment,
    name = name,
    originalName = originalName,
    popularity = popularity,
    profilePath = profilePath?.asImageUrl()
)

fun Credits.asCreditsModel() = CreditsModel(
    cast = cast.map(Cast::asCastModel),
    crew = crew.map(Crew::asCrewModel)
)

fun Cast.asCastModel() = CastModel(
    id = id,
    name = name,
    adult = adult,
    castId = castId,
    character = character,
    creditId = creditId,
    gender = gender,
    knownForDepartment = knownForDepartment,
    order = order,
    originalName = originalName,
    popularity = popularity,
    profilePath = profilePath
)

fun Crew.asCrewModel() = CrewModel(
    id = id,
    adult = adult,
    creditId = creditId,
    department = department,
    gender = gender,
    job = job,
    knownForDepartment = knownForDepartment,
    name = name,
    originalName = originalName,
    popularity = popularity,
    profilePath = profilePath
)
