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

package com.maximillianleonov.cinemax.core.common.result

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun <T> CinemaxResult<T>.isLoading(): Boolean {
    contract {
        returns() implies (this@isLoading is CinemaxResult.Loading<T>)
    }
    return this is CinemaxResult.Loading<T>
}

@OptIn(ExperimentalContracts::class)
fun <T> CinemaxResult<T>.isSuccess(): Boolean {
    contract {
        returns() implies (this@isSuccess is CinemaxResult.Success<T>)
    }
    return this is CinemaxResult.Success<T>
}

@OptIn(ExperimentalContracts::class)
fun <T> CinemaxResult<T>.isFailure(): Boolean {
    contract {
        returns() implies (this@isFailure is CinemaxResult.Failure<T>)
    }
    return this is CinemaxResult.Failure<T>
}
