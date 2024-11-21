package dev.kichan.multiwallpaper.ui.page

import android.app.WallpaperManager
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.kichan.multiwallpaper.ui.theme.MultiWallpaperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeLocalBottomSheet(onSelected : (Int) -> Unit, onDissmiss : () -> Unit) {
    ModalBottomSheet(onDismissRequest = { onDissmiss() }) {
        Button(onClick = { onSelected(WallpaperManager.FLAG_SYSTEM) }) {
            Text(text = "홈")
        }

        Button(onClick = { onSelected(WallpaperManager.FLAG_LOCK) }) {
            Text(text = "잠금")
        }

        Button(onClick = { onSelected(-1) }) {
            Text(text = "둘다")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeLocalBottomSheetPreview() {
    MultiWallpaperTheme {
        HomeLocalBottomSheet({}, {})
    }
}
