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

package com.maximillianleonov.cinemax.data.local.mapper

import com.maximillianleonov.cinemax.data.remote.dto.common.CastDto
import com.maximillianleonov.cinemax.data.remote.dto.common.CreditsDto
import com.maximillianleonov.cinemax.data.remote.dto.common.CrewDto
import com.maximillianleonov.cinemax.domain.model.CastModel
import com.maximillianleonov.cinemax.domain.model.CreditsModel
import com.maximillianleonov.cinemax.domain.model.CrewModel

fun CreditsDto.toCreditsModel() = CreditsModel(
    cast = cast.map(CastDto::toCastModel),
    crew = crew.map(CrewDto::toCrewModel)
)

fun CastDto.toCastModel() = CastModel(
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
    profilePath = profilePath?.toImageUrl()
)

fun CrewDto.toCrewModel() = CrewModel(
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
    profilePath = profilePath?.toImageUrl()
)
