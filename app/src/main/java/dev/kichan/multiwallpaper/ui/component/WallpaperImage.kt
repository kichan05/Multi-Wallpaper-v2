package dev.kichan.multiwallpaper.ui.component

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.kichan.multiwallpaper.ui.theme.ColorPalette

val imageShape = RoundedCornerShape(34.dp)

val shapeModifier = Modifier
    .aspectRatio(9f / 16f)
    .fillMaxWidth(1f)
    .clip(imageShape)
    .border(width = 2.dp, color = Color(0xffd3d3d3), shape = imageShape)

@Composable
fun WallpaperImage(
    modifier: Modifier = Modifier,
    image: Bitmap?,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    val combineModifier = modifier
        .then(shapeModifier)
        .pointerInput(Unit) {
            detectTapGestures(
                onTap = { onClick() },
                onLongPress = { onLongClick() }
            )
        }

    if (image != null) {
        Image(
            modifier = combineModifier,
            bitmap = image.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = combineModifier.background(ColorPalette.Gray6),
        )
    }
}