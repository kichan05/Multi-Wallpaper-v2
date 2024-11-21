package dev.kichan.multiwallpaper.ui.component

import android.app.WallpaperManager
import android.graphics.Rect
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kichan.multiwallpaper.model.Wallpaper
import dev.kichan.multiwallpaper.ui.theme.MultiWallpaperTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WallpaperItem(
    modifier: Modifier = Modifier,
    wallpaper: Wallpaper,
    onDeleteClick : () -> Unit
) {
    val imageShape = RoundedCornerShape(34.dp)
    var isDeleteMode by rememberSaveable { mutableStateOf(false) }
    val deleteModelOffsetY by animateDpAsState(targetValue = if (isDeleteMode) (-300).dp else 0.dp)
    val wallpaperImage by remember { mutableStateOf(wallpaper.getBitmap().asImageBitmap()) }

    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            IconButton(
                onClick = { onDeleteClick() },
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.Red,
                )
            }

            IconButton(
                onClick = { isDeleteMode = false },
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                )
            }
        }
        Image(
            modifier = Modifier
                .graphicsLayer {
                    translationY = deleteModelOffsetY.value
                }
                .aspectRatio(9f / 16f)
                .fillMaxWidth(1f)
                .clip(imageShape)
                .border(width = 2.dp, color = Color(0xffd3d3d3), shape = imageShape)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = { isDeleteMode = !isDeleteMode }
                    )
                }
            ,
            bitmap = wallpaperImage,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WallpaperItemPreview() {
    MultiWallpaperTheme {
        WallpaperItem(
            modifier = Modifier,
            wallpaper = Wallpaper(
                id = 0,
                path = "/data/user/0/dev.kichan.multiwallpaper/files/7c2f0fc1-da62-4263-a9d7-9820141912bf",
                timeStamp = "2024-11-20T18:34:17.010240",
                cropScale = 0f,
                cropOffsetX = 0f,
                cropOffsetY = 0f,
            ),
            onDeleteClick = {}
        )
    }
}