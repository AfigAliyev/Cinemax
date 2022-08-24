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

package com.maximillianleonov.cinemax.feature.search.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.hilt.navigation.compose.hiltViewModel
import com.maximillianleonov.cinemax.core.presentation.R
import com.maximillianleonov.cinemax.core.presentation.components.CinemaxTextField
import com.maximillianleonov.cinemax.core.presentation.theme.CinemaxTheme

@Composable
internal fun SearchRoute(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    SearchScreen(
        uiState = uiState,
        onQueryChange = { viewModel.onEvent(SearchEvent.ChangeQuery(it)) },
        modifier = modifier
    )
}

@Composable
private fun SearchScreen(
    uiState: SearchUiState,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current
) {
    Column(modifier = modifier.fillMaxSize()) {
        CinemaxTextField(
            modifier = Modifier
                .padding(
                    start = CinemaxTheme.spacing.extraMedium,
                    top = CinemaxTheme.spacing.small,
                    end = CinemaxTheme.spacing.extraMedium,
                    bottom = CinemaxTheme.spacing.extraMedium
                )
                .fillMaxWidth(),
            value = uiState.query,
            onValueChange = onQueryChange,
            placeholderResourceId = R.string.search_placeholder,
            iconResourceId = R.drawable.ic_search,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
        )
    }
}
