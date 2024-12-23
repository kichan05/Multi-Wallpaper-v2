package dev.kichan.multiwallpaper.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dev.kichan.multiwallpaper.ui.theme.ColorPalette
import dev.kichan.multiwallpaper.ui.theme.ColorPalette.Gray7
import dev.kichan.multiwallpaper.ui.theme.ColorPalette.Gray8
import dev.kichan.multiwallpaper.ui.theme.MultiWallpaperTheme

@Composable
fun LoadingDialog(modifier: Modifier = Modifier) {
    Dialog(onDismissRequest = { }, ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = modifier
                .background(ColorPalette.Gray0, shape = MaterialTheme.shapes.medium)
                .fillMaxWidth()
                .padding(vertical = 60.dp)
        ) {
            LoadingAnimation(circleColor = ColorPalette.HeechanBlue)
            Text(text = "잠시만 기다려 주세요", fontWeight = FontWeight.W600, fontSize = 20.sp, color = Gray8)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingDialogPreview() {
    MultiWallpaperTheme {
        LoadingDialog()
    }
}