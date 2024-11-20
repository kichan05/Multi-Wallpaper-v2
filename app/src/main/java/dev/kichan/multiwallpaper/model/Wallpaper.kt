package dev.kichan.multiwallpaper.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallpaper")
data class Wallpaper(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val path : String,
    @ColumnInfo(name = "time_stamp") val timeStamp : String,
) {
    fun getBitmap() : Bitmap {
        return BitmapFactory.decodeFile(path)
    }
}