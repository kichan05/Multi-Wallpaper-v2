package dev.kichan.multiwallpaper.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kichan.multiwallpaper.ui.theme.MultiWallpaperTheme

@Composable
fun PagerIndicator(modifier: Modifier = Modifier, pagerState: PagerState) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        val pageCount = pagerState.pageCount
        for (i in 0 until pageCount) {
            val isSelected = pagerState.currentPage == i
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = if (isSelected) Color(0xFF272727) else Color(0x4D707070),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Preview
@Composable
private fun PagerIndicatorPreview() {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 5 })

    MultiWallpaperTheme {
        PagerIndicator(pagerState = pagerState)
    }
}