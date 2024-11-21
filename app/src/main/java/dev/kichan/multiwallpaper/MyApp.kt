package dev.kichan.multiwallpaper

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.kichan.multiwallpaper.ui.Route
import dev.kichan.multiwallpaper.ui.page.AddPage
import dev.kichan.multiwallpaper.ui.page.CropPage
import dev.kichan.multiwallpaper.ui.page.HomePage
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