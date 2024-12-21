package dev.kichan.multiwallpaper.ui.page

import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.multiwallpaper.MainViewModel
import dev.kichan.multiwallpaper.ui.Route
import dev.kichan.multiwallpaper.ui.component.WallpaperImage
import dev.kichan.multiwallpaper.ui.theme.MultiWallpaperTheme
import dev.kichan.multiwallpaper.ui.theme.buttonColor

@Composable
fun AddPage(
    navController: NavController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        if(imageUri != null && it == null)
            return@rememberLauncherForActivityResult

        imageUri = it
    }

    val onSaveClick = {
        viewModel.saveWallpaper(
            uri = imageUri!!,
            scale = 1.0f,
            offsetX = 0f,
            offsetY = 0f
        ) {
            Toast.makeText(context, "저장 완료", Toast.LENGTH_LONG).show()

            navController.popBackStack()
        }
    }

    LaunchedEffect(Unit) {
        launcher.launch("image/*")
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            val bitmap = if (imageUri != null) MediaStore.Images.Media.getBitmap(
                context.contentResolver,
                imageUri
            ) else null

            WallpaperImage(image = bitmap, onClick = { launcher.launch("image/*") })

            Button(
                onClick = {
//                    viewModel.wallpaperUri.value = imageUri
//                    navController.navigate(Route.Crop.name)
                    onSaveClick()
                },
                colors = buttonColor,
                modifier = Modifier.fillMaxWidth(),
                enabled = imageUri != null
            ) {
                Text(text = "저장")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddPagePreview() {
    MultiWallpaperTheme {
        AddPage(
            navController = rememberNavController(),
            viewModel = MainViewModel(LocalContext.current as Application)
        )
    }
}
