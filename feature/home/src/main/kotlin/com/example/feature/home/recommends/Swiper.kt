package com.example.feature.home.recommends

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.absoluteValue
import kotlin.math.sign

data class SwiperData(val imgUrl: String, val onClick: () -> Unit)

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun Swiper(
    modifier: Modifier = Modifier,
    list: PersistentList<SwiperData>,
    delay: Long = 3000L
) {
    val state = rememberPagerState(0)
    LaunchedEffect(list) {
        while (true) {
            if (isActive) {
                delay(delay)
                state.animateScrollToPage((state.currentPage + 1) % list.size)
            }
        }
    }
    Box(modifier) {
        HorizontalPager(
            pageCount = list.size,
            modifier = Modifier,
            state = state
        ) { page ->
            AsyncImage(
                model = list[page].imgUrl,
                modifier = Modifier.fillMaxSize().clickable { list[page].onClick() },
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        HorizontalPagerIndicator(
            pagerState = state,
            pageCount = list.size,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .align(Alignment.BottomCenter)
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    pageCount: Int,
    pageIndexMapping: (Int) -> Int = { it },
    activeColor: Color = LocalContentColor.current,
    inactiveColor: Color = activeColor.copy(.5f),
    indicatorWidth: Dp = 8.dp,
    indicatorHeight: Dp = 4.dp,
    spacing: Dp = indicatorWidth,
    indicatorShape: Shape = MaterialTheme.shapes.small,
) {
    val stateBridge = remember(pagerState) {
        object : PagerStateBridge {
            override val currentPage: Int
                get() = pagerState.currentPage
            override val currentPageOffset: Float
                get() = pagerState.currentPageOffsetFraction
        }
    }

    HorizontalPagerIndicator(
        pagerState = stateBridge,
        pageCount = pageCount,
        modifier = modifier,
        pageIndexMapping = pageIndexMapping,
        activeColor = activeColor,
        inactiveColor = inactiveColor,
        indicatorHeight = indicatorHeight,
        indicatorWidth = indicatorWidth,
        spacing = spacing,
        indicatorShape = indicatorShape
    )
}


@Composable
private fun HorizontalPagerIndicator(
    pagerState: PagerStateBridge,
    pageCount: Int,
    modifier: Modifier = Modifier,
    pageIndexMapping: (Int) -> Int = { it },
    activeColor: Color = LocalContentColor.current,
    inactiveColor: Color = activeColor,
    indicatorWidth: Dp = 8.dp,
    indicatorHeight: Dp = indicatorWidth,
    spacing: Dp = indicatorWidth,
    indicatorShape: Shape = CircleShape,
) {

    val indicatorWidthPx = LocalDensity.current.run { indicatorWidth.roundToPx() }
    val spacingPx = LocalDensity.current.run { spacing.roundToPx() }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val indicatorModifier = Modifier
                .size(width = indicatorWidth, height = indicatorHeight)
                .background(color = inactiveColor, shape = indicatorShape)

            repeat(pageCount) {
                Box(indicatorModifier)
            }
        }

        Box(
            Modifier
                .offset {
                    val position = pageIndexMapping(pagerState.currentPage)
                    val offset = pagerState.currentPageOffset
                    val next = pageIndexMapping(pagerState.currentPage + offset.sign.toInt())
                    val scrollPosition = ((next - position) * offset.absoluteValue + position)
                        .coerceIn(
                            0f,
                            (pageCount - 1)
                                .coerceAtLeast(0)
                                .toFloat()
                        )

                    IntOffset(
                        x = ((spacingPx + indicatorWidthPx) * scrollPosition).toInt(),
                        y = 0
                    )
                }
                .size(width = indicatorWidth, height = indicatorHeight)
                .then(
                    if (pageCount > 0) Modifier.background(
                        color = activeColor,
                        shape = indicatorShape,
                    )
                    else Modifier
                )
        )
    }
}

internal interface PagerStateBridge {
    val currentPage: Int
    val currentPageOffset: Float
}


fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + (stop - start) * fraction
}