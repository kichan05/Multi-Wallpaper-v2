package dev.kichan.multiwallpaper

import android.app.Application
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Rect
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.multiwallpaper.ui.theme.MultiWallpaperTheme
import kotlin.math.roundToInt

@Composable
fun CropPage(
    navController: NavController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val imageUri by viewModel.wallpaperUri.observeAsState()
    val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)

    fun getScreenAspectRatio(context: Context): Float {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.widthPixels.toFloat() / displayMetrics.heightPixels.toFloat()
    }

    var scale by rememberSaveable { mutableStateOf(1f) }
    var offsetX by rememberSaveable { mutableStateOf(0f) }
    var offsetY by rememberSaveable { mutableStateOf(0f) }
    var boxSize by remember { mutableStateOf(IntSize.Zero) }

    Scaffold {
        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(getScreenAspectRatio(context))
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(Color.Gray)
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            scale *= zoom
                            offsetX += pan.x
                            offsetY += pan.y
                        }
                    }
                    .clipToBounds()
                    .onSizeChanged {
                        boxSize = it
                    }
            ) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offsetX,
                            translationY = offsetY,
//                            transformOrigin = TransformOrigin(0f, 0f)
                        )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = {
//                val imageWidth = bitmap.width.toFloat()
//                val imageHeight = bitmap.height.toFloat()
//
//                val boxWidth = boxSize.width
//                val boxHeight = boxSize.width
//                val cropLeft = ((0f - offsetX) / scale).coerceIn(0f, imageWidth)
//                val cropTop = ((0f - offsetY) / scale).coerceIn(0f, imageHeight)
//                val cropRight = ((boxWidth.toFloat() - offsetX) / scale).coerceIn(0f, imageWidth)
//                val cropBottom = ((boxHeight.toFloat() - offsetY) / scale).coerceIn(0f, imageHeight)
//                Log.d("TAG", "sacle : $scale")
//                Log.d("TAG", "offsetX : $offsetX")
//                Log.d("TAG", "offsetY : $offsetY")
//                Log.d("TAG", "boxWidth : ${boxSize.width}")
//                Log.d("TAG", "boxHeight : ${boxSize.height}")
//                Log.d("TAG", "imageWidth : $imageWidth, imageHeight : $imageHeight, ")
//                Log.d("TAG", "cropLeft: $cropLeft")
//                Log.d("TAG", "cropRight: $cropRight")
//                Log.d("TAG", "cropBottom: $cropBottom")
//                Log.d("TAG", "cropBottom: $cropBottom")
                viewModel.saveWallpaper(
                    uri = imageUri!!,
                    scale = scale,
                    offsetX = offsetX,
                    offsetY = offsetY
                ) {
                    Toast.makeText(context, "저장 완료", Toast.LENGTH_LONG).show()

                    navController.navigate(Route.Main.name)
                }
            }) {
                Text(text = "저장")
            }
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