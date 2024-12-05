package dev.kichan.multiwallpaper.ui.page

import android.app.Application
import android.app.WallpaperManager
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.multiwallpaper.MainViewModel
import dev.kichan.multiwallpaper.ui.Route
import dev.kichan.multiwallpaper.ui.component.HomeLocalBottomSheet
import dev.kichan.multiwallpaper.ui.component.PagerIndicator
import dev.kichan.multiwallpaper.ui.component.WallpaperItem
import dev.kichan.multiwallpaper.ui.theme.MultiWallpaperTheme

@Composable
fun HomePage(
    navController: NavController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val wallpaperManager = WallpaperManager.getInstance(context)

    val wallpaperList by viewModel.wallpapersList.observeAsState()
    val wallpaperPagerState =
        rememberPagerState(initialPage = 0, pageCount = { (wallpaperList?.size ?: 0) + 1 })
    var isScreenSelectDialogShow by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getWallpaper()
    }

    Scaffold {
        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = wallpaperPagerState,
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 32.dp),
                pageSpacing = 20.dp
            ) { page ->
                if (page < wallpaperList!!.size) {
                    val wallpaper = wallpaperList!![page]
                    key(wallpaper.id) {
                        WallpaperItem(
                            wallpaper = wallpaper,
                            onDeleteClick = {
                                viewModel.deleteWallpaper(wallpaper)
                            }
                        )
                    }
                } else {
                    val imageShape = RoundedCornerShape(34.dp)
                    key("add") {
                        Box(
                            modifier = Modifier
                                .aspectRatio(9f / 16f)
                                .weight(1f)
                                .clip(imageShape)
                                .background(color = Color(0xFFE6E6E6), shape = imageShape)
                                .border(width = 2.dp, color = Color(0xffd3d3d3), shape = imageShape)
                                .clickable {
                                    navController.navigate(Route.Add.name)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        }
                    }
                }
            }

            PagerIndicator(pagerState = wallpaperPagerState)

            Button(
                onClick = {
                    isScreenSelectDialogShow = true
                },
                enabled = wallpaperPagerState.currentPage < wallpaperList!!.size
            ) {
                Text(text = "배경화면으로 지정")
            }
        }

        if(isScreenSelectDialogShow) {
            HomeLocalBottomSheet(onSelected = { type ->
                if(type == WallpaperManager.FLAG_LOCK || type == -1) {
                    wallpaperManager.setBitmap(
                        wallpaperList!![wallpaperPagerState.currentPage].getBitmap(),
                        wallpaperList!![wallpaperPagerState.currentPage].getCropRect(),
                        true,
                        WallpaperManager.FLAG_LOCK
                    )
                }

                if(type == WallpaperManager.FLAG_SYSTEM || type == -1) {
                    wallpaperManager.setBitmap(
                        wallpaperList!![wallpaperPagerState.currentPage].getBitmap(),
                        wallpaperList!![wallpaperPagerState.currentPage].getCropRect(),
                        true,
                        WallpaperManager.FLAG_SYSTEM
                    )
                }
                Toast.makeText(context, "배경화면으로 지정되었습니다.", Toast.LENGTH_SHORT).show()
                isScreenSelectDialogShow = false
            }) {
                isScreenSelectDialogShow = false
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    MultiWallpaperTheme {
        HomePage(
            navController = rememberNavController(),
            viewModel = MainViewModel(LocalContext.current as Application)
        )
    }
}