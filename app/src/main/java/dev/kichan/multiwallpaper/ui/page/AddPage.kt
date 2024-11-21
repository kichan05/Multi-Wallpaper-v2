package dev.kichan.multiwallpaper.ui.page

import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.multiwallpaper.MainViewModel
import dev.kichan.multiwallpaper.ui.Route
import dev.kichan.multiwallpaper.ui.theme.MultiWallpaperTheme

@Composable
fun AddPage(
    navController: NavController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
    }

    Scaffold {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Button(onClick = {
                launcher.launch("image/*")
            }) {
                Text(text = "이미지 선택")
            }

            Button(onClick = {
//                viewModel.saveWallpaper(imageUri!!) {
//                    Toast.makeText(context, "저장 완료", Toast.LENGTH_SHORT).show()
//                }
                viewModel.wallpaperUri.value = imageUri
                navController.navigate(Route.Crop.name)
            }) {
                Text(text = "다음")
            }

            if(imageUri != null) {
                val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
                Image(bitmap = bitmap.asImageBitmap(), contentDescription = null)
            }


//            if(imageUri != null) {
//                val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .aspectRatio(getScreenAspectRatio(context))
//                        .background(Color.Gray)
//                        .pointerInput(Unit) {
//                            detectTransformGestures { _, _, zoom, _ ->
//
//                            }
//                        }
//                ) {
//                    Image(bitmap = bitmap.asImageBitmap(), contentDescription = null)
//                }
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddPagePreview() {
    MultiWallpaperTheme {  AddPage(
            navController = rememberNavController(),
            viewModel = MainViewModel(LocalContext.current as Application)
        )
    }
}
