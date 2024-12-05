package dev.kichan.multiwallpaper.ui.component

import android.app.WallpaperManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kichan.multiwallpaper.ui.theme.MultiWallpaperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeLocalBottomSheet(onSelected: (Int) -> Unit, onDismiss: () -> Unit) {
    ModalBottomSheet(onDismissRequest = { onDismiss() }) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ChoiceField(
                icon = Icons.Default.Home,
                name = "홈 화면"
            ) { onSelected(WallpaperManager.FLAG_SYSTEM) }
            ChoiceField(
                icon = Icons.Default.Lock,
                name = "잠금 화면"
            ) { onSelected(WallpaperManager.FLAG_LOCK) }
            ChoiceField(icon = Icons.Default.Menu, name = "홈 화면 + 잠금 화면") {
                onSelected(-1)
            }
        }
    }
}

@Composable
fun ChoiceField(
    icon: ImageVector,
    name: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = name)
    }
}

@Preview(showBackground = true)
@Composable
fun ChoiceFiPreview() {
    MultiWallpaperTheme {
        ChoiceField(icon = Icons.Default.Home, "홈 화면") {}
    }
}


@Preview(showBackground = true)
@Composable
fun HomeLocalBottomSheetPreview() {
    MultiWallpaperTheme {
        HomeLocalBottomSheet({}, {})
    }
}
