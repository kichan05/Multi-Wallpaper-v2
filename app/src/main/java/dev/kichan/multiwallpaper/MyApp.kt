package dev.kichan.multiwallpaper

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.kichan.multiwallpaper.ui.theme.MultiWallpaperTheme

@Composable
fun MyApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(startDestination = Route.Main.name, navController = navController) {
        composable(route = Route.Main.name) {
            HomePage(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(route = Route.Add.name) {
            AddPage(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(route = Route.Crop.name) {
            CropPage(navController = navController, viewModel = viewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    MultiWallpaperTheme {
        MyApp(MainViewModel(LocalContext.current as Application))
    }
}