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
        //todo: CropRect 계산식 작성

//                val imageWidth = bitmap.width.toFloat()
//                val imageHeight = bitmap.height.toFloat()
//
//                val boxWidth = boxSize.width
//                val boxHeight = boxSize.width
//                val cropLeft = ((0f - offsetX) / scale).coerceIn(0f, imageWidth)
//                val cropTop = ((0f - offsetY) / scale).coerceIn(0f, imageHeight)
//                val cropRight = ((boxWidth.toFloat() - offsetX) / scale).coerceIn(0f, imageWidth)
//                val cropBottom = ((boxHeight.toFloat() - offsetY) / scale).coerceIn(0f, imageHeight)
//                Log.d("TAG", "sacle : $scale")
//                Log.d("TAG", "offsetX : $offsetX")
//                Log.d("TAG", "offsetY : $offsetY")
//                Log.d("TAG", "boxWidth : ${boxSize.width}")
//                Log.d("TAG", "boxHeight : ${boxSize.height}")
//                Log.d("TAG", "imageWidth : $imageWidth, imageHeight : $imageHeight, ")
//                Log.d("TAG", "cropLeft: $cropLeft")
//                Log.d("TAG", "cropRight: $cropRight")
//                Log.d("TAG", "cropBottom: $cropBottom")
//                Log.d("TAG", "cropBottom: $cropBottom")
        return Rect(0, 0, 0, 0)
    }
}