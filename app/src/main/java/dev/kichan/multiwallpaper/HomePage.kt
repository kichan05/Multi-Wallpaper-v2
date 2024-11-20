package dev.kichan.multiwallpaper

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.multiwallpaper.ui.theme.MultiWallpaperTheme

@Composable
fun HomePage(
    navController: NavController,
    viewModel: MainViewModel
) {
    val wallpaperList by viewModel.wallpapersList.observeAsState()

    Scaffold {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Button(onClick = { navController.navigate(Route.Add.name) }) {
                Text(text = "추가")
            }

            Button(onClick = { viewModel.getWallpaper() }) {
                Text(text = "새로 고침")
            }


            LazyColumn {
                items(items = wallpaperList ?: listOf()) { wallpaper ->
                    WallpaperItem(wallpaper = wallpaper)
                }
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
