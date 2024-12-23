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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.multiwallpaper.MainViewModel
import dev.kichan.multiwallpaper.ui.Route
import dev.kichan.multiwallpaper.ui.component.HomeLocalBottomSheet
import dev.kichan.multiwallpaper.ui.component.LoadingDialog
import dev.kichan.multiwallpaper.ui.component.PagerIndicator
import dev.kichan.multiwallpaper.ui.component.WallpaperItem
import dev.kichan.multiwallpaper.ui.component.shapeModifier
import dev.kichan.multiwallpaper.ui.theme.ColorPalette
import dev.kichan.multiwallpaper.ui.theme.ColorPalette.Gray0
import dev.kichan.multiwallpaper.ui.theme.ColorPalette.Gray1
import dev.kichan.multiwallpaper.ui.theme.ColorPalette.Gray2
import dev.kichan.multiwallpaper.ui.theme.ColorPalette.Gray4
import dev.kichan.multiwallpaper.ui.theme.ColorPalette.Gray6
import dev.kichan.multiwallpaper.ui.theme.ColorPalette.Gray7
import dev.kichan.multiwallpaper.ui.theme.ColorPalette.Gray9
import dev.kichan.multiwallpaper.ui.theme.MultiWallpaperTheme
import dev.kichan.multiwallpaper.ui.theme.buttonColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
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
    var isLoadingDialogShow by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getWallpaper()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "다중 배경화면", fontWeight = FontWeight.SemiBold)
                },
                colors = TopAppBarColors(
                    containerColor = Gray0,
                    scrolledContainerColor = Gray4,
                    navigationIconContentColor = Gray4,
                    titleContentColor = Gray9,
                    actionIconContentColor = Gray4
                ),
                modifier = Modifier.border(1.dp, Gray2),
            )
        }
    ) {
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
                    key("add") {
                        Box(
                            modifier = shapeModifier
                                .background(ColorPalette.Gray3)
                                .clickable {
                                    navController.navigate(Route.Add.name)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = ColorPalette.Gray7
                            )
                        }
                    }
                }
            }

            PagerIndicator(pagerState = wallpaperPagerState)

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    isScreenSelectDialogShow = true
                },
                enabled = wallpaperPagerState.currentPage < wallpaperList!!.size,
                colors = buttonColor
            ) {
                Text(text = "배경화면으로 지정", modifier = Modifier.padding(horizontal = 20.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    if (isScreenSelectDialogShow) {
        HomeLocalBottomSheet(onSelected = { type ->
            isLoadingDialogShow = true
            CoroutineScope(Dispatchers.Default).launch {
                if (type == WallpaperManager.FLAG_LOCK || type == -1) {
                    wallpaperManager.setBitmap(
                        wallpaperList!![wallpaperPagerState.currentPage].getBitmap(),
                        wallpaperList!![wallpaperPagerState.currentPage].getCropRect(),
                        true,
                        WallpaperManager.FLAG_LOCK
                    )
                }

                if (type == WallpaperManager.FLAG_SYSTEM || type == -1) {
                    wallpaperManager.setBitmap(
                        wallpaperList!![wallpaperPagerState.currentPage].getBitmap(),
                        wallpaperList!![wallpaperPagerState.currentPage].getCropRect(),
                        true,
                        WallpaperManager.FLAG_SYSTEM
                    )
                }

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "배경화면으로 지정되었습니다.", Toast.LENGTH_SHORT).show()
                    isScreenSelectDialogShow = false
                    isLoadingDialogShow = false
                }
            }
        }) {
            isScreenSelectDialogShow = false
        }
    }
    if(isLoadingDialogShow) {
        LoadingDialog()
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