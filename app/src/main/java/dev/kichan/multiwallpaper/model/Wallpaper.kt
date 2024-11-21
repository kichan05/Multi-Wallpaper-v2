package dev.kichan.multiwallpaper.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
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
    @ColumnInfo(name = "crop_left") val cropLeft : Int,
    @ColumnInfo(name = "crop_top") val cropTop : Int,
    @ColumnInfo(name = "crop_right") val cropRight : Int,
    @ColumnInfo(name = "crop_bottom") val cropBottom : Int,
) {
    fun getBitmap() : Bitmap {
        return BitmapFactory.decodeFile(path)
    }

    fun getCropRect() : Rect {
        return Rect(cropLeft, cropTop, cropRight, cropBottom)
    }
}