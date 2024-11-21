package dev.kichan.multiwallpaper.ui.page

import android.app.Application
import android.content.Context
import android.provider.MediaStore
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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.multiwallpaper.MainViewModel
import dev.kichan.multiwallpaper.ui.Route
import dev.kichan.multiwallpaper.ui.component.WallpaperImage
import dev.kichan.multiwallpaper.ui.theme.MultiWallpaperTheme

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

    val onSaveClick = {
        viewModel.saveWallpaper(
            uri = imageUri!!,
            scale = scale,
            offsetX = offsetX,
            offsetY = offsetY
        ) {
            Toast.makeText(context, "저장 완료", Toast.LENGTH_LONG).show()

            navController.navigate(Route.Main.name)
        }
    }

    //todo: WallpaperImage 컴포넌트랑 sexy하게 통합할 수 있는 방법을 생각해보자
    val imageShape = RoundedCornerShape(34.dp)
    val shapeModifier = Modifier
        .aspectRatio(9f / 16f)
        .fillMaxWidth(1f)
        .clip(imageShape)
        .background(color = Color.Gray, shape = imageShape)
        .border(width = 2.dp, color = Color(0xffd3d3d3), shape = imageShape)
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

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 32.dp, vertical = 20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text(text = "이미지 크기를 조절해주세요. (사실 아직 안됨)")

            Box(
                modifier = shapeModifier
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
                        )
                )
            }
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