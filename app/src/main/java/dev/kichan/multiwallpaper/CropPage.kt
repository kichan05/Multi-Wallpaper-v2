package dev.kichan.multiwallpaper

import android.app.Application
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.multiwallpaper.ui.theme.MultiWallpaperTheme

@Composable
fun CropPage(
    navController: NavController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val imageUri by viewModel.wallpaperUri.observeAsState()
    val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)


    Scaffold {
        Box(
            modifier = Modifier.padding(it)
        ) {
            Image(bitmap = bitmap.asImageBitmap(), contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CropPagePreview() {
    val context = LocalContext.current
    MultiWallpaperTheme {
        CropPage(rememberNavController(), MainViewModel(context as Application))
    }
}
