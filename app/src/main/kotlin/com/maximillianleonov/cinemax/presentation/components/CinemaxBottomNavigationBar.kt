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

package com.maximillianleonov.cinemax.presentation.components

import androidx.annotation.FloatRange
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.maximillianleonov.cinemax.core.presentation.theme.CinemaxTheme
import com.maximillianleonov.cinemax.presentation.navigation.BottomNavigationSection

@Composable
fun CinemaxBottomNavigationBar(
    tabs: Array<BottomNavigationSection>,
    currentRoute: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = CinemaxTheme.colors.primaryDark
) {
    val routes = remember { tabs.map(BottomNavigationSection::route) }
    var currentSection by remember { mutableStateOf(tabs.first { it.route == currentRoute }) }
    tabs.firstOrNull { it.route == currentRoute }?.let { currentSection = it }

    Surface(
        modifier = modifier,
        color = color
    ) {
        val animationSpec = BottomNavigationLayoutAnimationSpec

        BottomNavigationLayout(
            selectedIndex = currentSection.ordinal,
            itemCount = routes.size,
            animationSpec = animationSpec,
            indicator = { BottomNavigationIndicator() },
            modifier = Modifier.navigationBarsPadding()
        ) {
            tabs.forEach { section ->
                val selected = section == currentSection
                val tint by animateColorAsState(
                    if (selected) {
                        CinemaxTheme.colors.primaryBlue
                    } else {
                        CinemaxTheme.colors.textGrey
                    }
                )

                val icon = painterResource(id = section.drawableResourceId)
                val text = stringResource(id = section.stringResourceId)

                BottomNavigationItem(
                    modifier = Modifier
                        .padding(CinemaxTheme.spacing.smallMedium)
                        .clip(CinemaxTheme.shapes.medium),
                    icon = {
                        Icon(
                            painter = icon,
                            tint = tint,
                            contentDescription = text
                        )
                    },
                    text = {
                        Text(
                            text = text,
                            color = tint,
                            style = CinemaxTheme.typography.medium.h6,
                            maxLines = 1
                        )
                    },
                    selected = selected,
                    onSelect = { onSelect(section.route) },
                    animationSpec = animationSpec
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationLayout(
    selectedIndex: Int,
    itemCount: Int,
    animationSpec: AnimationSpec<Float>,
    indicator: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val selectionFractions = remember(itemCount) {
        List(itemCount) { i ->
            Animatable(if (i == selectedIndex) 1f else 0f)
        }
    }
    selectionFractions.forEachIndexed { index, selectionFraction ->
        val targetValue = if (index == selectedIndex) 1f else 0f
        LaunchedEffect(key1 = targetValue, key2 = animationSpec) {
            selectionFraction.animateTo(targetValue = targetValue, animationSpec = animationSpec)
        }
    }

    val indicatorIndex = remember { Animatable(initialValue = 0f) }
    val targetIndicatorIndex = selectedIndex.toFloat()
    LaunchedEffect(key1 = targetIndicatorIndex) {
        indicatorIndex.animateTo(targetValue = targetIndicatorIndex, animationSpec = animationSpec)
    }

    Layout(
        modifier = modifier.height(72.dp),
        content = {
            content()
            Box(modifier = Modifier.layoutId(IndicatorLayoutId), content = indicator)
        }
    ) { measurables, constraints ->
        require(itemCount == (measurables.size - 1))

        val unselectedWidth = constraints.maxWidth / (itemCount + 1)
        val selectedWidth = 2 * unselectedWidth
        val indicatorMeasurable = measurables.first { it.layoutId == IndicatorLayoutId }

        val itemPlaceables = measurables
            .filterNot { it == indicatorMeasurable }
            .mapIndexed { index, measurable ->
                val width = lerp(
                    start = unselectedWidth,
                    stop = selectedWidth,
                    fraction = selectionFractions[index].value
                )
                measurable.measure(
                    constraints.copy(
                        minWidth = width,
                        maxWidth = width
                    )
                )
            }
        val indicatorPlaceable = indicatorMeasurable.measure(
            constraints.copy(
                minWidth = selectedWidth,
                maxWidth = selectedWidth
            )
        )

        layout(
            width = constraints.maxWidth,
            height = itemPlaceables.maxByOrNull { it.height }?.height ?: 0
        ) {
            val indicatorLeft = indicatorIndex.value * unselectedWidth
            indicatorPlaceable.placeRelative(x = indicatorLeft.toInt(), y = 0)
            var x = 0
            itemPlaceables.forEach { placeable ->
                placeable.placeRelative(x = x, y = 0)
                x += placeable.width
            }
        }
    }
}

@Composable
private fun BottomNavigationItem(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    selected: Boolean,
    onSelect: () -> Unit,
    animationSpec: AnimationSpec<Float>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.selectable(selected = selected, onClick = onSelect),
        contentAlignment = Alignment.Center
    ) {
        val animationProgress by animateFloatAsState(
            targetValue = if (selected) 1f else 0f,
            animationSpec = animationSpec
        )
        BottomNavigationItemLayout(
            icon = icon,
            text = text,
            animationProgress = animationProgress
        )
    }
}

@Composable
private fun BottomNavigationItemLayout(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    @FloatRange(from = 0.0, to = 1.0) animationProgress: Float,
    modifier: Modifier = Modifier,
    defaultTransformOrigin: TransformOrigin = TransformOrigin(
        TransformOriginPivotFractionX,
        TransformOriginPivotFractionY
    )
) {
    Layout(
        content = {
            Box(
                modifier = Modifier
                    .layoutId(IconLayoutId)
                    .padding(horizontal = CinemaxTheme.spacing.extraSmall),
                content = icon
            )
            val scale = lerp(
                start = BottomNavigationItemLayoutLerpStart,
                stop = BottomNavigationItemLayoutLerpStop,
                fraction = animationProgress
            )
            Box(
                modifier = Modifier
                    .layoutId(TextLayoutId)
                    .padding(horizontal = CinemaxTheme.spacing.extraSmall)
                    .graphicsLayer {
                        alpha = animationProgress
                        scaleX = scale
                        scaleY = scale
                        transformOrigin = defaultTransformOrigin
                    },
                content = text
            )
        },
        modifier = modifier
    ) { measurables, constraints ->
        val iconPlaceable = measurables.first { it.layoutId == IconLayoutId }.measure(constraints)
        val textPlaceable = measurables.first { it.layoutId == TextLayoutId }.measure(constraints)

        placeTextAndIcon(
            textPlaceable = textPlaceable,
            iconPlaceable = iconPlaceable,
            width = constraints.maxWidth,
            height = constraints.maxHeight,
            animationProgress = animationProgress
        )
    }
}

@Composable
private fun BottomNavigationIndicator(
    modifier: Modifier = Modifier,
    padding: Dp = CinemaxTheme.spacing.smallMedium,
    color: Color = CinemaxTheme.colors.primarySoft,
    shape: Shape = CinemaxTheme.shapes.medium
) {
    Spacer(
        modifier = modifier
            .fillMaxSize()
            .padding(padding)
            .background(color = color, shape = shape)
    )
}

private fun MeasureScope.placeTextAndIcon(
    textPlaceable: Placeable,
    iconPlaceable: Placeable,
    width: Int,
    height: Int,
    @FloatRange(from = 0.0, to = 1.0) animationProgress: Float
): MeasureResult {
    val iconY = (height - iconPlaceable.height) / 2
    val textY = (height - textPlaceable.height) / 2

    val textWidth = textPlaceable.width * animationProgress
    val iconX = (width - textWidth - iconPlaceable.width) / 2
    val textX = iconX + iconPlaceable.width

    return layout(width = width, height = height) {
        iconPlaceable.placeRelative(x = iconX.toInt(), y = iconY)
        if (animationProgress != 0f) {
            textPlaceable.placeRelative(x = textX.toInt(), y = textY)
        }
    }
}

private const val TextLayoutId = "text"
private const val IconLayoutId = "icon"
private const val IndicatorLayoutId = "indicator"
private const val TransformOriginPivotFractionX = 0f
private const val TransformOriginPivotFractionY = 0.5f
private const val BottomNavigationItemLayoutLerpStart = 0.6f
private const val BottomNavigationItemLayoutLerpStop = 1f
private val BottomNavigationLayoutAnimationSpec = SpringSpec<Float>(
    stiffness = 800f,
    dampingRatio = 0.8f
)
