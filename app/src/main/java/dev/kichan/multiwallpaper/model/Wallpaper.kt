package dev.kichan.multiwallpaper.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import androidx.compose.ui.MotionDurationScale
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
    @ColumnInfo("crop_scale") val cropScale: Float,
    @ColumnInfo("crop_offset_x") val cropOffsetX : Float,
    @ColumnInfo("crop_offset_y") val cropOffsetY : Float,
) {
    fun getBitmap() : Bitmap {
        return BitmapFactory.decodeFile(path)
    }

    fun getCropRect() : Rect {
        return Rect(0, 0, 0, 0)
    }
}