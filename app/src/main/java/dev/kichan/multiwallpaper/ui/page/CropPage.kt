package dev.kichan.multiwallpaper.ui.page

import android.app.Application
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.multiwallpaper.MainViewModel
import dev.kichan.multiwallpaper.common.pxToDp
import dev.kichan.multiwallpaper.ui.Route
import dev.kichan.multiwallpaper.ui.component.WallpaperImage
import dev.kichan.multiwallpaper.ui.component.imageShape
import dev.kichan.multiwallpaper.ui.component.shapeModifier
import dev.kichan.multiwallpaper.ui.theme.MultiWallpaperTheme

@Composable
fun CropPage(
    navController: NavController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val density = LocalDensity.current.density
    val imageUri by viewModel.wallpaperUri.observeAsState()
    val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)

    fun getScreenAspectRatio(context: Context): Float {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.widthPixels.toFloat() / displayMetrics.heightPixels.toFloat()
    }

//    var boxSize by remember { mutableStateOf(IntSize.Zero) }
    var scale by rememberSaveable { mutableStateOf<Float?>(null) }
    var offsetX by rememberSaveable { mutableStateOf(0f) }
    var offsetY by rememberSaveable { mutableStateOf(0f) }

    val onSaveClick = {
        Log.d("TAG", scale.toString())
//        viewModel.saveWallpaper(
//            uri = imageUri!!,
//            scale = scale,
//            offsetX = offsetX,
//            offsetY = offsetY
//        ) {
//            Toast.makeText(context, "저장 완료", Toast.LENGTH_LONG).show()
//
//            navController.navigate(Route.Main.name)
//        }
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 32.dp, vertical = 20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(getScreenAspectRatio(context))
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Red)
                    .fillMaxWidth()
                    .graphicsLayer(
                        scaleX = scale ?: 1.0f,
                        scaleY = scale ?: 1.0f,
                        translationX = offsetX,
                        translationY = offsetY,
                    )
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            scale = (scale ?: 1f) * zoom
                            offsetX += pan.x
                            offsetY += pan.y
                        }
                    }
                    .onGloballyPositioned  { l ->
                        if (scale == null) {
                            val width = l.size.width.toFloat() / density
                            val height = l.size.height.toFloat() / density

                            val scaleWidth = width / bitmap.width
                            val scaleHeight = height / bitmap.height

                            scale = maxOf(scaleWidth, scaleHeight)

                            Log.d("SIZE", "Screen : $width, $height")
                            Log.d("SIZE", "Image ${bitmap.width}, ${bitmap.height}")
                            Log.d("SIZE", "SW : $scaleWidth")
                            Log.d("SIZE", "SH : $scaleHeight")
                            Log.d("SIZE", "Scale : $scale")
                        }
                    },
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { onSaveClick() },
                modifier = Modifier.fillMaxWidth()
            ) {
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