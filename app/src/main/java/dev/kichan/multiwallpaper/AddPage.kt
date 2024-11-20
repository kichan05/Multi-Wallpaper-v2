package dev.kichan.multiwallpaper

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
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
import dev.kichan.multiwallpaper.ui.theme.MultiWallpaperTheme

@Composable
fun AddPage(
    navController: NavController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var imagePath by rememberSaveable { mutableStateOf<String?>(null) }
    var imageBitmap by rememberSaveable { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
    }

    val fileName = "a.jpg"

    Scaffold {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text(text = context.filesDir.toString())

            Button(onClick = {
                launcher.launch("image/*")
            }) {
                Text(text = "이미지 선택")
            }

            Button(onClick = {
                viewModel.saveWallpaper(imageUri!!) {
                    Toast.makeText(context, "저장 완료", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(text = "이미지 저장")
            }

            Button(onClick = {
                imageBitmap = BitmapFactory.decodeFile("${context.filesDir}/$fileName")
            }) {
                Text(text = "이미지 가져오기")
            }

            Text(text = "이미지 URI : $imageUri")
            Text(text = "저장된 이미지 경로 : $imagePath")
            if(imageBitmap != null) {
                Image(bitmap = imageBitmap!!.asImageBitmap(), contentDescription = null)
            }
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
