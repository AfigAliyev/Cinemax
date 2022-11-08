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

package com.maximillianleonov.cinemax.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxTextField
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CinemaxSelectionDialog(
    expanded: Boolean,
    onDismiss: () -> Unit,
    values: List<Pair<String, String>>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }
    val filteredValues by remember {
        derivedStateOf {
            values.filter { (_, value) ->
                value.startsWith(query.trim(), ignoreCase = true)
            }
        }
    }
    val isError = filteredValues.isEmpty()

    if (expanded) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(modifier = modifier) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    CinemaxTheme.colors.primary,
                                    CinemaxTheme.colors.accent,
                                    CinemaxTheme.colors.primaryVariant
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    CinemaxBackButton(
                        modifier = Modifier
                            .padding(CinemaxTheme.spacing.medium)
                            .align(Alignment.TopStart),
                        onClick = onDismiss
                    )

                    Card(
                        modifier = Modifier.padding(
                            horizontal = CinemaxTheme.spacing.medium,
                            vertical = 80.dp
                        ),
                        shape = CinemaxTheme.shapes.extraLarge,
                        colors = CardDefaults.cardColors(containerColor = CinemaxTheme.colors.primary)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CinemaxTextField(
                                modifier = Modifier.padding(
                                    vertical = CinemaxTheme.spacing.smallMedium,
                                    horizontal = CinemaxTheme.spacing.extraMedium
                                ),
                                value = query,
                                onValueChange = { query = it },
                                placeholderResourceId = R.string.search,
                                iconResourceId = R.drawable.ic_search,
                                isError = isError
                            )

                            LazyColumn {
                                items(filteredValues) { item ->
                                    val key = item.first
                                    val value = item.second

                                    Text(
                                        modifier = Modifier
                                            .clickable {
                                                onSelect(key)
                                                onDismiss()
                                            }
                                            .padding(
                                                horizontal = CinemaxTheme.spacing.extraLarge,
                                                vertical = CinemaxTheme.spacing.medium
                                            )
                                            .fillMaxWidth(),
                                        text = value,
                                        style = CinemaxTheme.typography.regular.h4,
                                        color = CinemaxTheme.colors.textPrimary,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
