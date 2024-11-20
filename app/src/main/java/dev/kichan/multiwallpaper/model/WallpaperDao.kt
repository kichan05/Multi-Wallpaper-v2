package dev.kichan.multiwallpaper.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WallpaperDao {
    @Query("SELECT * FROM wallpaper")
    fun getAll() : List<Wallpaper>

    @Insert
    fun insertWallpaper(wallpaper: Wallpaper)
}