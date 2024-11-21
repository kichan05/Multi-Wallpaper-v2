package dev.kichan.multiwallpaper.ui.component

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun WallpaperImage(modifier: Modifier = Modifier, image : Bitmap) {
    val imageShape = RoundedCornerShape(34.dp)

    Image(
        modifier = modifier
            .aspectRatio(9f / 16f)
            .fillMaxWidth(1f)
            .clip(imageShape)
            .border(width = 2.dp, color = Color(0xffd3d3d3), shape = imageShape)
        ,
        bitmap = image.asImageBitmap(),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )

}