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

package com.maximillianleonov.cinemax.core.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.maximillianleonov.cinemax.core.designsystem.component.LocalSnackbarHostState
import com.maximillianleonov.cinemax.core.model.UserMessage
import com.maximillianleonov.cinemax.core.ui.mapper.asString

@Composable
fun SnackbarUserMessageHandler(
    userMessage: UserMessage?,
    onShowMessage: (String) -> Unit,
    onDismiss: () -> Unit,
    snackbarHostState: SnackbarHostState = LocalSnackbarHostState.current
) {
    if (userMessage == null) return
    val message = userMessage.asString()
    LaunchedEffect(snackbarHostState, userMessage) {
        onShowMessage(message)
        onDismiss()
    }
}
