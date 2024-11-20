package dev.kichan.multiwallpaper

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import dev.kichan.multiwallpaper.model.Wallpaper
import dev.kichan.multiwallpaper.model.WallpaperDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.UUID

class MainViewModel(application: Application) : AndroidViewModel(application) {
        private val wallpaperDB by lazy { WallpaperDatabase.getDB(getApplication()).wallpaperDao() }

    fun saveWallpaper(uri: Uri, onSuccess: () -> Unit) {
        val filePath = FileUtil.saveImage(getApplication(), uri, UUID.randomUUID().toString())
        val timeStamp = LocalDateTime.now().toString()

        val wallpaper = Wallpaper(path = filePath, timeStamp = timeStamp)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                wallpaperDB.insertWallpaper(wallpaper)
            }
            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    }
}