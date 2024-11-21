package dev.kichan.multiwallpaper.ui.component

import android.app.WallpaperManager
import android.graphics.Rect
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kichan.multiwallpaper.model.Wallpaper
import dev.kichan.multiwallpaper.ui.theme.MultiWallpaperTheme

@Composable
fun WallpaperItem(
    modifier: Modifier = Modifier,
    wallpaper: Wallpaper,
) {
    val context = LocalContext.current
    val wallpaperManager = WallpaperManager.getInstance(context)

    Column(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .aspectRatio(9f / 16f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            bitmap = wallpaper.getBitmap().asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            wallpaperManager.setBitmap(
                wallpaper.getBitmap(),
                Rect(0, 0, 1920, 1080),
                false,
            )
            Toast.makeText(context, "적용 완료", Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "배경화면 적용")
        }
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
            )
        )
    }
}