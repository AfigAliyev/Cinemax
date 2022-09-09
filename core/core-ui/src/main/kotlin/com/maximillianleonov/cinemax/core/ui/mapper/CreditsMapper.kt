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

import com.maximillianleonov.cinemax.core.ui.model.Cast
import com.maximillianleonov.cinemax.core.ui.model.Credits
import com.maximillianleonov.cinemax.core.ui.model.Crew
import com.maximillianleonov.cinemax.domain.model.CastModel
import com.maximillianleonov.cinemax.domain.model.CreditsModel
import com.maximillianleonov.cinemax.domain.model.CrewModel

internal fun CreditsModel.toCredits() = Credits(
    cast = cast.map(CastModel::toCast),
    crew = crew.map(CrewModel::toCrew)
)

private fun CastModel.toCast() = Cast(
    id = id,
    name = name,
    character = character,
    profilePath = profilePath
)

private fun CrewModel.toCrew() = Crew(
    id = id,
    name = name,
    job = job,
    profilePath = profilePath
)
