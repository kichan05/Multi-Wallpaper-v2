package dev.kichan.multiwallpaper.ui.component

import android.content.Context
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kichan.multiwallpaper.model.Wallpaper
import dev.kichan.multiwallpaper.ui.theme.ColorPalette
import dev.kichan.multiwallpaper.ui.theme.MultiWallpaperTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WallpaperItem(
    modifier: Modifier = Modifier,
    wallpaper: Wallpaper,
    onDeleteClick: () -> Unit
) {
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    var isDeleteMode by remember { mutableStateOf(false) }
    var isMoveMode by remember { mutableStateOf(false) }

    val deleteModelOffsetY by animateDpAsState(targetValue = if (isDeleteMode) (-70).dp else 0.dp)
    val wallpaperImage by remember { mutableStateOf(wallpaper.getBitmap()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            IconButton(
                onClick = { onDeleteClick() },
                colors = IconButtonColors(
                    containerColor = ColorPalette.Red1,
                    contentColor = ColorPalette.Red6,
                    disabledContainerColor = ColorPalette.Gray3,
                    disabledContentColor = ColorPalette.Gray6
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                )
            }

            IconButton(
                onClick = { isDeleteMode = false },
                colors = IconButtonColors(
                    containerColor = ColorPalette.Indigo1,
                    contentColor = ColorPalette.Indigo6,
                    disabledContainerColor = ColorPalette.Gray3,
                    disabledContentColor = ColorPalette.Gray6
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                )
            }
        }

        WallpaperImage(
            modifier = Modifier
                .offset(y = deleteModelOffsetY),
            image = wallpaperImage,
            onClick = {
                vibrator.vibrate(50)
                isDeleteMode = false
            },
            onLongClick = {
                vibrator.vibrate(50)
                isDeleteMode = true
                isMoveMode = true
            }
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