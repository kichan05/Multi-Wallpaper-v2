package dev.kichan.multiwallpaper.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallpaper")
data class Wallpaper(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val path : String,
    @ColumnInfo(name = "time_stamp") val timeStamp : String,
)