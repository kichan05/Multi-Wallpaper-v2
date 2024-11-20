package dev.kichan.multiwallpaper

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import dev.kichan.multiwallpaper.model.Wallpaper
import dev.kichan.multiwallpaper.model.WallpaperDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.UUID

class MainViewModel(application: Application) : AndroidViewModel(application) {
    //todo: DI 적용.. 언젠가는 내가 하겠지
    private val wallpaperDB by lazy { WallpaperDatabase.getDB(getApplication()).wallpaperDao() }

    val wallpaperUri = MutableLiveData<Uri>()

    val wallpapersList = MutableLiveData<List<Wallpaper>>()

    fun saveWallpaper(uri: Uri, onSuccess: () -> Unit) {
        val filePath = FileUtil.saveImage(getApplication(), uri, UUID.randomUUID().toString())
        val timeStamp = LocalDateTime.now().toString()

        val wallpaper = Wallpaper(path = filePath, timeStamp = timeStamp)

        viewModelScope.launch(Dispatchers.IO) {
            wallpaperDB.insertWallpaper(wallpaper)

            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    }

    fun getWallpaper() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = wallpaperDB.getAll()
            withContext(Dispatchers.Main) {
                wallpapersList.value = res
            }
        }
    }
}